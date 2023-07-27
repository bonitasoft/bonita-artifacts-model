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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * @author Matthieu Chaffotte
 * @author Romain Bioteau
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Query {

    @XmlAttribute(required = true)
    private String name;

    @XmlElement
    private String description;

    @XmlAttribute(required = true)
    private String content;

    @XmlAttribute(required = true)
    private String returnType;

    @XmlElementWrapper(name = "queryParameters")
    @XmlElement(name = "queryParameter")
    private List<QueryParameter> queryParameters;

    public Query() {
        queryParameters = new ArrayList<>();
    }

    public Query(final String name, final String content, final String returnType) {
        this(name, null, content, returnType);
    }

    public Query(final String name, final String description, final String content, final String returnType) {
        this();
        this.name = name;
        this.description = description;
        this.content = content;
        this.returnType = returnType;
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

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public QueryParameter addQueryParameter(final String name, final String className) {
        QueryParameter parameter = new QueryParameter(name, className);
        queryParameters.add(parameter);
        return parameter;
    }

    public List<QueryParameter> getQueryParameters() {
        return queryParameters;
    }

    public void setQueryParameters(final List<QueryParameter> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public String getReturnType() {
        return returnType;
    }

    public boolean hasMultipleResults() {
        return List.class.getName().equals(returnType);
    }

    public boolean isCountQuery() {
        return Long.class.getName().equals(returnType);
    }

    public void setReturnType(final String returnType) {
        this.returnType = returnType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Query query = (Query) o;
        return Objects.equals(name, query.name) && Objects.equals(description, query.description)
                && Objects.equals(content, query.content) && Objects.equals(returnType, query.returnType)
                && Objects.equals(queryParameters, query.queryParameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, content, returnType, queryParameters);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Query.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("content='" + content + "'")
                .add("returnType='" + returnType + "'")
                .add("queryParameters=" + queryParameters)
                .toString();
    }
}
