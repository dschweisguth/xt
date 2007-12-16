#!/bin/sh
cd `dirname $0`
exec /System/Library/Frameworks/JavaVM.framework/Versions/1.4.2/Home/bin/java -jar xt-client.jar > log 2>&1 &
