# Customer REST API

Customer REST API

## Execute in local

### Create database using docker

#### 0. Get the docker image 

Download the image postgres:

`docker pull postgres`

Or check if it's already downloaded:

`docker images postgres`

#### 1. Create a folder for you for the database data

For example:

`mkdir ${HOME}/postgres-data/`

### 2. Run the postgres image

`docker run -d --rm \
--name dev-postgres \
-e POSTGRES_PASSWORD=1234 \
-v ${HOME}/postgres-data/:/var/lib/postgresql/data \
-p 5432:5432 \
postgres`

### Create database using docker

Configure the environment variables to execute CustomerApplication:

SPRING_DATASOURCE_URL = jdbc:postgresql://localhost:5432/customer_db
SPRING_DATASOURCE_USERNAME = postgres
SPRING_DATASOURCE_PASSWORD = 1234
SPRING_JPA_HIBERNATE_DDL_AUTO = update
