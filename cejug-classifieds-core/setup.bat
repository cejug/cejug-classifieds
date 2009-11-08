@echo off
ver

rem Loading the .passwords file
set PASS_FILE="%1"
if "%1" == "/clean" set PASS_FILE="%USERPROFILE%\.passwords"
if "%1" == "" set PASS_FILE="%USERPROFILE%\.passwords"

for /f %%P in ('type %PASS_FILE%') do SET %%P

ECHO _________
ECHO ######### JDBC Connection Pool jdbc/classifiedsPool
CALL asadmin --user=%ASADMIN_USER% delete-jdbc-connection-pool --cascade=true jdbc/classifiedsPool 
IF NOT "%1"=="/clean" (
	CALL asadmin --user=admin create-jdbc-connection-pool --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource --restype javax.sql.ConnectionPoolDataSource --property user=%MYSQL_USER%:password=%MYSQL_PASSWORD%:URL=%DB_URL% jdbc/classifiedsPool
)

ECHO _________
ECHO ######### JDBC Data Source jdbc/classifieds
CALL asadmin delete-jdbc-resource jdbc/classifieds
IF NOT "%1"=="/clean" (
	CALL asadmin --user=%ASADMIN_USER% create-jdbc-resource --connectionpoolid jdbc/arenaPool jdbc/classifieds
)

ECHO _________
ECHO ######### Deploying to Glassfish v3.x"
CALL asadmin --user $ASADMIN_USER deploy --force=true cejug-classifieds-core-*.jar
