package com.xcd.own.netdisk.server.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Administrator on 14-10-26.
 */
public class DBConnect {
    public static Statement getStatement() {
        Statement stat = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Connection connect = null;
        try {
            connect = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/xcdp2p", "root", "xcdroot");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            stat = connect.createStatement();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return stat;
    }
}
