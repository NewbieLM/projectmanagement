package com.maksym.projectmanagement;

public class Initializer {
    static {
        try {
            Class.forName(Util.JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
