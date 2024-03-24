<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="eshop.AppConstants" %>
<%
    String contextPath = request.getContextPath();
    String specificPath = AppConstants.BASE_URL;
    response.sendRedirect(contextPath + specificPath);
%>