package com.company;

import java.util.Scanner;
import com.businessLogic.CommandParser;
import com.logger.Logger;

/**
 *Main class
 */
public class Main {

    /**
     * main method
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner;
        Logger logger = Logger.getInstance();
        if(args.length == 0) {
            scanner = new Scanner(System.in);
            logger.log("Main.main From standard in");
        } else {
            scanner = new Scanner(args[0]);
            logger.log("Main.main From File" + args[0]);
        }
        if(scanner != null) {
            CommandParser logicalProcessor = new CommandParser();
            logicalProcessor.startProcessing(scanner);
        } else {
            logger.log("No input found. Please re-run the application with input");
            System.out.println("No input found. Please re-run the application with input");
        }
    }

}
