import org.codehaus.groovy.grails.orm.hibernate.cfg.GrailsAnnotationConfiguration

class ApplicationDataSource {
   def configClass = GrailsAnnotationConfiguration.class
   boolean pooling = true
   //String dbCreate = "create-drop" // one of 'create', 'create-drop','update'
   String url = "jdbc:derby://localhost:1527/sun-appserv-samples;user=app;password=app"
   String driverClassName = "org.apache.derby.jdbc.EmbeddedDriver"
   String username = "app"
   String password = "app"
}

configClass = GrailsAnnotationConfiguration.class

dataSource {
	configClass = GrailsAnnotationConfiguration.class
	pooled = true
	driverClassName = "org.apache.derby.jdbc.EmbeddedDriver"
	String url = "jdbc:derby://localhost:1527/sun-appserv-samples;user=app;password=app"
	// String url = "jdbc:derby://localhost:1527/sun-appserv-samples"
	username = "app"
	password = "app"
}

hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='com.opensymphony.oscache.hibernate.OSCacheProvider'
}
// environment specific settings
environments {
	development {
		dataSource {
			configClass = GrailsAnnotationConfiguration.class
			dbCreate = "update" // one of 'create', 'create-drop','update'
			url = "jdbc:derby://localhost:1527/sun-appserv-samples"
		}
	}
	test {
		dataSource {
			configClass = GrailsAnnotationConfiguration.class
			dbCreate = "update"			
			url = "jdbc:derby://localhost:1527/sun-appserv-samples"
		}
	}
	production {
		dataSource {
			configClass = GrailsAnnotationConfiguration.class
			dbCreate = "update"
			url = "jdbc:derby://localhost:1527/sun-appserv-samples"
		}
	}
}
