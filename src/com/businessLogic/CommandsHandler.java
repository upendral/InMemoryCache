package com.businessLogic;

import com.logger.Logger;
import com.utilities.Utility;

import java.util.*;

/**
 * Created by uppi on 3/23/2015.
 */
public class CommandsHandler {

    private Stack<String> commandStack;
    private HashMap<String, Stack<Integer>> buffer;
    private HashMap<String, Stack<Integer>> permanent;
    private HashMap<Integer, HashSet<String>> numEquals;
    private Logger logger;

    /**
     * Setting all the variables
     */
    public CommandsHandler() {
        commandStack = new Stack<String>();
        buffer = new HashMap<String, Stack<Integer>>();
        permanent = new HashMap<String, Stack<Integer>>();
        numEquals = new HashMap<Integer, HashSet<String>>();
        logger = Logger.getInstance();
    }

    /**
     * Set operation handler
     * @param key
     * @param value
     * @param command
     */
    public void set(String key, Integer value, String command) {
        if(commandStack.isEmpty()) {
            //Push into permanent
            Utility.updateMap(key, value, permanent, numEquals);
        } else {
            Utility.updateMap(key, value, buffer, numEquals);
            commandStack.push(command);
            //Push into buffer
        }
    }

    /**
     * Get operation handler
     * @param key
     */
    public void get(String key) {
        Integer value = null;
        if(Utility.elementExists(key, buffer)) {
            value = buffer.get(key).peek();
        } else if(Utility.elementExists(key, permanent)) {
            value = permanent.get(key).peek();
        }
        System.out.println(value == null ? "NULL" : value);
    }

    /**
     * Unset operation handler
     * @param key
     * @param command
     */
    public void unset(String key, String command) {
        if(commandStack.isEmpty()) {
            Utility.updateMap(key, null, permanent, numEquals);
        } else {
            if(Utility.elementExists(key, buffer)) {
                Utility.updateMap(key, null, buffer, numEquals);
            }
            Utility.updateMap(key, null, permanent, numEquals);
            commandStack.push(command);
        }
    }

    /**
     * End of the program handler
     */
    public void endProgram() {
        System.exit(0);
    }

    /**
     * Begin operation handler
     * @param command
     */
    public void begin(String command) {
        commandStack.push(command);
    }

    /**
     * Rollback operation handler
     */
    public void rollback() {
        if(!commandStack.isEmpty()) {
            if(commandStack.peek().equals("BEGIN")) {
                commandStack.pop();
                return;
            } else {
                while (!commandStack.peek().equals("BEGIN")) {
                    String command = commandStack.pop();
                    String key;
                    if (command.startsWith("SET")) {
                        key = command.substring(4, command.indexOf(' ', 4));
                        Integer value = Integer.parseInt(command.substring(command.indexOf(' ', 4) + 1));
                        if(Utility.elementExists(key, buffer)) {
                            Utility.undoSet(key, value, buffer, numEquals);
                        } else {
                            Utility.undoSet(key, value, permanent, numEquals);
                        }
                    } else {
                        key = command.substring(6);
                        if(Utility.elementExists(key, buffer)) {
                            Utility.undoUnSet(key, null, buffer, numEquals);
                        } else {
                            Utility.undoUnSet(key, null, permanent, numEquals);
                        }
                    }
                }
                commandStack.pop();
            }
        } else {
            System.out.println("NO TRANSACTION");
        }
    }

    /**
     * Commit operation handler
     * @param command
     */
    public void commit(String command) {
        if(!commandStack.isEmpty()) {
            for(Map.Entry<String, Stack<Integer>> entry : buffer.entrySet()) {
                String key = entry.getKey();
                Stack<Integer> value = entry.getValue();
                Utility.updateMap(key, value.peek(), permanent, numEquals);
            }
            commandStack.clear();
            buffer.clear();
        } else {
            System.out.println("NO TRANSACTION");
        }
    }

    /**
     * numEqualsTo operation handler
     * @param number
     * @param command
     */
    public void numEqualsTo(String number, String command) {
        Integer key = Integer.parseInt(number);
        if(numEquals.isEmpty() || !numEquals.containsKey(key) || numEquals.get(key).size() == 0) {
            System.out.println(0);
        } else {
            System.out.println(numEquals.get(key).size());
        }
    }

}
