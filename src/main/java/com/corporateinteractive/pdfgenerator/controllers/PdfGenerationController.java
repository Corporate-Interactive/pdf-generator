package com.corporateinteractive.pdfgenerator.controllers;

import com.corporateinteractive.pdfgenerator.services.PdfGenerationService;
import com.itextpdf.styledxmlparser.jsoup.internal.StringUtil;
import io.micrometer.common.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class PdfGenerationController {
    // private static final Logger LOGGER = LogManager.getLogger(PdfGenerationController.class);
    private final PdfGenerationService pdfGenerationService;

    public PdfGenerationController(PdfGenerationService pdfGenerationService) {
        this.pdfGenerationService = pdfGenerationService;
    }

    @GetMapping(value = "/generate-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePdf(@RequestParam(name="pdfURL", required=false) String pdfURL) throws IOException {
        if (StringUtils.isEmpty(pdfURL)) {
            pdfURL = "http://localhost:8000/application/pdf/pdf.jsp?email=emailforpdf&proposal=109068&v-key=7aafbd2e260355a675704bf6b0c286388791142b&userId=4806";
        }
        byte[] pdfBytes = pdfGenerationService.generatePdfFromUrl(pdfURL);
        return ResponseEntity.ok().body(pdfBytes);
    }

    @GetMapping(value = "/merge-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> mergePdf(@RequestParam(name="pdfURL", required=false) String pdfURL) throws IOException {
        byte[] pdfBytes = pdfGenerationService.generatePdfFromUrl(pdfURL);
        return ResponseEntity.ok().body(pdfBytes);
    }

    @GetMapping(value = "/split-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> splitPdf(@RequestParam(name="pdfURL", required=false) String pdfURL) throws IOException {
        byte[] pdfBytes = pdfGenerationService.generatePdfFromUrl(pdfURL);
        return ResponseEntity.ok().body(pdfBytes);
    }

}