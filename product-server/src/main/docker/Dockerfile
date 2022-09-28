FROM apache/skywalking-java-agent:8.12.0-java11

MAINTAINER zhoushuai zs11195310@163.com

ENV SW_AGENT_NAME product-server

ENV AGENT_JAR_DIR /skywalking/agent/skywalking-agent.jar

ADD product-server.jar /app/product-server.jar

EXPOSE 8765

CMD java -javaagent:$AGENT_JAR_DIR $JAVA_OPTS -jar /app/product-server.jar
