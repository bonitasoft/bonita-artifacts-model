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
package org.bonitasoft.engine.bpm.userfilter.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class UserFilterDefinitionImplTest {

    @Test
    void shouldBeEquals() throws Exception {
        var definition1 = new UserFilterDefinitionImpl("Select manager", "manager", "1.0.0");
        var definition2 = new UserFilterDefinitionImpl("Select manager", "manager", "1.0.0");
        definition2.setId(definition1.getId());

        assertThat(definition1).isEqualTo(definition2);
    }

    @Test
    void shouldNotBeEquals() throws Exception {
        var definition = new UserFilterDefinitionImpl("Select manager", "manager", "1.0.0");

        assertThat(definition)
                .isNotEqualTo(new UserFilterDefinitionImpl("Select manager", "manager", "1.0.0"))
                .isNotEqualTo(new UserFilterDefinitionImpl("Select managers", "manager", "1.0.0"))
                .isNotEqualTo(new UserFilterDefinitionImpl("Select manager", "managers", "1.0.0"))
                .isNotEqualTo(new UserFilterDefinitionImpl("Select manager", "manager", "1.0.1"))
                .isNotEqualTo(null);
    }

    @Test
    void shouldHaveSameHashCode() throws Exception {
        var definition1 = new UserFilterDefinitionImpl("Select manager", "manager", "1.0.0");
        var definition2 = new UserFilterDefinitionImpl("Select manager", "manager", "1.0.0");
        definition2.setId(definition1.getId());

        assertThat(definition1).hasSameHashCodeAs(definition2);
    }

    @Test
    void shouldNotHaveSameHashCode() throws Exception {
        var definition = new UserFilterDefinitionImpl("Select manager", "manager", "1.0.0");

        assertThat(definition)
                .doesNotHaveSameHashCodeAs(new UserFilterDefinitionImpl("Select manager", "manager", "1.0.0"))
                .doesNotHaveSameHashCodeAs(new UserFilterDefinitionImpl("Select managers", "manager", "1.0.0"))
                .doesNotHaveSameHashCodeAs(new UserFilterDefinitionImpl("Select manager", "managers", "1.0.0"))
                .doesNotHaveSameHashCodeAs(new UserFilterDefinitionImpl("Select manager", "manager", "1.0.1"));
    }

}
