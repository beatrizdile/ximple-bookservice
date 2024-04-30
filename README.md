# Book management system

This is a RESTful API made with Java and Spring Boot for a simple book management system.

It utilizes an in-memory database with Spring Data JPA.
Authentication was implemented using Spring Security.
The API documentation was constructed with Swagger.
Exceptions are being handled and the appropriate HTTP status codes are being returned.

## Getting started

Make sure you are using the latest JDK and at least Java 17, and `JAVA_HOME` path must be set up.

Clone the git repository:
```bash
git clone https://github.com/beatrizdile/ximple-bookservice.git
```

You can build the project and run the tests by running:
```bash
mvn package
```

Once successfully built, you can run the service with:
```bash
mvn spring-boot:run
```

The API documentation can be consulted through the [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) url. To be able to make any API requests you first need to register an account on `auth/register` and login at `auth/login`, then you'll receive a Bearer token to be inserted on the header as an authentication key.
