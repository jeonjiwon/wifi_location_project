<%@page import="models.LOC_HISTORY_MODEL"%>
<%@page import="java.util.List"%>
<%@page import="service.Location_History_Service"%>

<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>위치 히스토리 목록</title>
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
		Location_History_Service service = new Location_History_Service();
		List<LOC_HISTORY_MODEL> list = service.list();
		
	%>
	<h2>위치 히스토리 목록</h2>
	
	<%@ include file="condition_tag.jsp" %>
	
	<table >
		<thead style="background-color:#00AC6F">
			<tr>
				<th>ID</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>조회일자</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<% 
					for(LOC_HISTORY_MODEL model : list){
				%>
					<tr>
						<td> <%= model.getId() %> </td>
						<td> <%= model.getLnt() %> </td>
						<td> <%= model.getLat() %> </td>
						<td> <%= model.getInsert_dt() %> </td>
						<td>
	                        <input type="button" value="삭제" id="delete_mylocation"> 
		  					
						</td>
					</tr>
					
				<%
					}
				%>
			</tr>
		</tbody>
	</table>
	<script>
	    // 삭제 버튼 클릭 시 이벤트 처리
	    var deleteButtons = document.querySelectorAll("#delete_mylocation");
	    deleteButtons.forEach(function(button) {
	    	 button.addEventListener("click", function() {
	             // 현재 선택된 행의 ID 가져오기
	             var id = this.parentNode.parentNode.querySelector("td:first-child").innerText;
	             
	             // URL에 파라미터 추가하여 페이지 리디렉션
	             window.location.href = "delete_location_history.jsp?id=" + id;
	         });
	    });
	</script>
</body>
</html>