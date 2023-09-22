# 1st Docker build stage: build the project with Gradle
# 1. Aşama: Vert.x Uygulamasını Derle
FROM gradle:7.3.3-jdk8 as vertx-builder

# Çalışma dizinini belirt
WORKDIR /project

# Proje dosyalarını kopyala
COPY . /project/

# Uygulamayı derle
RUN gradle assemble --no-daemon

# 2. Aşama: Java Uygulamasını Derle
FROM adoptopenjdk:8-jre-hotspot as java-builder

# Uygulama dizini ve dosya ismini belirt
ENV APP_DIR /application
ENV APP_FILE CurrencyCalculater-fat.jar

# Çalışma dizinini belirt
WORKDIR $APP_DIR

# Vert.x aşamasından çıktıyı kopyala
COPY --from=vertx-builder /project/build/libs/*-fat.jar $APP_DIR/$APP_FILE

# Son İmaj: Uygulamayı Çalıştır
FROM adoptopenjdk:8-jre-hotspot

# Uygulama dizini ve dosya ismini belirt
ENV APP_DIR /application
ENV APP_FILE CurrencyCalculater-fat.jar

# Port numarasını belirt
EXPOSE 7010

# Çalışma dizinini belirt
WORKDIR $APP_DIR

# Uygulamayı kopyala
COPY --from=java-builder $APP_DIR/$APP_FILE $APP_DIR/

# Başlangıç komutunu belirt
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $APP_FILE"]
