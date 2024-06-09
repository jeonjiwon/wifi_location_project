package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.LOC_HISTORY_MODEL;
import models.WIFI_INTERFACE_MODEL;

public class Location_History_Service {
	
	public List<LOC_HISTORY_MODEL> list() {

		Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<LOC_HISTORY_MODEL> list = new ArrayList<>();
        
		try {
			dbConnection dbconnection = new dbConnection();
			con = dbconnection.connect();
						
			String sql = " SELECT ID, LNT, LAT, INSERT_DT "+
			             " FROM  LOC_HISTORY " +
						 " ORDER BY ID DESC ";
			
			preparedStatement = con.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            System.out.println("위치 히스토리를 조회합니다. ");
            
            while(rs.next()) {
                LOC_HISTORY_MODEL model = new LOC_HISTORY_MODEL();
            	model.setId(rs.getInt("ID"));
            	model.setLnt(rs.getString("LNT"));
            	model.setLat(rs.getString("LAT"));
            	model.setInsert_dt(rs.getDate("INSERT_DT"));
            	list.add(model);
            }

    		if (con != null) { 
        		try {
    				con.close();
    			} catch (SQLException e1) {
    				e1.printStackTrace();
    			} 
        	}
        	
			
		} catch(Exception e) {

	    	if (con != null) { 
	    		try {
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
	    	}
		}
		
		return list;
	}
	
	public void delete(String id) {

		Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        
		try {
			dbConnection dbconnection = new dbConnection();
			con = dbconnection.connect();
						
			String sql = "DELETE FROM LOC_HISTORY WHERE ID = ?";
			
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, id);
			
            preparedStatement.executeUpdate();
            System.out.println("위치 히스토리를 삭제합니다. [" + id +"]");
    		if (con != null) { 
        		try {
    				con.close();
    			} catch (SQLException e1) {
    				e1.printStackTrace();
    			} 
        	}
        	
		} catch(Exception e) {

	    	if (con != null) { 
	    		try {
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
	    	}
		}
	}

	public LOC_HISTORY_MODEL select_mylocation() {

		Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        LOC_HISTORY_MODEL model = new LOC_HISTORY_MODEL();
        
		try {
			dbConnection dbconnection = new dbConnection();
			con = dbconnection.connect();
						
			String sql = "SELECT ID, LNT, LAT "+
						 "FROM  ( SELECT ID, LNT, LAT FROM LOC_HISTORY ORDER BY ID DESC )"+
						 "LIMIT 1 OFFSET 0 " ;
			
			preparedStatement = con.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            System.out.println("내 위치 정보를 조회합니다.");
            
            while(rs.next()) {
            	model.setLnt(rs.getString("LNT"));
            	model.setLat(rs.getString("LAT"));
            }

    		if (con != null) { 
        		try {
    				con.close();
    			} catch (SQLException e1) {
    				e1.printStackTrace();
    			} 
        	}
        	
			
		} catch(Exception e) {

	    	if (con != null) { 
	    		try {
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
	    	}
		}
		
		return model;
	}

}
