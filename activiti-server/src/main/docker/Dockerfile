FROM java:8

MAINTAINER zhoushuai zs195310@aliyun.com

VOLUME /temp

ADD activiti-server.jar /zhoushuai/activiti-server.jar

EXPOSE 8761

CMD java -jar /zhoushuai/activiti-server.jar --spring.profiles.active=${environment}
