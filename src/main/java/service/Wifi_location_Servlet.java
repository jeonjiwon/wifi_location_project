package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import models.BOOK_MARK_GROUP_MODEL;
import models.WIFI_INTERFACE_MODEL;


@WebServlet("/wifi_location")
public class Wifi_location_Servlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
        	double lat = 0;
        	double lnt = 0;
        	if(request.getParameter("lat") != null && request.getParameter("lat") != null ) {
    	        lat = Double.parseDouble(request.getParameter("lat"));
    	        lnt = Double.parseDouble(request.getParameter("lnt"));
        	}
        	
            Wifi_location_Service wifidao = new Wifi_location_Service();
            List<WIFI_INTERFACE_MODEL> list = wifidao.list(lat, lnt);
            
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
}
