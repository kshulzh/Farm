FROM eclipse-temurin:17-jdk-alpine
ENV JAR_FILE "main-0.0.1.jar"
RUN mkdir -p /main
COPY /build/libs/${JAR_FILE} main/${JAR_FILE}
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar main/${JAR_FILE}"]