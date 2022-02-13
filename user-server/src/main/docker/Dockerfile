FROM java:8

MAINTAINER zhoushuai zs195310@aliyun.com

VOLUME /temp

ADD user-server.jar /zhoushuai/user-server.jar

EXPOSE 8764

ENTRYPOINT ["java", "-jar", "/zhoushuai/user-server.jar"]
