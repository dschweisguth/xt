# Something about Java 1.5 messes up client rendering, so we use Java 1.4.
# JAVA_HOME, not just PATH, must be set for Ant to find the right VM.
setenv JAVA_HOME /System/Library/Frameworks/JavaVM.framework/Versions/1.4.2/Home
setenv PATH $JAVA_HOME/bin:$PATH
