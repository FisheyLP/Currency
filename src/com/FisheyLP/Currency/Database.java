package com.FisheyLP.Currency;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    private static Connection con;
    private boolean initialized;

    public Database(String host, String port, String name, String user, String pass) {
        try {
            if (initialized) {
                throw new RuntimeException("Database already initialized!");
            }

            con = DriverManager.getConnection("jdbc:mysql://" +
                    host + ":" + port + "/" + name, user, pass);

            con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS currency ("
                    + "uuid varchar(36) PRIMARY KEY NOT NULL,"
                    + "name varchar(16) NOT NULL,"
                    + "money bigint NOT NULL DEFAULT 0);");

            initialized = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return con;
    }

    public ResultSet executeQuery(String sql) {
        try {
            return con.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int executeUpdate(String sql) {
        try {
            return con.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void close() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}