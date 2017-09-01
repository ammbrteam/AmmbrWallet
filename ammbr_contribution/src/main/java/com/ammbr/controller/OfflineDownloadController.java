package com.ammbr.controller;

import java.io.ByteArrayOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ammbr.service.AssociationService;

@RestController
public class OfflineDownloadController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OfflineDownloadController.class);

	private static final String CONTENT_TYPE = "application/zip";
	private static final String HEADER_KEY = "Content-disposition";
	private static final String HEADER_VALUE = "attachment;filename=";

	/* private static final String AUTH_TOKEN_PARAM = "authToken"; */
	private static String DOWNLOAD_FILE_NAME = "ammbr_wallet";

	private static ServletOutputStream servletOutputStream;

	@Autowired
	AssociationService associationService;

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void processHttpRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// String authToken = request.getParameter(AUTH_TOKEN_PARAM);
		LOGGER.info("Request received to download offline wallet");

		try {

			DOWNLOAD_FILE_NAME = "AmmbrWallet.zip";
			LOGGER.info("File will be downloaded with name : "
					+ DOWNLOAD_FILE_NAME);

			response.setContentType(CONTENT_TYPE);
			response.addHeader(HEADER_KEY, HEADER_VALUE + DOWNLOAD_FILE_NAME);

			ByteArrayOutputStream outputStream = associationService
					.getAmmrWallet();
			servletOutputStream = response.getOutputStream();
			servletOutputStream.write(outputStream.toByteArray());

			outputStream.flush();
			outputStream.close();

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (Exception exp) {

			String message = exp.getLocalizedMessage();
			String stackTrace = "";

			if (message != null)
				stackTrace = ExceptionUtils.getStackTrace(exp);
			else
				stackTrace = "Exception occured while processing file download request :\n"
						+ ExceptionUtils.getStackTrace(exp);

			LOGGER.error("Exception occurred while processing file download request.\n"
					+ stackTrace);
			throw new Exception(stackTrace);
		}
	}
}
