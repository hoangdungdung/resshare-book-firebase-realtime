#!/bin/bash
java -jar /home/saolaaa/resshare-book-firebase-realtime/target/resshare-book-firebase-realtime-1.0-exec.jar & 
MyPID=$!                        # You sign it's PID
echo $MyPID                     # You print to terminal
echo "kill $MyPID" > mystop.sh  # Write the the command kill pid in MyStop.sh

