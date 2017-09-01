package com.ammbr.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ASSOCIATION")
public class Association {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ASSOCIATION_ID")
	private Integer associationId;

	@Column(nullable = false, name = "EMAIL",length=60)
	private String email;

	@Column(nullable = false, name = "PUBLIC_HASH" ,length=255)
	private String publicHash;

	
	@Column(nullable = false, name = "ETHERIUM_ADDRESS",length=255)
	private String etheriumAddress;

	@Column(nullable = false, name = "BITCOIN_ADDRESS" ,length=255)
	private String bitcoinAddress;


	@Column(name = "CREATION_DATE")
	private Date creationDate;

	public Integer getAssociationId() {
		return associationId;
	}

	public void setAssociationId(Integer associationId) {
		this.associationId = associationId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getPublicHash() {
		return publicHash;
	}

	public void setPublicHash(String publicHash) {
		this.publicHash = publicHash;
	}

	public String getEtheriumAddress() {
		return etheriumAddress;
	}

	public void setEtheriumAddress(String etheriumAddress) {
		this.etheriumAddress = etheriumAddress;
	}

	public String getBitcoinAddress() {
		return bitcoinAddress;
	}

	public void setBitcoinAddress(String bitcoinAddress) {
		this.bitcoinAddress = bitcoinAddress;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column( name = "MODIFIED_DATE")
	private Date modifiedDate;
	
	

	
}
