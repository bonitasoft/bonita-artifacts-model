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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FormMappingModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElementWrapper(name = "form-mappings", required = true)
    @XmlElement(name = "form-mapping", required = false)
    private List<FormMappingDefinition> formMappings = new ArrayList<>();

    public FormMappingModel() {
        // required by JAXB
    }

    public List<FormMappingDefinition> getFormMappings() {
        return formMappings;
    }

    public void setFormMappings(List<FormMappingDefinition> formMappings) {
        this.formMappings = formMappings;
    }

    public void addFormMapping(final FormMappingDefinition mapping) {
        formMappings.add(mapping);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        FormMappingModel that = (FormMappingModel) o;
        return Objects.equals(formMappings, that.formMappings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formMappings);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FormMappingModel.class.getSimpleName() + "[", "]")
                .add("formMappings=" + formMappings)
                .toString();
    }
}
