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
package org.bonitasoft.engine.bpm.contract.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ConstraintDefinitionImplTest {

    @Test
    void should_add_input_name() throws Exception {
        //given
        final ConstraintDefinitionImpl constraintDefinition = new ConstraintDefinitionImpl("name", "expression",
                "explanation");

        //when
        constraintDefinition.addInputName("inputName");

        //then
        assertThat(constraintDefinition.getInputNames()).isNotNull().hasSize(1).containsExactly("inputName");

    }

    @Test
    void should_be_equals() throws Exception {
        //given
        var constraintDefinition1 = new ConstraintDefinitionImpl("name", "expression",
                "explanation");
        var constraintDefinition2 = new ConstraintDefinitionImpl("name", "expression",
                "explanation");

        //when
        constraintDefinition1.addInputName("inputName");
        constraintDefinition2.addInputName("inputName");

        //then
        assertThat(constraintDefinition1).isEqualTo(constraintDefinition2);
    }

    @Test
    void should_not_be_equals() throws Exception {
        //given
        var constraintDefinition1 = new ConstraintDefinitionImpl("name", "expression",
                "explanation");
        var constraintDefinition2 = new ConstraintDefinitionImpl("name2", "expression",
                "explanation");

        //then
        assertThat(constraintDefinition1).isNotEqualTo(constraintDefinition2)
                .isNotEqualTo(null);
    }

    @Test
    void shouldHaveSameHashCode() throws Exception {
        //given
        var constraintDefinition1 = new ConstraintDefinitionImpl("name", "expression",
                "explanation");
        var constraintDefinition2 = new ConstraintDefinitionImpl("name", "expression",
                "explanation");

        //then
        assertThat(constraintDefinition1).hasSameHashCodeAs(constraintDefinition2);
    }

    @Test
    void shouldNotHaveSameHashCode() throws Exception {
        //given
        var constraintDefinition1 = new ConstraintDefinitionImpl("name", "expression",
                "explanation");
        constraintDefinition1.addInputName("inputName");
        var constraintDefinition2 = new ConstraintDefinitionImpl();

        //then
        assertThat(constraintDefinition1).doesNotHaveSameHashCodeAs(constraintDefinition2)
                .doesNotHaveSameHashCodeAs(new ConstraintDefinitionImpl());

    }

}
