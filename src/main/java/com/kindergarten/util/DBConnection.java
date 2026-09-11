package com.kindergarten.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/kindergarten_management";

    private static final String USER =
            "root";

    private static final String PASSWORD =
            System.getenv("KINDERGARTEN_DB_PASSWORD");

    public static Connection getConnection()
            throws SQLException {

        if (PASSWORD == null || PASSWORD.isBlank()) {

            throw new SQLException(
                    "Database password environment variable is not set."
            );
        }

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}