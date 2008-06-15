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

<table>
	<tr>
		<td><img id="logo" src="images/logo_small.gif" /></td>
		<td>
		<h2>CEJUG-Classifieds</h2>
		</td>
	</tr>
</table>

<f:view>
	<h:form>
		<%-- TOOLBAR --%>
		<rich:toolBar height="25" itemSeparator="line">
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

		<rich:messages />
		<table>
			<tr width="100%">
				<td valign="top"><rich:dataGrid id="pp" columns="1"
					elements="20" rendered="#{adsService.categories[0] ne null}"
					value="#{adsService.categories}" var="cat">
					<f:facet name="header">
						<h:outputText value="List of Categories"></h:outputText>
					</f:facet>
					<rich:panel>
						<h:panelGrid columns="1">
							<h:outputText rendered="#{cat.name ne null}" value="#{cat.name}"
								title="#{cat.description }" />
						</h:panelGrid>
					</rich:panel>
				</rich:dataGrid></td>
				<td valign="top"><rich:dataGrid columns="3" elements="15"
					rendered="#{adsService.all[0] ne null}" value="#{adsService.all}"
					var="ad">
					<f:facet name="header">
						<h:outputText value="Advertisements"></h:outputText>
					</f:facet>
					<rich:panel>
						<f:facet name="header">
							<h:outputText value="#{ad.headline}"></h:outputText>
						</f:facet>
						<h:panelGrid columns="2">
							<h:outputText rendered="#{ad.shortDescription ne null}"
								value="Summary:" styleClass="label"></h:outputText>
							<h:outputText rendered="#{ad.shortDescription ne null}"
								value="#{ad.shortDescription}" />
							<h:outputText value="Text:" styleClass="label"></h:outputText>
							<h:outputText value="#{ad.fullText}" />
						</h:panelGrid>
					</rich:panel>
					<f:facet name="footer">
						<rich:datascroller />
					</f:facet>
				</rich:dataGrid> <h:outputText rendered="#{adsService.all[0] eq null}">No Ads, please use the button below to add one.</h:outputText><br />
				<h:commandButton action="adPublishing" value="Create an Ad..."></h:commandButton>
				</td>
			</tr>
		</table>
	</h:form>
	<hr/>
	<p align="center"><font color="darkgrey"><small>powered by <a
		href="https://cejug-classifieds.dev.java.net/">Cejug-Classifieds</a>
	&reg; 2008-2009 </small></font></p>
</f:view>

</body>
</html>