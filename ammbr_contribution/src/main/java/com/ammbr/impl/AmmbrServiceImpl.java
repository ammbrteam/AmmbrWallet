package com.ammbr.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

import javax.transaction.Transactional;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ammbr.dto.AssociationDto;
import com.ammbr.model.Association;
import com.ammbr.service.AssociationCrudRepository;
import com.ammbr.service.AssociationService;

@Service
public class AmmbrServiceImpl implements AssociationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AmmbrServiceImpl.class);

	private static final String ZIP = ".zip";
	private static final String UTF_ENCODING = "UTF-8";
	
	@Value("${file_path}")
	private String filePath;

	@Autowired
	AssociationCrudRepository associationCrudRepository;
	
	@Override
	public Association getAssociation(String emailId) {
		LOGGER.debug("Getting data for email id-"+emailId);
		Association association = associationCrudRepository.findByEmail(emailId);
		return association;
	}

	@Override
	@Transactional
	public Association saveAssociation(AssociationDto associationDto) {
		LOGGER.debug("Saving data for email id-"+associationDto.getEmail());
		Association association = new Association();
		association.setCreationDate(new Date());
		association.setBitcoinAddress(associationDto.getBitcoinAddress());
		association.setEmail(associationDto.getEmail());
		association.setEtheriumAddress(associationDto.getEtheriumAddress());
		association.setPublicHash(associationDto.getPublicHash());
		associationCrudRepository.save(association);
		return association;
	}

	@Override
	public ByteArrayOutputStream getAmmrWallet() throws Exception {

		// TODO: Get from properties file
		FileInputStream fileInputStream = new FileInputStream(new File(filePath));

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		IOUtils.copy(fileInputStream, byteArrayOutputStream);

		return byteArrayOutputStream;
	}
}
