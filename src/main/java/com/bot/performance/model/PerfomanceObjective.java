package com.bot.performance.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "performance_objective")
public class PerfomanceObjective {
    @Id
    @Column(name = "ObjectiveId")
    Long objectiveId;

    @Column(name = "Objective")
    String objective;

    @Column(name = "Description")
    String description;

    @Column(name = "TimeFrameStart")
    Date timeFrameStart;

    @Column(name = "TimeFrmaeEnd")
    Date timeFrmaeEnd;

    @Column(name = "ObjSeeType")
    boolean objSeeType;

    @Column(name = "ObjectiveType")
    String objectiveType;

    @Column(name = "Tag")
    String tag;

    @Column(name = "IsIncludeReview")
    boolean isIncludeReview;

    @Column(name = "ProgressMeassureType")
    int progressMeassureType;

    @Column(name = "StartValue")
    double startValue;

    @Column(name = "TargetValue")
    double targetValue;

    @Column(name = "CompanyId")
    int companyId;

    @Column(name = "UpdatedBy")
    Long updatedBy;

    @Column(name = "UpdatedOn")
    Date updatedOn;

    @Column(name = "CreatedBy")
    Long CreatedBy;

    @Column(name = "CreatedOn")
    Date createdOn;

    @Transient
    double currentValue;

    @Transient
    List<Integer> tagRole;

    @Transient
    int status;

    @Transient
    Long employeePerformanceId;

    @Transient
    List<PerformanceDetail> performanceDetail;

    public Long getObjectiveId() {
        return objectiveId;
    }

    public void setObjectiveId(Long objectiveId) {
        this.objectiveId = objectiveId;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimeFrameStart() {
        return timeFrameStart;
    }

    public void setTimeFrameStart(Date timeFrameStart) {
        this.timeFrameStart = timeFrameStart;
    }

    public Date getTimeFrmaeEnd() {
        return timeFrmaeEnd;
    }

    public void setTimeFrmaeEnd(Date timeFrmaeEnd) {
        this.timeFrmaeEnd = timeFrmaeEnd;
    }

    public boolean isObjSeeType() {
        return objSeeType;
    }

    public void setObjSeeType(boolean objSeeType) {
        this.objSeeType = objSeeType;
    }

    public String getObjectiveType() {
        return objectiveType;
    }

    public void setObjectiveType(String objectiveType) {
        this.objectiveType = objectiveType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isIncludeReview() {
        return isIncludeReview;
    }

    public void setIncludeReview(boolean includeReview) {
        isIncludeReview = includeReview;
    }

    public int getProgressMeassureType() {
        return progressMeassureType;
    }

    public void setProgressMeassureType(int progressMeassureType) {
        this.progressMeassureType = progressMeassureType;
    }

    public double getStartValue() {
        return startValue;
    }

    public void setStartValue(double startValue) {
        this.startValue = startValue;
    }

    public double getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(double targetValue) {
        this.targetValue = targetValue;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(Long createdBy) {
        CreatedBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public List<Integer> getTagRole() {
        return tagRole;
    }

    public void setTagRole(List<Integer> tagRole) {
        this.tagRole = tagRole;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getEmployeePerformanceId() {
        return employeePerformanceId;
    }

    public void setEmployeePerformanceId(Long employeePerformanceId) {
        this.employeePerformanceId = employeePerformanceId;
    }

    public List<PerformanceDetail> getPerformanceDetail() {
        return performanceDetail;
    }

    public void setPerformanceDetail(List<PerformanceDetail> performanceDetail) {
        this.performanceDetail = performanceDetail;
    }

    @Override
    public String toString() {
        return "PerfomanceObjective{" +
                "objectiveId=" + objectiveId +
                ", objective='" + objective + '\'' +
                ", description='" + description + '\'' +
                ", timeFrameStart=" + timeFrameStart +
                ", timeFrmaeEnd=" + timeFrmaeEnd +
                ", objSeeType=" + objSeeType +
                ", objectiveType='" + objectiveType + '\'' +
                ", tag='" + tag + '\'' +
                ", isIncludeReview=" + isIncludeReview +
                ", progressMeassureType=" + progressMeassureType +
                ", startValue=" + startValue +
                ", targetValue=" + targetValue +
                ", companyId=" + companyId +
                ", updatedBy=" + updatedBy +
                ", updatedOn=" + updatedOn +
                ", CreatedBy=" + CreatedBy +
                ", createdOn=" + createdOn +
                ", currentValue=" + currentValue +
                ", tagRole=" + tagRole +
                ", status=" + status +
                ", employeePerformanceId=" + employeePerformanceId +
                ", performanceDetail=" + performanceDetail +
                '}';
    }

    public PerfomanceObjective() {}
    public PerfomanceObjective(Long objectiveId, String objective, String description, Date timeFrameStart, Date timeFrmaeEnd, boolean objSeeType, String objectiveType, String tag, boolean isIncludeReview, int progressMeassureType, double startValue, double targetValue, int companyId, Long updatedBy, Date updatedOn, Long createdBy, Date createdOn, double currentValue, List<Integer> tagRole, int status, Long employeePerformanceId, List<PerformanceDetail> performanceDetail) {
        this.objectiveId = objectiveId;
        this.objective = objective;
        this.description = description;
        this.timeFrameStart = timeFrameStart;
        this.timeFrmaeEnd = timeFrmaeEnd;
        this.objSeeType = objSeeType;
        this.objectiveType = objectiveType;
        this.tag = tag;
        this.isIncludeReview = isIncludeReview;
        this.progressMeassureType = progressMeassureType;
        this.startValue = startValue;
        this.targetValue = targetValue;
        this.companyId = companyId;
        this.updatedBy = updatedBy;
        this.updatedOn = updatedOn;
        CreatedBy = createdBy;
        this.createdOn = createdOn;
        this.currentValue = currentValue;
        this.tagRole = tagRole;
        this.status = status;
        this.employeePerformanceId = employeePerformanceId;
        this.performanceDetail = performanceDetail;
    }
}