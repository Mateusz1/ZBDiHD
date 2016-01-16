/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package paliwa;

/**
 *
 * @author luna
 */

import java.sql.*;
 import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class db {
 
    
    public static void main(String[] args) {
        Connection connection = null;
 
    private static db _instance = null;   
    private Connection _conn = null;
    private final String CONNECTOR = "com.mysql.jdbc.Driver";
    private final String DATABASE = "paliwa";
    private final String USER = "luna";
    private final String PASS = "qwerty123";
     
    private db() {}
     
    public static db getInstance() {
        if(_instance == null)
            _instance = new db();
        return _instance;
    }
     
    public void createConn() throws ClassNotFoundException,
                                SQLException {
        Class.forName(CONNECTOR);
        _conn = DriverManager.getConnection(DATABASE,USER,PASS);
    }
     
    public Connection getConn() throws ClassNotFoundException, 
                                SQLException {
        createConn();
        return _conn;
    }
 
}
      