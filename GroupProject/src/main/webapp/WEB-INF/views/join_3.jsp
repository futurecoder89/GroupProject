<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
	<%@ page import="java.sql.*"%>
	<%
	request.setCharacterEncoding("utf-8");
	String id = request.getParameter("id");
	String pw = request.getParameter("pw");
	String name = request.getParameter("name");

	String phone1 = request.getParameter("phone1");
	String phone2 = request.getParameter("phone2");
	String phone3 = request.getParameter("phone3");
	String phone = phone1 + phone2 + phone3;

	String gender = request.getParameter("gender");

	String[] arr = request.getParameterValues("hobby");
	String hobby = "";

	if (arr != null) {
		for (int i = 0; i < arr.length; i++) {
			hobby = hobby + arr[i] + " ";
		}
	}
	String comments = request.getParameter("comments");

	String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	String user = "jsp";
	String password = "11111";

	try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, user, password);
		//Statement st = con.createStatement();

		String sql = "insert into member(id,pw,name,phone,gender,hobby,comments)" + " values(?,?,?,?,?,?,?)";

		PreparedStatement psmt = con.prepareStatement(sql);

		psmt.setString(1, id);
		psmt.setString(2, pw);
		psmt.setString(3, name);
		psmt.setString(4, phone);
		psmt.setString(5, gender);
		psmt.setString(6, hobby);
		psmt.setString(7, comments);

		psmt.executeUpdate();
		out.println("회원 가입 완료");
		psmt.execute("commit");
		con.close();
	} catch (Exception e) {
		out.println("회원 가입 실패");
	}
	%>

</body>
</html>