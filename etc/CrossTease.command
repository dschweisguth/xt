#!/bin/sh
cd `dirname $0`
exec java -Dcom.apple.macos.useScreenMenuBar=true -Dcom.apple.mrj.application.apple.menu.about.name=CrossTease -jar xt-client.jar > log 2>&1
