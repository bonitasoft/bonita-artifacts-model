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
package com.bonitasoft.engine.bdm.accesscontrol.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.bonitasoft.engine.bdm.model.NamedElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Attribute implements NamedElement {

    @XmlAttribute(required = true)
    private String name;

    @XmlElement(name = "attributes")
    private AttributesWrapper attributesWrapper;

    // Leave this constructor as Jaxb schemagen needs it:
    public Attribute() {
    }

    public Attribute(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AttributesWrapper getAttributesWrapper() {
        return attributesWrapper;
    }

    public void setAttributesWrapper(AttributesWrapper attributesWrapper) {
        this.attributesWrapper = attributesWrapper;
    }

}
