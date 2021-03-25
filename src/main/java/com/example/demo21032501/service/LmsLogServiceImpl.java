package com.example.demo21032501.service;

import com.example.demo21032501.dao.LmsLogRepository;
import com.example.demo21032501.dao.LmsServicerRepository;
import com.example.demo21032501.model.LmsLog;
import com.example.demo21032501.model.LmsLogForCount;
import com.example.demo21032501.model.LmsServicer;
import com.example.demo21032501.model.SimpleServicer;
import com.example.demo21032501.service.util.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@Service
public class LmsLogServiceImpl implements LmsLogService {

    private LmsServicerRepository lmsServicerRepository;
    private LmsLogRepository lmsLogRepository;
    private Pagination pagination;

    public LmsLogServiceImpl(LmsLogRepository lmsLogRepository, Pagination pagination, LmsServicerRepository lmsServicerRepository) {
        this.lmsLogRepository = lmsLogRepository;
        this.pagination = pagination;
        this.lmsServicerRepository = lmsServicerRepository;
    }


    @Override
    public Page<LmsLog> getLmsLogsByPage(int requiredPage, int elementsNumberPerOnePage) {
        PageRequest pageRequest = getPageRequestByLmsLogIdx(requiredPage, elementsNumberPerOnePage);
        return lmsLogRepository.findAll(pageRequest);
    }


    @Override
    public Page<LmsLog> findLmsLogsBySimpleServicer(int requiredPage, int elementsNumberPerPage, SimpleServicer simpleServicer) {
        PageRequest pageRequest = getPageRequestByLmsLogIdx(requiredPage, elementsNumberPerPage);
        return lmsLogRepository.findAllBySimpleServicer_Callback(simpleServicer.getCallback(), pageRequest);
    }

    @Override
    public Page<LmsLog> findLmsLogsBySavedDate(int requiredPage, int elementsNumberPerPage, Date startDate, Date endDate) {
        PageRequest pageRequest = getPageRequestByLmsLogIdx(requiredPage, elementsNumberPerPage);
        return lmsLogRepository.findAllBySavedDateBetween(startDate, endDate, pageRequest);
    }

    @Override
    public Page<LmsLog> findLmsLogsBySavedDateAndSimpleServicer(int requiredPage, int elementsNumberPerPage, Date startDate, Date endDate, SimpleServicer simpleServicer) {
        PageRequest pageRequest = getPageRequestByLmsLogIdx(requiredPage, elementsNumberPerPage);
        return lmsLogRepository.findAllBySimpleServicer_CallbackAndSavedDateBetween(simpleServicer.getCallback(), startDate, endDate, pageRequest);
    }


    @Override
    public void historyPageModelHandler(Model model, Date startDate, Date endDate, String callback, int pagerPerview, int requiredPage, int elementsNumberPerOnePage) {

        LmsServicer servicer = lmsServicerRepository.findBySimpleServicerCallback(callback);
        Page<LmsLog> pagedSearchBydateLmsLog;

        if (startDate != null && endDate != null) {

            SimpleDateFormat dateFormat = getSimpleDateFormat();

            if (servicer == null) { //서비스 콜백 값이 전체 검색 entire 거나 검색해서 값이 안나올 경우에는 그냥 전체 검색
                pagedSearchBydateLmsLog = this.findLmsLogsBySavedDate(requiredPage, elementsNumberPerOnePage, startDate, getNewlySetEndDate(endDate));
                model.addAttribute("searchTail", "&startDate=" + dateFormat.format(startDate) + "&endDate=" + dateFormat.format(endDate));
            } else {
                pagedSearchBydateLmsLog = this.findLmsLogsBySavedDateAndSimpleServicer(requiredPage, elementsNumberPerOnePage, startDate, getNewlySetEndDate(endDate), servicer.getSimpleServicer());
                model.addAttribute("searchTail", "&startDate=" + dateFormat.format(startDate) + "&endDate=" + dateFormat.format(endDate) + "&callback=" + callback);
            }

        } else { //In case that search period is not set
            if (servicer == null) {
                pagedSearchBydateLmsLog = this.getLmsLogsByPage(requiredPage, elementsNumberPerOnePage);
                model.addAttribute("searchTail", "");
            } else {
                pagedSearchBydateLmsLog = this.findLmsLogsBySimpleServicer(requiredPage, elementsNumberPerOnePage, servicer.getSimpleServicer());
                model.addAttribute("searchTail", "&callback=" + callback);
            }
        }
        model.addAttribute("logList", pagedSearchBydateLmsLog);
        model.addAttribute("pagerData", pagination.pagers(requiredPage, pagerPerview, pagedSearchBydateLmsLog.getTotalPages()));
    }


    @Override
    public Page<LmsLogForCount> getDailyLmsLogForCount(int requiredPage, int elementNumPerPage) {
        Page<LmsLogForCount> daily = lmsLogRepository.findDailyLmsLogForCount(pagination.elementsByPage(requiredPage, elementNumPerPage, getLmsLogForCountDailySort()));
        return daily;
    }

    @Override
    public Page<LmsLogForCount> getMonthlyLmsLogForCount(int requiredPage, int elementNumPerPage) {
        Page<LmsLogForCount> monthly = lmsLogRepository.findMonthlyLmsLogForCount(pagination.elementsByPage(requiredPage, elementNumPerPage, getLmsLogForCountMonthlySort()));
        return monthly;
    }

    @Override
    public Page<LmsLogForCount> findDailyLmsLogForCountByServicer(int requiredPage, int elementNumPerPage, SimpleServicer simpleServicer) {
        Pageable pageable = pagination.elementsByPage(requiredPage, elementNumPerPage, getLmsLogForCountDailySort());
        Page<LmsLogForCount> searchedLogs = lmsLogRepository.findDailyLmsLogForCountByServicer(simpleServicer.getCallback(), pageable);
        return searchedLogs;
    }

    @Override
    public Page<LmsLogForCount> findMonthlyLmsLogForCountByServicer(int requiredPage, int elementNumPerPage, SimpleServicer simpleServicer) {
        Pageable pageable = pagination.elementsByPage(requiredPage, elementNumPerPage, getLmsLogForCountMonthlySort());
        Page<LmsLogForCount> searchedLogs = lmsLogRepository.findMonthlyLmsLogForCountByServicer(simpleServicer.getCallback(), pageable);
        return searchedLogs;
    }

    @Override
    public Page<LmsLogForCount> findDailyLmsLogForCountByPeriod(int requiredPage, int elementNumPerPage, Date startDate, Date endDate) {
        Pageable pageable = pagination.elementsByPage(requiredPage, elementNumPerPage, getLmsLogForCountDailySort());
        Page<LmsLogForCount> searchedLogs = lmsLogRepository.findDailyLmsLogForCountByPeriod(startDate, endDate, pageable);
        return searchedLogs;

    }

    @Override
    public Page<LmsLogForCount> findMonthlyLmsLogForCountByPeriod(int requiredPage, int elementNumPerPage, Date startDate, Date endDate) {
        Pageable pageable = pagination.elementsByPage(requiredPage, elementNumPerPage, getLmsLogForCountMonthlySort());
        Page<LmsLogForCount> searchedLogs = lmsLogRepository.findMonthlyLmsLogForCountByPeriod(startDate, endDate, pageable);
        return searchedLogs;
    }

    @Override
    public Page<LmsLogForCount> findDailyLmsLogForCountByServicerAndPeriod(int requiredPage, int elementNumPerPage, SimpleServicer simpleServicer, Date startDate, Date endDate) {
        Pageable pageable = pagination.elementsByPage(requiredPage, elementNumPerPage, getLmsLogForCountDailySort());
        Page<LmsLogForCount> searchedLogs = lmsLogRepository.findDailyLmsLogForCountByServicerAndPeriod(simpleServicer.getCallback(), startDate, endDate, pageable);
        return searchedLogs;
    }

    @Override
    public Page<LmsLogForCount> findMonthlyLmsLogForCountByServicerAndPeriod(int requiredPage, int elementNumPerPage, SimpleServicer simpleServicer, Date startDate, Date endDate) {
        Pageable pageable = pagination.elementsByPage(requiredPage, elementNumPerPage, getLmsLogForCountMonthlySort());
        Page<LmsLogForCount> searchedLogs = lmsLogRepository.findMonthlyLmsLogForCountByServicerAndPeriod(simpleServicer.getCallback(), startDate, endDate, pageable);
        return searchedLogs;
    }

    @Override
    public void historyCountPageModelHandler(Model model, Date startDate, Date endDate, String dailyOrMonthly, String callback, int pagerPerview, int requiredPage, int elementsNumberPerOnePage) {
        LmsServicer servicer = lmsServicerRepository.findBySimpleServicerCallback(callback);
        Page<LmsLogForCount> countLog = null;

        if (startDate != null && endDate != null) {
            SimpleDateFormat dateFormat = getSimpleDateFormat();

            if (dailyOrMonthly.equals("DAY")) {
                if (servicer == null) {
                    //날짜:O /서비서:X /일별
                    countLog = this.findDailyLmsLogForCountByPeriod(requiredPage, elementsNumberPerOnePage, startDate, endDate);
                    model.addAttribute("searchTail", "&dailyOrMonthly=DAY&startDate="+dateFormat.format(startDate)+"&endDate="+dateFormat.format(endDate));
                } else {
                    //날짜:O /서비서:O /일별
                    countLog = this.findDailyLmsLogForCountByServicerAndPeriod(requiredPage, elementsNumberPerOnePage, servicer.getSimpleServicer(), startDate, endDate);
                    model.addAttribute("searchTail", "&dailyOrMonthly=DAY&callback=" + callback+"&startDate="+dateFormat.format(startDate)+"&endDate="+dateFormat.format(endDate));
                }
            } else if (dailyOrMonthly.equals("MONTH")) {
                if (servicer == null) {
                    //날짜:O /서비서:X /월별
                    countLog = this.findMonthlyLmsLogForCountByPeriod(requiredPage, elementsNumberPerOnePage, startDate, endDate);
                    model.addAttribute("searchTail", "&dailyOrMonthly=MONTH&startDate="+dateFormat.format(startDate)+"&endDate="+dateFormat.format(endDate));
                } else {
                    //날짜:O /서비서:O /월별
                    countLog = this.findMonthlyLmsLogForCountByServicerAndPeriod(requiredPage, elementsNumberPerOnePage, servicer.getSimpleServicer(), startDate, endDate);
                    model.addAttribute("searchTail", "&dailyOrMonthly=MONTH&callback=" + callback+"&startDate="+dateFormat.format(startDate)+"&endDate="+dateFormat.format(endDate));
                }
            }

        }else{ //전체 기간에서 검색
            if (dailyOrMonthly.equals("DAY")) {
                if (servicer == null) {
                    countLog = this.getDailyLmsLogForCount(requiredPage, elementsNumberPerOnePage);
                    model.addAttribute("searchTail", "&dailyOrMonthly=DAY");
                } else {
                    countLog = this.findDailyLmsLogForCountByServicer(requiredPage, elementsNumberPerOnePage, servicer.getSimpleServicer());
                    model.addAttribute("searchTail", "&dailyOrMonthly=DAY&callback=" + callback);
                }
            } else if (dailyOrMonthly.equals("MONTH")) {
                if (servicer == null) {
                    countLog = this.getMonthlyLmsLogForCount(requiredPage, elementsNumberPerOnePage);
                    model.addAttribute("searchTail", "&dailyOrMonthly=MONTH");
                } else {
                    countLog = this.findMonthlyLmsLogForCountByServicer(requiredPage, elementsNumberPerOnePage, servicer.getSimpleServicer());
                    model.addAttribute("searchTail", "&dailyOrMonthly=MONTH&callback=" + callback);
                }
            }
        }

        model.addAttribute("countLogList", countLog);
        model.addAttribute("pagerData", pagination.pagers(requiredPage, pagerPerview, countLog.getTotalPages()));
    }


    private SimpleDateFormat getSimpleDateFormat(){
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    private Date getNewlySetEndDate(Date endDate){
        Calendar calendarForEndDateSetting = Calendar.getInstance();
        calendarForEndDateSetting.setTime(endDate);
        calendarForEndDateSetting.add(Calendar.DATE, 1);
        Date newlySetEndDate = calendarForEndDateSetting.getTime();
        return newlySetEndDate;
    }

    private Sort getLmsLogForCountMonthlySort(){
        Sort sort = pagination.createNewSort(pagination.createNewSortOrder("logyear"), pagination.createNewSortOrder("logmonth"));
        return sort;
    }

    private Sort getLmsLogForCountDailySort(){
        Sort sort = pagination.createNewSort(pagination.createNewSortOrder("logyear"), pagination.createNewSortOrder("logmonth"), pagination.createNewSortOrder("logday"));
        return sort;
    }
    private PageRequest getPageRequestByLmsLogIdx(int requiredPage, int elementsNumberPerOnePage){
        PageRequest pageRequest = pagination.elementsByPage(requiredPage, elementsNumberPerOnePage, "lmsLogIdx");
        return pageRequest;
    }


}

