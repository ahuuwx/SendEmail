package com.example.email.service;

import com.example.email.dto.CustomerDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadFileCSV {
    public List<CustomerDto> readDataFromCSV(String fileName) {
        List<CustomerDto> books = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {
            // read the first line from the text file
            String line = br.readLine();
            // loop until all lines are read
            while (line != null) {
                String[] attributes = line.split(",");

                if (attributes.length == 3) {
                    //add customers with no email
                    //append that customer to csv file with format similar to
                    WriteDataToFileCSV writeDataToFileCSV = new WriteDataToFileCSV();
                    writeDataToFileCSV.writeDataToFileCSV("error.csv", attributes);
                } else {
                    //else add customer with full info to l
                    CustomerDto customer = createCustomer(attributes);
                    if (!customer.getTitle().equals("TITLE")) {
                        books.add(customer);
                    }
                }
                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return books;
    }

    public CustomerDto createCustomer(String[] metadata) {
        String title = metadata[0];
        String firstName = metadata[1];
        String lastName = metadata[2];
        String email;
        if (metadata[3] == null) {
            email = null;
        } else {
            email = metadata[3];
        }

        // create and return book of this metadata
        return new CustomerDto(title, firstName, lastName, email);
    }


}
