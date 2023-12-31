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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.bonitasoft.engine.bpm.flownode.EventDefinition;
import org.bonitasoft.engine.bpm.flownode.EventTriggerDefinition;
import org.bonitasoft.engine.bpm.process.ModelFinderVisitor;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Elias Ricken de Medeiros
 * @author Celine Souchet
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class EventDefinitionImpl extends FlowNodeDefinitionImpl implements EventDefinition {

    private static final long serialVersionUID = 2606127153595667535L;

    @XmlTransient
    private final List<EventTriggerDefinition> eventTriggers = new ArrayList<>();

    public EventDefinitionImpl(final String name) {
        super(name);
    }

    public EventDefinitionImpl(final long id, final String name) {
        super(id, name);
    }

    @Override
    public List<EventTriggerDefinition> getEventTriggers() {
        return Collections.unmodifiableList(eventTriggers);
    }

    public void addEventTrigger(final EventTriggerDefinition eventTrigger) {
        eventTriggers.add(eventTrigger);
    }

    @Override
    public void accept(ModelFinderVisitor visitor, long modelId) {
        super.accept(visitor, modelId);
        visitor.find(this, modelId);
    }
}
