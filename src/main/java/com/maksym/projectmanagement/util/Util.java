package com.maksym.projectmanagement.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.*;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class Util {
    private static String JDBC_DRIVER;
    private static String DATABASE_URL;

    private static String USER;
    private static String PASSWORD;

    private static volatile Connection connection;

    private static volatile SessionFactory sessionFactory;

    static {
        try (InputStream input = new FileInputStream("src/main/resources/db/mysql.properties")) {

            Properties prop = new Properties();
            prop.load(input);

            JDBC_DRIVER = prop.getProperty("database.driver");
            DATABASE_URL = prop.getProperty("database.url");
            USER = prop.getProperty("database.username");
            PASSWORD = prop.getProperty("database.password");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        if (connection != null) {
            return connection;
        }
        synchronized (Util.class) {
            if (connection == null) {
                connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            }
            return connection;
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory != null) {
            return sessionFactory;
        }
        synchronized (Util.class) {
            if (sessionFactory == null) {
                sessionFactory = new Configuration().configure("db/hibernate.cfg.xml").buildSessionFactory();
            }
            return sessionFactory;
        }
    }

    public static void closeConnection(Statement statement, ResultSet resultSet) throws SQLException {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

        } finally {
            if (statement != null) {
                statement.close();
            }

        }
    }

    public static void closeConnection(Statement statement) throws SQLException {
        closeConnection(statement, null);
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

    public static String getJdbcDriver() {
        return JDBC_DRIVER;
    }

    public static String getDatabaseUrl() {
        return DATABASE_URL;
    }
}
