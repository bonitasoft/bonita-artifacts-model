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
package org.bonitasoft.engine.bdm.model;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author Matthieu Chaffotte
 * @author Romain Bioteau
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class QueryParameter {

    @XmlAttribute(required = true)
    private String name;

    @XmlElement
    private String description;

    @XmlAttribute(required = true)
    private String className;

    public QueryParameter() {

    }

    public QueryParameter(final String name, final String className) {
        this.name = name;
        this.className = className;
    }

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

    public String getClassName() {
        return className;
    }

    public void setClassName(final String className) {
        this.className = className;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        QueryParameter that = (QueryParameter) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description)
                && Objects.equals(className, that.className);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, className);
    }

    @Override
    public String toString() {
        return QueryParameter.class.getSimpleName() + ": " + name + " (" + className + ")";
    }

}
