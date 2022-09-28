FROM apache/skywalking-java-agent:8.12.0-java11

MAINTAINER zhoushuai zs11195310@163.com

ENV SW_AGENT_NAME order-server

ADD order-server.jar /app/order-server.jar

EXPOSE 8763

CMD java $JAVA_TOOL_OPTIONS $JAVA_OPTS -jar /app/order-server.jar
