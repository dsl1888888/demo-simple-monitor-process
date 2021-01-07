package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.demo.config.GmailConfiguration;
import com.example.demo.testdomain.FileUtils;
import com.example.demo.testdomain.TelnetUtil;
import com.example.demo.testdomain.TesDomainObj;
import com.example.demo.tools.gmail.SendGMailTLS2;

import lombok.extern.slf4j.Slf4j;

/**
 * https://blog.csdn.net/u012702547/article/details/88775298
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
public class DemoTelnetApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(DemoTelnetApplication.class, args);
    }

    private static final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

    // 每隔3秒执行一次
    @Scheduled(cron = "0/30 * * * * ?")
    public synchronized void testCron()
    {
        System.out.println("cron,当前时间：" + format.format(new Date()));

        List<TesDomainObj> readJsonGetData = FileUtils.readJsonGetData("checkDomain.json");

        for (TesDomainObj tesDomainObj : readJsonGetData)
        {
            if (null == tesDomainObj)
            {
                continue;
            }

            String hostname = tesDomainObj.getDomain(); // hostname 可以是主机的 IP 或者
                                                        // 域名
            int port = tesDomainObj.getPort();

            int timeout = 6000;
            boolean isConnected = TelnetUtil.telnet(hostname, port, timeout);

            if (!isConnected)
            {
                // 这里要发邮件通知

                new Thread(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        // Recipient's email ID needs to be mentioned.
                        String to = GmailConfiguration.gmailSendAccountTO;// change
                                                                          // accordingly

                        // Sender's email ID needs to be mentioned
                        String from = GmailConfiguration.gmailSendAccount;// change
                                                                          // accordingly

                        final String username = GmailConfiguration.gmailSendAccount;// change
                                                                                    // accordingly
                        final String password = GmailConfiguration.gmailSendPassword;// change
                                                                                     // accordingly

                        // Set Subject: header field
                        String subject = "Gmail - Server Warn";

                        String sbString = "";
                        sbString = "isConnected=" + isConnected + ",hostname=" + hostname + " ,port=" + port;

                        // Now set the actual message
                        String text = "Hello" + sbString;

                        SendGMailTLS2.sendddd(to, from, username, password, subject, text);

                    }
                }).start();
            }

            log.info("isConnected={},hostname={} ,port={} ", isConnected, hostname, port);
        }

    }

}
