<%@include file="/includes.jsp"%>
<%-- ADVERSIMENTS PAINEL --%>
<f:view>
<rich:panel>

	<f:facet name="header">
		<h:outputText value="Advesiments"></h:outputText>
	</f:facet>

	<h:form>
		<rich:dataGrid value="#{adversimentBean.adversiments}"
			var="adversiment" columns="3" elements="9">

			<rich:panel>

				<f:facet name="header">

					<h:outputText
						value="#{adversiment.shortDescription} #{adversiment.status}"></h:outputText>

				</f:facet>

				<h:panelGrid columns="2">

					<h:outputText value="Advertiser:" styleClass="label"></h:outputText>

					<h:outputText value="#{adversiment.advertiser}" />

					<h:outputText value="Headline:" styleClass="label"></h:outputText>

					<h:outputText value="#{adversiment.headline}" />

					<h:outputText value="FullText:" styleClass="label"></h:outputText>

					<h:outputText value="#{adversiment.fullText}" />

					<h:outputText value="Status:" styleClass="label"></h:outputText>

					<h:outputText value="#{adversiment.status}" />

				</h:panelGrid>

			</rich:panel>

			<f:facet name="footer">

				<rich:datascroller></rich:datascroller>

			</f:facet>

		</rich:dataGrid>


	</h:form>

</rich:panel>
</f:view>

