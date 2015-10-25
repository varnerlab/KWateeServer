#!/bin/tcsh

# Execute Kwatee code generation job -
java -Xmx2000M -Djava.library.path=/usr/local/lib -classpath ./dist/kwatee-1.0-SNAPSHOT.jar org.varnerlab.kwatee.foundation.VLCGGenerator $1

# Exit flag
exit 0
