package com.maksym.projectmanagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.List;

public class Util {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost/company?serverTimezone=UTC";

    private static final String USER = "root";
    private static final String PASSWORD = "pass";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
    }

    public static void closeConnection(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            statement.close();
            connection.close();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public static void closeConnection(Connection connection, Statement statement) throws SQLException {
        closeConnection(connection, statement, null);
    }

    public static String arrayToQueryParameters(Object[] parameters) {
        if (parameters.length == 0) {
            throw new RuntimeException("An array must contain at least one element");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");

        for (Object obj : parameters) {
            stringBuilder.append(obj);
            stringBuilder.append(", ");
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());

        stringBuilder.append(")");
        return stringBuilder.toString();
    }


    public static void writeToConsole(List<? extends Object> messages) {
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
