FROM java:8

MAINTAINER zhoushuai zs195310@aliyun.com

VOLUME /temp

ADD gateway-server.jar /zhoushuai/gateway-server.jar

EXPOSE 8762

ENTRYPOINT ["java", "-jar", "/zhoushuai/gateway-server.jar"]
