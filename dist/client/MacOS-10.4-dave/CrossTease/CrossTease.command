#!/bin/tcsh
chdir `dirname $0`
hup ssh -N -R 10099:localhost:10099 schweisguth.org &
/System/Library/Frameworks/JavaVM.framework/Versions/1.4.2/Home/bin/java -Djava.rmi.server.hostname=localhost -jar xt-client.jar
