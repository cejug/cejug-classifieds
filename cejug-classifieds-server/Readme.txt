CEJUG CLASSIFIEDS PROJECT
####### under construction.

Thanks for test Cejug-Classifieds... 

If you are not familiar with the concept of Java EE 5 applications, 
please read: http://java.sun.com/developer/technicalArticles/J2EE/intro_ee5/

Contents:

	I	- Setup 
	I 	- Building the project
	II	- License
	III	- Aknowledge
	IV	- ...

When you first checkout the project, you noticed several compiler errors 
in your IDE - this is expected. It is due the need to rebuild the project
at least once to generate the contract artifacts.

I - Setup

In order to build the project, you need to download and install the
   following software:
   
- JDK 1.6:		http://java.sun.com/javase/downloads/?intcmp=1281
- APACHE ANT:	http://ant.apache.org/ (Eclipse & Netbeans comes with ant)
- Glassfish:	https://glassfish.dev.java.net/

After installing the software, don't forget to configure the environment
variables:

JAVA_HOME:		Java installation directory
AS_HOME :		Glassfish V2 installation directory
DERBY_HOME:		The JavaDB embedded in Glassfish (it is in the AS_HOME/javadb)
PATH:			$PATH:$AS_HOME/bin:$DERBY_HOME/bin:$JAVA_HOME/bin

example (from Ubuntu):

	export JAVA_HOME=/usr/lib/jvm/java-6-sun-1.6.0.06
	export AS_HOME=/home/fgaucho/dev/glassfish	
	export DERBY_HOME=$AS_HOME/javadb
	export PATH=$PATH:$AS_HOME/bin:$DERBY_HOME/bin:$JAVA_HOME/bin

To test the setup, open a shell console and type:

	asadmin start-database
	asadmin start-domain domain1
	
and then open this URL in your browser: http://localhost:8080/

you should see a welcome message on the browser, otherwise please check:   

https://glassfish.dev.java.net/downloads/v2-b58g.html	

I - BUILDING

1) Open a console
2) Go to the directory where you checked out the cejug-classifieds-server 
3) type:
	asadmin start-database
	asadmin start-domain domain1
	ant deploy

After that, open in your browser the URL:
	http://localhost:8080/cejug-classifieds-server/

you should see a welcome message on the browser, otherwise please send
your questions to dev@cejug-classifieds.dev.java.net
