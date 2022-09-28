FROM apache/skywalking-java-agent:8.12.0-java11

MAINTAINER zhoushuai zs11195310@163.com

ENV SW_AGENT_NAME user-server

ENV AGENT_JAR_DIR /skywalking/agent/skywalking-agent.jar

ADD user-server.jar /app/user-server.jar

EXPOSE 8766

CMD java -javaagent:$AGENT_JAR_DIR $JAVA_OPTS -jar /app/user-server.jar
