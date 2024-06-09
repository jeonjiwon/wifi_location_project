package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import models.WIFI_INTERFACE_MODEL;

public class Wifi_location_Service {
	
    private static final double EARTH_RADIUS = 6371000 * 0.001;  //km단위

    
	public List<WIFI_INTERFACE_MODEL> list(double lat, double lnt) {
		
		// 내 위치 정보를 입력하면 가까운 위치에 있는 와이파이 정보 20개 보여주는 기능 구현		
		List<WIFI_INTERFACE_MODEL> loc_list = new ArrayList<>();
		List<WIFI_INTERFACE_MODEL> resultList = new ArrayList<>();
		Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        
		try {
			dbConnection dbconnection = new dbConnection();
			con = dbconnection.connect();
			
			
			String sql = "SELECT MGR_NO, WRDOFC, MAIN_NM, ADRES1, ADRES2, INSTL_FLOOR, "+
								"INSTL_TY, INSTL_MBY, SVC_SE, CMCWR, CNSTC_YEAR, INOUT_DOOR, "+
								"REMARS3, LNT, LAT, "+
								"strftime('%Y-%m-%d %H:%M', WORK_DTTM) AS WORK_DTTM "+
			             " FROM  WIFI_INTERFACE " ;
			
			preparedStatement = con.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            System.out.println("위치 정보를 조회합니다. ");
            
            
            while(rs.next()) {
            	WIFI_INTERFACE_MODEL model = new WIFI_INTERFACE_MODEL();
            	model.setMgr_no(rs.getString("MGR_NO"));
            	model.setWrdofc(rs.getString("WRDOFC"));
            	model.setMain_nm(rs.getString("MAIN_NM"));
            	model.setAdres1(rs.getString("ADRES1"));
            	model.setAdres2(rs.getString("ADRES2"));
            	model.setInstl_floor(rs.getString("INSTL_FLOOR"));
            	model.setInstl_ty(rs.getString("INSTL_TY"));
            	model.setInstl_mby(rs.getString("INSTL_MBY"));
            	model.setSvc_se(rs.getString("SVC_SE"));
            	model.setCmcwr(rs.getString("CMCWR"));
            	model.setCnstc_year(rs.getString("CNSTC_YEAR"));
            	model.setInout_door(rs.getString("INOUT_DOOR"));
            	model.setRemars3(rs.getString("REMARS3"));
            	model.setLnt(rs.getDouble("LNT"));
            	model.setLat(rs.getDouble("LAT"));
            	model.setStr_work_dttm(rs.getString("WORK_DTTM"));
            	            	
            	model.setDis_tinct(Math.round(calculateDistance(model.getLnt(), model.getLat(), lnt, lat)) / 10000.0);
            	
            	loc_list.add(model);
            }
            
            if(loc_list != null && loc_list.size() > 0) {
                loc_list.sort(Comparator.comparing(WIFI_INTERFACE_MODEL::getDis_tinct));
                resultList = loc_list.stream().limit(20).collect(Collectors.toList());
                
                if(lat != 0 && lnt != 0) {
                	// 조회 성공 시에 조회했던 내 위도, 적도 정보 추가저장 
                    sql = "INSERT INTO LOC_HISTORY (ID, LAT, LNT, INSERT_DT) "+
                    	  "VALUES (IFNULL((SELECT MAX(ID) FROM LOC_HISTORY), 0) + 1, ?, ?, datetime('now','localtime'))";	
    	
    				preparedStatement = con.prepareStatement(sql);
    	            preparedStatement.setDouble(1, lat);
    	            preparedStatement.setDouble(2, lnt);
    			    preparedStatement.executeUpdate();
                }
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
		return resultList;
	}
	
	
	
	public WIFI_INTERFACE_MODEL detail(String mgr_no, double dis_tinct) {

		Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
    	WIFI_INTERFACE_MODEL model = new WIFI_INTERFACE_MODEL();
        
		try {
			dbConnection dbconnection = new dbConnection();
			con = dbconnection.connect();
						
			String sql = "SELECT MGR_NO, WRDOFC, MAIN_NM, ADRES1, ADRES2, INSTL_FLOOR, "+
								"INSTL_TY, INSTL_MBY, SVC_SE, CMCWR, CNSTC_YEAR, INOUT_DOOR, "+
								"REMARS3, LNT, LAT, " +
								"strftime('%Y-%m-%d %H:%M', WORK_DTTM) AS WORK_DTTM "+
			             " FROM  WIFI_INTERFACE " +
						 " WHERE MGR_NO = ? ";
			
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, mgr_no);
            rs = preparedStatement.executeQuery();
            System.out.println("위치 정보 상세를 조회합니다. ");
            
            while(rs.next()) {
            	model.setMgr_no(rs.getString("MGR_NO"));
            	model.setWrdofc(rs.getString("WRDOFC"));
            	model.setMain_nm(rs.getString("MAIN_NM"));
            	model.setAdres1(rs.getString("ADRES1"));
            	model.setAdres2(rs.getString("ADRES2"));
            	model.setInstl_floor(rs.getString("INSTL_FLOOR"));
            	model.setInstl_ty(rs.getString("INSTL_TY"));
            	model.setInstl_mby(rs.getString("INSTL_MBY"));
            	model.setSvc_se(rs.getString("SVC_SE"));
            	model.setCmcwr(rs.getString("CMCWR"));
            	model.setCnstc_year(rs.getString("CNSTC_YEAR"));
            	model.setInout_door(rs.getString("INOUT_DOOR"));
            	model.setRemars3(rs.getString("REMARS3"));
            	model.setLnt(rs.getDouble("LNT"));
            	model.setLat(rs.getDouble("LAT"));
            	model.setStr_work_dttm(rs.getString("WORK_DTTM"));
            	model.setDis_tinct(dis_tinct);
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
	
	// 하버사인 공식을 사용하여 거리 계산
	public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // 위도와 경도를 라디안으로 변환
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double deltaLat = Math.toRadians(lat2 - lat1);
        double deltaLon = Math.toRadians(lon2 - lon1);

        // 하버사인 공식 계산
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                   Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                   Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c ; // 거리 (km 단위)
    }

	
}
