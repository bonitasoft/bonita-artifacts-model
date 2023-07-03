# Bonita Runtime Model

[![Build](https://github.com/bonitasoft/bonita-artifacts-model/workflows/Build/badge.svg)](https://github.com/bonitasoft/bonita-artifacts-model/actions/workflows/build.yml)
[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=bonitasoft_bonita-artifacts-model&metric=alert_status)](https://sonarcloud.io/dashboard?id=bonitasoft_bonita-artifacts-model)
[![GitHub release](https://img.shields.io/github/v/release/bonitasoft/bonita-artifacts-model?color=blue&label=Release)](https://github.com/bonitasoft/bonita-artifacts-model/releases)
[![Maven Central](https://img.shields.io/maven-central/v/org.bonitasoft.engine/bonita-process-definition-model.svg?label=Maven%20Central&color=orange&logo=apachemaven)](https://central.sonatype.com/artifact/org.bonitasoft.engine/bonita-process-definition-model/)
[![License: LGPL v2.1](https://img.shields.io/badge/License-LGPL%20v2.1-yellow.svg)](https://www.gnu.org/licenses/old-licenses/lgpl-2.1.en.html)

This repository contains the different mode modules required to write and read the Business Archive format. Such as:

* The `bonita-process-definition-model` module. 
* The `bonita-form-mapping-model` module. 
* The `bonita-business-archive` module. 

## Quick start

### Pre-requisite

* [Java 11][java] for compilation

### Build

#### Using Maven

* Build it using maven `./mvnw clean verify`

## Contribute

### Report issues

If you want to report an issue or a bug use our [official bugtracker](https://bonita.atlassian.net/projects/BBPMC)

### How to contribute

Before contributing, read the [guidelines](CONTRIBUTING.md)

### Branching strategy

This repository follows the [gitflow branching strategy](https://gitversion.net/docs/learn/branching-strategies/gitflow/examples).

### Release

To release a new version, maintainers may use the Release and Publication GitHub actions.

* Release action will invoke the `gitflow-maven-plugin` to perform all required merges, version updates and tag creation.
* Publication action will build and deploy a given tag to Maven Central
* A Github release should be created and associated to the tag.

## Resources

* [Documentation][documentation]


[java]: https://adoptium.net/temurin/releases/?version=11
[documentation]: https://documentation.bonitasoft.com

    
