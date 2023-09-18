FROM maven:3.9.4-amazoncorretto-20 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM amazoncorretto:20-alpine-jdk
COPY --from=build /home/app/target/telegram-note-bot-1.0-alpha.jar /usr/local/lib/application.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/application.jar"]