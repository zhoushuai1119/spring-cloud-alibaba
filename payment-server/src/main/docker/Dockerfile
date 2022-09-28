FROM apache/skywalking-java-agent:8.12.0-java11

MAINTAINER zhoushuai zs11195310@163.com

ENV SW_AGENT_NAME payment-server

ADD payment-server.jar /app/payment-server.jar

EXPOSE 8764

CMD java $JAVA_TOOL_OPTIONS $JAVA_OPTS -jar /app/payment-server.jar
