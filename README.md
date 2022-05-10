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