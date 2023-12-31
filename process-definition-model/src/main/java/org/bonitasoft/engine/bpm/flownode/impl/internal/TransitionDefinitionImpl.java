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

import static org.bonitasoft.engine.expression.ExpressionBuilder.getNonNullCopy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;

import org.bonitasoft.engine.bpm.flownode.FlowNodeDefinition;
import org.bonitasoft.engine.bpm.flownode.TransitionDefinition;
import org.bonitasoft.engine.bpm.internal.NamedDefinitionElementImpl;
import org.bonitasoft.engine.bpm.process.ModelFinderVisitor;
import org.bonitasoft.engine.expression.Expression;
import org.bonitasoft.engine.expression.impl.ExpressionImpl;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Baptiste Mesta
 * @author Matthieu Chaffotte
 * @author Celine Souchet
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
public class TransitionDefinitionImpl extends NamedDefinitionElementImpl implements TransitionDefinition {

    private static final long serialVersionUID = -5629473055955264480L;
    public static final int DEFAULT_SOURCE_TARGET_FLOWNODE = 0;

    @XmlIDREF
    @XmlAttribute
    private FlowNodeDefinitionImpl source;

    @XmlIDREF
    @XmlAttribute
    private FlowNodeDefinitionImpl target;

    @XmlElement(type = ExpressionImpl.class, name = "condition")
    private Expression expression;

    public TransitionDefinitionImpl(final String name, final FlowNodeDefinitionImpl source,
            final FlowNodeDefinitionImpl target) {
        super(name);
        this.source = source;
        this.target = target;
    }

    public TransitionDefinitionImpl(final String name) {
        this(name, null, null);
    }

    @Override
    public long getSource() {
        return source != null ? source.getId() : DEFAULT_SOURCE_TARGET_FLOWNODE;
    }

    @Override
    @JsonIgnore
    public FlowNodeDefinition getSourceFlowNode() {
        return source;
    }

    @Override
    public long getTarget() {
        return target != null ? target.getId() : DEFAULT_SOURCE_TARGET_FLOWNODE;
    }

    @Override
    @JsonIgnore
    public FlowNodeDefinition getTargetFlowNode() {
        return target;
    }

    @Override
    public Expression getCondition() {
        return expression;
    }

    public void setCondition(final Expression expression) {
        this.expression = getNonNullCopy(expression);
    }

    @Override
    public void accept(ModelFinderVisitor visitor, long modelId) {
        visitor.find(this, modelId);
    }

}
