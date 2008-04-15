CEJUG CLASSIFIEDS PROJECT
####### under construction.

Thanks for test Cejug-Classifieds...

Contents:

	I 	- Building the project
	II	- License
	III	- Aknowledge
	IV	- ...

When you first checkout the project, you noticed several compiler errors 
in your IDE. It is due the need to rebuild the project at least once to generate
the contract artifacts.

I - BUILDING

1) Setup - in order to build the project, you need to download and install the
   following software:
   
- Glassfish:	https://glassfish.dev.java.net/
- JAXWS:		https://jax-ws.dev.java.net/
- APACHE ANT:	http://ant.apache.org/     (Eclipse & Netbeans comes with embeded ant)

After installing the software, don't forget to configure the environment variables:

- env.AS_HOME
- env.JAXWS_HOME

2) Build:

- Startup the Glassfish server
- Refresh your code view (Eclipse)
- run the ant task "build.full"

After build, refresh the IDE view and check if the errors are gone. If not, review
the installation and build process.
