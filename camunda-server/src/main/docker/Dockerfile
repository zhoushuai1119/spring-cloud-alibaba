FROM java:8

MAINTAINER zhoushuai zs195310@aliyun.com

VOLUME /temp

ADD camunda-server.jar /app/camunda-server.jar

EXPOSE 8768

CMD java -jar /app/camunda-server.jar --spring.profiles.active=${environment}
