FROM apache/skywalking-java-agent:8.12.0-java11

MAINTAINER zhoushuai zs11195310@163.com

ENV SW_AGENT_NAME gateway-server

ADD gateway-server.jar /app/gateway-server.jar

EXPOSE 8762

CMD java $JAVA_TOOL_OPTIONS $JAVA_OPTS -jar /app/gateway-server.jar
