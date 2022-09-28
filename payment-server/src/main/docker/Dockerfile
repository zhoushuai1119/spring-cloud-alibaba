FROM apache/skywalking-java-agent:8.12.0-java11

MAINTAINER zhoushuai zs11195310@163.com

ENV SW_AGENT_NAME payment-server

ENV AGENT_JAR_DIR /skywalking/agent/skywalking-agent.jar

ADD payment-server.jar /app/payment-server.jar

EXPOSE 8764

CMD java -javaagent:$AGENT_JAR_DIR $JAVA_OPTS -jar /app/payment-server.jar
