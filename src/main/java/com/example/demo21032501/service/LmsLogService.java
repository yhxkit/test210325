package com.example.demo21032501.service;

import com.example.demo21032501.model.LmsLog;
import com.example.demo21032501.model.LmsLogForCount;
import com.example.demo21032501.model.SimpleServicer;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.util.Date;

public interface LmsLogService {
    Page<LmsLog> getLmsLogsByPage(int requiredPage, int elementsNumberPerOnePage);

    Page<LmsLog> findLmsLogsBySimpleServicer(int requiredPage, int elementsNumberPerPage, SimpleServicer simpleServicer);
    Page<LmsLog> findLmsLogsBySavedDate(int requiredPage, int elementsNumberPerPage, Date startDate, Date endDate);
    Page<LmsLog> findLmsLogsBySavedDateAndSimpleServicer(int requiredPage, int elementsNumberPerPage, Date startDate, Date endDate, SimpleServicer simpleServicer);

    Page<LmsLogForCount> getDailyLmsLogForCount(int requiredPage, int elementNumPerPage);
    Page<LmsLogForCount> getMonthlyLmsLogForCount(int requiredPage, int elementNumPerPage);

    Page<LmsLogForCount> findDailyLmsLogForCountByServicer(int requiredPage, int elementNumPerPage, SimpleServicer simpleServicer);
    Page<LmsLogForCount> findMonthlyLmsLogForCountByServicer(int requiredPage, int elementNumPerPage, SimpleServicer simpleServicer);

    Page<LmsLogForCount> findDailyLmsLogForCountByPeriod(int requiredPage, int elementNumPerPage, Date startDate, Date endDate);
    Page<LmsLogForCount> findMonthlyLmsLogForCountByPeriod(int requiredPage, int elementNumPerPage, Date startDate, Date endDate);

    Page<LmsLogForCount> findDailyLmsLogForCountByServicerAndPeriod(int requiredPage, int elementNumPerPage, SimpleServicer simpleServicer, Date startDate, Date endDate);
    Page<LmsLogForCount> findMonthlyLmsLogForCountByServicerAndPeriod(int requiredPage, int elementNumPerPage, SimpleServicer simpleServicer, Date startDate, Date endDate);


    void historyPageModelHandler(Model model, Date startDate, Date endDate, String callback, int pagerPerview, int requiredPage, int elementsNumberPerOnePage);
    void historyCountPageModelHandler(Model model, Date startDate, Date endDate, String dayOrMonth, String callback, int pagerPerview, int requiredPage, int elementsNumberPerOnePage);

}
