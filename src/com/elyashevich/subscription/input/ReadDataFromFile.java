package com.elyashevich.subscription.input;

import com.elyashevich.subscription.exception.InputTechnicalException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadDataFromFile {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String INCORRECT_FILE = "File isn't correct to reading countries list.";
    private static final String START_READING = "Start reading from file...";
    private static final String END_READING = "End reading from file...";

    public ArrayList<String> readFromFile(String filename) throws InputTechnicalException {
        ArrayList<String> dataList = new ArrayList<>();
        File file = new File(filename);
        if (!file.isFile() || !file.exists() || !file.canRead()) {
            throw new InputTechnicalException(INCORRECT_FILE);
        }
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            LOGGER.debug(START_READING, Level.DEBUG);
            while (scanner.hasNext()) {
                dataList.add(scanner.nextLine());
            }
            LOGGER.debug(END_READING, Level.DEBUG);
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.FATAL, e.getMessage());
            throw new RuntimeException();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return dataList.isEmpty() ? null : dataList;
    }
}
