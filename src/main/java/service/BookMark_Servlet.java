package service;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import models.BOOK_MARK_GROUP_MODEL;


@WebServlet("/bookMark")
public class BookMark_Servlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 클라이언트에서 전송된 데이터의 인코딩을 UTF-8로 설정
        request.setCharacterEncoding("UTF-8");
        
        // 클라이언트로부터 받은 JSON 데이터 파싱
        BufferedReader reader = request.getReader();
        JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

        String group_id = null;
        String group_nm = null;
        int disp_sq = 0;
        String action = null;
        if(jsonObject.get("group_id") != null) {
        	group_id = jsonObject.get("group_id").getAsString();
        }
        if(jsonObject.get("group_nm") != null) {
        	group_nm = jsonObject.get("group_nm").getAsString();
        }
        if(jsonObject.get("disp_sq") != null) {
        	disp_sq = jsonObject.get("disp_sq").getAsInt();
        }
        if(jsonObject.get("action") != null) {
        	action = jsonObject.get("action").getAsString();
        }

        BOOK_MARK_GROUP_MODEL model = new BOOK_MARK_GROUP_MODEL();
        model.setGroup_id(group_id);
        model.setGroup_nm(group_nm);
        model.setDisp_sq(disp_sq);
        
        
        if ("add".equals(action)) {
            add_bookMarkGroup(request, response, model);
        } else if ("update".equals(action)) {
        	update_bookMarkGroup(request, response, model);
        }  else if ("delete".equals(action)) {
        	delete_bookMarkGroup(request, response, model);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    protected void add_bookMarkGroup(HttpServletRequest request, HttpServletResponse response, BOOK_MARK_GROUP_MODEL model)
   throws ServletException, IOException {
    	try {

            String result;
	        BookMark_Service bookmarkDao = new BookMark_Service();        
	        if (bookmarkDao.group_insert(model)) {
	            result = "북마크를 성공적으로 생성하였습니다.";
	        } else {
	            result = "북마크 생성 시 오류가 발생했습니다.";
	        }
	
	        request.setAttribute("result", result);
	        request.getRequestDispatcher("bookmark_group_add.jsp").forward(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    protected void update_bookMarkGroup(HttpServletRequest request, HttpServletResponse response, BOOK_MARK_GROUP_MODEL model)
            throws ServletException, IOException {
    	try {
	        String result;
	
	        BookMark_Service bookmarkDao = new BookMark_Service();        
	        if (bookmarkDao.group_update(model)) {
	            result = "북마크를 성공적으로 수정하였습니다.";
	        } else {
	            result = "북마크 수정 시 오류가 발생했습니다.";
	        }
	
	        request.setAttribute("result", result);
	        request.getRequestDispatcher("bookmark_group_modify.jsp").forward(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    protected void delete_bookMarkGroup(HttpServletRequest request, HttpServletResponse response, BOOK_MARK_GROUP_MODEL model)
            throws ServletException, IOException {
    	try {
	        String result;
	
	        BookMark_Service bookmarkDao = new BookMark_Service();        
	        if (bookmarkDao.group_delete(model)) {
	            result = "북마크를 성공적으로 삭제하였습니다.";
	        } else {
	            result = "북마크 삭제 시 오류가 발생했습니다.";
	        }
	
	        request.setAttribute("result", result);
	        request.getRequestDispatcher("bookmark_group_delete.jsp").forward(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
}
