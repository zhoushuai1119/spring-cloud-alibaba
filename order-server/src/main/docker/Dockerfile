FROM java:8

MAINTAINER zhoushuai zs195310@aliyun.com

VOLUME /temp

ADD order-server.jar /zhoushuai/order-server.jar

EXPOSE 8763

CMD java -jar /zhoushuai/order-server.jar --spring.profiles.active=${environment}
