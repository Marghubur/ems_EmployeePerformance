package com.bot.performance.repository;

import com.bot.performance.db.service.DbManager;
import com.bot.performance.model.AppraisalDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AppraisalDetailRepository {
    @Autowired
    DbManager dbManager;

    public AppraisalDetail getActiveAppraisalDetailRepository() {
        return dbManager.queryRaw("select o.* from appraisal_detail o where o.IsActiveCycle = 1", AppraisalDetail.class);
    }
}