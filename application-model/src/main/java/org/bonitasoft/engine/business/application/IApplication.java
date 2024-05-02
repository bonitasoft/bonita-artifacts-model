/**
 * Copyright (C) 2023 Bonitasoft S.A.
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
package org.bonitasoft.engine.business.application;

import java.util.Date;

import org.bonitasoft.engine.bpm.BaseElement;

/**
 * Contains the meta information of a Bonita Living Application.
 */
public interface IApplication extends BaseElement {

    /**
     * Retrieves the <code>Application</code> token
     *
     * @return the <code>Application</code> token
     */
    String getToken();

    /**
     * Retrieves the <code>Application</code> display name
     *
     * @return the <code>Application</code> display name
     */
    String getDisplayName();

    /**
     * Retrieves the <code>Application</code> version
     *
     * @return the <code>Application</code> version
     */
    String getVersion();

    /**
     * Retrieves the <code>Application</code> description
     *
     * @return the <code>Application</code> description
     */
    String getDescription();

    /**
     * Retrieves the icon path of this <code>Application</code>
     *
     * @return the icon path of this <code>Application</code>
     */
    String getIconPath();

    /**
     * Retrieves the <code>Application</code> creation date
     *
     * @return the <code>Application</code> creation date
     */
    Date getCreationDate();

    /**
     * Retrieves the identifier of the user that created this <code>Application</code>
     *
     * @return the identifier of the user that created this <code>Application</code>
     */
    long getCreatedBy();

    /**
     * Retrieves the <code>Application</code> last updated date
     *
     * @return the <code>Application</code> last updated date
     */
    Date getLastUpdateDate();

    /**
     * Retrieves the identifier of the user that updated this <code>Application</code>
     *
     * @return the identifier of the user that updated this <code>Application</code>
     */
    long getUpdatedBy();

    /**
     * Retrieves the <code>Application</code> state. The possible values are {@link ApplicationState#ACTIVATED}
     * and {@link ApplicationState#DEACTIVATED}
     *
     * @return the <code>Application</code> state
     * @see ApplicationState
     */
    String getState();

    /**
     * Retrieves the identifier of the associated {@code Profile} or null if there is no profile is associated to this
     * application.
     *
     * @return the identifier of the associated {@code Profile} or null if there is no profile is associated to this
     *         application.
     * @see "org.bonitasoft.engine.profile.Profile"
     */
    Long getProfileId();

    /**
     * Indicate the presence or absence of an {@link Icon} for the Application that can be retrieved using
     * {@code org.bonitasoft.engine.api.ApplicationAPI#getIconOfApplication(long applicationId)}
     *
     * @see Icon
     * @return true if there is an icon for this application
     * @since 7.13.0
     */
    boolean hasIcon();

    /**
     * Retrieves the visibility of the Application. Possible values are:
     * ALL - everyone can use the application
     * TECHNICAL_USER - Only the technical user can use the application
     * RESTRICTED - Access to the application is determined by the user profile
     * These value are purely indicative, changing them in the application object won't change the accessibility to the
     * the actual application
     *
     * @return the enum ApplicationVisibility of the application
     */
    ApplicationVisibility getVisibility();

    /**
     * Indicates whether the application can be modified
     *
     * @return Whether the application can be modified
     */
    default boolean isEditable() {
        // usually not, only legacy apps can be editable.
        return false;
    }
}
