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

        // Ŭ���̾�Ʈ���� ���۵� �������� ���ڵ��� UTF-8�� ����
        request.setCharacterEncoding("UTF-8");
        
        // Ŭ���̾�Ʈ�κ��� ���� JSON ������ �Ľ�
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
	            result = "�ϸ�ũ�� ���������� �����Ͽ����ϴ�.";
	        } else {
	            result = "�ϸ�ũ ���� �� ������ �߻��߽��ϴ�.";
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
	            result = "�ϸ�ũ�� ���������� �����Ͽ����ϴ�.";
	        } else {
	            result = "�ϸ�ũ ���� �� ������ �߻��߽��ϴ�.";
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
	            result = "�ϸ�ũ�� ���������� �����Ͽ����ϴ�.";
	        } else {
	            result = "�ϸ�ũ ���� �� ������ �߻��߽��ϴ�.";
	        }
	
	        request.setAttribute("result", result);
	        request.getRequestDispatcher("bookmark_group_delete.jsp").forward(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
}
