package com.example.email.service;


import com.example.email.dto.CustomerDto;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class SendEmailService {
    JSONParser parser = new JSONParser();

    public String replaceString(String oldString, CustomerDto dto) {
        String replace = oldString.replace("{{TITLE}}", dto.getTitle());
        String replace1 = replace.replace("{{FIRST_NAME}}", dto.getFirstName());
        String replace2 = replace1.replace("{{LAST_NAME}}", dto.getLastName());

        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        ZonedDateTime today = ZonedDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String finalString = replace2.replace("{{TODAY}}", today.format(formatter));
        return finalString;
    }

    public void sendGmail() throws AuthenticationFailedException {
        final String username = "yourusernamehere@gmail.com";
        final String password = "yourpasswordhere";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); // TLS

        Session session = Session.getInstance(prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));


            //retrieve data from email template json
            JSONObject a = (JSONObject) parser.parse(new FileReader("email_template.json"));

            //read customers info from csv file
            ReadFileCSV readFileCSV = new ReadFileCSV();
            List<CustomerDto> dtos = readFileCSV.readDataFromCSV("customers.csv");

            List<JSONObject> objectList = new ArrayList<>();
            //each customer
            for (CustomerDto dto : dtos) {
                //merge data and create a json file to
                //Creating a JSONObject object
                JSONObject jsonObject = new JSONObject();
                //Inserting key-value pairs into the json object
                jsonObject.put("from", a.get("from"));
                jsonObject.put("to", dto.getEmail());
                jsonObject.put("subject", a.get("subject"));
                jsonObject.put("mimeType", a.get("mimeType"));

                String finalString = replaceString((String) a.get("body"), dto);
                jsonObject.put("body", finalString);

                objectList.add(jsonObject);

                try {
                    FileWriter file = new FileWriter(dto.getFirstName() + ".json");
                    file.write(jsonObject.toJSONString());
                    file.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                String subject = (String) jsonObject.get("subject");
                String body = (String) jsonObject.get("body");
                String mimeType = (String) jsonObject.get("mimeType");
                String recipient= (String) jsonObject.get("to");

                message.setSubject(subject);
                message.setText(body);
                message.isMimeType(mimeType);
                message.setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse(recipient)
                );
                Transport.send(message);

            }

        } catch (MessagingException | FileNotFoundException e) {
            e.printStackTrace();
            throw new AuthenticationFailedException("FAIL");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
