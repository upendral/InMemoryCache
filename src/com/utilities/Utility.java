package com.utilities;

import com.logger.Logger;

import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Utility class for all the operations
 * Created by uppi on 3/22/2015.
 */

public class Utility {

    private static Logger logger;

    /**
     * gets the command list
     * @param scanner
     * @return
     */
    public static List<String> getCommandList(Scanner scanner) {
        if (scanner != null) {
            ArrayList<String> commandList = new ArrayList<String>();
            while (scanner.hasNextLine()) {
                commandList.add(scanner.nextLine());
            }
            return commandList;
        } else {
            logger = Logger.getInstance();
            logger.log("getCommandList no input found");
            return null;
        }
    }

    /**
     * update the given  map
     * @param key
     * @param value
     * @param map
     * @param numEquals
     */
    public static void updateMap(String key, Integer value, HashMap<String, Stack<Integer>> map, HashMap<Integer, HashSet<String>> numEquals) {
        if(map == null) {
            map = new HashMap<String, Stack<Integer>> ();
        }
        if(map.containsKey(key)) {
            if(!map.get(key).isEmpty()) {
                Integer previousKey = map.get(key).peek();
                if (previousKey != null) {
                    removeFromNumEqualsToMap(previousKey, key, numEquals);
                }
            }
            map.get(key).push(value);
            if(value != null) {
                updateNumEqualsToMap(value, key, numEquals);
            }
        } else {
            if(value != null) {
                Stack values = new Stack();
                values.push(value);
                map.put(key, values);
                updateNumEqualsToMap(value, key, numEquals);
            }
        }
    }

    /**
     * Undo the preceding set operation
     * @param key
     * @param value
     * @param map
     * @param numEquals
     */
    public static void undoSet(String key, Integer value, HashMap<String, Stack<Integer>> map, HashMap<Integer, HashSet<String>> numEquals) {
        if(!map.isEmpty()) {
            if(map.containsKey(key)) {
                if(!map.get(key).isEmpty()) {
                    map.get(key).pop();
                }
                removeFromNumEqualsToMap(value, key, numEquals);
                if(!map.get(key).isEmpty()) {
                    Integer nextValue = map.get(key).peek();
                    if (nextValue != null) {
                        updateNumEqualsToMap(nextValue, key, numEquals);
                    }
                }
            }
        }
    }

    /**
     * Undo the unset operation
     * @param key
     * @param value
     * @param map
     * @param numEquals
     */
    public static void undoUnSet(String key, Integer value, HashMap<String, Stack<Integer>> map, HashMap<Integer, HashSet<String>> numEquals) {
        if(!map.isEmpty()) {
            if(map.containsKey(key) && !map.isEmpty()) {
                map.get(key).pop();
                Integer nextValue = map.get(key).peek();
                if(nextValue != null) {
                    updateNumEqualsToMap(nextValue, key, numEquals);
                }
            }
        }
    }

    /**
     * Remove from the numEquals table
     * @param key
     * @param value
     * @param map
     */
    private static void removeFromNumEqualsToMap(Integer key, String value, HashMap<Integer, HashSet<String>> map) {
        if(map == null) {
            map = new HashMap<Integer, HashSet<String>>();
        }
        if(map.containsKey(key) && !map.get(key).isEmpty()){
            map.get(key).remove(value);
        }
    }

    /**
     * Update the numEqualsMap
     * @param key
     * @param value
     * @param map
     */
    public static void updateNumEqualsToMap(Integer key, String value, HashMap<Integer, HashSet<String>> map) {
        if(map == null) {
            map = new HashMap<Integer, HashSet<String>> ();
        }
        if(map.containsKey(key) && !map.get(key).isEmpty()) {
            map.get(key).add(value);
        } else {
            HashSet<String> set = new HashSet<String>();
            set.add(value);
            map.put(key, set);
        }
    }

    /**
     * Check if the given element exists
     * @param key
     * @param map
     * @return
     */
    public static boolean elementExists(String key, HashMap<String, Stack<Integer>> map) {
        if(map != null && map.containsKey(key) && map.get(key) != null && !map.get(key).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

}
