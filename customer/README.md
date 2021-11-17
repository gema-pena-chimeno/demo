# Customer REST API

Customer REST API

## Execute CustomerApplication

### Create database using docker

#### 0. Get the docker image 

Download the image postgres:

`docker pull postgres`

Or check if it's already downloaded:

`docker images postgres`

#### 1. Create a folder for you for the database data

For example:

`mkdir ${HOME}/postgres-data/`

And another for the customer images:
`mkdir ${HOME}/image-data/`

### 2. Run the postgres image

`docker run -d --rm \
--name dev-postgres \
-e POSTGRES_PASSWORD=1234 \
-v ${HOME}/postgres-data/:/var/lib/postgresql/data \
-v ${HOME}/image-data/:/image-data \
-p 5432:5432 \
postgres`

### Create database using docker

Configure the environment variables to execute CustomerApplication:

SPRING_DATASOURCE_URL = jdbc:postgresql://localhost:5432/demo_db
SPRING_DATASOURCE_USERNAME = postgres
SPRING_DATASOURCE_PASSWORD = 1234
SPRING_JPA_HIBERNATE_DDL_AUTO = update

Also add the parameter to save the images of the user:
IMAGE_FOLDER = ${HOME}/image-data/

And to create the URL to access to them:
URL_PATH=/image-data

## Create the database

Log into the database:
`psql -h localhost -U postgres`

And create the database:
`create database demo_db;`

And insert the first ADMIN user, for example:
`insert into user_info(id, user_name, password, role, active, version, created_at, last_updated)
values('8be33244-c099-47e9-86ad-11538a8c1443', 'admin', '1234567890aB', 'ADMIN', true, 0, now(), now());`

## Execute in docker

We also have the chance to execute 2 containers, one for the database and another for the application:

Note that the database will be empty.

Execute:
`docker-compose up`

In case that the images must be built again execute:
`docker-compose up --build`

Execute this command to stop the containers"
`docker-compose down`