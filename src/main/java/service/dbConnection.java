package service;

import java.sql.Connection;
import java.sql.DriverManager;

public class dbConnection {
	public Connection connect() throws Exception {
        Connection conn = null;
        try {
        	
        	Class.forName("org.sqlite.JDBC");
        	
            String url = "jdbc:sqlite:C:\\sqlite\\zerobase.db";
            conn = DriverManager.getConnection(url);
           
        } catch (Exception e) {
        	if (conn != null) { conn.close(); }
            System.out.println(e.getMessage());
        } 
        return conn;
    }
}
