/**
 * Copyright (C) 2019 Bonitasoft S.A.
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
package com.bonitasoft.engine.bdm.accesscontrol.builder;

import static java.util.Arrays.stream;

import java.util.Arrays;

import com.bonitasoft.engine.bdm.accesscontrol.model.AccessRule;
import com.bonitasoft.engine.bdm.accesscontrol.model.Attribute;
import com.bonitasoft.engine.bdm.accesscontrol.model.AttributesWrapper;

/**
 * @author Emmanuel Duchastenier
 */
public class AccessRuleBuilder {

    private final AccessRule accessRule;

    public AccessRuleBuilder(String name) {
        this(name, "");
    }

    public AccessRuleBuilder(String name, String condition) {
        accessRule = new AccessRule();
        accessRule.setName(name);
        accessRule.setCondition(condition);
    }

    public static AccessRuleBuilder anAccessRule(String name) {
        return new AccessRuleBuilder(name);
    }

    public AccessRuleBuilder withDescription(String description) {
        accessRule.setDescription(description);
        return this;
    }

    public AccessRuleBuilder withStaticProfiles(String... profiles) {
        accessRule.setStaticProfiles(Arrays.asList(profiles));
        return this;
    }

    public AccessRuleBuilder withDynamicProfiles(String dynamicProfilesScript) {
        accessRule.setDynamicProfiles(dynamicProfilesScript);
        return this;
    }

    public AccessRuleBuilder withAttributeWrapper(AttributesWrapper attributesWrapper) {
        accessRule.setAttributesWrapper(attributesWrapper);
        return this;
    }

    public AccessRuleBuilder forBo(String businessObjectname) {
        return withAttributes(businessObjectname, new Attribute[0]);
    }

    public AccessRuleBuilder withAttributes(String businessObjectname, Attribute... attributes) {
        AttributesWrapper attributesWrapper = new AttributesWrapperBuilder(businessObjectname)
                .withAttributes(attributes).create();
        accessRule.setAttributesWrapper(attributesWrapper);
        return this;
    }

    public AccessRuleBuilder withAttributes(String businessObjectname, AttributeBuilder... attributes) {
        Attribute[] attr = stream(attributes).map(AttributeBuilder::create).toArray(Attribute[]::new);
        return withAttributes(businessObjectname, attr);
    }

    public AccessRule create() {
        return accessRule;
    }

}
