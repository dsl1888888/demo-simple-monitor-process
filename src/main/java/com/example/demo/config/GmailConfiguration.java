package com.example.demo.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Component
public class GmailConfiguration {

    @Value(value="${gmail_send_account}")
    private String gmail_send_account;
    @Value(value="${gmail_send_password}")
    private String gmail_send_password;
    @Value(value="${gmail_send_account_TO}")
    private String gmail_send_account_TO;
   

    public static String gmailSendAccountTO;
    public static String gmailSendAccount;
    public static String gmailSendPassword; 

    @PostConstruct
    public void init(){
        gmailSendAccountTO=gmail_send_account_TO;
        gmailSendAccount = gmail_send_account;
        gmailSendPassword = gmail_send_password;

        log.info("");
        log.info("");
        log.info("");
        log.info("#####################################################################################");
        log.info("###################               GmailConfiguration init...............  start      ");
        log.info("#######");
        log.info("gmailSendAccount="+ gmailSendAccount);
        log.info("gmailSendPassword="+gmailSendPassword);
       
        log.info("#######");
        log.info("###################               GmailConfiguration init...............  end        ");
        log.info("#####################################################################################");
    }
}
