//package com.corporateinteractive.pdfgenerator.filters;
//
//import com.corporateinteractive.pdfgenerator.controllers.PdfGenerationController;
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.io.IOException;
//
//public class AuthFilter implements Filter {
//    private static final Logger LOGGER = LogManager.getLogger(AuthFilter.class);
//
//    @Autowired
//    private PdfGenerationController pdfGenerationController;
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        // Check if the request is mapped to your controller
//        if (request instanceof HttpServletRequest) {
//            HttpServletRequest httpRequest = (HttpServletRequest) request;
//            String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
//
//            if ("/my-controller-path".equals(path)) {
//                // If the request is mapped to your controller, call the controller method
////                myController.myControllerMethod();
//            }
//        }
//
//        // Pass the request down the filter chain
//        chain.doFilter(request, response);
//    }
//}