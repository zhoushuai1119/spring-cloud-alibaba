FROM apache/skywalking-java-agent:8.12.0-java11

MAINTAINER zhoushuai zs11195310@163.com

ENV SW_AGENT_NAME camunda-server

ENV AGENT_JAR_DIR /skywalking/agent/skywalking-agent.jar

ADD camunda-server.jar /app/camunda-server.jar

EXPOSE 8768

CMD java -javaagent:$AGENT_JAR_DIR $JAVA_OPTS -jar /app/camunda-server.jar
