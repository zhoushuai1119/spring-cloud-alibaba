FROM java:8

MAINTAINER zhoushuai zs195310@aliyun.com

VOLUME /temp

ADD payment-server.jar /app/payment-server.jar

EXPOSE 8764

CMD java ${JAVA_OPTS} -jar /app/payment-server.jar
