<%@include file="includes.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CEJUG-Classifieds</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<h1>CEJUG-Classifieds</h1>

<f:view>
	<h:form>

		<%-- TOOLBAR --%>
		<rich:toolBar height="34" itemSeparator="line">

			<rich:toolBarGroup>

				<h:graphicImage id="edit" value="/images/icons/edit.gif" />

				<h:outputLabel value="Edit" for="edit" />

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

		<%-- MENU --%>
		<rich:panel>

			<f:facet name="header">
				<h:outputText value="Menu"></h:outputText>
			</f:facet>

			<%@include file="/pages/menu.jsp"%>
		</rich:panel>

		<br />

		<%-- PAINELS FOR INFORMATIONS --%>
		<rich:panel style="padding:0" headerClass="outpanelHeader">

			<f:facet name="header">

				<rich:spacer height="4" />

			</f:facet>

			<h2 align="center"><h:outputText
				value="Benefits of CEJUG-Classifieds" /></h2>

			<h:panelGrid columns="2" columnClasses="gridContent">

				<rich:panel bodyClass="inpanelBody">

					<f:facet name="header">

						<h:outputText value="For users" />

					</f:facet>

					<ul>

						<li>Offer your book, voucher, etc</li>

						<li>Keep the your list clean</li>

						<li>Organize and search your favorites books, articles, etc</li>

					</ul>

				</rich:panel>

				<rich:panel bodyClass="inpanelBody">

					<f:facet name="header">

						<h:outputText value="For JUGs" />

					</f:facet>

					<ul>

						<li>Control yours adversiments</li>

						<li>Clean-up your list</li>

						<li>It´s free. It´s open source</li>

					</ul>

				</rich:panel>

			</h:panelGrid>

		</rich:panel>
	</h:form>
</f:view>

</body>
</html>