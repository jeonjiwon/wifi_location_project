<%@page import="java.util.List"%>
<%@page import="service.BookMark_Service"%>
<%@page import="models.BOOK_MARK_GROUP_MODEL"%>
<%@page import="java.net.URLEncoder"%>

<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>북마크 그룹</title>
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
	<%
		BookMark_Service service = new BookMark_Service();
		List<BOOK_MARK_GROUP_MODEL> list = service.group_list();
	%>
	
	<h2>북마크 그룹 목록</h2>
	<%@ include file="condition_tag.jsp" %>
	<form>   
		<input type="button" value="북마크 그룹 이름 추가" id="add_bookmark_group">
	</form>
	<table>
		<thead style="background-color:#00AC6F">
			<tr>
				<th>ID</th>
				<th>북마크 이름</th>
				<th>순서</th>
				<th>등록일자</th>
				<th>수정일자</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<% 
					for(BOOK_MARK_GROUP_MODEL model : list) {
				%>
					<tr>
						<td> <%= model.getGroup_id() %> </td>
						<td> <%= model.getGroup_nm() %> </td>
						<td> <%= model.getDisp_sq() %> </td>
						<td> <%= model.getInsert_dt() %> </td>
						<td> <%= model.getUpdate_dt() %> </td>
						<td>
							<a href="bookmark_group_modify.jsp?group_id=<%= URLEncoder.encode(model.getGroup_id(), "UTF-8") %>">
							    <p>수정</p>
							</a>
							<a href="bookmark_group_delete.jsp?group_id=<%= URLEncoder.encode(model.getGroup_id(), "UTF-8") %>">
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
	<script>
		document.getElementById("add_bookmark_group").addEventListener("click", function() {
	      	window.location.href = "bookmark_group_add.jsp";
		});
	</script>
</body>
</html>