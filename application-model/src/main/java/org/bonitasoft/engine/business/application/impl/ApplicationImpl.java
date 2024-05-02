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

import java.util.Objects;
import java.util.StringJoiner;

import org.bonitasoft.engine.business.application.Application;

/**
 * @author Elias Ricken de Medeiros
 */
public class ApplicationImpl extends AbstractApplicationImpl implements Application {

    private static final long serialVersionUID = -5393587887795907117L;

    private Long layoutId;
    private Long homePageId;
    private Long themeId;
    private boolean editable;

    public ApplicationImpl(final String token, final String version, final String description) {
        super(token, version, description);
    }

    public ApplicationImpl(final String token, final String version, final String description, Long layoutId,
            final Long themeId) {
        this(token, version, description);
        this.layoutId = layoutId;
        this.themeId = themeId;
    }

    @Override
    public Long getHomePageId() {
        return homePageId;
    }

    public void setHomePageId(final Long homePageId) {
        this.homePageId = homePageId;
    }

    @Override
    public Long getLayoutId() {
        return layoutId;
    }

    @Override
    public Long getThemeId() {
        return themeId;
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
        ApplicationImpl that = (ApplicationImpl) o;
        return super.equals(that)
                && isEditable() == that.isEditable()
                && Objects.equals(getLayoutId(), that.getLayoutId())
                && Objects.equals(getHomePageId(), that.getHomePageId())
                && Objects.equals(getThemeId(), that.getThemeId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getLayoutId(), getHomePageId(), getThemeId(), isEditable());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ApplicationImpl.class.getSimpleName() + "[", "]")
                .add("version='" + getVersion() + "'")
                .add("layoutId=" + layoutId)
                .add("iconPath='" + getIconPath() + "'")
                .add("creationDate=" + getCreationDate())
                .add("createdBy=" + getCreatedBy())
                .add("lastUpdateDate=" + getLastUpdateDate())
                .add("updatedBy=" + getUpdatedBy())
                .add("state='" + getState() + "'")
                .add("homePageId=" + homePageId)
                .add("displayName='" + getDisplayName() + "'")
                .add("profileId=" + getProfileId())
                .add("themeId=" + themeId)
                .add("description='" + getDescription() + "'")
                .add("token='" + getToken() + "'")
                .add("hasIcon=" + hasIcon())
                .add("editable=" + editable)
                .add("visibility=" + getVisibility())
                .toString();
    }
}
