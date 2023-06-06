FROM openjdk:17
VOLUME /tmp
EXPOSE 8002
ADD ./target/items-0.0.1-SNAPSHOT.jar items.jar
ENTRYPOINT [ "java","-jar","/items.jar" ]