# demo
Synthetic API for The Agile Monkeys

It contains 3 modules:
* Data: entities in the shared database. See [README.md](./data/README.md)
* Customer: customer REST API. See [README.md](./customer/README.md)
* User: user REST API. See [README.md](./user/README.md)

See [HELP.md](./user/HELP.md) for information about Spring and Spring Boot.

## Build applications

In the root we have a Dockerfile that allows to create the docker images to deploy the applications.

The script has a parameter, MODULE, to set the name of the module to build:
- customer: creates the image customer-app.
- user: creates the image user-app.

Example of the command:
```shell
docker build --build-arg MODULE=customer --progress=plain -t customer-app .
```
```shell
docker build --build-arg MODULE=user --progress=plain -t user-app .
```

## Execute applications

The images can be executed setting the required environment properties.

For example, to execute the image in local, again the local database (see [LocalTest.md](./LocalTest.md)), we could execute the command:
```shell
docker run \
-e SPRING_DATASOURCE_URL='jdbc:postgresql://localhost:5432/demo_db' \
-e SPRING_DATASOURCE_USERNAME='postgres' \
-e SPRING_DATASOURCE_PASSWORD='dbPassword' \
-e SPRING_JPA_HIBERNATE_DDL_AUTO='update' \
-e IMAGE_FOLDER='/var/lib/docker/volumes/image-data/' \
-e URL_PATH='/image-data' \
customer-app
```

For user-app we can execute:
```shell
docker run \
-e SPRING_DATASOURCE_URL='jdbc:postgresql://localhost:5432/demo_db' \
-e SPRING_DATASOURCE_USERNAME='postgres' \
-e SPRING_DATASOURCE_PASSWORD='dbPassword' \
-e SPRING_JPA_HIBERNATE_DDL_AUTO='update' \
user-app
```

## Future improvements
* Change from Basic Auth to OAuth2.
* Improvement:
  * Implement an annotation:
    ``` java
    @Target({ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Constraint(validatedBy = {ImageValidator.class})
    public @interface ValidImageFile {
    }
    ```
  * And refactor ImageValidator to have a custom REST parameter validator
    ``` java
    @SupportedValidationTarget(ValidationTarget.PARAMETERS)
    public class ImageValidator implements ConstraintValidator<ValidImageFile, MultipartFile> {
      ... 
    ```
* If it's worth the effort, define and implement a better way to insert the first admin user in a secure way.
* Investigate secure ways to keep the user passwords.
* Add pagination to the customer and user list.