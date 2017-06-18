FROM frolvlad/alpine-oraclejdk8:slim

MAINTAINER Thibault Duperron <zomzog@zomzog.fr>

VOLUME /tmp
ADD target/ze-shop-eureka.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]