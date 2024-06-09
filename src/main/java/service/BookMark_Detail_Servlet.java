package service;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import models.BOOK_MARK_DETAIL_MODEL;
import models.BOOK_MARK_GROUP_MODEL;
import models.LOC_HISTORY_MODEL;


@WebServlet("/bookMark_detail")
public class BookMark_Detail_Servlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
        	BookMark_Service dao = new BookMark_Service();
        	List<BOOK_MARK_GROUP_MODEL> list = dao.group_list();
            
            // 응답을 JSON 형식으로 설정
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // JSON 객체를 생성하고 데이터를 담음
            Gson gson = new Gson();
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.add("data", gson.toJsonTree(list));

            // JSON 문자열을 응답으로 전송
            response.getWriter().write(jsonResponse.toString());

            
        } catch (Exception e) {
            e.printStackTrace();
            // 오류 응답
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 클라이언트에서 전송된 데이터의 인코딩을 UTF-8로 설정
        request.setCharacterEncoding("UTF-8");
        
        // 클라이언트로부터 받은 JSON 데이터 파싱
        BufferedReader reader = request.getReader();
        JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

        String group_id = null;
        String mgr_no = null;
        String action = null;
        
        if(jsonObject.get("group_id") != null) {
        	group_id = jsonObject.get("group_id").getAsString();
        }
        if(jsonObject.get("mgr_no") != null) {
        	mgr_no = jsonObject.get("mgr_no").getAsString();
        }
        if(jsonObject.get("action") != null) {
        	action = jsonObject.get("action").getAsString();
        }
        
        BOOK_MARK_DETAIL_MODEL model = new BOOK_MARK_DETAIL_MODEL();
        model.setGroup_id(group_id);
        model.setMgr_no(mgr_no);
        
        if("add".equals(action)) {
        	insert_bookMarkDetail(request, response, model);
        } else if("delete".equals(action)) {
        	delete_bookMarkDetail(request, response, model);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
        
        
    }
    
    protected void insert_bookMarkDetail(HttpServletRequest request, HttpServletResponse response, BOOK_MARK_DETAIL_MODEL model)
            throws ServletException, IOException {
    	try {
	        String result;
	
	        BookMark_Service bookmarkDao = new BookMark_Service();        
	        if (bookmarkDao.bookmark_detail_insert(model)) {
	            result = "북마크를 성공적으로 등록하였습니다.";
	        } else {
	            result = "북마크 등록 시 오류가 발생했습니다.";
	        }
	
	        request.setAttribute("result", result);
	        request.getRequestDispatcher("list_wifi_location_detail.jsp").forward(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    protected void delete_bookMarkDetail(HttpServletRequest request, HttpServletResponse response, BOOK_MARK_DETAIL_MODEL model)
            throws ServletException, IOException {
    	try {
	        String result;
	
	        BookMark_Service bookmarkDao = new BookMark_Service();        
	        if (bookmarkDao.bookmark_detail_delete(model)) {
	            result = "북마크를 성공적으로 삭제하였습니다.";
	        } else {
	            result = "북마크 삭제 시 오류가 발생했습니다.";
	        }
	
	        request.setAttribute("result", result);
	        request.getRequestDispatcher("delete_bookmark_detail.jsp").forward(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
}
