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
package com.bonitasoft.engine.bdm.accesscontrol.model;

import static org.bonitasoft.engine.bdm.model.BusinessObjectModel.INFO_PROPERTIES_RESOURCE;
import static org.bonitasoft.engine.bdm.model.BusinessObjectModel.VERSION_PROPERTY_KEY;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.glassfish.hk2.osgiresourcelocator.ResourceFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Adrien Lachambre, Emmanuel Duchastenier
 */
@XmlRootElement(name = "accessControlModel")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "modelVersion", "productVersion", "businessObjectRules" })
public class BusinessObjectAccessControlModel {

    public static final String CURRENT_MODEL_VERSION = "1.0";

    public static final String CURRENT_PRODUCT_VERSION;

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessObjectAccessControlModel.class);

    static {
        final Properties info = new Properties();
        try {
            var infoProperties = Optional.ofNullable(ResourceFinder.findEntry(INFO_PROPERTIES_RESOURCE))
                    .orElseGet(() -> BusinessObjectAccessControlModel.class.getResource(INFO_PROPERTIES_RESOURCE));
            if (infoProperties != null) {
                info.load(infoProperties.openStream());
            }
        } catch (final IOException e) {
            LOGGER.error("Failed to retrieve product version", e);
        }
        final String version = info.getProperty(VERSION_PROPERTY_KEY);
        CURRENT_PRODUCT_VERSION = version == null || version.isBlank() ? null : version;
    }

    @XmlAttribute(name = "modelVersion")
    private String modelVersion;

    @XmlAttribute(name = "productVersion")
    private String productVersion;

    @XmlElement(name = "businessObjectRule")
    private List<BusinessObjectRule> businessObjectRules;

    public BusinessObjectAccessControlModel() {
        businessObjectRules = new ArrayList<>();
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

    public List<BusinessObjectRule> getBusinessObjectRules() {
        return businessObjectRules;
    }

    public void setBusinessObjectRules(final List<BusinessObjectRule> businessObjectRules) {
        this.businessObjectRules = businessObjectRules;
    }

    public BusinessObjectAccessControlModel addBusinessObjectRules(final BusinessObjectRule... businessObjectRules) {
        Collections.addAll(this.businessObjectRules, businessObjectRules);
        return this;
    }

}
