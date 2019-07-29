FROM openjdk:8
VOLUME /tmp
ADD target/cashback-0.0.1-SNAPSHOT.jar cashback-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","cashback-0.0.1-SNAPSHOT.jar"]
CMD [""]