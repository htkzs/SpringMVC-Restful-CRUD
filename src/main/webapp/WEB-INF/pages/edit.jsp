<%--
  Created by IntelliJ IDEA.
  User: 66
  Date: 2022/12/10
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>update employee</title>
</head>
<body>
<% pageContext.setAttribute("cpt",request.getContextPath());%>
<%= pageContext.getAttribute("cpt")%>
<from:form action="${pageContext.getAttribute('cpt')}/emp/${employee.id}" method="post" modelAttribute="employee">
     <!--使用POST模拟PUT请求-->
     <input type="hidden" name="_method" value="put">
     <!--通过一个隐藏域回写id-->
     <input type="hidden" name="id" value="${employee.id}">
     email:<form:input path="email"/><br/>
     gender:&nbsp;&nbsp;&nbsp;男： <form:radiobutton path="gender" value="1"/>  &nbsp;&nbsp;&nbsp;
                              女： <form:radiobutton path="gender" value="0"/><br/>
     <!--itemLabel:指定需要展示的信息 itemValue：指定需要提交的信息-->
     department：<form:select path="department.id" items="${requestScope.deptItems}" itemLabel="departmentName" itemValue="id"/><br/>
    <input type="submit" value="提交修改">
</from:form>



</body>
</html>
