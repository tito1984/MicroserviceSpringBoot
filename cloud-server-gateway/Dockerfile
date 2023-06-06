FROM openjdk:17
VOLUME /tmp
EXPOSE 8090
ADD ./target/cloud-server-gateway-0.0.1-SNAPSHOT.jar cloud-server-gateway.jar
ENTRYPOINT [ "java","-jar","/cloud-server-gateway.jar" ]