########
FROM maven:3.8.2-jdk-11 as maven_build

WORKDIR /app
#COPY pom.xml .
#
## To resolve dependencies in a safe way (no re-download when the source code changes)
#RUN mvn clean package -Dmaven.main.skip -Dmaven.test.skip && rm -r target
#
## To package the application
#COPY ./* .
#RUN mvn clean install -Dmaven.test.skip

RUN echo \
    "<settings xmlns='http://maven.apache.org/SETTINGS/1.0.0\' \
    xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' \
    xsi:schemaLocation='http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd'> \
        <localRepository>/root/Users/myname/.m2/repository</localRepository> \
        <interactiveMode>true</interactiveMode> \
        <usePluginRegistry>false</usePluginRegistry> \
        <offline>false</offline> \
    </settings>" \
    > /usr/share/maven/conf/settings.xml;

# To package the application
COPY . /app
RUN mvn clean install -Dmaven.test.skip

RUN ls .

########
FROM openjdk:11-jdk-slim
WORKDIR /app
ARG MODULE

RUN echo ${MODULE}
#COPY --from=maven_build /app/${MODULE}/target/*.jar ${MODULE}.jar
COPY --from=maven_build /app/${MODULE}/target/*.jar .
RUN ls *.jar

#startup
#ENTRYPOINT java -jar app.jar

#ADD ./target/spring-boot-example-0.0.1-SNAPSHOT.jar /developments/
#ENTRYPOINT ["java","-jar","/developments/spring-boot-example-0.0.1-SNAPSHOT.jar"]

######## startup
ENV JAVA_OPTS ""
CMD [ "bash", "-c", "java ${JAVA_OPTS} -jar ./*.jar -v"]
