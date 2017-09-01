package com.ammbr.service;

import org.springframework.data.repository.CrudRepository;

import com.ammbr.model.Association;

public interface AssociationCrudRepository extends CrudRepository<Association, Integer> {

	
	public Association findByEmail(String emailId);
}
