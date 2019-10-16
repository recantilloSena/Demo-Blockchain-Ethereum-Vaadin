/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tps.blockchain.ui;

import com.tps.blockchain.model.Usuarios;
import com.tps.blockchain.service.UsuariosService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

/**
 *
 * @author RICARDO
 */
@Route("index")
@Theme(value = Lumo.class,variant = Lumo.DARK)
public class MainView  extends VerticalLayout{

    private final  ComboBox<String> comboUsuarios = new ComboBox<>("Usuario BlockChain");
   
    private final  Button login = new Button("Login to Blockchain Ethereum");
    
    private Usuarios usuarios;
            
    
    public MainView() {
        this.usuarios = new Usuarios();
        
        usuarios.setUsuario("Usuario A");
        usuarios.setUsuarioBlockchain("0x5a898e3fe06504cca13b05d42dc47566c1ecdf16");
       
        comboUsuarios.setItems("Usuario A","Usuario B" );
       
        
        add(login,comboUsuarios);
        
        login.addClickListener((t) -> {
            
            //Login al BlockChain
            Web3j redEthereum = Web3j.build(new HttpService("http://localhost:8545"));
            
            Notification.show("Successfuly connected to Ethereum");
            
            try {
            
            // web3_clientVersion returns the current client version.
            Web3ClientVersion clientVersion = redEthereum.web3ClientVersion().send();

            // eth_blockNumber returns the number of most recent block.
            EthBlockNumber blockNumber = redEthereum.ethBlockNumber().send();

            // eth_gasPrice, returns the current price per gas in wei.
            EthGasPrice gasPrice = redEthereum.ethGasPrice().send();
            
            
            
             Notification.show(clientVersion.getWeb3ClientVersion());
             
             System.out.println("Block number: " + blockNumber.getBlockNumber());
             System.out.println("Gas price: " + gasPrice.getGasPrice());
             
             
             
             
             EthGetBalance balanceWei = redEthereum.ethGetBalance("0xfb29bc5791ba364464371692804fbf8332498316", DefaultBlockParameterName.LATEST).send();
             BigDecimal balanceInEther = Convert.fromWei(balanceWei.getBalance().toString(), Unit.ETHER);
             
             Notification.show("balance in Ether: " + balanceInEther);
             
             System.out.println("balance in wei: " + balanceWei);
             
            }catch (IOException ex){
                throw new RuntimeException("Error whilst sending json-rpc requests", ex);
            }
                    
            
        });
        
        
    }
    
    
    
}
