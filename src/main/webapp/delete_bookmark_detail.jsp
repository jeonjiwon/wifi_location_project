<%@page import="java.net.URLEncoder"%>
<%@page import="models.BOOK_MARK_DETAIL_MODEL"%>
<%@page import="java.util.List"%>
<%@page import="service.BookMark_Service"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="kr">
<head>
	<meta charset="UTF-8">
	<title>북마크 삭제</title>
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
    	String mgr_no = request.getParameter("mgr_no");
		String group_id = request.getParameter("group_id");
		BookMark_Service service = new BookMark_Service();
		BOOK_MARK_DETAIL_MODEL model = service.group_detail_selectOne(mgr_no, group_id);
	%>
	<table>
        <colgroup>
            <col style="width:20%; background-color:#00AC6F;" />
            <col style="width:80%;"/>
        </colgroup>
         <tbody>
            <tr>
                <th>북마크이름</th>
                <td><input type="text" id="group_nm" name="group_nm"></td>
            </tr>
            <tr>
                <th>와이파이명</th>
                <td><input type="text" id="main_nm" name="main_nm" ></td>
            </tr>
            <tr>
                <th>등록일자</th>
                <td><input type="text" id="insert_dt" name="insert_dt" ></td>
            </tr>
         </tbody>
	</table>
	
	
	<form action="BookMark_Servlet" method="post">        
		<a href="list_bookmark_detail.jsp">돌아가기</a>
		<input type="button" value="삭제" id="delete_detail_bookmark">
	</form>
	
	<script>
		var group_nm = '<%= model.getGroup_nm() %>';
		var main_nm = '<%= model.getMain_nm()%>';
		var insert_dt = '<%= model.getInsert_dt()%>';
		var group_id = '<%= model.getGroup_id()%>';
		var mgr_no = '<%= model.getMgr_no()%>';
		
		document.getElementById("group_nm").value = group_nm;
		document.getElementById("main_nm").value = main_nm;
		document.getElementById("insert_dt").value = insert_dt;
	
		document.getElementById("delete_detail_bookmark").addEventListener("click", function() {         	
            // JavaScript 객체 생성
            var data = {
                "group_id": group_id,
                "mgr_no": mgr_no,
                "action" : "delete"
            };
            
            // JSON 문자열로 변환
            var jsonData = JSON.stringify(data);
            
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "/bookMark_detail", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

            xhr.onreadystatechange = function() {
            	 if (xhr.readyState == 4) {
           	        if (xhr.status == 200) {
           	            alert("선택한 북마크 목록이 삭제되었습니다.");
           	        } else {
           	            alert("북마크 목록 삭제 시 오류가 발생했습니다." );
           	        }
       	            // 확인 버튼을 누른 후에 페이지 이동
           	        window.location.href = "list_bookmark_detail.jsp";
           	    }
            };

            xhr.send(jsonData);
		});
	</script>
</body>
</html>