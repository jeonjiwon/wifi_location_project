package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.BOOK_MARK_DETAIL_MODEL;
import models.BOOK_MARK_GROUP_MODEL;

public class BookMark_Service {
	
	public List<BOOK_MARK_GROUP_MODEL> group_list() {

		Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<BOOK_MARK_GROUP_MODEL> list = new ArrayList<>();
        
		try {
			dbConnection dbconnection = new dbConnection();
			con = dbconnection.connect();
						
			String sql =   "SELECT GROUP_ID, GROUP_NM, DISP_SQ," 
						 + "strftime('%Y-%m-%d %H:%M', INSERT_DT) AS INSERT_DT, "
						 + "strftime('%Y-%m-%d %H:%M', UPDATE_DT) AS UPDATE_DT  "
						 + "FROM   BOOK_MARK_GROUP "
						 + "ORDER BY GROUP_ID";
			
			preparedStatement = con.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            System.out.println("북마크 그룹 리스트 조회합니다. ");
            
            while(rs.next()) {
            	BOOK_MARK_GROUP_MODEL model = new BOOK_MARK_GROUP_MODEL();
            	model.setGroup_id(rs.getString("GROUP_ID"));
            	model.setGroup_nm(rs.getString("GROUP_NM"));
            	model.setDisp_sq(rs.getInt("DISP_SQ"));
            	model.setInsert_dt(rs.getString("INSERT_DT"));
            	model.setUpdate_dt(rs.getString("UPDATE_DT"));

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
	
	public BOOK_MARK_GROUP_MODEL group_selectOne(String group_id) {

		Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        BOOK_MARK_GROUP_MODEL model = new BOOK_MARK_GROUP_MODEL();
        
		try {
			dbConnection dbconnection = new dbConnection();
			con = dbconnection.connect();
						
			String sql =   "SELECT GROUP_ID, GROUP_NM, DISP_SQ," 
						 + "strftime('%Y-%m-%d %H:%M', INSERT_DT) AS INSERT_DT, "
						 + "strftime('%Y-%m-%d %H:%M', UPDATE_DT) AS UPDATE_DT  "
						 + "FROM   BOOK_MARK_GROUP "
						 + "WHERE  GROUP_ID = ? ";
			
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, group_id);
            rs = preparedStatement.executeQuery();
            System.out.println("북마크 그룹 한개 조회합니다. (BY GROUP_ID) ");
            
            while(rs.next()) {
            	model.setGroup_id(rs.getString("GROUP_ID"));
            	model.setGroup_nm(rs.getString("GROUP_NM"));
            	model.setDisp_sq(rs.getInt("DISP_SQ"));
            	model.setInsert_dt(rs.getString("INSERT_DT"));
            	model.setUpdate_dt(rs.getString("UPDATE_DT"));

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
	
	public boolean group_insert(BOOK_MARK_GROUP_MODEL model) {

		Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        
		try {
			if(model.getGroup_nm() == null || model.getDisp_sq() == 0) {
				return false;
			}
			
			dbConnection dbconnection = new dbConnection();
			con = dbconnection.connect();
						
			String sql = "INSERT INTO BOOK_MARK_GROUP " + 
							" (GROUP_ID, GROUP_NM, DISP_SQ, INSERT_DT) "+ 
 						" VALUES (IFNULL((SELECT MAX(GROUP_ID) FROM BOOK_MARK_GROUP), 0) + 1" + 
						       ", ?, ?, datetime('now','localtime'))" ;

			
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, model.getGroup_nm());
			preparedStatement.setInt(2, model.getDisp_sq());			
			preparedStatement.executeUpdate();
            
			System.out.println("북마크 그룹을 생성합니다. ");
    		
			if (con != null) { 
        		try {
    				con.close();
    			} catch (SQLException e1) {
    				e1.printStackTrace();
    			} 
        	}
			return true;
        	
		} catch(Exception e) {

	    	if (con != null) { 
	    		try {
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
	    	}
			return false;
		}
	}
	
	public boolean group_update(BOOK_MARK_GROUP_MODEL model) {

		Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        
		try {
			if(model.getGroup_nm() == null || model.getDisp_sq() == 0) {
				return false;
			}
			
			dbConnection dbconnection = new dbConnection();
			con = dbconnection.connect();
						
			String sql =  "UPDATE 	BOOK_MARK_GROUP "
						+ "SET 		GROUP_NM = ?, DISP_SQ = ?, UPDATE_DT = datetime('now','localtime') "
						+ "WHERE 	GROUP_ID  = ? " ;
			
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, model.getGroup_nm());
			preparedStatement.setInt(2, model.getDisp_sq());
			preparedStatement.setString(3, model.getGroup_id());
			
			
            preparedStatement.executeUpdate();
            System.out.println("북마크 그룹내용을 수정합니다. [" + model.getGroup_id() +"]");
    		if (con != null) { 
        		try {
    				con.close();
    			} catch (SQLException e1) {
    				e1.printStackTrace();
    			} 
        	}
    		return true;
        	
		} catch(Exception e) {

	    	if (con != null) {
	    		try {
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
	    	}
	    	return false;
		}
	}
	
	public boolean group_delete(BOOK_MARK_GROUP_MODEL model) {

		Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        
		try {
			if(model.getGroup_id() == null) {
				return false;
			}
			
			dbConnection dbconnection = new dbConnection();
			con = dbconnection.connect();
						
			String sql = "DELETE FROM BOOK_MARK_GROUP WHERE GROUP_ID = ?";
			
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, model.getGroup_id());
			
            preparedStatement.executeUpdate();
            System.out.println("북마크 그룹 삭제합니다. [" + model.getGroup_id() +"]");
    		if (con != null) { 
        		try {
    				con.close();
    			} catch (SQLException e1) {
    				e1.printStackTrace();
    			} 
        	}
        	
    		return true;
		} catch(Exception e) {

	    	if (con != null) { 
	    		try {
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
	    	}
	    	return false;
		}
	}
	
	
	/////////////////////////// 북마크 디테일 관련 ////////////////////////////
	public List<BOOK_MARK_DETAIL_MODEL> group_detail_list() {

		Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<BOOK_MARK_DETAIL_MODEL> list = new ArrayList<>();
        
		try {
			dbConnection dbconnection = new dbConnection();
			con = dbconnection.connect();
						
			String sql =   "SELECT MST.GROUP_NM, WI.MAIN_NM, DTL.GROUP_ID, DTL.MGR_NO,  " 
						 + "strftime('%Y-%m-%d %H:%M', DTL.INSERT_DT) AS INSERT_DT "
						 + "FROM   BOOK_MARK_DETAIL DTL "
						 + "INNER JOIN WIFI_INTERFACE WI ON DTL.MGR_NO = WI.MGR_NO "
						 + "INNER JOIN BOOK_MARK_GROUP MST ON MST.GROUP_ID = DTL.GROUP_ID "
						 + "ORDER BY DTL.GROUP_ID, DTL.GROUP_ID";
			
			preparedStatement = con.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            System.out.println("북마크 상세 리스트 조회합니다. ");
            
            while(rs.next()) {
            	BOOK_MARK_DETAIL_MODEL model = new BOOK_MARK_DETAIL_MODEL();
            	model.setGroup_id(rs.getString("GROUP_ID"));
            	model.setGroup_nm(rs.getString("GROUP_NM"));
            	model.setMgr_no(rs.getString("MGR_NO"));
            	model.setMain_nm(rs.getString("MAIN_NM"));
            	model.setInsert_dt(rs.getString("INSERT_DT"));

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
	
	public BOOK_MARK_DETAIL_MODEL group_detail_selectOne(String mgr_no, String group_id) {

		Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
    	BOOK_MARK_DETAIL_MODEL model = new BOOK_MARK_DETAIL_MODEL();
        
		try {
			dbConnection dbconnection = new dbConnection();
			con = dbconnection.connect();
						
			String sql =   "SELECT MST.GROUP_NM, WI.MAIN_NM, DTL.GROUP_ID, DTL.MGR_NO, " 
						 + "strftime('%Y-%m-%d %H:%M', DTL.INSERT_DT) AS INSERT_DT "
						 + "FROM   BOOK_MARK_DETAIL DTL "
						 + "LEFT OUTER JOIN WIFI_INTERFACE WI ON DTL.MGR_NO = WI.MGR_NO "
						 + "LEFT OUTER JOIN BOOK_MARK_GROUP MST ON MST.GROUP_ID = DTL.GROUP_ID "
						 + "WHERE DTL.GROUP_ID = ? AND DTL.MGR_NO = ? ";
			
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, group_id);
			preparedStatement.setString(2, mgr_no);
            rs = preparedStatement.executeQuery();
            
            System.out.println("북마크 상세 리스트 한건 조회합니다.(삭제버튼 클릭 시) ");

            while(rs.next()) {
	        	model.setGroup_id(rs.getString("GROUP_ID"));
	        	model.setGroup_nm(rs.getString("GROUP_NM"));
	        	model.setMgr_no(rs.getString("MGR_NO"));
	        	model.setMain_nm(rs.getString("MAIN_NM"));
	        	model.setInsert_dt(rs.getString("INSERT_DT"));
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
	
	public boolean bookmark_detail_insert(BOOK_MARK_DETAIL_MODEL model) {

		Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        
		try {
			dbConnection dbconnection = new dbConnection();
			con = dbconnection.connect();
						
			String sql = "INSERT INTO BOOK_MARK_DETAIL (MGR_NO, GROUP_ID, INSERT_DT) "
					   + "VALUES(?, ?, datetime('now','localtime'))";
			
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, model.getMgr_no());
			preparedStatement.setString(2, model.getGroup_id());
			
            preparedStatement.executeUpdate();
            System.out.println("북마크 디테일 등록 합니다.");
    		if (con != null) { 
        		try {
    				con.close();
    			} catch (SQLException e1) {
    				e1.printStackTrace();
    			} 
        	}
    		return true;
        	
		} catch(Exception e) {

	    	if (con != null) { 
	    		try {
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
	    	}
	    	return false;
		}
	}

	public boolean bookmark_detail_delete(BOOK_MARK_DETAIL_MODEL model) {

		Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        
		try {
			dbConnection dbconnection = new dbConnection();
			con = dbconnection.connect();
						
			String sql = "DELETE FROM BOOK_MARK_DETAIL WHERE GROUP_ID = ? AND MGR_NO = ?";
			
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, model.getGroup_id());
			preparedStatement.setString(2, model.getMgr_no());
			
            preparedStatement.executeUpdate();
            System.out.println("북마크 디테일 삭제합니다.");
    		if (con != null) { 
        		try {
    				con.close();
    			} catch (SQLException e1) {
    				e1.printStackTrace();
    			} 
        	}
    		
    		return true;
        	
		} catch(Exception e) {

	    	if (con != null) { 
	    		try {
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
	    	}
	    	return false;
		}
	}
}
