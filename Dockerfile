FROM adoptopenjdk/openjdk11:alpine-jre
CMD mnv clean package
COPY target/pos-scheduled-billing-0.0.1-SNAPSHOT.jar pos-scheduled-billing.jar
ENTRYPOINT ["java", "-jar", "pos-scheduled-billing.jar"]
EXPOSE 8087