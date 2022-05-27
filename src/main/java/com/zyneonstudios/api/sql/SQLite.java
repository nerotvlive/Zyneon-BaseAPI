package com.zyneonstudios.api.sql;

import java.sql.*;

public class SQLite {

    private Connection con;

    public SQLite(String path) {
        if (!isConnected()) {
            try {
                con = DriverManager.getConnection("jdbc:sqlite:" + path);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return con;
    }

    public boolean isConnected() {
        return (con != null);
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
        System.gc();
    }

    public boolean isAlreadySet(String table, String check) {
        boolean is;
        try {
            PreparedStatement ps = getConnection().prepareStatement("SELECT VALUE FROM "+table+" WHERE UUID = ?");
            ps.setString(1, check);
            ResultSet rs = ps.executeQuery();
            is = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            is = false;
        }
        return is;
    }
}