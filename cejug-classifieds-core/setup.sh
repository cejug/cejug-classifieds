#!/bin/bash

if [ -n "$1" -a -f $1 ]; then
    . $1;export PASS_FILE="$1";
    if [ -n "$2" -a "$2" = "/clean" ]; then
        export CLEAN="/clean"
    fi
else
    . $HOME/.passwords;export PASS_FILE="$HOME/.classifieds";
fi

GF_VERSION=$(asadmin version -t)

echo "$GF_VERSION" | grep -q 'v2.'
if [ $? -eq 0 ] ; then
    echo "Sorry, you need to install and configure the Glassfish V3 before to run this project.";
    echo "You can get the newest Glassfish version here: http://download.java.net/glassfish/v3/promoted/";
    exit 1;
else
    mysql -v -u$MYSQL_USER -hlocalhost -P3306 -p$MYSQL_PASSWORD -e "create database IF NOT EXISTS  classifieds;"
    
    #echo "Shell Script for Glassfish v3.x"
    echo
    echo "-------- JDBC Connection Pool jdbc/classifiedsPool"
    asadmin --user=$ASADMIN_USER delete-jdbc-connection-pool --cascade=true jdbc/classifiedsPool   
    if [ -z "$CLEAN" ]; then jdbc/arenaPool
        asadmin create-jdbc-connection-pool --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource --restype javax.sql.ConnectionPoolDataSource --property "User=$MYSQL_USER:Password=$MYSQL_PASSWORD:URL=$DB_URL" jdbc/classifiedsPool
    fi
    echo
    echo "-------- JDBC Data Source jdbc/classifieds"
    asadmin --user=$ASADMIN_USER delete-jdbc-resource jdbc/classifieds
    if [ -z "$CLEAN" ]; then
        asadmin --user=$ASADMIN_USER create-jdbc-resource --connectionpoolid jdbc/classifiedsPool jdbc/classifieds
    fi
    echo
    echo "-------- Deploying to Server $GF_VERSION"
    asadmin --user $ASADMIN_USER deploy --force=true target/cejug-classifieds-core-*.jar
fi
