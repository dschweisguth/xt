#!/bin/tcsh
chdir `dirname $0`
hup ssh -N -R 10098:localhost:10098 grandma-laura@schweisguth.org &
java -Djava.rmi.server.hostname=localhost -jar xt-client.jar
