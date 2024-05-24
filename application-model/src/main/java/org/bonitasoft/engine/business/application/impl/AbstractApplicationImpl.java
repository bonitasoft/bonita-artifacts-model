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
package org.bonitasoft.engine.business.application.impl;

import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;

import org.bonitasoft.engine.bpm.internal.BaseElementImpl;
import org.bonitasoft.engine.business.application.ApplicationVisibility;
import org.bonitasoft.engine.business.application.IApplication;

/**
 * Contains the meta information of a Bonita Living Application.
 */
public abstract class AbstractApplicationImpl extends BaseElementImpl implements IApplication {

    private static final long serialVersionUID = -1956628711562364258L;

    private final String version;
    private String iconPath;
    private Date creationDate;
    private long createdBy;
    private Date lastUpdateDate;
    private long updatedBy;
    private String state;
    private String displayName;
    private Long profileId;
    private final String description;
    private final String token;
    private boolean hasIcon;
    private boolean editable;
    private ApplicationVisibility visibility;

    protected AbstractApplicationImpl(final String token, final String version, final String description) {
        this.token = token;
        this.version = version;
        this.description = description;
    }

    @Override
    public ApplicationVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(ApplicationVisibility visibility) {
        this.visibility = visibility;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(final String iconPath) {
        this.iconPath = iconPath;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final long createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(final Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final long updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
    }

    @Override
    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(final Long profileId) {
        this.profileId = profileId;
    }

    @Override
    public boolean hasIcon() {
        return hasIcon;
    }

    public void setHasIcon(boolean hasIcon) {
        this.hasIcon = hasIcon;
    }

    @Override
    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        AbstractApplicationImpl that = (AbstractApplicationImpl) o;
        return getCreatedBy() == that.getCreatedBy() && getUpdatedBy() == that.getUpdatedBy() && hasIcon == that.hasIcon
                && Objects.equals(getVersion(), that.getVersion())
                && Objects.equals(getIconPath(), that.getIconPath())
                && Objects.equals(getCreationDate(), that.getCreationDate())
                && Objects.equals(getLastUpdateDate(), that.getLastUpdateDate())
                && Objects.equals(getState(), that.getState())
                && Objects.equals(getDisplayName(), that.getDisplayName())
                && Objects.equals(getProfileId(), that.getProfileId())
                && Objects.equals(getDescription(), that.getDescription())
                && Objects.equals(getToken(), that.getToken()) && getVisibility() == that.getVisibility();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getVersion(), getIconPath(), getCreationDate(),
                getCreatedBy(), getLastUpdateDate(), getUpdatedBy(), getState(), getDisplayName(),
                getProfileId(), getDescription(), getToken(), hasIcon, getVisibility(), isEditable());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AbstractApplicationImpl.class.getSimpleName() + "[", "]")
                .add("version='" + version + "'")
                .add("iconPath='" + iconPath + "'")
                .add("creationDate=" + creationDate)
                .add("createdBy=" + createdBy)
                .add("lastUpdateDate=" + lastUpdateDate)
                .add("updatedBy=" + updatedBy)
                .add("state='" + state + "'")
                .add("displayName='" + displayName + "'")
                .add("profileId=" + profileId)
                .add("description='" + description + "'")
                .add("token='" + token + "'")
                .add("hasIcon=" + hasIcon)
                .add("editable=" + isEditable())
                .add("visibility=" + visibility)
                .toString();
    }
}
