# 실행 전용 이미지 (JDK 필요 없고 JRE만 있어도 됨)
FROM eclipse-temurin:21-jre

WORKDIR /app

# GitHub Actions에서 만들어서 보내줄 app.jar만 복사
COPY app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
