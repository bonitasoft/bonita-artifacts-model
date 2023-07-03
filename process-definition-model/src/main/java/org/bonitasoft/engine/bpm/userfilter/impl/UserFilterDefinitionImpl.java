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
package org.bonitasoft.engine.bpm.userfilter.impl;

import static org.bonitasoft.engine.expression.ExpressionBuilder.getNonNullCopy;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.bonitasoft.engine.bpm.flownode.impl.internal.NameExpressionMapAdapter;
import org.bonitasoft.engine.bpm.internal.NamedDefinitionElementImpl;
import org.bonitasoft.engine.bpm.process.ModelFinderVisitor;
import org.bonitasoft.engine.bpm.userfilter.UserFilterDefinition;
import org.bonitasoft.engine.expression.Expression;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@XmlAccessorType(XmlAccessType.FIELD)
public class UserFilterDefinitionImpl extends NamedDefinitionElementImpl implements UserFilterDefinition {

    private static final long serialVersionUID = -6045216424839658552L;
    @XmlAttribute(name = "userFilterId")
    private final String filterId;
    @XmlAttribute
    private final String version;
    @XmlJavaTypeAdapter(NameExpressionMapAdapter.class)
    @XmlElement(name = "inputs")
    private final Map<String, Expression> inputs = new HashMap<>();

    public UserFilterDefinitionImpl(final String name, final String filterId, final String version) {
        super(name);
        this.filterId = filterId;
        this.version = version;
    }

    public UserFilterDefinitionImpl() {
        super();
        this.filterId = "default Id";
        this.version = "default version";
    }

    @Override
    public String getUserFilterId() {
        return filterId;
    }

    @Override
    public Map<String, Expression> getInputs() {
        return inputs;
    }

    public void addInput(final String name, final Expression expression) {
        inputs.put(name, getNonNullCopy(expression));
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public void accept(ModelFinderVisitor visitor, long modelId) {
        visitor.find(this, modelId);
    }

}
