FROM java:8

MAINTAINER zhoushuai zs195310@aliyun.com

VOLUME /temp

ADD product-server.jar /zhoushuai/product-server.jar

EXPOSE 8765

CMD java -jar /zhoushuai/product-server.jar --spring.profiles.active=${environment}
