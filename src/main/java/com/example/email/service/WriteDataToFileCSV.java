package com.example.email.service;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteDataToFileCSV {
    public void writeDataToFileCSV(String filePath, String[] attributes) {
        // first create file object for file placed at location
        // specified by filepath
        File file = new File(filePath);
        try {
            if (file.exists()) {
                // create FileWriter object with file as parameter
                FileWriter outputfile = new FileWriter(file, true);

                // create CSVWriter object filewriter object as parameter
                CSVWriter writer = new CSVWriter(outputfile);
//                // adding header to csv
//                String[] header = {"TITLE", "FIRST_NAME", "LAST_NAME", "EMAIL"};
//                writer.writeNext(header);
                writer.writeNext(attributes);
                // closing writer connection
                writer.close();
            } else {
                FileWriter outputfile = new FileWriter(file);

                // create CSVWriter object filewriter object as parameter
                CSVWriter writer = new CSVWriter(outputfile);
                // adding header to csv
                String[] header = {"TITLE", "FIRST_NAME", "LAST_NAME", "EMAIL"};
                writer.writeNext(header);
                writer.writeNext(attributes);
                // closing writer connection
                writer.close();
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
