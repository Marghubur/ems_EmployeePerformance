package com.bot.performance.service;

import com.bot.performance.db.service.DbManager;
import com.bot.performance.db.utils.LowLevelExecution;
import com.bot.performance.model.*;
import com.bot.performance.repository.AppraisalDetailRepository;
import com.bot.performance.repository.PromotionAndHikeRepository;
import com.bot.performance.serviceinterface.IPromotionAndHikeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PromotionAndHikeService implements IPromotionAndHikeService {
    @Autowired
    PromotionAndHikeRepository promotionAndHikeRepository;
    @Autowired
    AppraisalDetailRepository appraisalDetailRepository;

    @Autowired
    DbManager dbManager;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    CurrentSession currentUserDetail;
    @Autowired
    LowLevelExecution lowLevelExecution;

    @Transactional
    public List<AppraisalReviewDetail> addPromotionAndHike(List<AppraisalReviewDetail> appraisalReviewDetails) throws Exception {
        java.util.Date utilDate = new java.util.Date();
        long appraisalReviewId = dbManager.nextLongPrimaryKey(AppraisalReviewDetail.class) - 1;
        var activeAppraisalDetails = appraisalDetailRepository.getActiveAppraisalDetailRepository();
        if (activeAppraisalDetails == null)
            throw new Exception("Appraisal detail not found");

        List<AppraisalReviewFinalizerStatus> appraisalReviewFinalizer = new ArrayList<>();
        int AppraisalFinalizer = dbManager.nextIntPrimaryKey(AppraisalReviewFinalizerStatus.class) - 1;

        for (var promotionDetail : appraisalReviewDetails) {
            if (promotionDetail.getEmployeeId() == 0)
                throw new Exception("Invalid employee selected");

            appraisalReviewId = appraisalReviewId + 1;
            var employeeSalaryDetail = dbManager.getById(promotionDetail.getEmployeeId(), EmployeeSalaryDetail.class);
            promotionDetail.setAppraisalDetailId(activeAppraisalDetails.getAppraisalDetailId());
            validateHikeDetail(promotionDetail, employeeSalaryDetail);
             promotionDetail.setAppraisalCycleStartDate(activeAppraisalDetails.getAppraisalCycleStartDate());
            promotionDetail.setAppraisalReviewId(appraisalReviewId);
            promotionDetail.setPreviousSalary(employeeSalaryDetail.getCTC());

            //var result = appraisalDetailRepository.getApprovalChainRepository(promotionDetail.getEmployeeId());
            var data = getAppraisalLevel(promotionDetail.getEmployeeId());
            long finalAppraisalReviewId = appraisalReviewId;
            int i = 0;
            while (i < data.size()) {
                var employee = appraisalDetailRepository.getEmployeeByRoleIdRepository(data.get(i).getRoleId(), promotionDetail.getEmployeeId());
                AppraisalFinalizer = AppraisalFinalizer + 1;
                var reviewerDetail = new AppraisalReviewFinalizerStatus();
                reviewerDetail.setAppraisalFinalizer(AppraisalFinalizer);
                reviewerDetail.setAppraisalReviewId(finalAppraisalReviewId);
                reviewerDetail.setReviwerId(employee.getEmployeeUid());
                reviewerDetail.setEmail(employee.getEmail());
                reviewerDetail.setFullName(employee.getName());
                reviewerDetail.setActionRequired(true);
                if (i + 1 == 1) {
                    reviewerDetail.setStatus(9);
                    reviewerDetail.setReactedOn(utilDate);
                } else if (i + 1 == 2) {
                    reviewerDetail.setStatus(2);
                } else {
                    reviewerDetail.setStatus(0);
                }

                reviewerDetail.setApprovalLevel(i + 1);
                appraisalReviewFinalizer.add(reviewerDetail);
                i++;
            }
        }
        dbManager.saveAll(appraisalReviewDetails, AppraisalReviewDetail.class);
        dbManager.saveAll(appraisalReviewFinalizer, AppraisalReviewFinalizerStatus.class);
        return appraisalReviewDetails;
    }

    private List<ChainDetail> getAppraisalLevel(long employeeId) throws Exception {
        var objectCategory = appraisalDetailRepository.getObjectiveCategoryByEmpIdRepository(employeeId);
        List<AppraisalLevel> appraisalLevels = objectMapper.readValue(objectCategory.getApprovalWorkflow(), new TypeReference<List<AppraisalLevel>>() {});
        var appraisalLevel = appraisalLevels.stream().filter(x -> x.getRoleId() == objectCategory.getRoleId()).toList();
        var chainDetail = appraisalLevels.get(0).getChain().stream().filter(ChainDetail::getIsActive).collect(Collectors.toList());
        return  chainDetail;
    }

    private void validateHikeDetail(AppraisalReviewDetail appraisalReviewDetail, EmployeeSalaryDetail employeeSalaryDetail) throws Exception {
        BigDecimal proposedHikeAmount = (employeeSalaryDetail.getCTC().multiply(appraisalReviewDetail.getHikePercentage()))
                .divide(new BigDecimal(100));
        proposedHikeAmount = proposedHikeAmount.setScale(2, BigDecimal.ROUND_HALF_EVEN);

        if (!proposedHikeAmount.equals(appraisalReviewDetail.getHikeAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN)))
            throw new Exception("Proposed hike amount calculation mismatched");

        if (appraisalReviewDetail.getEstimatedSalary().subtract(employeeSalaryDetail.getCTC()).signum() < 0)
            throw new Exception("Expected CTC is invalid");

        if (appraisalReviewDetail.getHikePercentage().signum() < 0)
            throw new Exception("Hike percentage is invalid");

        if (appraisalReviewDetail.getHikeAmount().signum() < 0)
            throw new Exception("Hike amount is invalid");

        if (appraisalReviewDetail.getEmployeeId() <= 0)
            throw new Exception("Employee detail not found");
    }

    public List<AppraisalReviewDetailDTO> getPromotionAndHikeService(long employeeId) throws Exception {
        if (employeeId <= 0)
            throw new Exception("Invalid employee id");

        return promotionAndHikeRepository.getPromotionAndHikeRepository(employeeId);
    }

    public List<TeamMemberAndAppraisalFinalizer> getApprovePromotionAndHikeService() throws Exception {
        return promotionAndHikeRepository.getApprovePromotionAndHikeRepository();
    }

    public String reOpenAppraisalObjectiveService(Long userId, List<Integer> reviewIds) throws Exception {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid employee id passed");
        }

        if (reviewIds == null || reviewIds.isEmpty()) {
            throw new IllegalArgumentException("Appraisal review ids are not value");
        }

        String status = "fail";
        try {
            String ids = objectMapper.writeValueAsString(reviewIds);

            Map<String, Object> result = lowLevelExecution.executeProcedure("sp_appraisal_review_detail_reopen_review", List.of(
                    new DbParameters("_EmployeeId", userId, Types.BIGINT),
                    new DbParameters("_AppraisalReviewIds", ids, Types.VARCHAR)
            ));

            if (result == null) {
                throw new RuntimeException("Fail to update the record(s)");
            }

            if (result.containsKey("_ProcessingResult")) {
                status = result.get("_ProcessingResult").toString();
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return status;
    }

    public String reOpenEmployeeObjectiveService(Long employeeId, int appraisalDetailId) throws Exception {
        if (employeeId <= 0)
            throw new IllegalArgumentException("Invalid employee id passed");

        if (appraisalDetailId <= 0)
            throw new IllegalArgumentException("Invalid appraisal detail id passed");

        String status = "fail";
        try {
            Map<String, Object> result = lowLevelExecution.executeProcedure("sp_employee_performance_reopen", List.of(
                    new DbParameters("_EmployeeId", employeeId, Types.BIGINT),
                    new DbParameters("_AppraisalDetailId", appraisalDetailId, Types.INTEGER)
            ));

            if (result == null)
                throw new RuntimeException("Fail to update the record(s)");

            if (result.containsKey("_ProcessingResult"))
                status = result.get("_ProcessingResult").toString();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return status;
    }
    @Transactional
    public List<AppraisalReviewFinalizerStatus> approveAppraisalReviewDetailService(List<AppraisalReviewDetailDTO> appraisalReviewDetailDTOS) throws Exception {
        return manageAppraisalReviewDetailService(appraisalReviewDetailDTOS, ApplicationConstant.Approved);

    }
    @Transactional
    public List<AppraisalReviewFinalizerStatus> rejectAppraisalReviewDetailService(List<AppraisalReviewDetailDTO> appraisalReviewDetailDTOS) throws Exception {
        return manageAppraisalReviewDetailService(appraisalReviewDetailDTOS, ApplicationConstant.Rejected);
    }


    private List<AppraisalReviewFinalizerStatus> manageAppraisalReviewDetailService(List<AppraisalReviewDetailDTO> appraisalReviewDetailDTOS, int status) throws Exception {
        java.util.Date utilDate = new java.util.Date();
        List<AppraisalReviewFinalizerStatus> appraisalReviewFinalizers = new ArrayList<>();

        for (var appraisalDetail : appraisalReviewDetailDTOS) {
            if (appraisalDetail.getEmployeeId() == 0)
                throw new Exception("Invalid employee selected");

            if (appraisalDetail.getAppraisalReviewId() == 0)
                throw new Exception("Invalid appraisal");

            var result = appraisalDetailRepository.getAppraisalFinalizerReviewRepository(appraisalDetail.getAppraisalReviewId());
            result = objectMapper.convertValue(result, new TypeReference<List<AppraisalReviewFinalizerStatus>>() {});
            var finalizerReview = result.stream().filter(x -> x.getReviwerId() == currentUserDetail.getUserDetail().getUserId()).toList();
            if (finalizerReview.get(0).getStatus() == ApplicationConstant.NotSubmitted ||
                finalizerReview.get(0).getStatus() == ApplicationConstant.Pending) {
                finalizerReview.get(0).setStatus(status);
                finalizerReview.get(0).setReactedOn(utilDate);
            }

            var nextFinalizerReview = result.stream().filter(x -> x.getApprovalLevel() == finalizerReview.get(0).getApprovalLevel()+1).toList();
            if (nextFinalizerReview.size() > 0 && nextFinalizerReview.get(0).getStatus() == ApplicationConstant.NotSubmitted) {
                nextFinalizerReview.get(0).setStatus(ApplicationConstant.Pending);
            }
            appraisalReviewFinalizers.addAll(result);
        }
        dbManager.saveAll(appraisalReviewFinalizers, AppraisalReviewFinalizerStatus.class);
        return appraisalReviewFinalizers;
    }
}
