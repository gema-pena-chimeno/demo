FROM maven:3.8.2-jdk-11

WORKDIR /demo
COPY . .
#TODO: clean
#RUN ls .
#RUN mvn -e clean install -DskipTests
RUN mvn clean install -DskipTests

CMD mvn spring-boot:run
