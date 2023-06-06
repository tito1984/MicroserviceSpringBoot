FROM openjdk:11
VOLUME /tmp
ADD ./target/products-0.0.1-SNAPSHOT.jar products.jar
ENTRYPOINT [ "java","-jar","/products.jar" ]