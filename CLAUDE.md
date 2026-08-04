# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is the **Bonita Artifacts Model** repository, which defines the runtime model for the Bonita BPM platform. It contains multiple Maven modules that define various domain models for applications, processes, business objects, connectors, forms, profiles, and organizations.

The repository uses JAXB (XML binding) to generate Java classes from XML schemas and vice versa. Models defined as Java classes with JAXB annotations are used to serialize/deserialize XML configurations that define Bonita runtime artifacts.

## Build Commands

### Full Build
```bash
./mvnw clean verify
```

### Build Without Tests
```bash
./mvnw clean verify -DskipTests
```

### Compile Only
```bash
./mvnw clean compile
```

### Run Tests for a Specific Module
```bash
./mvnw test -f <module-name>/pom.xml
```

Example:
```bash
./mvnw test -f application-model/pom.xml
```

### Run a Single Test Class
```bash
./mvnw test -Dtest=ClassName -f <module-name>/pom.xml
```

### Format Code (Spotless)
```bash
./mvnw spotless:apply
```

### Check Code Format
```bash
./mvnw spotless:check
```

### Run Sonar Analysis
```bash
./mvnw sonar:sonar
```

## Module Architecture

The project follows a multi-module Maven structure with the following key modules:

### Core Model Modules
- **common-artifacts-model**: Base classes and interfaces shared across all models (`BaseElement`, `NamedElement`, `DescriptionElement`, XML parser utilities)
- **process-definition-model**: Complete BPMN process definition model (activities, gateways, events, data, contracts, connectors, contexts)
- **business-object-model**: Business Data Model (BDM) definitions with query generators
- **form-mapping-model**: Form mapping between processes/tasks and pages
- **application-model**: Application descriptor model (menus, pages, links)
- **organization-model**: Organization structure (users, groups, roles, memberships)
- **profile-model**: User profile definitions
- **connector-model**: Connector and user filter implementation descriptors
- **bdm-access-control-model**: Business object access control rules
- **business-archive**: Business Archive (BAR) format for packaging process definitions
- **artifacts-model-dependencies**: BOM for dependency management
- **coverage-report**: Aggregated Jacoco coverage report

### Key Architecture Patterns

1. **JAXB-Based XML Serialization**: Most modules use JAXB annotations (`@XmlRootElement`, `@XmlAttribute`, `@XmlElement`) to define the mapping between Java objects and XML. The `package-info.java` files define XML namespaces using `@XmlSchema`.

2. **XSD Generation**: Several modules use the `jaxb2-maven-plugin` to automatically generate XSD schemas from annotated Java classes during the build (see `business-object-model`, `application-model`, `form-mapping-model`, etc.).

3. **Builder Pattern**: Models use fluent builder APIs for constructing complex objects (e.g., `FormMappingModelBuilder`, `ApplicationNodeBuilder`).

4. **Converter/Parser Pattern**: Dedicated converter and parser classes handle XML serialization/deserialization (e.g., `ApplicationNodeContainerConverter`, `OrganizationParser`, `BDMAccessControlParser`).

5. **Contribution Pattern** (business-archive module): Uses a visitor-like pattern where `BusinessArchiveContribution` implementations handle specific artifact types in BAR files.

## JAXB Migration Context

The migration from `javax.xml.bind` (JAXB 2.x) to `jakarta.xml.bind` is complete. The project now uses JAXB 4, managed by importing `org.glassfish.jaxb:jaxb-bom` in the `artifacts-model-dependencies` BOM. When working with JAXB:

- Always use `jakarta.xml.bind.*` imports, never `javax.xml.bind.*`
- To upgrade JAXB, bump the single `jaxb-bom.version` property in `artifacts-model-dependencies/pom.xml`. Never pin `jakarta.xml.bind-api` or `jaxb-runtime` individually — the whole point of the BOM import is that the API and runtime cannot drift apart
- The activation implementation (`org.eclipse.angus:angus-activation`) comes transitively from `jaxb-runtime`; do not add explicit activation dependencies
- `org.glassfish.hk2:osgi-resource-locator` is NOT a JAXB dependency: modules declare it directly because their parsers use `ResourceFinder` to resolve XSDs in OSGi environments (Bonita Studio); keep it where declared
- After changing imports, run `./mvnw spotless:apply` to ensure proper formatting

## Testing

- Tests use **JUnit 5** (Jupiter) with AssertJ for assertions
- Some modules use **Mockito** for mocking
- Custom AssertJ assertions exist for domain objects (e.g., `BusinessObjectAssert`, `FieldAssert`)
- Integration tests use Maven Failsafe plugin

## Code Style and Formatting

- The project uses **Spotless** with Eclipse formatter configuration
- Formatter config: `formatter.xml`
- Import order: `eclipse.importorder`
- License header: `header.txt`
- Always run `./mvnw spotless:apply` before committing
- Spotless check runs automatically during `process-sources` phase

## Commit Message Format

Follow the conventional commit format:
```
type(category): description [flags]
```

Types: `breaking`, `build`, `ci`, `chore`, `docs`, `feat`, `fix`, `other`, `perf`, `refactor`, `revert`, `style`, `test`

Example: `feat(business-object-model): add support for multiple queries`

## Branching Strategy

- Main development branch: **develop**
- Uses **GitFlow** branching strategy
- Feature branches: `feature/*`
- Bugfix branches: `bugfix/*`
- Hotfix branches: `hotfix/*`
- Release branches: `release/*`

## Java Version

- Requires **Java 17** for compilation
- Maven compiler is configured to target Java 17 (`maven.compiler.release=17`)
- Uses Maven wrapper (`./mvnw`) - Maven 3.8.6+

## Regenerating AssertJ Classes

If you modify a model class and need to regenerate its AssertJ assertion class:

1. Run `./mvnw clean compile` to ensure classes are compiled
2. Delete the assertion class you want to regenerate
3. Edit the module's `pom.xml` and add the AssertJ plugin configuration in the `<build>` section with the classes to regenerate
4. Run `./mvnw assertj:generate-assertions -f <module>/pom.xml`
5. Remove `@javax.annotation.Generated` annotations from generated classes
6. Format with `./mvnw spotless:apply`
7. Restore the original `pom.xml` (remove the plugin configuration)

## Key Dependencies

- **JAXB API/Runtime**: XML binding (transitioning from javax to jakarta)
- **Jackson**: JSON processing (annotations, databind)
- **Lombok**: Boilerplate reduction (getters, setters, builders)
- **SLF4J 2.0**: Logging API
- **JUnit 5**: Testing framework
- **AssertJ**: Fluent assertions
- **Mockito**: Mocking framework

## Common Issues

- If tests fail with XML parsing errors, ensure XSD schemas are generated: run `./mvnw clean compile` first
- If spotless check fails, run `./mvnw spotless:apply` to auto-format
- If you see JAXB class loading issues, check that `jakarta.xml.bind-api` and `jaxb-runtime` are both resolved; no `javax.xml.bind` or `com.sun.activation` artifacts should be on the classpath
