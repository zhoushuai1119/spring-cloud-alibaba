FROM openjdk:11-jre

MAINTAINER zhoushuai zs195310@aliyun.com

VOLUME /temp

ADD product-server.jar /app/product-server.jar

EXPOSE 8765

CMD java ${JAVA_OPTS} -jar /app/product-server.jar
