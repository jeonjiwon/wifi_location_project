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
import models.LOC_HISTORY_MODEL;
import models.WIFI_INTERFACE_MODEL;


@WebServlet("/mylocation_history")
public class Location_History_Servlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
        	Location_History_Service dao = new Location_History_Service();
        	LOC_HISTORY_MODEL model = dao.select_mylocation();
            
            // 응답을 JSON 형식으로 설정
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // JSON 객체를 생성하고 데이터를 담음
            Gson gson = new Gson();
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.add("data", gson.toJsonTree(model));

            // JSON 문자열을 응답으로 전송
            response.getWriter().write(jsonResponse.toString());

            
        } catch (Exception e) {
            e.printStackTrace();
            // 오류 응답
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
