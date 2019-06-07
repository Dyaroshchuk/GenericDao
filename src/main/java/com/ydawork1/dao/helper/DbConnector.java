package com.ydawork1.dao.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Optional;

public class DbConnector {

    private static final String dbUrl = "jdbc:h2:tcp://localhost/~/test";
    private static final String name = "dima";
    private static final String password = "6531";

    public static Optional<Connection> connect() {
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(dbUrl, name, password);
            return Optional.of(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}