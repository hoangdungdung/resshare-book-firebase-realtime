#!/bin/bash
# Startup script chay tren may local (macOS) cho resshare-book-firebase-realtime
# Thu muc: /Users/hoangdung/SaolaIT/resshare-book-firebase-realtime
# Dung: ./startup-local.sh

APP_DIR=/Users/hoangdung/SaolaIT/resshare-book-firebase-realtime
JAR=/Users/hoangdung/SaolaIT/resshare-book-firebase-realtime/target/resshare-book-firebase-realtime-1.0-exec.jar
LOG=$APP_DIR/log
PIDFILE=$APP_DIR/app.pid

cd "$APP_DIR" || { echo "Khong vao duoc thu muc $APP_DIR"; exit 1; }

# 1) Kiem tra jar co ton tai khong
if [ ! -f "$JAR" ]; then
    echo "LOI: Khong tim thay jar: $JAR"
    echo "Build truoc bang: mvn clean package (hoac ./mvnw clean package)"
    exit 1
fi

# 2) Neu dang chay roi thi khong start them
if [ -f "$PIDFILE" ] && kill -0 "$(cat "$PIDFILE")" 2>/dev/null; then
    echo "App da chay voi PID $(cat "$PIDFILE"). Chay ./mystop-local.sh truoc khi start lai."
    exit 1
fi

# 3) Khoi dong nen bang nohup, ghi log ra file
nohup java -jar "$JAR" > "$LOG" 2>&1 &
MyPID=$!
echo "$MyPID" > "$PIDFILE"

# 4) Cho 2s roi kiem tra process con song khong
sleep 2
if ! kill -0 "$MyPID" 2>/dev/null; then
    echo "LOI: resshare-book-firebase-realtime khoi dong that bai (process da chet). Xem log:"
    echo "  tail -n 40 $LOG"
    rm -f "$PIDFILE"
    exit 1
fi

echo "Da khoi dong resshare-book-firebase-realtime (local), PID = $MyPID"
echo "Log: $LOG"
