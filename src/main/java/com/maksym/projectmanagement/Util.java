package com.maksym.projectmanagement;

import java.sql.*;

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

    public static String arrayToQueryParameters(Object[] parameters) {
        if (parameters.length == 0){
            throw new RuntimeException("An array must contain at least one element");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");

        for (int i = 0; i < parameters.length; i++) {
            stringBuilder.append(parameters[i]);
            stringBuilder.append(", ");
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());

        stringBuilder.append(")");
        return stringBuilder.toString();
    }

}
