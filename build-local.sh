#!/bin/bash
# Build script chay tren may local (macOS) cho resshare-book-firebase-realtime
# Thu muc: /Users/hoangdung/SaolaIT/resshare-book-firebase-realtime
# Dung: ./build-local.sh           (build, bo qua test)
#       ./build-local.sh test      (build kem chay test)

APP_DIR=/Users/hoangdung/SaolaIT/resshare-book-firebase-realtime
cd "$APP_DIR" || { echo "Khong vao duoc thu muc $APP_DIR"; exit 1; }

# Tim mvn: uu tien mvn trong PATH, neu khong co thi dung ban cai san
MVN=$(command -v mvn)
if [ -z "$MVN" ]; then
    MVN=/Users/hoangdung/java/apache-maven-3.9.6/bin/mvn
fi
if [ ! -x "$MVN" ]; then
    echo "LOI: Khong tim thay mvn. Cai Maven hoac sua duong dan trong script."
    exit 1
fi

# Mac dinh skip test; truyen 'test' de chay test
if [ "$1" = "test" ]; then
    SKIP=""
    echo "Build CO chay test..."
else
    SKIP="-DskipTests"
    echo "Build (bo qua test)..."
fi

"$MVN" clean package $SKIP
RC=$?

if [ $RC -ne 0 ]; then
    echo "BUILD THAT BAI (exit $RC)"
    exit $RC
fi

JAR="$APP_DIR/target/resshare-book-firebase-realtime-1.0-exec.jar"
if [ -f "$JAR" ]; then
    echo "BUILD OK -> $JAR"
else
    echo "BUILD xong nhung khong thay jar: $JAR"
    exit 1
fi
