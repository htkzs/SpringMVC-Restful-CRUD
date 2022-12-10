<%--
  Created by IntelliJ IDEA.
  User: 66
  Date: 2022/12/10
  Time: 20:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    pageContext.setAttribute("cpt",request.getContextPath());
%>
<%=pageContext.getAttribute("cpt")%>
<form method="post" action="${cpt}/emp/convertor">
    <input type="text" value="zhangsan-zhonghuan@163.com-1-101" name="dataString">
    <input type="submit" value="快速添加"/>
</form>
</body>
</html>
