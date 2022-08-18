REPOSITORY=/home/ubuntu/troupe/S07P12A804/Troupe
PROJECT_NAME=backend
PROJECT_NAME2=frontend

echo "> 기본 디렉토리로 이동"

cd $REPOSITORY

echo "> Git Pull"

git pull origin main

echo "> 백엔드 프로젝트 디렉토리로 이동"

cd $REPOSITORY/$PROJECT_NAME/

echo "> 백엔드 프로젝트 Build 시작"

./gradlew build

echo "> 기본 디렉토리로 이동"

cd $REPOSITORY

echo "> Build 파일 복사"

cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/

echo "> 프론트엔드 프로젝트 디렉토리로 이동"

cd $REPOSITORY/$PROJECT_NAME2

echo "> 프론트엔드 빌드 디렉토리로 이동"

cd build

echo "> 프론트엔트 프로젝트 Build 시작"

npm run build


echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)

echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z $CURRENT_PID ]; then
    echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."

else
    echo "> kill -15 $CURRENT_PID"
    kill -15 $CURRENT_PID
    sleep 5

    CURRENT_PID2=$(pgrep -f {PROJECT_NAME}.*.jar)
    if [ -z $CURRENT_PID2 ]; then
        echo ">정상종료되었습니다."
    else
        echo ">강제 종료합니다."
        kill -9 $CURRENT_PID2
        sleep 5
    fi
fi

echo "> 새 애플리케이션 배포"

cd $REPOSITORY

JAR_NAME=$(ls $REPOSITORY/ | grep jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

nohup java -jar $REPOSITORY/$JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
