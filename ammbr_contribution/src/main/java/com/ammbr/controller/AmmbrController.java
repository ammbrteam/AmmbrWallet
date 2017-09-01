package com.ammbr.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.abi.datatypes.Address;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.ammbr.dto.AssociationDto;
import com.ammbr.dto.TokenPerEthDto;
import com.ammbr.model.Association;
import com.ammbr.service.AmmbrService;
import com.ammbr.service.AssociationService;

@RestController
public class AmmbrController {

	@Autowired
	private Web3j web3j;
	
	@Value("${contract.binary}")
	private String contractBinary;
	
	@Value("${private.key}")
	private String privKey;
	
	@Value("${contract.address}")
	private String contractAddress;

	@Value("${gas.price}")
	private BigInteger gasPrice;
	
	@Value("${gas.limit}")
	private BigInteger gasLimit;
	
	@Value("${start.block}")
	private BigDecimal startBlock;
	
	@Value("${end.block}")
	private BigDecimal endBlock;
	
	@Value("${token.per.etherium}")
	private Integer tokenPerEtherium;
	
	@Value("${token.per.bitcoin}")
	private Integer tokenPerBitcoin;
	
	@Value("${token.per.bank}")
	private Integer tokenPerBank;
	
	private static AmmbrService ammbrService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AmmbrController.class);
	@Autowired
	AssociationService associationService;

	@RequestMapping("/hello")
	public String sayHello() {
		LOGGER.debug("Calling sayHello method");
		return "hi";

	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/addAssociation")
	public AssociationDto addAssociation(@RequestBody AssociationDto associationDto) {
		LOGGER.debug("Calling addAssociationDto method");
		associationService.saveAssociation(associationDto);
		return associationDto;
	}

	@RequestMapping("/getAssociation")
	public AssociationDto getAssociation(@RequestParam("emailId") String emailId) {
		LOGGER.debug("Calling getAssociationDto method");
		Association association = associationService.getAssociation(emailId);
		if (association!=null) {
			AssociationDto associationDto = new AssociationDto();
			associationDto.setBitcoinAddress(association.getBitcoinAddress());
			associationDto.setEmail(association.getEmail());
			associationDto.setEtheriumAddress(association.getEtheriumAddress());
			associationDto.setPublicHash(association.getPublicHash());
			return associationDto;
		}
		return null;
	}
	
	
	
	@RequestMapping("/getTokenPerEth")
	public TokenPerEthDto getTokenPerEth() {
		LOGGER.debug("Calling getAssociationDto method");
			TokenPerEthDto tokenPerEthDto=new TokenPerEthDto();
			ammbrService = AmmbrService.getAmmbrServiceInstance(web3j,contractBinary,privKey,contractAddress,gasPrice,gasLimit);
			int bonus = ammbrService.calculateBonus(startBlock,endBlock);
			LOGGER.info("bonus: "+bonus);			
			tokenPerEthDto.setBonus(bonus);
			
			tokenPerEthDto.setTokenPerBitcoin(tokenPerBitcoin);
			tokenPerEthDto.setTokenPerEtherium(tokenPerEtherium);
			tokenPerEthDto.setTokenPerBank(tokenPerBank);
			
			
		return tokenPerEthDto;
	}

	@RequestMapping("/getBonus")
	public Future<TransactionReceipt> getBonus() throws IOException{
		ammbrService = AmmbrService.getAmmbrServiceInstance(web3j,contractBinary,privKey,contractAddress,gasPrice,gasLimit);
		Address add = new Address("0x41181C122716fFE8eCF127639D7e57456973AAd0");
		return ammbrService.getBonus(add);
	}
	
	@RequestMapping("/getTokenPerEther")
	public Future<TransactionReceipt> getTokenPerEther(){
		ammbrService = AmmbrService.getAmmbrServiceInstance(web3j,contractBinary,privKey,contractAddress,gasPrice,gasLimit);
		Address add = new Address("0x41181C122716fFE8eCF127639D7e57456973AAd0");
		return ammbrService.getTokenPerEther(add);
	}
	
	@RequestMapping("/getTokenPerBankWire")
	public BigInteger getTokenPerBankWire() throws InterruptedException, ExecutionException{
		ammbrService = AmmbrService.getAmmbrServiceInstance(web3j,contractBinary,privKey,contractAddress,gasPrice,gasLimit);
		Address add = new Address("0x41181C122716fFE8eCF127639D7e57456973AAd0");
		return ammbrService.getTokenPerBankWire(add).get().getValue();
	}
	
	
}
