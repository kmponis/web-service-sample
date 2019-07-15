# Download parent-pom-sample code
FROM alpine/git AS parentpomclone
WORKDIR /parent-pom
RUN git clone https://gitlab.com/kmponis-examples/parent-pom-sample.git

# Build code
FROM maven:3.5-jdk-8-alpine AS build
WORKDIR /builddir
COPY --from=parentpomclone /parent-pom/parent-pom-sample /builddir
RUN mvn install
COPY . /builddir
RUN mvn install -DskipTests

#Deploy code
FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=build /builddir/target/web-service-sample.jar .
EXPOSE 8880
ENTRYPOINT ["java","-jar","web-service-sample.jar"]