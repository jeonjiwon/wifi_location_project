package service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.sqlite.util.StringUtils;

import models.WIFI_INTERFACE_MODEL;

public class OpenApiWifi_Service {
	
	public String interface_wifi() {
		HttpURLConnection conn = null;
		URL url = null;
		BufferedReader rd = null;
		StringBuilder sb = new StringBuilder();
		String line;
		
		int pageIndex = 1000; // API 페이징 처리 건수 
		int startIdx = 1;
		int endIdx = pageIndex;
		int totalCnt  = pageIndex;
		int callCnt = 1;

		Connection db_conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        
        
		try {
			
			dbConnection dbconnection = new dbConnection();
			db_conn = dbconnection.connect();
			while(true) {
				
				if(endIdx > totalCnt) endIdx = totalCnt;
				
				System.out.printf("%d 시작인덱스 %d종료인덱스\n", startIdx, endIdx);
				
				StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
				urlBuilder.append("/" + URLEncoder.encode("6949754972776a73353841664b4444", "UTF-8")); 
				urlBuilder.append("/" + URLEncoder.encode("xml", "UTF-8"));
				urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo", "UTF-8"));
				urlBuilder.append("/" + URLEncoder.encode(String.valueOf(startIdx), "UTF-8")); 
				urlBuilder.append("/" + URLEncoder.encode(String.valueOf(endIdx), "UTF-8"));
				
				
//				urlBuilder.append("/" + URLEncoder.encode("", "UTF-8"));  //X_SWIFI_WRDOFC
//				urlBuilder.append("/" + URLEncoder.encode("", "UTF-8"));  //X_SWIFI_ADRES1
				
				url = new URL(urlBuilder.toString());
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Content-type", "application/xml");
				
				System.out.println("Response code: " + conn.getResponseCode());
				
				if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {

					// 기존 데이터 삭제하고 새로 등록 
					String sql_delete = "DELETE FROM WIFI_INTERFACE ";
					preparedStatement = db_conn.prepareStatement(sql_delete);
		            preparedStatement.executeUpdate();
					
					// 성공 (200~300)
					rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

					String sql = " INSERT INTO WIFI_INTERFACE"
							+ "	(  	MGR_NO, 		WRDOFC,			MAIN_NM,  "
							+ "		ADRES1,			ADRES2, "
							+ "		INSTL_FLOOR,	INSTL_TY,		INSTL_MBY, "
							+ "		SVC_SE,			CMCWR,			CNSTC_YEAR,"
							+ "		INOUT_DOOR,		REMARS3, "
							+ "		LNT,			LAT,			WORK_DTTM "
							+ "	 ) "
							+ "	VALUES"
							+ "	(  	?, 				?, 				?, "
							+ "		?,				?, "
							+ "		?,				?,				?, "
							+ "		?,				?,				?, "
							+ "		?,				?, "
							+ "		?,				?,				datetime('now','localtime') "
							+ "	) "
							;
					
					WIFI_INTERFACE_MODEL param = new WIFI_INTERFACE_MODEL();
					while ((line = rd.readLine()) != null) {
						sb.append(line);
						
						if(callCnt == 1 && line.startsWith("<list_total_count")) {
							String totalCount = MakeColumnData(line, "list_total_count");
							if(totalCount != null && totalCount != "") {
								totalCnt = Integer.parseInt(totalCount);
							}
						}
						else if(line.startsWith("<X_SWIFI_MGR_NO")) {
							param.setMgr_no(MakeColumnData(line, "X_SWIFI_MGR_NO"));
						}
						else if(line.startsWith("<X_SWIFI_WRDOFC")) {
							param.setWrdofc(MakeColumnData(line, "X_SWIFI_WRDOFC"));
						}
						else if(line.startsWith("<X_SWIFI_MAIN_NM")) {
							param.setMain_nm(MakeColumnData(line, "X_SWIFI_MAIN_NM"));
						}
						else if(line.startsWith("<X_SWIFI_ADRES1")) {
							param.setAdres1(MakeColumnData(line, "X_SWIFI_ADRES1"));
						}
						else if(line.startsWith("<X_SWIFI_ADRES2")) {
							param.setAdres2(MakeColumnData(line, "X_SWIFI_ADRES2"));
						}
						else if(line.startsWith("<X_SWIFI_INSTL_FLOOR")) {
							param.setInstl_floor(MakeColumnData(line, "X_SWIFI_INSTL_FLOOR"));
						}
						else if(line.startsWith("<X_SWIFI_INSTL_TY")) {
							param.setInstl_ty(MakeColumnData(line, "X_SWIFI_INSTL_TY"));
						}
						else if(line.startsWith("<X_SWIFI_INSTL_MBY")) {
							param.setInstl_mby(MakeColumnData(line, "X_SWIFI_INSTL_MBY"));
						}
						else if(line.startsWith("<X_SWIFI_SVC_SE")) {
							param.setSvc_se(MakeColumnData(line, "X_SWIFI_SVC_SE"));
						}
						else if(line.startsWith("<X_SWIFI_CMCWR")) {
							param.setCmcwr(MakeColumnData(line, "X_SWIFI_CMCWR"));
						}
						else if(line.startsWith("<X_SWIFI_CNSTC_YEAR")) {
							param.setCnstc_year(MakeColumnData(line, "X_SWIFI_CNSTC_YEAR"));
						}
						else if(line.startsWith("<X_SWIFI_INOUT_DOOR")) {
							param.setInout_door(MakeColumnData(line, "X_SWIFI_INOUT_DOOR"));
						}
						else if(line.startsWith("<X_SWIFI_REMARS3")) {
							param.setRemars3(MakeColumnData(line, "X_SWIFI_REMARS3"));
						}
						else if(line.startsWith("<LAT")) {
							param.setLnt(Double.parseDouble(MakeColumnData(line, "LAT")));
						}
						else if(line.startsWith("<LNT")) {
							param.setLnt(Double.parseDouble(MakeColumnData(line, "LNT")));
						}
						else if(line.startsWith("</row>")) {
							
							preparedStatement = db_conn.prepareStatement(sql);
				            preparedStatement.setString(1, param.getMgr_no());
				            preparedStatement.setString(2, param.getWrdofc());
				            preparedStatement.setString(3, param.getMain_nm());
				            preparedStatement.setString(4, param.getAdres1());
				            preparedStatement.setString(5, param.getAdres2());
				            preparedStatement.setString(6, param.getInstl_floor());
				            preparedStatement.setString(7, param.getInstl_ty());
				            preparedStatement.setString(8, param.getInstl_mby());
				            preparedStatement.setString(9, param.getSvc_se());
				            preparedStatement.setString(10, param.getCmcwr());
				            preparedStatement.setString(11, param.getCnstc_year());
				            preparedStatement.setString(12, param.getInout_door());
				            preparedStatement.setString(13, param.getRemars3());
				            preparedStatement.setDouble(14, param.getLat());
				            preparedStatement.setDouble(15, param.getLnt());
				            
				            preparedStatement.executeUpdate();		
				            param = new WIFI_INTERFACE_MODEL();
						}
					}
					
				
				} 
				else {
					// 실패
					rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				}
				
				
				startIdx = (pageIndex * callCnt) + 1;
				callCnt ++;
				endIdx = (pageIndex * callCnt);
				
				if(startIdx > totalCnt) {
					break;
				}
				
			}
			
			if(rd != null) rd.close();
			if(conn != null) conn.disconnect();
			
		} catch (Exception e) {
            e.printStackTrace();
            return "위치 정보를 가져오는데 오류가 발생했습니다. ";
		} finally {
		      try {
			      if (db_conn != null) { db_conn.close();}
		  } catch (SQLException ex) {
		      System.out.println(ex.getMessage());
		  }
		}
		System.out.println(totalCnt + "개의 WIFI 정보를 정상적으로 저장하였습니다. ");
		return totalCnt + "개의 WIFI 정보를 정상적으로 저장하였습니다. ";
	}
	
	
	
	public String MakeColumnData(String line, String findColumn) {
		if(line.equals("<"+findColumn+"/>")) {
			return "";
		}
		else return (line.split("<" + findColumn + ">")[1]).split("</" + findColumn + ">")[0];
	}
	
	
}
