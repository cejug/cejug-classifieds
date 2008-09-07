<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@include file="/includes.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Publish Advertisement - This will be i18n. don't worry</title>
</head>
<body>
<f:view>
   <h:form>
     <h3>Publish Ad</h3>
     <rich:messages/>
     <h:panelGrid columns="2">
     <h:outputLabel for="title" value="Title:"></h:outputLabel>
     <h:inputText id="title" value="#{adsBean.advertisement.headline}" required="true"></h:inputText>
     <h:outputLabel for="summary" value="Summary:"></h:outputLabel>
     <h:inputText id="summary" value="#{adsBean.advertisement.summary}" required="false"></h:inputText>
     <h:outputLabel for="text" value="Text:"></h:outputLabel>
     <h:inputTextarea id="text" value="#{adsBean.advertisement.text}" required="true"></h:inputTextarea>
     </h:panelGrid><br/>
     <h:commandButton action="home" actionListener="#{adsBean.publish}" value="Publish"></h:commandButton>
     <h:commandButton action="home" immediate="true" value="Cancel"></h:commandButton>
   </h:form>
</f:view>
</body>
</html>