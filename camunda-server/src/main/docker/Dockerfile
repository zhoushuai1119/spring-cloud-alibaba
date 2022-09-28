FROM apache/skywalking-java-agent:8.12.0-java11

MAINTAINER zhoushuai zs11195310@163.com

ENV SW_AGENT_NAME camunda-server

ADD camunda-server.jar /app/camunda-server.jar

EXPOSE 8768

CMD java $JAVA_TOOL_OPTIONS $JAVA_OPTS -jar /app/camunda-server.jar
