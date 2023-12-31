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
package org.bonitasoft.engine.bpm.parameter.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.bonitasoft.engine.bpm.internal.NamedDefinitionElementImpl;
import org.bonitasoft.engine.bpm.parameter.ParameterDefinition;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Matthieu Chaffotte
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
public class ParameterDefinitionImpl extends NamedDefinitionElementImpl implements ParameterDefinition {

    private static final long serialVersionUID = -3997656451808629180L;

    @XmlAttribute(required = true)
    private final String type;

    @Setter
    @XmlElement
    private String description;

    public ParameterDefinitionImpl(final String parameterName, final String type) {
        super(parameterName);
        this.type = type;
    }

    public ParameterDefinitionImpl() {
        super();
        this.type = "";
    }
}
