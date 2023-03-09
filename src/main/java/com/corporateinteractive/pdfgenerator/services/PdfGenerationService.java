package com.corporateinteractive.pdfgenerator.services;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.media.MediaType;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfGenerationService {
    private static final Logger LOGGER = LogManager.getLogger(PdfGenerationService.class);

    public byte[] generatePdfFromUrl(String pdfUrl) throws IOException {

        String requestURL = pdfUrl;
        try {
            URI uri = new URI(pdfUrl);
            requestURL = uri.getScheme() + "://" +uri.getHost() + (StringUtils.isNotBlank(uri.getPort() + "") ? ":" + uri.getPort() : "");
        } catch (URISyntaxException e) {
            System.out.println("Invalid URL: " + e.getMessage());
            return null;
        }

        LOGGER.info("Setting PDF Document: " + pdfUrl);
        URL url = new URL(pdfUrl);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ConverterProperties properties = new ConverterProperties();
        properties.setBaseUri(requestURL);
        FontProvider fontProvider = new FontProvider();
        fontProvider.addDirectory("/Users/coreybaines/CITAUS/stores/QCLOUD/_fonts/unicode");
        fontProvider.addDirectory("/Users/coreybaines/CITAUS/stores/QCLOUD/_application_fonts");
        properties.setFontProvider(fontProvider);
        MediaDeviceDescription mediaDeviceDescription =
                new MediaDeviceDescription(MediaType.SCREEN);
        mediaDeviceDescription.setWidth(PageSize.A4.getWidth());
        properties.setMediaDeviceDescription(mediaDeviceDescription);
        HtmlConverter.convertToPdf(url.openStream(), outputStream, properties);
        return outputStream.toByteArray();
    }

    private static List<File> traverseFonts(File... folders)
    {
        List<File> fonts = new ArrayList<>();
        FileFilter foldersAndAcceptedFontFiles = new FileFilter()
        {
            @Override
            public boolean accept(File pathname)
            {
                if (pathname.isDirectory())
                {
                    return true;
                }
                return pathname.getName().toLowerCase().endsWith(".otf") || pathname.getName().toLowerCase().endsWith(".ttf");
            }
        };
        for (File folder : folders)
        {
            File[] listFiles = folder.listFiles(foldersAndAcceptedFontFiles);
            if (listFiles == null)
            {
                continue;
            }
            for (File file : listFiles)
            {
                if (file.isDirectory())
                {
                    fonts.addAll(traverseFonts(file));
                }
                else
                {
                    fonts.add(file);
                }
            }
        }

        return fonts;
    }
}