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
            
            // ������ JSON �������� ����
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // JSON ��ü�� �����ϰ� �����͸� ����
            Gson gson = new Gson();
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.add("data", gson.toJsonTree(list));

            // JSON ���ڿ��� �������� ����
            response.getWriter().write(jsonResponse.toString());

            
        } catch (Exception e) {
            e.printStackTrace();
            // ���� ����
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Ŭ���̾�Ʈ���� ���۵� �������� ���ڵ��� UTF-8�� ����
        request.setCharacterEncoding("UTF-8");
        
        // Ŭ���̾�Ʈ�κ��� ���� JSON ������ �Ľ�
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
	            result = "�ϸ�ũ�� ���������� ����Ͽ����ϴ�.";
	        } else {
	            result = "�ϸ�ũ ��� �� ������ �߻��߽��ϴ�.";
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
	            result = "�ϸ�ũ�� ���������� �����Ͽ����ϴ�.";
	        } else {
	            result = "�ϸ�ũ ���� �� ������ �߻��߽��ϴ�.";
	        }
	
	        request.setAttribute("result", result);
	        request.getRequestDispatcher("delete_bookmark_detail.jsp").forward(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
}
