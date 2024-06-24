# Bonita Runtime Model

[![Build](https://github.com/bonitasoft/bonita-artifacts-model/workflows/Build/badge.svg)](https://github.com/bonitasoft/bonita-artifacts-model/actions/workflows/build.yml)
[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=bonitasoft_bonita-artifacts-model&metric=alert_status)](https://sonarcloud.io/dashboard?id=bonitasoft_bonita-artifacts-model)
[![GitHub release](https://img.shields.io/github/v/release/bonitasoft/bonita-artifacts-model?color=blue&label=Release)](https://github.com/bonitasoft/bonita-artifacts-model/releases)
[![Maven Central](https://img.shields.io/maven-central/v/org.bonitasoft.engine/bonita-process-definition-model.svg?label=Maven%20Central&color=orange&logo=apachemaven)](https://central.sonatype.com/artifact/org.bonitasoft.engine/bonita-artifacts-model-dependencies/)
[![License: LGPL v2.1](https://img.shields.io/badge/License-LGPL%20v2.1-yellow.svg)](https://www.gnu.org/licenses/old-licenses/lgpl-2.1.en.html)

This repository contains the different modules that define the Bonita Runtime model. Which are:

* `bonita-application-model`
* `bonita-bdm-access-control-model`
* `bonita-business-archive`
* `bonita-business-object-model`
* `bonita-connector-model`
* `bonita-form-mapping-model`
* `bonita-organization-model`
* `bonita-process-definition-model`
* `bonita-profile-model`

## Quick start

### Pre-requisite

* [Java 11][java] for compilation

### Build

#### Using Maven

* Build it using maven `./mvnw clean verify`

## Contribute

### Report issues

If you want to report an issue or a bug use our [official bugtracker](https://bonita.atlassian.net/projects/BBPMC).

### How to contribute

Before contributing, read the [guidelines](CONTRIBUTING.md).

### Branching strategy

This repository follows the [GitFlow branching strategy](https://gitversion.net/docs/learn/branching-strategies/gitflow/examples).

### Regenerate Assert classes

You may want to regenerate Assert classes after modifying a model element. To do so, you can use the [AssertJ](https://joel-costigliola.github.io/assertj/assertj-assertions-generator-maven-plugin.html#quickstart) plugin. 

1. First, run `./mvnw clean compile` to make sure the classes are found.
2. Delete the classes you want to regenerate.
3. Then edit the project's pom.xml and add the plugin configuration in the `build` section with the classes/packages you want to regenerate. E.g., in `application-model/pom.xml` add:
```xml
      <plugin>
        <groupId>org.assertj</groupId>
        <artifactId>assertj-assertions-generator-maven-plugin</artifactId>
        <version>2.2.0</version>
        <configuration>
          <classes>
            <param>org.bonitasoft.engine.business.application.impl.ApplicationLinkImpl</param>
            <param>org.bonitasoft.engine.business.application.xml.ApplicationLinkNode</param>
            <param>org.bonitasoft.engine.business.application.xml.ApplicationNodeContainer</param>
          </classes>
          <generateAssertions>true</generateAssertions>
          <generateBddAssertions>false</generateBddAssertions>
          <generateSoftAssertions>false</generateSoftAssertions>
          <generateJUnitSoftAssertions>false</generateJUnitSoftAssertions>
          <hierarchical>false</hierarchical>
          <targetDir>application-model/src/test/java</targetDir>
          <generateAssertionsForAllFields>true</generateAssertionsForAllFields>
        </configuration>
      </plugin>
```
4. Then run the following command to regenerate the Assert classes: `./mvnw assertj:generate-assertions -f application-model/pom.xml` (adapt `-f` to your project)
5. Remove the `@javax.annotation.Generated` annotation from the generated classes and the generated `Assertions.java` file.
6. Format with `./mvnw spotless:apply`.
7. Make sure the generated classes satisfy your needs and that you did not corrupt other classes.
8. Restore the original state of the project's pom.xml file.

### Release

To release a new version, maintainers may use the Release and Publication GitHub actions.

1. [Release action](https://github.com/bonitasoft/bonita-artifacts-model/actions/workflows/release.yml) will invoke the `gitflow-maven-plugin` to perform all required merges, version updates and tag creation.
2. [Publication action](https://github.com/bonitasoft/bonita-artifacts-model/actions/workflows/publish.yml) will build and deploy a given tag to Maven Central.
3. A GitHub release should be created and associated to the tag.

## Resources

* [Documentation][documentation]


[java]: https://adoptium.net/temurin/releases/?version=11
[documentation]: https://documentation.bonitasoft.com
