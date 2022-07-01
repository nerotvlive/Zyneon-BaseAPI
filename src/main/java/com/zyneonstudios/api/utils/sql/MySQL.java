package com.zyneonstudios.api.utils.sql;

import java.sql.*;

public class MySQL {

    private String host;
    private String port;
    private String database;
    private String username;
    private Connection con;

    public MySQL(String host, String port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        if (!isConnected()) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return "The password isn't cached.";
    }

    public Connection getConnection() {
        return con;
    }

    public boolean isConnected() {
        return (con != null);
    }

    public boolean isAlreadySet(String table, String check) {
        boolean is;
        try {
            PreparedStatement ps = getConnection().prepareStatement("SELECT VALUE FROM " + table + " WHERE UUID = ?");
            ps.setString(1, check);
            ResultSet rs = ps.executeQuery();
            is = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            is = false;
        }
        return is;
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        con = null;
        host = null;
        port = null;
        database = null;
        username = null;
        System.gc();
    }
}