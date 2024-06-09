<%@ page import="service.OpenApiWifi_Service" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<title>서울시 공공와이파이 서비스 위치 정보</title>
</head>
<body>
	<%
		OpenApiWifi_Service openApiWifi = new OpenApiWifi_Service();
		String str = openApiWifi.interface_wifi();
		out.write(str);
	%>
	
	<div>
		<a href="index.html">홈으로 이동</a>
	</div>
</body>
</html>
