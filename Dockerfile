# 1st Docker build stage: build the project with Gradle
FROM gradle:7.3.3-jdk8 as builder
WORKDIR /project
COPY . /project/
RUN gradle assemble --no-daemon

# 2nd Docker build stage: copy builder output and configure entry point
FROM adoptopenjdk:8-jre-hotspot
ENV APP_DIR /application
ENV APP_FILE CurrencyCalculater-fat.jar

EXPOSE 7006

WORKDIR $APP_DIR
COPY --from=builder /project/build/libs/*-fat.jar $APP_DIR/$APP_FILE

ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $APP_FILE"]

