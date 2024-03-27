FROM openjdk:21-bookworm

COPY ./target/*.jar /src/app/app.jar
EXPOSE 8080
CMD ["java","-jar","/src/app/app.jar"]
