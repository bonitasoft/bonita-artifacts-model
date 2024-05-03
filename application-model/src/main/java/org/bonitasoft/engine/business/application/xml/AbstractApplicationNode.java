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
package org.bonitasoft.engine.business.application.xml;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Abstract class for application nodes, which may have different concrete implementations.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractApplicationNode {

    @XmlAttribute(required = true)
    private String token;

    @XmlAttribute(required = true)
    private String version;

    @XmlElement(required = true)
    private String displayName;

    @XmlElement
    private String description;

    @XmlAttribute
    private String profile;

    @XmlAttribute(required = true)
    private String state;

    @XmlElement
    private String iconPath;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * To be overridden by concrete implementation (calling super).
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AbstractApplicationNode that = (AbstractApplicationNode) o;
        return Objects.equals(token, that.token) && Objects.equals(version, that.version)
                && Objects.equals(displayName, that.displayName) && Objects.equals(description, that.description)
                && Objects.equals(profile, that.profile)
                && Objects.equals(state, that.state)
                && Objects.equals(iconPath, that.iconPath);
    }

    /**
     * To be overridden by concrete implementation.
     */
    @Override
    public int hashCode() {
        return Objects.hash(token, version, displayName, description, profile, state, iconPath);
    }
}
