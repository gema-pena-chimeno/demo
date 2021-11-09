# demo
Synthetic API project for The Agile Monkeys


docker pull postgres

docker images

## 1. Create a folder in a known location for you
$ mkdir ${HOME}/postgres-data/

## 2. run the postgres image
$ docker run -d \
--name dev-postgres \
-e POSTGRES_PASSWORD=1234 \
-v ${HOME}/postgres-data/:/var/lib/postgresql/data \
-p 5432:5432 \
postgres

SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/customer_db
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=1234
SPRING_JPA_HIBERNATE_DDL_AUTO=update
