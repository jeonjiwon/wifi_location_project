<%@page import="service.BookMark_Service"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="models.BOOK_MARK_GROUP_MODEL"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>북마크 그룹 삭제</title>
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
        String group_id = request.getParameter("group_id");
		BookMark_Service service = new BookMark_Service();
		BOOK_MARK_GROUP_MODEL model = new BOOK_MARK_GROUP_MODEL();
		model = service.group_selectOne(group_id);// 모델에서 값 가져오기
    %>
	<table>
        <colgroup>
            <col style="width:20%; background-color:#00AC6F;" />
            <col style="width:80%;"/>
        </colgroup>
         <tbody>
            <tr>
                <th>북마크이름</th>
                <td><input type="text" id="group_nm" name="group_nm" required="required"></td>
            </tr>
            <tr>
                <th>순서</th>
                <td><input type="number" id="disp_sq" name="disp_sq" required="required"></td>
            </tr>
         </tbody>
	</table>

	<form action="BookMark_Servlet" method="post">        
		<a href="bookmark_group_list.jsp">돌아가기</a>
		<input type="button" value="삭제" id="delete_bookmark">
	</form>
	
	<script>
		var group_id = '<%= model.getGroup_id() %>';
		var group_nm = '<%= model.getGroup_nm() %>';
		document.getElementById("group_nm").value = group_nm;
		var disp_sq = '<%= model.getDisp_sq()  %>';
		document.getElementById("disp_sq").value = disp_sq;
	
		document.getElementById("delete_bookmark").addEventListener("click", function() {         	
            // JavaScript 객체 생성
            var data = {
                "group_id": group_id,
                "action" : "delete"
            };
            
            // JSON 문자열로 변환
            var jsonData = JSON.stringify(data);
            
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "/bookMark", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

            xhr.onreadystatechange = function() {
            	 if (xhr.readyState == 4) {
           	        if (xhr.status == 200) {
           	            alert("선택한 북마크 그룹이 삭제되었습니다.");
           	        } else {
           	            alert("북마크 삭제 시 오류가 발생했습니다." );
           	        }
       	            // 확인 버튼을 누른 후에 페이지 이동
           	        window.location.href = "bookmark_group_list.jsp";
           	    }
            };

            xhr.send(jsonData);
		});
	</script>
</body>
</html>