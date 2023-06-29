<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<script>
	function movepage(){
		location.replace('join_1.html');		
	}
</script>
</head>
<body>
	<%@ page import="java.sql.*"%>
	<%
	request.setCharacterEncoding("utf-8");
	String s = request.getParameter("id");
	String message = ""; //중복확인 버튼옆에 표시할 텍스트

	String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	String user = "jsp";
	String password = "11111";

	try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, user, password);
		Statement st = con.createStatement();

		if (s != null) {
			String sql_id = "select id from member where id='" + s + "'";
			ResultSet rs = st.executeQuery(sql_id);

			rs.next();
			int count = rs.getRow();// 현재 행의 번호를 리턴, 
			// 만약 행이 하나도 없는 경우 0을 리턴

			if (count == 0)	message = "사용가능한 아이디입니다";
			else	message = "사용중인 아이디입니다";
		}
		con.close();
	} catch (Exception e) {
		out.println("ID불러오기 실패");
	}
	%>
	<h3>회원가입</h3>
	<form action="join_3.jsp" method="post">
	<!-- 
	text 이벤트
	change -> text안의 value가 바뀌고 focus가 다른 요소로 변경되야 이벤트가 발생 
	input -> text안의 value가 바뀌면 바로 이벤트가 발생 
	 -->
		<p>
			아이디: <input type="text" name="id"	value=<%=s%> oninput="movepage()">
			<input type="button" value="중복확인"> 
			<span>&nbsp;<%=message%></span>
		</p>
		<p>
			비밀번호: <input type="password" name="pw">
		</p>
		<p>
			이름: <input type="text" name="name">
		</p>
		<p>
			연락처: <select name="phone1">
				<option value="010">010</option>
				<option value="011">011</option>
				<option value="016">016</option>
				<option value="017">017</option>
				<option value="019">019</option>
			</select> <input type="text" name="phone2" size="4" maxlength="4">- <input
				type="text" name="phone3" size="4" maxlength="4">

		</p>
		<p>
			성별: <input type="radio" name="gender" value="남성">남성 <input
				type="radio" name="gender" value="여성">여성
		</p>
		<p>
			취미: 독서<input type="checkbox" name="hobby" value="독서" checked>
			운동<input type="checkbox" name="hobby" value="운동"> 영화<input
				type="checkbox" name="hobby" value="영화">
		</p>
		<textarea name="comments" cols="30" rows="3" placeholder="가입인사">
	</textarea>
		<br> <br> <input type="submit" value="가입하기"> <input
			type="reset" value="다시쓰기">
	</form>
</body>
</html>