package com.corporateinteractive.pdfgenerator.services;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.media.MediaType;

@Service
public class PdfGenerationService {
	private static final Logger LOGGER = LogManager.getLogger(PdfGenerationService.class);

	private String getBaseUri(String pdfUrl) throws URISyntaxException {
		URI uri = new URI(pdfUrl);
		return uri.getScheme() + "://" + uri.getHost() + (uri.getPort() > 0 ? ":" + uri.getPort() : "");
	}

	public void generatePdfFromUrl(String pdfUrl, OutputStream os) throws IOException, URISyntaxException {

		String storeDir = "/stores/QCLOUD";
		LOGGER.info("Setting PDF Document: " + pdfUrl);
		URL url = new URL(pdfUrl);
		ConverterProperties properties = new ConverterProperties();
		properties.setBaseUri(getBaseUri(pdfUrl));
		FontProvider fontProvider = new FontProvider();

		List<File> fontFiles = traverseFonts(new File(storeDir, "_fonts/unicode"),
				new File(storeDir, "_application_fonts"));
		for (File fontFile : fontFiles) {
			fontProvider.addFont(fontFile.getAbsolutePath());
		}
		properties.setFontProvider(fontProvider);
		MediaDeviceDescription mediaDeviceDescription = new MediaDeviceDescription(MediaType.SCREEN);
		// mediaDeviceDescription.setWidth(PageSize.A4.getWidth());
		properties.setMediaDeviceDescription(mediaDeviceDescription);
		HtmlConverter.convertToPdf(url.openStream(), os, properties);

	}

	private static List<File> traverseFonts(File... folders) {
		List<File> fonts = new ArrayList<>();
		FileFilter foldersAndAcceptedFontFiles = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if (pathname.isDirectory()) {
					return true;
				}
				return pathname.getName().toLowerCase().endsWith(".otf")
						|| pathname.getName().toLowerCase().endsWith(".ttf");
			}
		};
		for (File folder : folders) {
			File[] listFiles = folder.listFiles(foldersAndAcceptedFontFiles);
			if (listFiles == null) {
				continue;
			}
			for (File file : listFiles) {
				if (file.isDirectory()) {
					fonts.addAll(traverseFonts(file));
				} else {
					fonts.add(file);
				}
			}
		}

		return fonts;
	}

	public static void main(String[] args) throws IOException, URISyntaxException {
		String url = "http://localhost:8080/application/pdf/pdf.jsp?email=emailforpdf&proposal=11564&v-key=2d83fe6c6c65a2220e74b7b488c672ff46d543f3&userId=38";
		OutputStream os = new FileOutputStream(new File("/temp/pdf-110319-7-" + RandomStringUtils.randomAlphabetic(3) + ".pdf"));
		new PdfGenerationService().generatePdfFromUrl(url,
				os );
		os.close();
		LOGGER.info("done");
	}

}