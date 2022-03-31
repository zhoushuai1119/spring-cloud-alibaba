FROM java:8

MAINTAINER zhoushuai zs195310@aliyun.com

VOLUME /temp

ADD gateway-server.jar /app/gateway-server.jar

EXPOSE 8762

ENTRYPOINT ["java", "-jar", "/app/gateway-server.jar"]
