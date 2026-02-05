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

## Prerequisites

* [Java 11][java] for compilation

## How to build

To build the project, run the following command in the root of the project with the maven wrapper:

```
./mvnw install
```

This will install all the Bonita Runtime artifacts.

## How to contribute

### Report issues

If you want to report an issue or a bug use our [official bugtracker](https://bonita.atlassian.net/projects/BBPMC).

### Code contributions

Before contributing, read the [guidelines](CONTRIBUTING.md).

## How to release

This project uses the [gitflow-maven-plugin](https://github.com/aleksandr-m/gitflow-maven-plugin) for release
management. Releases are created using the GitHub Actions workflow.

### Branch Strategy

- **develop**: Main development branch for future releases
- **support/A.B.x**: Maintenance branches for older versions (e.g., support/1.0.x, support/1.1.x, support/2.0.x)
- **master**: Not used (removed, use support branches for maintenance)

### Creating a Release

Releases are created via the GitHub Actions
workflow [Release](https://github.com/bonitasoft/bonita-process-model/actions/workflows/release.yml)

#### Workflow Parameters

| Parameter                   | Description                                                                  | Default     | Example                         |
|-----------------------------|------------------------------------------------------------------------------|-------------|---------------------------------|
| **version**                 | Version to release (leave empty to use current pom.xml version)              | empty       | `1.2.2`                         |
| **nextDevelopmentVersion**  | Next development version (leave empty to use versionDigitToIncrement policy) | empty       | `1.2.3-SNAPSHOT`                |
| **versionDigitToIncrement** | Version digit to increment in next development version                       | `2` (patch) | `0`=major, `1`=minor, `2`=patch |

#### Release Types

**Patch Release (X.Y.Z)** - Bug fixes and minor updates

- Set `versionDigitToIncrement`: **2** (default)
- Example: `1.2.2 → 1.2.3-SNAPSHOT`
- Use for: Support branches, hotfixes

**Minor Release (X.Y.0)** - New features, backward compatible

- Set `versionDigitToIncrement`: **1**
- Example: `1.2.2 → 1.3.0-SNAPSHOT`
- Use for: Regular releases from develop

**Major Release (X.0.0)** - Breaking changes

- Set `versionDigitToIncrement`: **0**
- Example: `1.2.2 → 2.0.0-SNAPSHOT`
- Use for: Major version bumps

### Release Process

When you run the workflow from any branch (develop or support/*), it will:

1. Update version to release version (removes -SNAPSHOT)
2. Commit: "chore(release): Update versions for release"
3. Create git tag (e.g., `1.2.2`)
4. Update version to next development version
5. Commit: "chore(release): Update for next development version"
6. Push commits and tags to GitHub

**Note**: The workflow does NOT create a release branch - it performs the release directly on the branch you selected.

### Cascade Merging for Support Branches

When releasing from a **support branch**, you should manually cascade merge the changes up to newer branches and
develop.

**Example**: After doing a release from `support/1.0.x`, merge `support/1.0.x` into `support/1.1.x`, then merge
`support/1.1.x` into `support/1.2.x`, and so on into `develop`.

## Resources

* [Documentation][documentation]


[java]: https://adoptium.net/temurin/releases/?version=11
[documentation]: https://documentation.bonitasoft.com
