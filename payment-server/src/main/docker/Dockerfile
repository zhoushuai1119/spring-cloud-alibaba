FROM java:8

MAINTAINER zhoushuai zs195310@aliyun.com

VOLUME /temp

ADD payment-server.jar /zhoushuai/payment-server.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/zhoushuai/payment-server.jar"]
