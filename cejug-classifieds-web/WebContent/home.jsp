<%@include file="/includes.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CEJUG-Classifieds</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<h2>CEJUG-Classifieds</h2>

<f:view>
	<h:form>

		<%-- TOOLBAR --%>
		<rich:toolBar height="34" itemSeparator="line">
			<rich:toolBarGroup>
				<h:graphicImage id="login" value="/images/edit.gif" />
				<h:commandLink action="login" value="Login"></h:commandLink>
			</rich:toolBarGroup>
			<rich:toolBarGroup>
				<h:graphicImage id="find" value="/images/icons/find.gif" />
				<h:outputLabel value="Find" for="find" />
			</rich:toolBarGroup>
			<rich:toolBarGroup>
				<h:graphicImage id="filter" value="/images/icons/filter.gif" />
				<h:outputLabel value="Filter" for="filter" />
			</rich:toolBarGroup>
			<rich:toolBarGroup location="right">
				<h:inputText styleClass="barsearch" />
				<h:commandButton styleClass="barsearchbutton"
					onclick="return false;" value="Search" />
			</rich:toolBarGroup>
		</rich:toolBar>

		<br />

		<rich:messages />
		<table> <tr> <td valign="top"> 
			<rich:panel >
				<f:facet name="header">
					<h:outputText value="Categories"></h:outputText>
				</f:facet>
                <h:outputText value="List of Categories"></h:outputText>
			</rich:panel>
			</td>
			<td valign="top">
			<rich:dataGrid columns="3" elements="15" rendered="#{adsService.all[0] ne null}"
				value="#{adsService.all}" var="ad">
				<f:facet name="header">
					<h:outputText value="Advertisements"></h:outputText>
				</f:facet>
				<rich:panel>
					<f:facet name="header">
						<h:outputText value="#{ad.title}"></h:outputText>
					</f:facet>
					<h:panelGrid columns="2">
						<h:outputText rendered="#{ad.summary ne null}" value="Summary:" styleClass="label"></h:outputText>
						<h:outputText rendered="#{ad.summary ne null}" value="#{ad.summary}" />
						<h:outputText value="Text:" styleClass="label"></h:outputText>
						<h:outputText value="#{ad.text}" />
					</h:panelGrid>
				</rich:panel>
				<f:facet name="footer">
					<rich:datascroller />
				</f:facet>
			</rich:dataGrid>
			
			<h:outputText rendered="#{adsService.all[0] eq null}">No Ads, please use the button below to add one.</h:outputText><br/>
			<h:commandButton action="adPublishing" value="Create an Ad..."></h:commandButton>
</td>  </tr> </table>
		
		<br />

	</h:form>
</f:view>

</body>
</html>