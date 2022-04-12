FROM java:8

MAINTAINER zhoushuai zs195310@aliyun.com

VOLUME /temp

ADD user-server.jar /app/user-server.jar

EXPOSE 8766

CMD java -jar /app/user-server.jar --spring.profiles.active=${environment}
