FROM maven:3.8.2-jdk-11

WORKDIR /demo
COPY . .
RUN ls .
#TODO: remove
RUN mvn -e clean install -DskipTests

CMD mvn spring-boot:run
