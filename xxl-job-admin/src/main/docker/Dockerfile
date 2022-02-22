FROM java:8

MAINTAINER zhoushuai zs195310@aliyun.com

VOLUME /temp

ADD xxl-job-admin.jar /zhoushuai/xxl-job-admin.jar

EXPOSE 19090

CMD java -jar /zhoushuai/xxl-job-admin.jar --spring.profiles.active=${environment}
