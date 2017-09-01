package com.ammbr.service;

import java.io.ByteArrayOutputStream;

import com.ammbr.dto.AssociationDto;
import com.ammbr.model.Association;

public interface AssociationService {

	
	public Association getAssociation(String emailId);
	
	
	
	public Association saveAssociation(AssociationDto associationDto );
	
	public ByteArrayOutputStream getAmmrWallet() throws Exception;

}
