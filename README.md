# Spring Security Multiple Issuers

[![Build](https://github.com/michaelruocco/spring-security-multiple-issuers/workflows/pipeline/badge.svg)](https://github.com/michaelruocco/spring-security-multiple-issuers/actions)
[![codecov](https://codecov.io/gh/michaelruocco/spring-security-multiple-issuers/branch/master/graph/badge.svg?token=FWDNP534O7)](https://codecov.io/gh/michaelruocco/spring-security-multiple-issuers)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/272889cf707b4dcb90bf451392530794)](https://www.codacy.com/gh/michaelruocco/spring-security-multiple-issuers/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=michaelruocco/spring-security-multiple-issuers&amp;utm_campaign=Badge_Grade)
[![BCH compliance](https://bettercodehub.com/edge/badge/michaelruocco/spring-security-multiple-issuers?branch=master)](https://bettercodehub.com/)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_spring-security-multiple-issuers&metric=alert_status)](https://sonarcloud.io/dashboard?id=michaelruocco_spring-security-multiple-issuers)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_spring-security-multiple-issuers&metric=sqale_index)](https://sonarcloud.io/dashboard?id=michaelruocco_spring-security-multiple-issuers)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_spring-security-multiple-issuers&metric=coverage)](https://sonarcloud.io/dashboard?id=michaelruocco_spring-security-multiple-issuers)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=michaelruocco_spring-security-multiple-issuers&metric=ncloc)](https://sonarcloud.io/dashboard?id=michaelruocco_spring-security-multiple-issuers)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.michaelruocco/spring-security-multiple-issuers.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.michaelruocco%22%20AND%20a:%22spring-security-multiple-issuers%22)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Overview

This repo is used to try and demonstrate how to configure a spring 0Auth2 resource server to work
with more than one authorization provider / issuer uri. In this particular case we are using
two different instances of keycloak.

## Running tests

```bash
./gradlew clean spotlessApply build integrationTest
```

## Running locally

To start up two different instances of keycloak with their own realms and users you can run the
docker compose command:

```bash
docker-compose up -d
```

This will bring up two local instances of keycloak:

* The first on `http://localhost:8097` that contains a realm `demo-realm-1` and two users `demo-user-1` and `demo-user-2` both with passwords `pwd`
* The second on `http://localhost:8098` that contains a realm `demo-realm-2` and two users `demo-user-3` and `demo-user-4` both with passwords `pwd`

To run the service and have it use both instances of keycloak to perform token validation you can run:

```bash
./gradlew bootRun
```

This will start the service on port 8099. If you want to test being able to call the service using either keycloak instance
as the token issuer then you can import the postman collection at `postman/spring-security-multiple-issuers.postman_collection.json`
and use the two example requests under each issuer sub folder, each folder is configured with the to generate a token against
each keycloak instance.

* For the first keycloak instance you can use usernames `demo-user-1` and `demo-user-2` both with the password `pwd`
* For the second keycloak instance you can use usernames `demo-user-3` and `demo-user-4` both with the password `pwd`

## Building docker image locally

To build the docker image locally you can run the following commands:

```bash
./gradlew clean buildImage currentVersion
```

This will build a docker image using the current version number that will also be printed
to the console by this command.

## Running docker image locally

To run the docker image locally you can run the following command, assuming you have run the
command to build the docker image locally beforehand:

```bash
docker-compose --profile local-app-docker up -d
```

This will start the service on port 8099 in the same way the `./gradlew bootRun` command does,
the only difference in this case is that the application is running inside docker, rather than
directly on the local machine.

*Note* in order for this example to work locally you will also need to updated your `/etc/hosts`
file to add the following two entries:

```bash
127.0.0.1       keycloak-1
127.0.0.1       keycloak-2
```

You will also need to update the postman collection so that the Auth URL and Access Token URL
so they both use the appropriate keycloak host name rather than just localhost e.g:

* `http://keycloak-1:8097`
* `http://keycloak-2:8098`

if you do not do the two steps defined above the authentication will fail because the
issuer (iss) claim in the token will not match the issuer uri configured inside the
application.

## Useful Commands

```gradle
// cleans build directories
// prints currentVersion
// formats code
// builds code
// runs tests
// checks dependency versions
./gradlew clean currentVersion dependencyUpdates spotlessApply build
```