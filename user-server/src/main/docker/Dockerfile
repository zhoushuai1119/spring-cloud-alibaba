FROM apache/skywalking-java-agent:8.12.0-java11

MAINTAINER zhoushuai zs11195310@163.com

ENV SW_AGENT_NAME user-server

ADD user-server.jar /app/user-server.jar

EXPOSE 8766

CMD java $JAVA_TOOL_OPTIONS $JAVA_OPTS -jar /app/user-server.jar
