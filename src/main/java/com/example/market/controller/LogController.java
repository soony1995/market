//package com.example.market.controller;
//
//import com.example.market.component.Logger;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Controller
//@RequiredArgsConstructor
//public class LogController {
//
//    private final LogDemoService logDemoService;
//    private final Logger logger;
//
//    @RequestMapping("log-demo")
//    @ResponseBody
//    public String logDemo(HttpServletRequest request){
//        String requestURL = request.getRequestURI().toString();
//        logger.setRequestURL(requestURL);
//
//        logger.log("controller test");
//        logDemoService.logic("testId");
//    }
//}
