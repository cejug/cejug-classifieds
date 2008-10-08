<%@include file="/includes.jsp"%>
<f:view>
<tr:document>
 <tr:form>
  <tr:statusIndicator></tr:statusIndicator>
  <tr:inputText value="#{pageFlowScope.value}"></tr:inputText>
  <tr:outputText partialTriggers="cmdSend" value="#{pageFlowScope.output}"></tr:outputText>
  <tr:commandButton id="cmdSend" text="Send..." partialSubmit="true">
    <tr:setActionListener from="#{pageFlowScope.value}" to="#{pageFlowScope.output}"/>
  </tr:commandButton>
  </tr:form>
</tr:document>
</f:view>