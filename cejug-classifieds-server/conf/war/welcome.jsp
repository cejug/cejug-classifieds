<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="net.java.dev.cejug_classifieds.metadata.admin.MonitorResponse,java.util.List,java.util.Calendar"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page
	import="net.java.dev.cejug_classifieds.metadata.admin.AlivePeriod"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cejug Classifieds</title>
</head>
<body>
<%
	MonitorResponse m = (MonitorResponse) request
			.getAttribute("monitorResponse");
%>

<table cellpadding=2 " cellspacing="5">
	<tr valign="top">
		<td rowspan="2"><img src="img/logo.jpg"></td>
		<td>
		<p>Welcome to Cejug-Classifieds</p>
		</td>
	</tr>
	<tr>
		<td>
		<p><span style='color: red; font-weight: bold'>TODO</span>: here
		we should fill some instructions to the system administrators about
		how to configure to just installed application, tunning, installation
		testing, links etc.</p>
		<%
			List<AlivePeriod> periods = (List<AlivePeriod>)m.getAlivePeriods(); 
			AlivePeriod period =  periods.get(0);
		%>
		<p>The Cejug Classifieds is online since <%=period.getStart().getTime()%>.</p>
		</td>
	</tr>
</table>

</body>
</html>