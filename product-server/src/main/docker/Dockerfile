FROM apache/skywalking-java-agent:8.12.0-java11

MAINTAINER zhoushuai zs11195310@163.com

ENV SW_AGENT_NAME product-server

ADD product-server.jar /app/product-server.jar

EXPOSE 8765

CMD java $JAVA_TOOL_OPTIONS $JAVA_OPTS -jar /app/product-server.jar
