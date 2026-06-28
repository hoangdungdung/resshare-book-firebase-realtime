#!/bin/bash
# Restart script (local) cho resshare-book-firebase-realtime
# Goi tuan tu: mystop-local.sh -> build-local.sh -> startup-local.sh
# Dung: ./restart-local.sh           (build, bo qua test)
#       ./restart-local.sh test      (build kem chay test)

APP_DIR=/Users/hoangdung/SaolaIT/resshare-book-firebase-realtime
cd "$APP_DIR" || { echo "Khong vao duoc thu muc $APP_DIR"; exit 1; }

echo "==== [1/3] STOP ===="
bash "$APP_DIR/mystop-local.sh"

echo "==== [2/3] BUILD ===="
bash "$APP_DIR/build-local.sh" "$1"
RC=$?
if [ $RC -ne 0 ]; then
    echo "BUILD THAT BAI (exit $RC) - khong start lai."
    exit $RC
fi

echo "==== [3/3] START ===="
bash "$APP_DIR/startup-local.sh"
