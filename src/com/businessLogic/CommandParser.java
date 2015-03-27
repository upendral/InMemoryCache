package com.businessLogic;

import com.logger.Logger;

import java.util.Scanner;

/**
 * Parser for the given commands
 * Created by uppi on 3/22/2015.
 */

public class CommandParser {

    private Logger logger;

    /**
     * Start the command processing
     * @param input
     */
    public void startProcessing(Scanner input) {
        logger = Logger.getInstance();
        CommandsHandler handler = new CommandsHandler();
        while(input.hasNextLine()) {
            String command = input.nextLine();
            logger.log("startProcessing " + command);
            String key = null;
            if(command.startsWith("SET")) {
                key = command.substring(4, command.indexOf(' ', 4));
                Integer value = Integer.parseInt(command.substring(command.indexOf(' ', 4) + 1));
                handler.set(key, value, command);
            } else if(command.startsWith("GET")) {
                key = command.substring(4);
                handler.get(key);
            } else if(command.startsWith("UNSET")) {
                key = command.substring(6);
                handler.unset(key, command);
            } else if(command.startsWith("NUMEQUALTO")) {
                key = command.substring(11);
                handler.numEqualsTo(key, command);
            } else if(command.startsWith("END")) {
                handler.endProgram();
            } else if(command.startsWith("BEGIN")) {
                handler.begin(command);
            } else if(command.startsWith("ROLLBACK")) {
                handler.rollback();
            } else if(command.startsWith("COMMIT")) {
                handler.commit(command);
            }
        }
    }

}
