FROM java:8

MAINTAINER zhoushuai zs195310@aliyun.com

VOLUME /temp

ADD order-server.jar /zhoushuai/camunda-server.jar

EXPOSE 8768

CMD java -jar /zhoushuai/camunda-server.jar --spring.profiles.active=${environment}
