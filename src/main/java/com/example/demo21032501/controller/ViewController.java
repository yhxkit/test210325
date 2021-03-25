package com.example.demo21032501.controller;

import com.example.demo21032501.service.LmsLogService;
import com.example.demo21032501.service.LmsServicerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@Controller
public class ViewController {

    @Value("${com.estgames.lmstool.url}")
    private String url;

    @Value("${com.estgames.lmstool.eum}")
    private String eum;

    private LmsServicerService lmsServicerService;
    private LmsLogService lmsLogService;

    public ViewController(LmsServicerService lmsServicerService, LmsLogService lmsLogService) {
        this.lmsServicerService = lmsServicerService;
        this.lmsLogService = lmsLogService;
    }


    @GetMapping("/")
    public String toMainPage(Model model, HttpServletRequest req) {

        if (req.getParameter("session") == null || req.getParameter("access") == null) {
            return "redirect:" + eum + "/sign-in?url=" + url;
        }
        model.addAttribute("tail", "?session=" + req.getParameter("session") + "&access=" + req.getParameter("access"));
        model.addAttribute("servicerList", lmsServicerService.getAllServicerList());
        return "main_content";
    }


    @GetMapping("/service")
    public String toServiceManagingPage(Model model, HttpServletRequest req) {
        if (req.getParameter("session") == null || req.getParameter("access") == null) {
            return "redirect:" + eum + "/sign-in?url=" + url;
        }
        model.addAttribute("tail", "?session=" + req.getParameter("session") + "&access=" + req.getParameter("access"));
        model.addAttribute("servicerList", lmsServicerService.getAllServicerList());
        return "service_management_content";
    }


    @GetMapping("/history")
    public String toHistoryPage(Model model, HttpServletRequest req,
                                @RequestParam(defaultValue = "5") int pagerPerview,
                                @RequestParam(defaultValue = "20") int elementsNumberPerOnePage,
                                @RequestParam(defaultValue = "1") int requiredPage,
                                @RequestParam(defaultValue = "entire") String callback,
                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate
    ) {

        if (req.getParameter("session") == null || req.getParameter("access") == null) {
            return "redirect:" + eum + "/sign-in?url=" + url;
        }

        model.addAttribute("tail", "?session=" + req.getParameter("session") + "&access=" + req.getParameter("access"));
        model.addAttribute("servicerList", lmsServicerService.getAllServicerList());

        lmsLogService.historyPageModelHandler(model, startDate, endDate, callback, pagerPerview, requiredPage, elementsNumberPerOnePage);

        return "history_content";
    }


    @GetMapping("/history/log")
    public String toHistoryCountPage(Model model, HttpServletRequest req,
                                     @RequestParam(defaultValue = "5") int pagerPerview,
                                     @RequestParam(defaultValue = "20") int elementsNumberPerOnePage,
                                     @RequestParam(defaultValue = "1") int requiredPage,
                                     @RequestParam(defaultValue = "entire") String callback,
                                     @RequestParam(defaultValue = "MONTH") String dailyOrMonthly,
                                     @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                     @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate
    ) {

        if (req.getParameter("session") == null || req.getParameter("access") == null) {
            return "redirect:" + eum + "/sign-in?url=" + url;
        }

        model.addAttribute("tail", "?session=" + req.getParameter("session") + "&access=" + req.getParameter("access"));
        model.addAttribute("servicerList", lmsServicerService.getAllServicerList());

        lmsLogService.historyCountPageModelHandler(model, startDate, endDate, dailyOrMonthly, callback, pagerPerview, requiredPage, elementsNumberPerOnePage);

        return "history_count_content";
    }


}
