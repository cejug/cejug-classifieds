<%@include file="/includes.jsp"%>
<f:view>
	<tr:document>
		<tr:form>
			<tr:statusIndicator></tr:statusIndicator>
			<tr:inputText value="#{pageFlowScope.value}"></tr:inputText>
			<tr:outputText partialTriggers="cmdSend"
				value="#{pageFlowScope.output}"></tr:outputText>
			<tr:commandButton id="cmdSend" text="Send..." partialSubmit="true">
				<tr:setActionListener from="#{pageFlowScope.value}"
					to="#{pageFlowScope.output}" />
			</tr:commandButton>
		</tr:form>
		<!-- Loading data from server .... -->
		<tr:table value="#{adsService.categories}" var="cat">
			<tr:column headerText="Categories">
              <h:commandLink action="#{buildingHandler.select}" immediate="false" value="#{cat.id}">
                <h:outputText value="#{cat.name} (#{cat.available})" />
              </h:commandLink>
			</tr:column>
		</tr:table>
	</tr:document>
</f:view>
