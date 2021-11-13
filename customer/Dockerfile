FROM maven:3.8.2-jdk-11

WORKDIR /demo

# copy the project files
COPY ./pom.xml ./pom.xml

# build all dependencies for offline use
RUN mvn dependency:go-offline -B

VOLUME ${HOME}/.m2/repository

# copy your other files
COPY ./src ./src

# build for release
RUN mvn package -DskipTests

#COPY . .
##TODO: clean
##RUN ls .
##RUN mvn -e clean install -DskipTests
#RUN mvn clean install -DskipTests

VOLUME /root/.m2/repository

CMD mvn spring-boot:run
