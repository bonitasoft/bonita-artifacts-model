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
package org.bonitasoft.engine.bpm.flownode.impl.internal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import org.bonitasoft.engine.bpm.flownode.ErrorEventTriggerDefinition;
import org.bonitasoft.engine.bpm.process.ModelFinderVisitor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Elias Ricken de Medeiros
 */
@Getter
@EqualsAndHashCode
@ToString
@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class ErrorEventTriggerDefinitionImpl implements ErrorEventTriggerDefinition {

    private static final long serialVersionUID = -8002085238119587513L;

    @XmlAttribute
    private final String errorCode;

    public ErrorEventTriggerDefinitionImpl(final String errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorEventTriggerDefinitionImpl() {
        this.errorCode = null;
    }

    @Override
    public void accept(ModelFinderVisitor visitor, long modelId) {
        // nothing to do here:
    }
}
