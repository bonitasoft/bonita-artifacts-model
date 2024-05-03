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

import static org.bonitasoft.engine.business.application.xml.AdvancedApplicationNodeAssert.assertThat;

import org.junit.jupiter.api.Test;

class AdvancedApplicationNodeTest {

    @Test
    void simple_setters_and_getters_are_working() {
        //given
        final String displayName = "My application";
        final String description = "This is my main application";
        final String iconPath = "/icon.jpg";
        final String profile = "User";
        final String token = "myapp";
        final String version = "1.0";
        final String state = "ACTIVATED";

        //when
        final AdvancedApplicationNode app = new AdvancedApplicationNode();
        app.setDisplayName(displayName);
        app.setDescription(description);
        app.setIconPath(iconPath);
        app.setProfile(profile);
        app.setState(state);
        app.setToken(token);
        app.setVersion(version);

        //then
        assertThat(app).hasDisplayName(displayName).hasDescription(description)
                .hasIconPath(iconPath)
                .hasProfile(profile).hasState(state).hasToken(token).hasVersion(version);
    }

    @Test
    void equals_should_return_true_on_different_applications_with_same_content() {
        AdvancedApplicationNode applicationNode1 = ApplicationNodeBuilder
                .newAdvancedApplication("appToken", "appName", "1.0")
                .create();
        AdvancedApplicationNode applicationNode2 = ApplicationNodeBuilder
                .newAdvancedApplication("appToken", "appName", "1.0")
                .create();
        assertThat(applicationNode1).isEqualTo(applicationNode2);
    }

    @Test
    void equals_should_return_false_on_legacy_applications_with_same_content() {
        AdvancedApplicationNode applicationNode1 = ApplicationNodeBuilder
                .newAdvancedApplication("appToken", "appName", "1.0")
                .create();
        ApplicationNode applicationNode2 = ApplicationNodeBuilder.newApplication("appToken", "appName", "1.0")
                .create();
        assertThat(applicationNode1).isNotEqualTo(applicationNode2);
    }

    @Test
    void equals_should_return_false_on_different_applications_with_different_content() {
        AdvancedApplicationNode applicationNode1 = ApplicationNodeBuilder
                .newAdvancedApplication("appToken", "appName", "1.0")
                .withIconPath("/img1.png")
                .create();
        AdvancedApplicationNode applicationNode2 = ApplicationNodeBuilder
                .newAdvancedApplication("appToken", "appName", "1.0")
                .withIconPath("/img1.gif")
                .create();
        assertThat(applicationNode1).isNotEqualTo(applicationNode2);
    }

}
