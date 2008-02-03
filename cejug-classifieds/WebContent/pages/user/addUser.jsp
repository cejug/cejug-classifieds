<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

    <html>

      <head>

        <title>repeater </title> 
 
      </head>

      <body>

        <f:view>

          <h:form>

              <rich:panel header="Simple Echo">

                <h:inputText size="50" value="#{userBean.test}" > 

                  <a4j:support event="onkeyup" reRender="rep"/>

                </h:inputText>

                <h:outputText value="#{userBean.test}" id="rep"/>

              </rich:panel>

          </h:form>

        </f:view>

      </body>

    </html>