FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ENV JAR_FILE = "server-0.1.jar"
COPY build/libs/server-0.1.jar server.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /server.jar"]