#FROM adoptopenjdk:11-jre-hotspot
#ARG JAR_FILE=customer-0.0.1-SNAPSHOT.jar
#COPY ${JAR_FILE} application.jar
##ENTRYPOINT ["java", "-jar", "application.jar"]
##ENTRYPOINT ["./wait-for-it.sh", "db:3306", "--", "java", "-Dspring.profiles.active=dev", "-jar", "application.jar"
#ENTRYPOINT ["java", "-Dspring.profiles.active=development", "-jar", "application.jar"]

FROM maven:3.8.2-jdk-11

WORKDIR /demo
COPY . .
RUN ls .
# you get the idea:
# COPY moduleN/pom.xml moduleN/pom.xml
RUN mvn -e clean install -DskipTests
#RUN mvn -N clean install -DskipTests
#COPY pom.xml .
#RUN mvn -B -e -C org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline



#RUN mkdir -p /parent/
#RUN ls ./parent/
#COPY ../* /parent/
#WORKDIR ./parent
#RUN ls
#RUN head pom.xml
#RUN mvn -N clean install -DskipTests
#WORKDIR .
#RUN ls
#COPY . .
#RUN ls
#RUN mvn -N clean install -DskipTests
#RUN cd ./customer
#RUN mvn clean install -DskipTests

CMD mvn spring-boot:run
