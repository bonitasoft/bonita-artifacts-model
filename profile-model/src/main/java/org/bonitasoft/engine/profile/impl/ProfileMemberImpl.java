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
package org.bonitasoft.engine.profile.impl;

import java.util.Objects;

import org.bonitasoft.engine.bpm.internal.BaseElementImpl;
import org.bonitasoft.engine.profile.ProfileMember;

/**
 * @author Celine Souchet
 */
public class ProfileMemberImpl extends BaseElementImpl implements ProfileMember {

    private static final long serialVersionUID = -6991070623635291374L;

    private long profileId;

    private long userId = -1;

    private long groupId = -1;

    private long roleId = -1;

    private String displayNamePart1;

    private String displayNamePart2;

    private String displayNamePart3;

    public ProfileMemberImpl() {
        super();
    }

    public ProfileMemberImpl(final long profileId) {
        super();
        this.profileId = profileId;
    }

    @Override
    public long getProfileId() {
        return profileId;
    }

    public void setProfileId(final long profileId) {
        this.profileId = profileId;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    public void setUserId(final long userId) {
        this.userId = userId;
    }

    @Override
    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(final long groupId) {
        this.groupId = groupId;
    }

    @Override
    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(final long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String getDisplayNamePart1() {
        return displayNamePart1;
    }

    public void setDisplayNamePart1(final String displayNamePart1) {
        this.displayNamePart1 = displayNamePart1;
    }

    @Override
    public String getDisplayNamePart2() {
        return displayNamePart2;
    }

    public void setDisplayNamePart2(final String displayNamePart2) {
        this.displayNamePart2 = displayNamePart2;
    }

    @Override
    public String getDisplayNamePart3() {
        return displayNamePart3;
    }

    public void setDisplayNamePart3(final String displayNamePart3) {
        this.displayNamePart3 = displayNamePart3;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), profileId, userId, groupId, roleId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        ProfileMemberImpl that = (ProfileMemberImpl) o;
        return profileId == that.profileId && userId == that.userId && groupId == that.groupId && roleId == that.roleId;
    }

}
