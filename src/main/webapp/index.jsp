<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
    String specificPath = "/shop";
    response.sendRedirect(contextPath + specificPath);
%>