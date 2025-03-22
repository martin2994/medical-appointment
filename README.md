# Medical Appointment

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Prerequisites

- Java 21
- Docker

## Running the development database

You can find in the `docker-compopose.yaml` file the service to run a PostgreSQL database.
The database will be automatically provisioned by flyway when the application is started.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/medical-appointment-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Create image

You can create an image using:
```shell script
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/medical-appointment-jvm .
```
Then use it in the `docker-compose.yaml`.

## Related Guides

- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and
  Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on
  it.

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

### TODO
- Add more javadoc + comments + package-info
- Handle not found object correctly on GET/UPDATE/DELETE by id
- Add UT on constraint error
- Add IT with a h2 database
- Add search filter for visit and patient
- Add field validation constraints
- Handle correctly exceptions with ServerExceptionMappers
- Add Authorization (Use Quarkus Keycloak dev tools)
- Add Swagger / OpenApi documentation
- Check image creation with jib
- Add a way to include or not patient data in the visit and the visit list in the patient
- On Visit object maybe add the doctor name and search by doctor name
- Encrypt data / check logger for sensitive data
