<%@include file="/includes.jsp"%>

<f:view>


	<rich:panel>

		<f:facet name="header">
			<h:outputText value="Login"></h:outputText>
		</f:facet>

		<h:form>

			<h:outputLabel for="username">Username:</h:outputLabel>
			<h:inputText id="username"></h:inputText>



		</h:form>



	</rich:panel>

</f:view>