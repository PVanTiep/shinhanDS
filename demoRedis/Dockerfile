FROM maslick/minimalka:jdk11
VOLUME /tmp
ARG JAR_FILE=target/SpringDemoRedis.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]