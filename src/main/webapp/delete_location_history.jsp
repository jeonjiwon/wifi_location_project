<%@page import="service.Location_History_Service"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
</head>
<body>
    <% 
        String id = request.getParameter("id");
    	Location_History_Service service = new Location_History_Service();
    	service.delete(id);
    %>
    
    <script>
        alert("선택한 내 위치 정보가 삭제되었습니다. ");

        // 확인 버튼을 누른 후에 페이지 이동
        window.location.href = "list_myhistory.jsp";
    </script>
</body>
</html>