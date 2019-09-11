<%@page import="kr.co.itcen.emaillist.dao.EmaillistDao"%>
<%@page import="kr.co.itcen.emaillist.vo.EmaillistVo"%>
<%
	request.setCharacterEncoding("utf-8");
	String firstName=request.getParameter("firstName");
	String lastName=request.getParameter("lastName");
	String email=request.getParameter("email");

	EmaillistVo vo=new EmaillistVo();
	vo.setFirstname(firstName);
	vo.setLastname(lastName);
	vo.setEmail(email);
	new EmaillistDao().insert(vo);
	
	response.sendRedirect(request.getContextPath());//그냥 페이지 전송하면 된다.
%>
