FROM java:8

MAINTAINER zhoushuai zs195310@aliyun.com

VOLUME /temp

ADD mqjob-handler.jar /zhoushuai/mqjob-handler.jar

EXPOSE 8767

CMD java -jar /zhoushuai/mqjob-handler.jar --spring.profiles.active=${environment}
