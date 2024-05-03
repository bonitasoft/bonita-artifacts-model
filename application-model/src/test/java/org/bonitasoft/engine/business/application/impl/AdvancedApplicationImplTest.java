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

import static org.bonitasoft.engine.business.application.impl.AdvancedApplicationImplAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Date;

import org.bonitasoft.engine.business.application.ApplicationState;
import org.bonitasoft.engine.business.application.ApplicationVisibility;
import org.junit.jupiter.api.Test;

class AdvancedApplicationImplTest {

    @Test
    void setters_and_getters_are_ok() {
        //given
        String token = "hr";
        String version = "1.0";
        String description = "hr description";
        long updatedBy = 100L;
        long profileId = 20L;
        String iconPath = "icon.jpg";
        long createdBy = 91L;
        Date creationDate = new Date();
        String displayName = "Human resources";
        Date lastUpdateDate = new Date(creationDate.getTime() + 1000);
        String state = ApplicationState.ACTIVATED.name();

        //when
        AdvancedApplicationImpl application = new AdvancedApplicationImpl(token, version, description);
        application.setUpdatedBy(updatedBy);
        application.setProfileId(profileId);
        application.setHasIcon(true);
        application.setIconPath(iconPath);
        application.setCreatedBy(createdBy);
        application.setCreationDate(creationDate);
        application.setDisplayName(displayName);
        application.setLastUpdateDate(lastUpdateDate);
        application.setState(state);
        application.setVisibility(ApplicationVisibility.RESTRICTED);

        //then
        assertThat(application).hasToken(token).hasVersion(version).hasDescription(description)
                .hasUpdatedBy(updatedBy).hasProfileId(profileId).hasIconPath(iconPath).hasCreatedBy(createdBy)
                .hasCreationDate(creationDate)
                .hasDisplayName(displayName).hasLastUpdateDate(lastUpdateDate)
                .hasState(state).hasVisibility(ApplicationVisibility.RESTRICTED)
                .isNotEditable();
    }

    @Test
    void equals_should_return_true_on_different_applications_with_same_content() {
        //given
        String token = "hr";
        String version = "1.0";
        String description = "hr description";

        //when
        AdvancedApplicationImpl application1 = new AdvancedApplicationImpl(token, version, description);
        AdvancedApplicationImpl application2 = new AdvancedApplicationImpl(token, version, description);

        // then
        assertThat(application1).isEqualTo(application2);
        assertEquals(application1.toString(), application2.toString());
    }

    @Test
    void equals_should_return_false_on_legacy_applications_with_same_content() {
        //given
        String token = "hr";
        String version = "1.0";
        String description = "hr description";

        //when
        AdvancedApplicationImpl application1 = new AdvancedApplicationImpl(token, version, description);
        ApplicationImpl application2 = new ApplicationImpl(token, version, description);

        // then
        assertThat(application1).isNotEqualTo(application2);
        assertNotEquals(application1.toString(), application2.toString());
    }

    @Test
    void equals_should_return_false_on_different_applications_with_different_content() {
        //given
        String token = "hr";
        String version = "1.0";
        String description = "hr description";

        //when
        AdvancedApplicationImpl application1 = new AdvancedApplicationImpl(token, version, description);
        application1.setIconPath("/img.png");
        AdvancedApplicationImpl application2 = new AdvancedApplicationImpl(token, version, description);
        application1.setIconPath("/img.jpg");

        // then
        assertThat(application1).isNotEqualTo(application2);
        assertNotEquals(application1.toString(), application2.toString());
    }
}
