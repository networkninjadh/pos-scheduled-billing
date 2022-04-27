FROM adoptopenjdk/openjdk11:alpine-jre
CMD mnv clean package
COPY target/pos-store-api-0.0.1-SNAPSHOT.jar pos-store-api.jar
ENTRYPOINT ["java", "-jar", "pos-store-api.jar"]
EXPOSE 8087