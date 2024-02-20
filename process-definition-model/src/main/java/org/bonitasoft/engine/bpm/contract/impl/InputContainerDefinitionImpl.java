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
package org.bonitasoft.engine.bpm.contract.impl;

import java.util.ArrayList;
import java.util.List;

import org.bonitasoft.engine.bpm.contract.InputContainerDefinition;
import org.bonitasoft.engine.bpm.contract.InputDefinition;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.FIELD)
public class InputContainerDefinitionImpl implements InputContainerDefinition {

    private static final long serialVersionUID = 1L;
    @XmlElementWrapper(name = "inputDefinitions")
    @XmlElement(type = InputDefinitionImpl.class, name = "inputDefinition")
    private List<InputDefinition> inputs = new ArrayList<>();

    public InputContainerDefinitionImpl(List<InputDefinition> inputs) {
        this.inputs = inputs;
    }

    public void addInput(final InputDefinition input) {
        inputs.add(input);
    }
}
