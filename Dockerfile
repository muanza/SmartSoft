FROM maven:3.9.9-eclipse-temurin-11 AS build
WORKDIR /workspace
COPY pom.xml .
COPY faturacao-crm/pom.xml faturacao-crm/pom.xml
COPY faturacao-pos/pom.xml faturacao-pos/pom.xml
COPY faturacao-crm/src faturacao-crm/src
COPY faturacao-pos/src faturacao-pos/src
RUN mvn -q -DskipTests package

FROM quay.io/wildfly/wildfly:26.1.3.Final-jdk11
COPY --from=build /workspace/faturacao-crm/target/faturacao-crm.war /opt/jboss/wildfly/standalone/deployments/faturacao-crm.war
COPY --from=build /workspace/faturacao-pos/target/faturacao-pos.war /opt/jboss/wildfly/standalone/deployments/faturacao-pos.war
EXPOSE 8080 9990
