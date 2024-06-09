<%@page import="models.WIFI_INTERFACE_MODEL"%>
<%@page import="java.util.List"%>
<%@page import="service.Wifi_location_Service"%>

<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>

<html lang="kr">
<head>
    <meta charset="UTF-8">
    <title>와이파이 정보</title>
    <style>
        table {
            width: 100%;
        }
        th, td {
            border: solid 1px #000;
        }
    </style>
    
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
</head>
<body>
    <% 
        // 파라미터로 전달된 mgr_no 값 가져오기
        String mgr_no = request.getParameter("mgr_no");
    	double dis_tinct = Double.parseDouble(request.getParameter("dis_tinct"));
        Wifi_location_Service service = new Wifi_location_Service();
        WIFI_INTERFACE_MODEL model = service.detail(mgr_no, dis_tinct);
    %>
    
    
    <form action="/bookmark_detail_add_form" method="post">
        <select id="select_group_id" name="select_group_id">
            <option value="">북마크 그룹 이름 선택</option>
        </select>
        <input type="button" value="즐겨찾기 추가하기" id ="add_bookmark_detail">
    </form>
    
    <table>
        <colgroup>
            <col style="width:20%; background-color:#00AC6F;" />
            <col style="width:80%;"/>
        </colgroup>
        <tbody>
            <tr>
                <th>거리(Km)</th>
                <td><%= model.getDis_tinct() %></td>
            </tr>
            <tr>
                <th>관리번호</th>
                <td><%= model.getMgr_no() %></td>
            </tr>
            <tr>
                <th>자치구</th>
                <td><%= model.getWrdofc() %></td>
            </tr>
            <tr>
                <th>와이파이명</th>
                <td><%= model.getMain_nm() %></td>
            </tr>
            <tr>
                <th>도로명주소</th>
                <td><%= model.getAdres1() %></td>
            </tr>
            <tr>
                <th>상세주소</th>
                <td><%= model.getAdres2() %></td>
            </tr>
            <tr>
                <th>설치위치(층)</th>
                <td><%= model.getInstl_floor() %></td>
            </tr>
            <tr>
                <th>설치유형</th>
                <td><%= model.getInstl_ty() %></td>
            </tr>
            <tr>
                <th>설치기관</th>
                <td><%= model.getInstl_mby() %></td>
            </tr>
            <tr>
                <th>서비스구분</th>
                <td><%= model.getSvc_se() %></td>
            </tr>
            <tr>
                <th>망종류</th>
                <td><%= model.getCmcwr() %></td>
            </tr>
            <tr>
                <th>설치년도</th>
                <td><%= model.getCnstc_year() %></td>
            </tr>
            <tr>
                <th>실내외구분</th>
                <td><%= model.getInout_door() %></td>
            </tr>
            <tr>
                <th>WIFI 접속환경</th>
                <td><%= model.getRemars3() %></td>
            </tr>
            <tr>
                <th>X좌표</th>
                <td><%= model.getLnt() %></td>
            </tr>
            <tr>
                <th>Y좌표</th>
                <td><%= model.getLat() %></td>
            </tr>
            <tr>
                <th>작업일자</th>
                <td><%= model.getStr_work_dttm() %></td>
            </tr>
        </tbody>
    </table>
    <script>		
    
	    $(document).ready(function() {
	        $.ajax({
	            url: '/bookMark_detail', // 서버에서 데이터를 가져올 URL
	            method: 'GET',
	            dataType: 'json',
	            success: function(response) {
	                var selectGroup = $("#select_group_id");
	                $.each(response.data, function(index, item) {
	                    var option = $("<option></option>").val(item.group_id).text(item.group_nm);
	                    selectGroup.append(option);
	                });
	            },
	            error: function(xhr, status, error) {
	                console.error('데이터를 가져오는 중 오류 발생:', error);
	            }
	        });
	    });
	    
	    
		var mgr_no = '<%= model.getMgr_no() %>';
		
		document.getElementById("add_bookmark_detail").addEventListener("click", function() {
			var group_id = document.getElementById("select_group_id").value;
         	
            // 필수값이 모두 입력되었는지 검사
            if (group_id.trim() === "") {
                alert("추가할 북마크 항목을 선택바랍니다.");
                return;
            }
            
            // JavaScript 객체 생성
            var data = {
                "group_id": group_id,
                "mgr_no": mgr_no,
                "action" : "add"
            };
            
            // JSON 문자열로 변환
            var jsonData = JSON.stringify(data);
            
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "/bookMark_detail", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

            xhr.onreadystatechange = function() {
            	 if (xhr.readyState == 4) {
           	        if (xhr.status == 200) {
           	            alert("북마크 그룹을 등록하였습니다.");
           	        } else {
           	            alert("북마크 처리 시 오류가 발생했습니다.");
           	        }
           	    }
            };

            xhr.send(jsonData);
		});
	</script>
</body>
</html>