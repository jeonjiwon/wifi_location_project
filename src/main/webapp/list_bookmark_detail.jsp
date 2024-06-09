<%@page import="java.net.URLEncoder"%>
<%@page import="models.BOOK_MARK_DETAIL_MODEL"%>
<%@page import="java.util.List"%>
<%@page import="service.BookMark_Service"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="kr">
<head>
	<meta charset="UTF-8">
	<title>북마크 목록</title>
	<style>
		table {
			width: 100%;
		}
		th, td {
			border: solid 1px #000;
		}
	</style>
</head>
<body>
	<h2>북마크 목록</h2>
	<%@ include file="condition_tag.jsp" %>
	
	<%
		BookMark_Service service = new BookMark_Service();
		List<BOOK_MARK_DETAIL_MODEL> list = service.group_detail_list();
	%>
	<table>
		<thead style="background-color:#00AC6F">
			<tr>
				<th>ID</th>
				<th>북마크 이름</th>
				<th>와이파이명</th>
				<th>등록일자</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<% 
					for(BOOK_MARK_DETAIL_MODEL model : list) {
				%>
					<tr>
						<td> <%= model.getGroup_id() %> </td>
						<td> <%= model.getGroup_nm() %> </td>
						<td> <%= model.getMain_nm() %> </td>
						<td> <%= model.getInsert_dt() %> </td>
						<td>
							<a href="delete_bookmark_detail.jsp?mgr_no=<%= URLEncoder.encode(model.getMgr_no(), "UTF-8") %>&group_id=<%= URLEncoder.encode(model.getGroup_id(), "UTF-8") %>">
							    <p>삭제</p>
							</a>
						</td>
					</tr>
					
				<%
					}
				%>
			</tr>
		</tbody>
	</table>

</body>
</html>