package com.example.demo21032501.dao;

import com.example.demo21032501.model.LmsLog;
import com.example.demo21032501.model.LmsLogForCount;
import com.example.demo21032501.model.SimpleServicer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface LmsLogRepository extends PagingAndSortingRepository<LmsLog, Integer> {

    Page<LmsLog> findAll(Pageable pageable);

    Page<LmsLog> findAllBySimpleServicer_Callback(String callback, Pageable pageable);
    Page<LmsLog> findAllBySimpleServicer(SimpleServicer simpleServicer, Pageable pageable);
    Page<LmsLog> findAllBySavedDateBetween(Date startDate, Date endDate, Pageable pageable);
    Page<LmsLog> findAllBySimpleServicer_CallbackAndSavedDateBetween(String callback, Date startDate, Date endDate, Pageable pageable);
    Page<LmsLog> findAllBySimpleServicerAndSavedDateBetween(SimpleServicer simpleServicer, Date startDate, Date endDate, Pageable pageable);

    @Query(value = "SELECT l.simpleServicer as servicer, YEAR(l.savedDate) as logyear , MONTH(l.savedDate) as logmonth, DAY(l.savedDate) as logday, SUM(l.totalSentCnt) as logtotal, COUNT(l) as logcase from LmsLog l group by YEAR(l.savedDate) , MONTH(l.savedDate), DAY(l.savedDate), simpleServicer")
    Page<LmsLogForCount> findDailyLmsLogForCount(Pageable pageable);

    @Query(value = "SELECT l.simpleServicer as servicer, YEAR(l.savedDate) as logyear , MONTH(l.savedDate) as logmonth, 1 as logday, SUM(l.totalSentCnt) as logtotal, COUNT(l) as logcase from LmsLog l group by YEAR(l.savedDate), MONTH(l.savedDate), simpleServicer")
    Page<LmsLogForCount> findMonthlyLmsLogForCount(Pageable pageable);


    // org.springframework.data.repository.query.Param;
    // use condition with a where clause for sake having clause does not work in this query
    @Query(value = "SELECT l.simpleServicer as servicer, YEAR(l.savedDate) as logyear , MONTH(l.savedDate) as logmonth, 1 as logday, SUM(l.totalSentCnt) as logtotal, COUNT(l) as logcase from LmsLog l where  l.simpleServicer.callback =:simplesss group by YEAR(l.savedDate) , MONTH(l.savedDate), l.simpleServicer ")
    Page<LmsLogForCount> findMonthlyLmsLogForCountByServicer(@Param("simplesss") String callback, Pageable pageable);

    @Query(value = "SELECT l.simpleServicer as servicer, YEAR(l.savedDate) as logyear , MONTH(l.savedDate) as logmonth, DAY(l.savedDate) as logday, SUM(l.totalSentCnt) as logtotal, COUNT(l) as logcase from LmsLog l where  l.simpleServicer.callback =:simplesss group by YEAR(l.savedDate) , MONTH(l.savedDate), DAY(l.savedDate), l.simpleServicer ")
    Page<LmsLogForCount> findDailyLmsLogForCountByServicer(@Param("simplesss") String callback, Pageable pageable);


    @Query(value = "SELECT l.simpleServicer as servicer, YEAR(l.savedDate) as logyear , MONTH(l.savedDate) as logmonth, DAY(l.savedDate) as logday, SUM(l.totalSentCnt) as logtotal, COUNT(l) as logcase from LmsLog l where  l.simpleServicer =:simplesss group by YEAR(l.savedDate) , MONTH(l.savedDate), DAY(l.savedDate), l.simpleServicer ")
    Page<LmsLogForCount> findDailyLmsLogForCountByServicerBothServicer_CallbackAndServicer_ServicerNameNeedToBeMatched(@Param("simplesss") SimpleServicer simpleServicer, Pageable pageable);

    @Query(value = "SELECT l.simpleServicer as servicer, YEAR(l.savedDate) as logyear , MONTH(l.savedDate) as logmonth, 1 as logday, SUM(l.totalSentCnt) as logtotal, COUNT(l) as logcase from LmsLog l where  l.simpleServicer.callback =:simplesss group by YEAR(l.savedDate) , MONTH(l.savedDate), l.simpleServicer ")
    Page<LmsLogForCount> findMonthlyLmsLogForCountByServicerBothServicer_CallbackAndServicer_ServicerNameNeedToBeMatched(@Param("simplesss") String callback, Pageable pageable);



    @Query(value = "SELECT l.simpleServicer as servicer, YEAR(l.savedDate) as logyear , MONTH(l.savedDate) as logmonth, DAY(l.savedDate) as logday, SUM(l.totalSentCnt) as logtotal, COUNT(l) as logcase from LmsLog l where  l.savedDate BETWEEN :startD AND :endD group by YEAR(l.savedDate) , MONTH(l.savedDate), DAY(l.savedDate), l.simpleServicer ")
    Page<LmsLogForCount> findDailyLmsLogForCountByPeriod(@Param("startD") Date startDate, @Param("endD") Date endDate, Pageable pageable);

    @Query(value = "SELECT l.simpleServicer as servicer, YEAR(l.savedDate) as logyear , MONTH(l.savedDate) as logmonth, 1 as logday, SUM(l.totalSentCnt) as logtotal, COUNT(l) as logcase from LmsLog l where l.savedDate BETWEEN :startD AND :endD group by YEAR(l.savedDate) , MONTH(l.savedDate), l.simpleServicer ")
    Page<LmsLogForCount> findMonthlyLmsLogForCountByPeriod(@Param("startD") Date startDate, @Param("endD") Date endDate, Pageable pageable);

    @Query(value = "SELECT l.simpleServicer as servicer, YEAR(l.savedDate) as logyear , MONTH(l.savedDate) as logmonth, DAY(l.savedDate) as logday, SUM(l.totalSentCnt) as logtotal, COUNT(l) as logcase from LmsLog l where  l.simpleServicer.callback =:simplesss and l.savedDate BETWEEN :startD AND :endD group by YEAR(l.savedDate) , MONTH(l.savedDate), DAY(l.savedDate), l.simpleServicer ")
    Page<LmsLogForCount> findDailyLmsLogForCountByServicerAndPeriod(@Param("simplesss") String callback, @Param("startD") Date startDate, @Param("endD") Date endDate, Pageable pageable);

    @Query(value = "SELECT l.simpleServicer as servicer, YEAR(l.savedDate) as logyear , MONTH(l.savedDate) as logmonth, 1 as logday, SUM(l.totalSentCnt) as logtotal, COUNT(l) as logcase from LmsLog l where  l.simpleServicer.callback =:simplesss and l.savedDate BETWEEN :startD AND :endD group by YEAR(l.savedDate) , MONTH(l.savedDate), l.simpleServicer ")
    Page<LmsLogForCount> findMonthlyLmsLogForCountByServicerAndPeriod(@Param("simplesss") String callback, @Param("startD") Date startDate, @Param("endD") Date endDate, Pageable pageable);


}
