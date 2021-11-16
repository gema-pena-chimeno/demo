FROM java:8
FROM maven:alpine

# image layer
WORKDIR /app
ADD pom.xml /app
RUN mvn verify clean --fail-never

# Image layer: with the application
COPY . /app
RUN mvn -v
RUN mvn clean install -DskipTests
EXPOSE 8080
#ADD ./target/your.jar .
ENTRYPOINT ["java","-jar","/app/target/*.jar"]

#FROM maven:3.8.2-jdk-11
#
#
#
#VOLUME ${HOME}/.m2/repository
#
##ADD gwr $HOME
#
##RUN ls ${HOME}/.m2/repository
#
##RUN echo \
##    "<settings xmlns='http://maven.apache.org/SETTINGS/1.0.0\' \
##    xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' \
##    xsi:schemaLocation='http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd'> \
##        <localRepository>${HOME}/.m2/repository</localRepository> \
##        <interactiveMode>true</interactiveMode> \
##        <usePluginRegistry>false</usePluginRegistry> \
##        <offline>false</offline> \
##    </settings>" \
##    > ${HOME}/.m2/settings.xml
#
##RUN ls ${HOME}/.m2/repository
#
## build all dependencies for offline use
##RUN mvn dependency:go-offline -B
#
#
#
#WORKDIR /demo
#
## copy the project files
#COPY ./pom.xml ./pom.xml
#
#RUN mvn install -nsu
#
## copy your other files
#COPY ./src ./src
#
## build for release
#RUN mvn package -DskipTests
#
##COPY . .
###TODO: clean
###RUN ls .
###RUN mvn -e clean install -DskipTests
##RUN mvn clean install -DskipTests
#
##VOLUME /root/.m2/repository
#
#CMD mvn spring-boot:run
