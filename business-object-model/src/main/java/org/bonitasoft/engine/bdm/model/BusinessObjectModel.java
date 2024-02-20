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
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * @author Matthieu Chaffotte
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BusinessObjectModel {

    public static final String CURRENT_MODEL_VERSION = "1.0";

    @XmlElementWrapper(name = "businessObjects", required = true)
    @XmlElement(name = "businessObject", required = true)
    private List<BusinessObject> businessObjects;

    @XmlAttribute(name = "modelVersion", required = false)
    private String modelVersion;

    @XmlAttribute(name = "productVersion", required = false)
    private String productVersion;

    public BusinessObjectModel() {
        businessObjects = new ArrayList<>();
    }

    public void setModelVersion(final String modelVersion) {
        this.modelVersion = modelVersion;
    }

    public String getModelVersion() {
        return modelVersion;
    }

    public void setProductVersion(final String productVersion) {
        this.productVersion = productVersion;
    }

    public String getProductVersion() {
        return productVersion;
    }

    public List<BusinessObject> getBusinessObjects() {
        return businessObjects;
    }

    public void setBusinessObjects(final List<BusinessObject> businessObjects) {
        this.businessObjects = businessObjects;
    }

    public void addBusinessObject(final BusinessObject businessObject) {
        businessObjects.add(businessObject);
    }

    public Set<String> getBusinessObjectsClassNames() {
        final HashSet<String> set = new HashSet<>();
        for (final BusinessObject o : businessObjects) {
            set.add(o.getQualifiedName());
        }
        return set;
    }

    public List<BusinessObject> getReferencedBusinessObjectsByComposition() {
        final List<BusinessObject> refs = new ArrayList<>();
        for (final BusinessObject bo : businessObjects) {
            refs.addAll(bo.getReferencedBusinessObjectsByComposition());
        }
        return refs;
    }

    public List<UniqueConstraint> getUniqueConstraints() {
        final List<UniqueConstraint> constraints = new ArrayList<>();
        for (final BusinessObject bo : businessObjects) {
            constraints.addAll(bo.getUniqueConstraints());
        }
        return constraints;
    }

    public List<Index> getIndexes() {
        final List<Index> indexes = new ArrayList<>();
        for (final BusinessObject bo : businessObjects) {
            indexes.addAll(bo.getIndexes());
        }
        return indexes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BusinessObjectModel that = (BusinessObjectModel) o;
        return Objects.equals(businessObjects, that.businessObjects) && Objects.equals(modelVersion, that.modelVersion)
                && Objects.equals(productVersion, that.productVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessObjects, modelVersion, productVersion);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BusinessObjectModel.class.getSimpleName() + "[", "]")
                .add("businessObjects=" + businessObjects)
                .toString();
    }
}
