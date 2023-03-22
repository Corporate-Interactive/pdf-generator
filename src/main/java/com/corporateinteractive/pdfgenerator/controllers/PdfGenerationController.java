package com.corporateinteractive.pdfgenerator.controllers;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.corporateinteractive.pdfgenerator.services.PdfGenerationService;
import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class PdfGenerationController {
    // private static final Logger LOGGER = LogManager.getLogger(PdfGenerationController.class);
    private final PdfGenerationService pdfGenerationService;

    public PdfGenerationController(PdfGenerationService pdfGenerationService) {
        this.pdfGenerationService = pdfGenerationService;
    }

    @GetMapping(value = "/generate-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public void generatePdf(@RequestParam(name="pdfURL", required=false) String pdfURL, HttpServletResponse response) throws IOException, URISyntaxException {
//        	pdfUrl = "http://localhost:8080/application/pdf/pdf.jsp?email=emailforpdf&proposal=11564&v-key=2d83fe6c6c65a2220e74b7b488c672ff46d543f3&userId=38";
        pdfGenerationService.generatePdfFromUrl(pdfURL, response.getOutputStream());
    }

}