FROM gradle:8.14-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle clean build -x test -x checkstyleMain -x checkstyleTest

FROM eclipse-temurin:21-alpine
COPY --from=build /app/build/libs/furima-0.0.1-SNAPSHOT.jar furima.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "furima.jar", "--spring.profiles.active=prod", "--debug"]