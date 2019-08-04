FROM openjdk:8-alpine

COPY target/uberjar/book-api.jar /book-api/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/book-api/app.jar"]
