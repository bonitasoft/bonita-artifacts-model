/**
 * Copyright (C) 2023 Bonitasoft S.A.
 * Bonitasoft, 32 rue Gustave Eiffel - 38000 Grenoble
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation
 * version 2.1 of the License.
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth
 * Floor, Boston, MA 02110-1301, USA.
 **/
package com.bonitasoft.engine.bdm.accesscontrol;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.bind.UnmarshalException;

import org.junit.jupiter.api.Test;

import com.bonitasoft.engine.bdm.accesscontrol.builder.AccessRuleBuilder;
import com.bonitasoft.engine.bdm.accesscontrol.builder.AttributeBuilder;
import com.bonitasoft.engine.bdm.accesscontrol.builder.AttributesWrapperBuilder;
import com.bonitasoft.engine.bdm.accesscontrol.builder.BusinessObjectRuleBuilder;
import com.bonitasoft.engine.bdm.accesscontrol.model.Attribute;
import com.bonitasoft.engine.bdm.accesscontrol.model.AttributesWrapper;
import com.bonitasoft.engine.bdm.accesscontrol.model.BusinessObjectAccessControlModel;
import com.bonitasoft.engine.bdm.accesscontrol.model.BusinessObjectRule;

class BDMAccessControlParserTest {

    private final BDMAccessControlParser parser = new BDMAccessControlParser();

    @Test
    void should_parse_model_to_xml() throws Exception {
        BusinessObjectAccessControlModel model = new BusinessObjectAccessControlModel()
                .addBusinessObjectRules(
                        new BusinessObjectRuleBuilder("MyBusinessObject1")
                                .withRules(new AccessRuleBuilder("myRule1", "return true")
                                        .withStaticProfiles("User", "Admin")
                                        .withAttributes("MyBusinessObject1", new Attribute("Attr11"),
                                                new Attribute("Attr12")),
                                        new AccessRuleBuilder("myRule2", "return true")
                                                .withDynamicProfiles("return \"User\"")
                                                .withAttributeWrapper(new AttributesWrapperBuilder("MyBusinessObject1")
                                                        .withAttributes(
                                                                new Attribute("Attr21"),
                                                                new AttributeBuilder("Attr22")
                                                                        .withAttributes("ComposedBO",
                                                                                new Attribute("Attr3"))
                                                                        .create())
                                                        .create()))
                                .create(),
                        new BusinessObjectRuleBuilder("MyBusinessObject2")
                                .withRules(new AccessRuleBuilder("myRule1", "return true")
                                        .withStaticProfiles("User", "Admin")
                                        .withAttributes("MyBusinessObject2", new Attribute("Attr11"),
                                                new Attribute("Attr12")),
                                        new AccessRuleBuilder("myRule2", "return true")
                                                .withDynamicProfiles("return \"User\"")
                                                .withAttributeWrapper(new AttributesWrapperBuilder("MyBusinessObject2")
                                                        .withAttributes(
                                                                new Attribute("Attr21"),
                                                                new AttributeBuilder("Attr22")
                                                                        .withAttributes("ComposedBO",
                                                                                new Attribute("Attr3"))
                                                                        .create())
                                                        .create()))
                                .create());
        String xml = parser.convert(model);
        String xmlToMatch = loadXml("/bdmAccessControlTest.xml");
        assertThat(xml).isEqualToNormalizingNewlines(xmlToMatch); // ensure this pass on windows as the jaxb formatting always put \n as end of line character
    }

    @Test
    void should_parse_xml_to_model() throws Exception {
        String xmlSrc = loadXml("/bdmAccessControlTest.xml");
        BusinessObjectAccessControlModel model = parser.convert(xmlSrc);
        assertThat(model.getBusinessObjectRules()).hasSize(2);
        assertThat(model.getBusinessObjectRules()).extracting("businessObjectQualifiedName").containsExactly(
                "MyBusinessObject1",
                "MyBusinessObject2");

        model.getBusinessObjectRules().forEach(this::validateBusinessObjectRule);
    }

    @Test
    void parse_invalid_xml_should_generate_exception() throws Exception {
        assertThatExceptionOfType(UnmarshalException.class)
                .isThrownBy(() -> parser.convert(loadXml("/bdmAccessControl_invalid.xml")));
    }

    // =================================================================================================================
    // UTILS
    // =================================================================================================================

    private String loadXml(String filename) throws IOException {
        try (var inputStream = getClass().getResourceAsStream(filename)) {
            assertThat(inputStream).isNotNull();
            return new String(inputStream.readAllBytes());
        }
    }

    private void validateBusinessObjectRule(BusinessObjectRule businessObjectRule) {
        assertThat(businessObjectRule.getRules()).hasSize(2);
        assertThat(businessObjectRule.getRules()).extracting("name").containsExactly("myRule1", "myRule2");
        assertThat(businessObjectRule.getRules()).extracting("usingDynamicProfiles").containsExactly(false, true);
        assertThat(businessObjectRule.getRules()).extracting("condition").containsExactly("return true", "return true");

        assertThat(businessObjectRule.getRules()).extracting("staticProfiles").containsExactly(asList("User", "Admin"),
                Collections.emptyList());
        List<Attribute> attributes = businessObjectRule.getRules().stream()
                .flatMap(rule -> rule.getAttributesWrapper().getAttributes().stream())
                .collect(Collectors.toList());
        assertThat(attributes).extracting("name").containsExactly("Attr11", "Attr12", "Attr21", "Attr22");
        AttributesWrapper composedObjectAttributesWrapper = attributes.stream()
                .map(Attribute::getAttributesWrapper)
                .filter(Objects::nonNull)
                .findFirst().orElseThrow();
        assertThat(composedObjectAttributesWrapper.getBusinessObjectQualifiedName()).isEqualTo("ComposedBO");
        assertThat(composedObjectAttributesWrapper.getAttributes()).extracting("name").containsExactly("Attr3");
        assertThat(businessObjectRule.getRules()).extracting("dynamicProfiles").containsExactly(null,
                "return \"User\"");
    }

}
