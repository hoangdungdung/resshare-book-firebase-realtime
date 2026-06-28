#!/bin/bash
# Stop script cho resshare-book-firebase-realtime (local)
APP_DIR=/Users/hoangdung/SaolaIT/resshare-book-firebase-realtime
PIDFILE=$APP_DIR/app.pid

if [ -f "$PIDFILE" ]; then
    PID=$(cat "$PIDFILE")
    if kill -0 "$PID" 2>/dev/null; then
        kill "$PID" && echo "Da dung resshare-book-firebase-realtime (PID $PID)"
    else
        echo "Process PID $PID khong con chay."
    fi
    rm -f "$PIDFILE"
else
    echo "Khong tim thay $PIDFILE - app co the chua chay."
fi
