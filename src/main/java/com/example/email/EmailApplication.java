package com.example.email;

import com.example.email.service.ReadFileCSV;
import com.example.email.service.SendEmailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.mail.AuthenticationFailedException;
import java.util.LinkedList;

@SpringBootApplication
public class EmailApplication {

    public static void main(String[] args) throws AuthenticationFailedException {

        SpringApplication.run(EmailApplication.class, args);
        SendEmailService sendEmailService=new SendEmailService();
        sendEmailService.sendGmail();
    }

}
