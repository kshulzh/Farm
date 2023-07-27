FROM eclipse-temurin:17-jdk-alpine
ENV JAR_FILE "file-service-0.0.1.jar"
RUN mkdir -p /file-service
COPY /build/libs/${JAR_FILE} file-service/${JAR_FILE}
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar file-service/${JAR_FILE}"]