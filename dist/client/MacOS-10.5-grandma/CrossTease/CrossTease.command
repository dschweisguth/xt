#!/bin/tcsh
chdir `dirname $0`
hup ssh -N -R 10098:localhost:10098 grandma-laura@schweisguth.org &
/System/Library/Frameworks/JavaVM.framework/Versions/1.4.2/Home/bin/java -Djava.rmi.server.hostname=localhost -jar xt-client.jar
