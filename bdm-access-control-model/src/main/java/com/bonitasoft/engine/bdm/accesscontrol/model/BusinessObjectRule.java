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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "businessObjectQualifiedName", "rules" })
public class BusinessObjectRule {

    @XmlID
    @XmlAttribute(required = true)
    private String businessObjectQualifiedName;

    @XmlElement(name = "rule")
    private List<AccessRule> rules;

    // Leave this constructor as Jaxb schemagen needs it:
    public BusinessObjectRule() {
        rules = new ArrayList<>();
    }

    public List<AccessRule> getRules() {
        return rules;
    }

    public void setRules(List<AccessRule> rules) throws IllegalArgumentException {
        this.rules = rules;
    }

    public String getBusinessObjectQualifiedName() {
        return businessObjectQualifiedName;
    }

    public BusinessObjectRule setBusinessObjectQualifiedName(final String businessObjectQualifiedName) {
        this.businessObjectQualifiedName = businessObjectQualifiedName;
        return this;
    }

    public BusinessObjectRule addRules(AccessRule... rules) {
        Collections.addAll(this.rules, rules);
        return this;
    }
}
