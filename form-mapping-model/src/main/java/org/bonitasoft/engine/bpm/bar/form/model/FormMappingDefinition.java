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
package org.bonitasoft.engine.bpm.bar.form.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

import org.bonitasoft.engine.form.FormMappingTarget;
import org.bonitasoft.engine.form.FormMappingType;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class FormMappingDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    private String form;

    @XmlAttribute(required = true)
    private FormMappingTarget target;

    @XmlAttribute
    private FormMappingType type;

    @XmlAttribute
    private String taskname;

    /**
     * This constructor is for JAXB
     */
    protected FormMappingDefinition() {
        // required by JAXB
    }

    public FormMappingDefinition(final String form, final FormMappingType type, final FormMappingTarget target) {
        this.form = form;
        this.type = type;
        this.target = target;
    }

    public FormMappingDefinition(final String form, final FormMappingType type, final FormMappingTarget target,
            final String taskname) {
        this(form, type, target);
        setTaskname(taskname);
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public FormMappingTarget getTarget() {
        return target;
    }

    public void setTarget(FormMappingTarget target) {
        this.target = target;
    }

    public FormMappingType getType() {
        return type;
    }

    public void setType(FormMappingType type) {
        this.type = type;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        FormMappingDefinition that = (FormMappingDefinition) o;
        return Objects.equals(form, that.form) && target == that.target && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(form, target, type);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FormMappingDefinition.class.getSimpleName() + "[", "]")
                .add("page='" + form + "'")
                .add("target=" + target)
                .add("type=" + type)
                .add("taskname='" + taskname + "'")
                .toString();
    }
}
