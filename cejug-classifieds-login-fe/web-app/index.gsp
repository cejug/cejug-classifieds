<html>
    <head>
        <title>Cejug-Classifieds-Login</title>
		<meta name="layout" content="main" />
    </head>
    <body>
        <h1 style="margin-left:20px;">Welcome to Cejug-Classifieds-Login module</h1>
        <p style="margin-left:20px;width:80%">This is an EJB module exposed through a Grails frontend.
        The goal is to allow administrators to manage users and passwords used by the Java EE Container to authenticate
        the customers of the <a href='http://fgaucho.dyndns.org:8080/cejug-classifieds-richfaces/index.faces'>cejug-classifieds-richfaces</a>.</p>
        <p style="margin-left:20px;width:80%">The following tables are curently supported:<br></p>
        <div class="dialog" style="margin-left:20px;width:60%;">
            <ul>
              <g:each var="c" in="${grailsApplication.controllerClasses}">
                    <li class="controller"><g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link></li>
              </g:each>
            </ul>
        </div>
    </body>
</html>