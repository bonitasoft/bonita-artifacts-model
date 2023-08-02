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
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.bonitasoft.engine.bdm.model.NamedElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "name", "usingDynamicProfiles", "description", "condition", "staticProfiles", "dynamicProfiles",
        "attributesWrapper" })
public class AccessRule implements NamedElement {

    @XmlAttribute(required = true)
    private String name;

    @XmlAttribute(required = true)
    private boolean usingDynamicProfiles;

    @XmlAttribute
    private String description;

    @XmlElement(name = "condition", required = true)
    private String condition;

    @XmlElementWrapper(name = "staticProfiles")
    @XmlElement(name = "profile")
    private List<String> staticProfiles;

    @XmlElement(name = "dynamicProfiles")
    private String dynamicProfiles;

    @XmlElement(name = "attributes", required = true)
    private AttributesWrapper attributesWrapper;

    // Leave this constructor as Jaxb schemagen needs it:
    public AccessRule() {
        staticProfiles = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public List<String> getStaticProfiles() {
        return staticProfiles;
    }

    public void setStaticProfiles(List<String> profiles) {
        this.staticProfiles = profiles;
        setUsingDynamicProfiles(false);
    }

    public AccessRule addStaticProfiles(String... profiles) {
        Collections.addAll(this.staticProfiles, profiles);
        setUsingDynamicProfiles(false);
        return this;
    }

    /**
     * If profiles are set as dynamic, returns the corresponding script
     *
     * @return a script that returns dynamic profiles (as a String), or null if none defined
     */
    public String getDynamicProfiles() {
        return dynamicProfiles;
    }

    public void setDynamicProfiles(String dynamicProfiles) {
        this.dynamicProfiles = dynamicProfiles;
        setUsingDynamicProfiles(true);
    }

    public AttributesWrapper getAttributesWrapper() {
        return attributesWrapper;
    }

    public void setAttributesWrapper(AttributesWrapper attributesWrapper) {
        this.attributesWrapper = attributesWrapper;
    }

    public void setUsingDynamicProfiles(boolean useDynamicProfiles) {
        this.usingDynamicProfiles = useDynamicProfiles;
    }

    public boolean isUsingDynamicProfiles() {
        return usingDynamicProfiles;
    }
}
