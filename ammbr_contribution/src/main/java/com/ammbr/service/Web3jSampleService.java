package com.ammbr.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;

public class Web3jSampleService {

	 @Autowired
	    private Web3j web3j;
	 
	    public String getClientVersion() throws IOException {
	        Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
	        return web3ClientVersion.getWeb3ClientVersion();
	    }
	    
	    
}
