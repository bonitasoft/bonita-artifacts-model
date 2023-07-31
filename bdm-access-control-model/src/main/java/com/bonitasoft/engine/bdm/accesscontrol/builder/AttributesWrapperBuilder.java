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

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;

import com.bonitasoft.engine.bdm.accesscontrol.model.Attribute;
import com.bonitasoft.engine.bdm.accesscontrol.model.AttributesWrapper;

public class AttributesWrapperBuilder {

    private final AttributesWrapper accessRuleAttributesWrapper;

    public AttributesWrapperBuilder(String businessObjectname) {
        accessRuleAttributesWrapper = new AttributesWrapper();
        accessRuleAttributesWrapper.setBusinessObjectQualifiedName(businessObjectname);
    }

    public static AttributesWrapperBuilder anAttributeWrapper(String name) {
        return new AttributesWrapperBuilder(name);
    }

    public AttributesWrapperBuilder withAttributes(Attribute... accessRuleAttributes) {
        accessRuleAttributesWrapper.setAttributes(asList(accessRuleAttributes));
        return this;
    }

    public AttributesWrapperBuilder withAttributes(AttributeBuilder... attributes) {
        Attribute[] attr = stream(attributes).map(AttributeBuilder::create).toArray(Attribute[]::new);
        return withAttributes(attr);
    }

    public AttributesWrapperBuilder withAttributes(String... attributeNames) {
        Attribute[] attr = stream(attributeNames).map(Attribute::new).toArray(Attribute[]::new);
        return withAttributes(attr);
    }

    public AttributesWrapper create() {
        return accessRuleAttributesWrapper;
    }

}
