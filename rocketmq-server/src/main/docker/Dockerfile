FROM java:8

MAINTAINER zhoushuai zs195310@aliyun.com

VOLUME /temp

ADD rocketmq-server.jar /zhoushuai/rocketmq-server.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/zhoushuai/rocketmq-server.jar"]
