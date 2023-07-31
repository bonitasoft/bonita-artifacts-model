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

import com.bonitasoft.engine.bdm.accesscontrol.model.Attribute;
import com.bonitasoft.engine.bdm.accesscontrol.model.AttributesWrapper;

public class AttributeBuilder {

    private final Attribute attribute;

    public AttributeBuilder(String name) {
        attribute = new Attribute();
        attribute.setName(name);
    }

    public static AttributeBuilder anAttribute(String name) {
        return new AttributeBuilder(name);
    }

    public AttributeBuilder withAttributesWrapper(AttributesWrapper attributesWrapper) {
        attribute.setAttributesWrapper(attributesWrapper);
        return this;
    }

    public AttributeBuilder withAttributesWrapper(AttributesWrapperBuilder attributesWrapperBuilder) {
        return withAttributesWrapper(attributesWrapperBuilder.create());
    }

    public AttributeBuilder withAttributes(String businessObjectname, Attribute... attributes) {
        AttributesWrapper attributesWrapper = new AttributesWrapperBuilder(businessObjectname)
                .withAttributes(attributes).create();
        attribute.setAttributesWrapper(attributesWrapper);
        return this;
    }

    public Attribute create() {
        return attribute;
    }

}
