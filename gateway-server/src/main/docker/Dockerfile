FROM openjdk:11-jre

MAINTAINER zhoushuai zs195310@aliyun.com

VOLUME /temp

ADD gateway-server.jar /app/gateway-server.jar

EXPOSE 8762

CMD java ${JAVA_OPTS} -jar /app/gateway-server.jar
