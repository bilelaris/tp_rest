package com.bilelaris;


import java.sql.Connection;
import java.sql.DriverManager;

class JdbcConnection {
    static Connection createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/tpdaws","root","pass4root");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
