/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tps.blockchain.rest;

import com.vaadin.flow.component.notification.Notification;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

/**
 *
 * @author RICARDO
 */

@RestController
@RequestMapping("/api/v1/")
public class ApiBlockchain {
    
    @GetMapping("/blockdata")
    public ResponseEntity showBlockData(){
        
        Web3j redEthereum = Web3j.build(new HttpService("http://localhost:8545"));
            
          
           Map<String, Object> response = new HashMap<>();
           
           
            
            try {
            
            // web3_clientVersion returns the current client version.
            Web3ClientVersion clientVersion = redEthereum.web3ClientVersion().send();

            // eth_blockNumber returns the number of most recent block.
            EthBlockNumber blockNumber = redEthereum.ethBlockNumber().send();

            // eth_gasPrice, returns the current price per gas in wei.
            EthGasPrice gasPrice = redEthereum.ethGasPrice().send();
            
            
            
             
             
              
             
             response.put("version", clientVersion.getWeb3ClientVersion());
             response.put("Block number", blockNumber.getBlockNumber());
             response.put("Gas price", gasPrice.getGasPrice());
             
             
             
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
             
            }catch (IOException ex){
                throw new RuntimeException("Error whilst sending json-rpc requests", ex);
            }
        
       
        
    }
    
    
    
}
