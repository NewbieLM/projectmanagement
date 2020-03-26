package com.maksym.projectmanagement.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class IOUtil {

    public static void writeToConsole(List<?> messages) {
        for (Object message : messages) {
            writeToConsole(message.toString());
        }
    }

    public static void writeToConsole(Object message) {
        writeToConsole(message.toString());
    }

    public static void writeToConsole(String message) {
        System.out.println(message);
    }

    public static String readFromConsole(String tip) {
        String inputMessage = null;
        if (tip != null && tip.length() > 0) {
            writeToConsole(tip);
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            inputMessage = bufferedReader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (inputMessage != null) {
            inputMessage = inputMessage.trim();
        }
        return inputMessage;
    }

    public static Integer readNumberFromConsole(String tip) {
        String inputMessage = readFromConsole(tip);
        while (!isNumeric(inputMessage)) {
            inputMessage = readFromConsole("It should be a number (include only digits), try again:");
        }
        return Integer.parseInt(inputMessage);
    }

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
