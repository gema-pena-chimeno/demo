# Local testing

To test the applications Customer App and User App in local we need a database.

In order to make it simpler for each developer, we describe in this page:
* How to create a local database using docker.
* How to configure the applications to use the database. 

## Create database using docker

### 1. Get the docker image 

Check if the postgres image is already downloaded:

`docker images postgres`

In case that is not downloaded execute:

`docker pull postgres`

### 1. Create a folder for the database data

For example:

`mkdir ${HOME}/postgres-data/`

In order to save the images of the Customer App we need to create another folder, for example:

`mkdir ${HOME}/image-data/`

### 2. Run the postgres image

`docker run -d --rm \
--name dev-postgres \
-e POSTGRES_PASSWORD=1234 \
-v ${HOME}/postgres-data/:/var/lib/postgresql/data \
-p 5432:5432 \
postgres`

### 3. Create the database (only the first time)

Access to the database:

`psql -h localhost -U postgres `

And create the database:

`CREATE DATABASE demo_db`

### Create the first admin user  (only the first time)

After the creation of the database, to insert the first user, access to the local database:

`psql -h localhost -U postgres`

Select the database:

`\c demo_db`

And insert the user with role ADMIN, for example:
`insert into user_info(id, user_name, password, role, active, version, created_at, last_updated)
values('8be33244-c099-47e9-86ad-11538a8c1443', 'admin', '1234567890aB', 'ADMIN', true, 0, now(), now());`
`
### Database environment properties

These applications are configured to be executed with environment variables.

Configure the database environment variables to execute the applications:

_SPRING_DATASOURCE_URL = jdbc:postgresql://localhost:5432/demo_db_

_SPRING_DATASOURCE_USERNAME = postgres_

_SPRING_DATASOURCE_PASSWORD = 1234_

_SPRING_JPA_HIBERNATE_DDL_AUTO = update_

In order to configure the application Customer more properties must be set. See customer [README.md](./customer/README.md) for details.

## Sum up

Once the database is created and the first user inserted, as the data is saved in a local folder, we just have to execute the postgres image to keep testing our applications. 

`docker run -d --rm \
--name dev-postgres \
-e POSTGRES_PASSWORD=1234 \
-v ${HOME}/postgres-data/:/var/lib/postgresql/data \
-p 5432:5432 \
postgres`
