package com.example.theostore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VoiceInputParser {

    static String[] names = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen",
            "eighteen", "nineteen", "twenty", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
    static List<String> namesList = Arrays.asList(names);

    static String[] simpleStopWords = {"me", "my", "the", "you", "want", "I", "like", "would", "a", "can", "please", "some"};
    static List<String> simpleStopWordsList = Arrays.asList(simpleStopWords);

    static String[] trayWords = {"tray", "tree", "box"}; // "tree" = a work-around for bad speech processing. Probably fine - I reckon people wont store trees ...
    static List<String> trayWordsList = Arrays.asList(trayWords);

    static String[] bringCommands = {"bring", "ring", "retrieve", "find", "get", "have", "give"}; // a work around for bad speech to text where 'bring' is classified as 'ring'
    static List<String> bringCommandsList = Arrays.asList(bringCommands);

    static String[] storeCommands = {"store", "put", "stored"};
    static List<String> storeCommandsList = Arrays.asList(storeCommands);


    public static int textNumberToInt(String trayName) {
        for (int i = 0; i < 20; i++) {
            if (trayName.equals(names[i])){
                return Integer.parseInt(names[i+20]);
            }
        }
        // otherwise trayName is just "1" or something
        return Integer.parseInt(trayName);
    }


    public static Command parseInputText(String inputText){
        System.out.println("Starting parsing ...");
        // convert to list of lowercase strings
        ArrayList<String> words = new ArrayList<String>(Arrays.asList(inputText.toLowerCase().split(" ")));

        // lets remove basic words that mean nothing... "me", "the", "can", ... etc
        for (String word : new ArrayList<String>(words)) { // iterate through ArrayList copy, but remove from the original ArrayList
            if (simpleStopWordsList.contains(word)) {
                words.remove(word);
            } // does iterating through list like this work if we're removing things at the same time?
        }

        // simple command may just be 'tray 1' -> meaning bring that tray
        if (trayWordsList.contains(words.get(0))) {
            if (words.size() == 1) {
                // 'tray' (could have been 'tray please' or something) -> bring any tray
                System.out.println("bring random tray");
                return new Command(CommandType.BRING_ANY);
            } else {
                if (namesList.contains(words.get(1))) {
                    // bring tray X
                    //System.out.println("bring tray " +  words.get(1));
                    return new Command(CommandType.BRING, textNumberToInt(words.get(1)));
                } else {
                    //bring any tray
                    System.out.println("bring random tray");
                    return new Command(CommandType.BRING_ANY);
                }
            }

        }

        // info on the desired command is deduced from the first command word and then the words which follow that
        // lets find where the command starts
        int cmdStartIndex = -1;
        for (int i = 0; i < words.size(); i++) {

            if (bringCommandsList.contains(words.get(i)) || storeCommandsList.contains(words.get(i))) {
                cmdStartIndex = i;
                break;
            }
        }

        if (cmdStartIndex  == -1) {
            System.out.println("Don't understand the input - no command word was given?");
            return new Command(CommandType.ERROR);
        } else {
            // remove the unimportant words before the first command
            for (int i = 0; i < cmdStartIndex; i++) {
                words.remove(0);
            }
        }
        // our input now starts with a bring or store command

        // if first word is a store command then the user must intend to store current tray
        if (storeCommandsList.contains(words.get(0))) {
            // INSERT FUNCTION CALL TO STORE CURRENT TRAY
            System.out.println("store current tray");
            return new Command(CommandType.STORE);
        }

        // if first word is bring command
        if (bringCommandsList.contains(words.get(0))) {
            // after bring just search remaining string for X or 'tray X'
            // -> then it means bring some specific tray X
            for (int i = 1; i < words.size(); i++){
                if (trayWordsList.contains(words.get(i))){
                    if (i + 1 < (words.size())){
                        if (namesList.contains(words.get(i+1))){
                            // we have some 'bring ... tray X'
                            // INSERT FUNCTION CALL TO BRING SPECIFIED TRAY
                            System.out.println("bring tray " +  words.get(i+1));
                            return new Command(CommandType.BRING, textNumberToInt(words.get(i+1)));
                        } else if (i + 2 < (words.size())) {
                            if (words.get(i+1).equals("number") && namesList.contains(words.get(i+2))) {
                                // bring tray number X
                                System.out.println("bring tray " +  words.get(i+2));
                                return new Command(CommandType.BRING, textNumberToInt(words.get(i+2)));
                            }
                        } else {
                            // some 'bring ... tray ... [unimportant info]'
                            // INSERT FUNCTION CALL TO BRING RANDOM TRAY
                            System.out.println("bring random tray");
                            return new Command(CommandType.BRING_ANY);
                        }
                    } else {
                        // another case we might have 'bring ... tray'
                        // INSERT FUNCTION CALL TO BRING RANDOM TRAY
                        System.out.println("bring random tray");
                        return new Command(CommandType.BRING_ANY);
                    }
                }
            }
            // otherwise it is of form: bring [some item description we need to search for a bring the right tray for]
            // we then just send a request with the given search string
            String searchString = "";
            for (int i = 1; i < words.size(); i++){
                searchString += words.get(i);
                if (i < words.size()-1) {
                    searchString += " ";
                }
            }
            // INSERT APPROPRIATE FUNCTION CALL - which will do a get request to find the most appropriate tray, and then do a put request to bring that tray?
            System.out.println("Find tray for the search: \"" + searchString + "\"");
            return new Command(CommandType.FIND_ITEM, words);
        }


        // otherwise, if reach here we really don't understand what has been said
        System.out.println(words);
        System.out.println("Couldn't understand that");
        return new Command(CommandType.ERROR);

    }

}
