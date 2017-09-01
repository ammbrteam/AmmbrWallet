package com.ammbr.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;

import rx.Observable;

public class AmmbrService extends Contract{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AmmbrService.class);

	private static AmmbrService ammbrServiceInstance;	
		
	private AmmbrService(String contractBinary, String contractAddress,
			Web3j web3j, Credentials credentials, BigInteger gasPrice,
			BigInteger gasLimit) {
		super(contractBinary, contractAddress, web3j, credentials, gasPrice, gasLimit);
	}

	public static AmmbrService getAmmbrServiceInstance(Web3j web3j,String contractBinary,String privKey,String contractAddress,BigInteger gasPrice,BigInteger gasLimit){
		if(null == ammbrServiceInstance){
			Credentials credentials = Credentials.create(privKey);
			ammbrServiceInstance= new AmmbrService(contractBinary, contractAddress, web3j, credentials, gasPrice, gasLimit);
		}
		/*Observable<String> ethBlock = web3j.ethBlockHashObservable();
		web3j.ethBlockNumber();
		try{
			EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(contractAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
			BigInteger blockHexNumber = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send().getBlock().getNumber();//.getGasLimit();
			System.out.println(blockHexNumber);
		}catch(Exception e)
		{
			
		}*/
		return ammbrServiceInstance;
	}
	
	
	 public Future<TransactionReceipt> getBonus(Address id) throws IOException {
		    org.web3j.abi.datatypes.Function function = new Function("bonus", Arrays.<Type>asList(id), Collections.<TypeReference<?>>emptyList());
		    //EthGetBalance web3ClientVersion =   web3j.ethGetBalance("0xA8C1Ca2d5643f401394BeF4Ed30a9E34e36fF05e", DefaultBlockParameterName.LATEST).send();
		    //String balance = web3ClientVersion.getBalance().toString();
		    return executeTransactionAsync(function);
		  }

		  public Future<TransactionReceipt> getTokenPerEther(Address id) {
		    Function function = new Function("tokenPerEther", Arrays.<Type>asList(id), Collections.<TypeReference<?>>emptyList());
		    return executeTransactionAsync(function);
		  }

		  public Future<Uint256> getTokenPerBankWire(Address id) {
			  Function function = new Function("bonus",
		                Arrays.<Type>asList(id),
		                Arrays.<TypeReference<?>>asList(
		      new TypeReference<Uint256>() {}));
		    return executeCallSingleValueReturnAsync(function);
		  }
	
		private BigDecimal[] calculateWeekBlock(BigDecimal startBlock,BigDecimal endBlock){
			BigDecimal totalBlockMine = endBlock.subtract(startBlock);
			
			  BigDecimal blockMined = startBlock;
			  BigDecimal blockMineInWeek = totalBlockMine.divide(new BigDecimal("4"),2, BigDecimal.ROUND_HALF_UP);
			  
			  BigDecimal weekBlock[] = new BigDecimal[4];
			  for(int count =0 ; count < 4 ; count++){
			        blockMined = blockMined.add(blockMineInWeek);
			        weekBlock[count] = blockMined;
			    }
			  
			return weekBlock;
			
		}
		
		public int calculateBonus(BigDecimal startBlock,BigDecimal endBlock){
			try{
				BigDecimal weekBlock[] = calculateWeekBlock(startBlock,endBlock);
				BigInteger blockHexNumber = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send().getBlock().getNumber();
				BigDecimal currentBlock = new BigDecimal(blockHexNumber);
				LOGGER.info("currentBlock "+currentBlock);
				
			    if(currentBlock.compareTo(weekBlock[0])==-1)
			        return 30;
			    else if(currentBlock.compareTo(weekBlock[1])==-1)
			        return 20;
			    else if(currentBlock.compareTo(weekBlock[2])==-1)
			        return 10;
			    else 
			        return 0;
				
			}catch(Exception e){
				LOGGER.error("Exception occur while calculating bonus "+e.getStackTrace());
				return 0;
			}
		}
}
