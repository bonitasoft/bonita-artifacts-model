# Architecture Decision Record: javax to jakarta XML Binding Migration

## Status
Proposed

## Context

The Java EE platform has been transferred to the Eclipse Foundation and rebranded as Jakarta EE. As part of this transition, all `javax.*` namespaces have been migrated to `jakarta.*` namespaces starting with Jakarta EE 9.

This project currently uses:
- `javax.xml.bind:jaxb-api:2.3.1` (Java EE 8)
- `org.glassfish.jaxb:jaxb-runtime:2.3.1` (Java EE 8)

We need to migrate to:
- `jakarta.xml.bind:jakarta.xml.bind-api:3.0.0+` (Jakarta EE 9+)
- `org.glassfish.jaxb:jaxb-runtime:3.0.0+` (Jakarta EE 9+)

### Impact Analysis

**Affected Modules:** 11 of 12 modules
**Affected Java Files:** 148 files (141 source + 7 test)
**Affected POMs:** 11 module POMs + 1 BOM

**Critical Files:**
- `common-artifacts-model`: Base classes used by all other modules
- `artifacts-model-dependencies`: BOM that centralizes dependency versions

## Decision

We will migrate all `javax.xml.bind.*` imports to `jakarta.xml.bind.*` across the entire codebase in a single, atomic change.

### Migration Strategy

#### Phase 1: Dependency Updates (Maven POMs)
1. Update BOM (`artifacts-model-dependencies/pom.xml`) first
2. All other modules inherit from BOM
3. No version changes needed in individual module POMs

#### Phase 2: Java Code Updates
1. Update `common-artifacts-model` first (base classes)
2. Update remaining modules in dependency order
3. Use automated find-replace for consistency

#### Phase 3: Testing & Validation
1. Run full build to verify compilation
2. Execute test suite to verify functionality
3. Verify XSD generation still works correctly

## Implementation Plan

### 1. Maven Dependency Updates

**File:** `artifacts-model-dependencies/pom.xml`

Replace:
```xml
<dependency>
  <groupId>javax.xml.bind</groupId>
  <artifactId>jaxb-api</artifactId>
  <version>2.3.1</version>
</dependency>
<dependency>
  <groupId>org.glassfish.jaxb</groupId>
  <artifactId>jaxb-runtime</artifactId>
  <version>2.3.1</version>
</dependency>
```

With:
```xml
<dependency>
  <groupId>jakarta.xml.bind</groupId>
  <artifactId>jakarta.xml.bind-api</artifactId>
  <version>3.0.1</version>
</dependency>
<dependency>
  <groupId>org.glassfish.jaxb</groupId>
  <artifactId>jaxb-runtime</artifactId>
  <version>3.0.2</version>
</dependency>
```

**Note:** Keep `com.sun.activation:jakarta.activation:1.2.2` as-is (already Jakarta).

**Update all module POMs** (11 files):
- Replace `javax.xml.bind:jaxb-api` → `jakarta.xml.bind:jakarta.xml.bind-api`
- Keep exclusions for `javax.activation:javax.activation-api`
- No version changes (inherited from BOM)

### 2. Java Code Import Updates

**Search Pattern:** `javax.xml.bind`
**Replace With:** `jakarta.xml.bind`

**Affected Classes (by import type):**

#### JAXB Core Classes:
- `javax.xml.bind.JAXBContext` → `jakarta.xml.bind.JAXBContext`
- `javax.xml.bind.JAXBElement` → `jakarta.xml.bind.JAXBElement`
- `javax.xml.bind.JAXBException` → `jakarta.xml.bind.JAXBException`
- `javax.xml.bind.Marshaller` → `jakarta.xml.bind.Marshaller`
- `javax.xml.bind.Unmarshaller` → `jakarta.xml.bind.Unmarshaller`
- `javax.xml.bind.DataBindingException` → `jakarta.xml.bind.DataBindingException`
- `javax.xml.bind.UnmarshalException` → `jakarta.xml.bind.UnmarshalException`

#### JAXB Annotations:
- `javax.xml.bind.annotation.*` → `jakarta.xml.bind.annotation.*`
  - `@XmlElement`, `@XmlAttribute`, `@XmlRootElement`
  - `@XmlAccessorType`, `@XmlAccessType`
  - `@XmlElementWrapper`, `@XmlType`, `@XmlTransient`
  - `@XmlSchema`, `@XmlNs`, `@XmlNsForm`
  - `@XmlID`, `@XmlIDREF`, `@XmlSeeAlso`, `@XmlValue`

#### JAXB Adapters:
- `javax.xml.bind.annotation.adapters.XmlAdapter` → `jakarta.xml.bind.annotation.adapters.XmlAdapter`
- `javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter` → `jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter`

### 3. Module Update Order

To minimize compilation errors, update in this order:

1. **common-artifacts-model** (base classes)
   - 3 files: `LongToStringAdapter.java`, `BaseDefinitionElementImpl.java`, `NamedDefinitionElementImpl.java`
   - Also: `AbstractParser.java` (used by multiple parsers)

2. **process-definition-model** (48 files)
   - All impl classes under `org.bonitasoft.engine.bpm.*`
   - 27 package-info.java files

3. **business-object-model** (10+ files)
   - `BusinessObjectModelConverter.java` (critical converter)
   - Model classes
   - Test helpers

4. **Remaining modules** (can be done in parallel):
   - organization-model (11 files)
   - connector-model (8 files)
   - application-model (7 files)
   - bdm-access-control-model (6 files)
   - profile-model (5 files)
   - form-mapping-model (4 files)
   - business-archive (4 files)

5. **Test files** (7 files across modules)

### 4. Build Tool Verification

**JAXB Maven Plugin:**
- Current: `org.codehaus.mojo:jaxb2-maven-plugin:2.5.0`
- Action: Verify compatibility with Jakarta, upgrade if needed
- Alternative: Consider `org.jvnet.jaxb:jaxb-maven-plugin` for Jakarta support

### 5. Post-Migration Validation

1. **Compilation:** `./mvnw clean compile`
2. **XSD Generation:** Verify XSD files are generated correctly in target directories
3. **Unit Tests:** `./mvnw test`
4. **Integration Tests:** `./mvnw verify`
5. **Code Format:** `./mvnw spotless:apply`
6. **Coverage Report:** Check coverage metrics remain unchanged

## Risks and Constraints

### Risks

1. **API Compatibility Risk: LOW**
   - Jakarta XML Binding 3.x maintains full API compatibility
   - Only namespace changes, no behavioral changes

2. **Build Tool Risk: MEDIUM**
   - jaxb2-maven-plugin may not support Jakarta out of the box
   - Mitigation: Upgrade plugin or switch to Jakarta-aware alternative

3. **Downstream Consumer Risk: HIGH**
   - Projects depending on these artifacts must also migrate
   - Breaking change for consumers still on javax
   - Mitigation: Version bump (1.3.0 → 2.0.0) to signal breaking change

4. **XSD Namespace Risk: LOW**
   - Generated XSD schemas should remain unchanged
   - XML namespaces are independent of Java package names

### Constraints

1. **No Functional Changes**
   - Pure namespace migration only
   - No API changes, no behavioral changes
   - All existing tests must pass without modification

2. **Atomic Migration**
   - Cannot partially migrate (mixing javax and jakarta will cause conflicts)
   - Must update all modules together

3. **Version Compatibility**
   - Requires Java 11+ (already required)
   - Maven 3.6+ (already met with wrapper)

4. **Backward Compatibility**
   - This is a breaking change for consumers
   - Requires semantic version bump (major or minor)

## Dependencies to Update

### Central BOM (`artifacts-model-dependencies/pom.xml`)
```xml
<!-- FROM -->
<groupId>javax.xml.bind</groupId>
<artifactId>jaxb-api</artifactId>
<version>2.3.1</version>

<!-- TO -->
<groupId>jakarta.xml.bind</groupId>
<artifactId>jakarta.xml.bind-api</artifactId>
<version>3.0.1</version>

<!-- FROM -->
<groupId>org.glassfish.jaxb</groupId>
<artifactId>jaxb-runtime</artifactId>
<version>2.3.1</version>

<!-- TO -->
<groupId>org.glassfish.jaxb</groupId>
<artifactId>jaxb-runtime</artifactId>
<version>3.0.2</version>
```

### Module POMs (11 modules)
Update `<groupId>` and `<artifactId>` only:
- `javax.xml.bind:jaxb-api` → `jakarta.xml.bind:jakarta.xml.bind-api`

Modules requiring updates:
1. common-artifacts-model
2. process-definition-model
3. business-object-model
4. connector-model
5. profile-model
6. organization-model
7. application-model
8. bdm-access-control-model
9. business-archive
10. form-mapping-model

### Keep As-Is
- `com.sun.activation:jakarta.activation:1.2.2` (already Jakarta)
- `org.glassfish.hk2:osgi-resource-locator:2.4.0` (not affected)

## Testing Strategy

### Unit Tests
- All existing unit tests must pass
- No test modifications needed (API-compatible)
- Focus on marshalling/unmarshalling tests

### Integration Tests
- Verify end-to-end serialization/deserialization
- Check XSD generation in each module

### Regression Tests
- Compare generated XSD files before/after migration
- Verify XML output format remains identical

### Key Test Files to Monitor:
1. `FormMappingModelMarshallerTest.java`
2. `BusinessObjectModelConverterTest.java`
3. `BDMAccessControlParserTest.java`
4. `ApplicationNodeContainerConverterTest.java`
5. All parser tests in organization-model and profile-model

## Timeline

This is a straightforward namespace migration with no expected functional issues.

**Estimated Effort:**
- POM updates: ~1 hour
- Java code updates: ~2 hours (with automated find-replace)
- Testing & verification: ~2 hours
- **Total: ~5 hours**

## Consequences

### Positive
- Modern Jakarta EE alignment
- Future-proof for Java ecosystem evolution
- Enables adoption of Jakarta EE 9+ features
- Maintains full API compatibility

### Negative
- Breaking change for downstream consumers
- Requires coordinated migration across ecosystem
- Version bump needed (suggest 2.0.0)

### Neutral
- No functional changes
- No performance impact
- Same XSD schemas

## References

- [Jakarta XML Binding Specification](https://jakarta.ee/specifications/xml-binding/3.0/)
- [Jakarta EE 9 Migration Guide](https://eclipse-ee4j.github.io/jakartaee-platform/jakartaee9/JakartaEE9ReleasePlan)
- [JAXB Maven Plugin Jakarta Support](https://github.com/mojohaus/jaxb2-maven-plugin)

## Next Steps

1. Get approval for migration plan
2. Create feature branch: `refactor/xml-bind/move-from-javax-to-jakarta` (already exists)
3. Update dependencies in BOM
4. Update Java imports across all modules
5. Run full test suite
6. Update CLAUDE.md to reflect Jakarta usage
7. Create PR with detailed migration notes
