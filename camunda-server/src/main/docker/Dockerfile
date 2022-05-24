FROM openjdk:11-jre

MAINTAINER zhoushuai zs195310@aliyun.com

VOLUME /temp

ADD camunda-server.jar /app/camunda-server.jar

EXPOSE 8768

CMD java ${JAVA_OPTS} -jar /app/camunda-server.jar
