#!/bin/bash
java -jar /home/ec2-user/book/resshare_book.jar & 
MyPID=$!                        # You sign it's PID
echo $MyPID                     # You print to terminal
echo "kill $MyPID" > mystop.sh  # Write the the command kill pid in MyStop.sh

