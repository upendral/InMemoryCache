package com.logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Logger class for the logging
 * Created by uppi on 3/23/2015.
 */
public class Logger {

    private static String loggerFilePath = "Logger.txt";
    private static Logger logger;
    private static RandomAccessFile file;

    /**
     * loggerFilePath the log file
     */
    private Logger() {
        try {
            file = new RandomAccessFile(loggerFilePath, "rw");
        } catch(FileNotFoundException ex) {
            System.out.println("Exception in file creation");
        }
    }

    /**
     * Getting single instace of this class
     * @return
     */
    public static Logger getInstance() {
        if(logger == null) {
            logger = new Logger();
        }
        return logger;
    }

    /**
     * Log the given line into the text file set
     * @param input
     */
    public static void log(String input) {
        try {
            file.writeChars(input);
        } catch (IOException ex) {
            System.out.println("IOException writing to file");
        }
    }
}
