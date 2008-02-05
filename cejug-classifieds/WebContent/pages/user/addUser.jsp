<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">

	<title>Lista de Palestrantes</title>
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->


</head>
  
<body>
	<f:view>
		<a4j:form ajaxSubmit="true" reRender="principal">
			<h:panelGroup id="principal">
				<h2>
					Lista de Palestrantes
				</h2>
				
				<h:messages errorStyle="color:#900" infoStyle="color:green;" 
						showSummary="false" showDetail="true"/>
						
				
				
				
				
				
			</h:panelGroup>
		</a4j:form>
	</f:view>
</body>
</html>