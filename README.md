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

To start up two different instances of keycloak with their own realms and users you can run the docker
compose command:

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

## Useful Commands

```gradle
// cleans build directories
// prints currentVersion
// formats code
// builds code
// runs tests
// checks for gradle issues
// checks dependency versions
./gradlew clean currentVersion dependencyUpdates lintGradle spotlessApply build
```