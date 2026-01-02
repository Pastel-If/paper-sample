#!/bin/bash

# 설정
MC_VERSION="1.21.11"
PROJECT="paper"
SERVER_DIR="server"
JAR_NAME="paper.jar"

JAVA_MIN_RAM="2G"
JAVA_MAX_RAM="2G"

# 폴더 이동
mkdir -p "$SERVER_DIR"
cd "$SERVER_DIR" || exit 1

# paper.jar 다운로드
if [ ! -f "$JAR_NAME" ]; then
  echo "▶ paper.jar 다운로드 시작됨."

  BUILD=$(curl -s "https://api.papermc.io/v2/projects/$PROJECT/versions/$MC_VERSION" \
    | grep -o '"builds":[^]]*' \
    | grep -o '[0-9]\+' \
    | tail -n 1)

  if [ -z "$BUILD" ]; then
    echo "❌ PaperMC 빌드 정보를 가져오지 못했습니다."
    exit 1
  fi

  URL="https://api.papermc.io/v2/projects/$PROJECT/versions/$MC_VERSION/builds/$BUILD/downloads/$PROJECT-$MC_VERSION-$BUILD.jar"

  echo "▶ 최신 빌드 다운로드 중 (빌드 $BUILD)..."
  curl -o "$JAR_NAME" "$URL"

  echo "✅ 다운로드 완료"
else
  echo "▶ paper.jar 존재함. 다운로드 스킵됨."
fi

# EULA 자동 동의
if [ ! -f "eula.txt" ]; then
  echo "▶ eula.txt 생성"
  echo "eula=true" > eula.txt
  echo "▶ EULA가 자동으로 동의됨."
fi

# 서버 실행
echo "▶ 서버 실행 중..."
java -Xms$JAVA_MIN_RAM -Xmx$JAVA_MAX_RAM -jar "$JAR_NAME" nogui
