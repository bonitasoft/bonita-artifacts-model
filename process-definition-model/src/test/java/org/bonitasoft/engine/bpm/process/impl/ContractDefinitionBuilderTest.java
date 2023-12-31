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
package org.bonitasoft.engine.bpm.process.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.bonitasoft.engine.bpm.contract.InputDefinition;
import org.bonitasoft.engine.bpm.contract.Type;
import org.bonitasoft.engine.bpm.contract.impl.ContractDefinitionImpl;
import org.bonitasoft.engine.bpm.contract.impl.InputDefinitionImpl;
import org.bonitasoft.engine.bpm.flownode.impl.internal.FlowElementContainerDefinitionImpl;
import org.bonitasoft.engine.bpm.flownode.impl.internal.UserTaskDefinitionImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ContractDefinitionBuilderTest {

    private static final String name = "name";

    private static final Type type = Type.TEXT;

    private static final String description = "description";

    private static final String actorName = "actor name";

    @Mock
    ContractDefinitionImpl contractDefinitionImpl;

    @Mock
    private ProcessDefinitionBuilder processDefinitionBuilder;

    @Mock
    private FlowElementContainerDefinitionImpl container;

    private UserTaskDefinitionImpl activity;

    private ContractDefinitionBuilder contractDefinitionBuilder;

    @BeforeEach
    void before() throws Exception {
        activity = new UserTaskDefinitionImpl(name, actorName);
        contractDefinitionBuilder = new ContractDefinitionBuilder(processDefinitionBuilder, container, activity);

    }

    @Test
    void addContractOnProcess() throws Exception {
        //when
        final ProcessDefinitionBuilder myProcess = new ProcessDefinitionBuilder().createNewInstance("myProcess", "1.0");
        myProcess.addContract();

        //then
        assertThat(myProcess.getProcess().getContract()).isNotNull();

    }

    @Test
    void addInputTest() throws Exception {
        //when
        final ContractDefinitionBuilder builder = contractDefinitionBuilder.addInput(name, type, description);

        //then
        assertThat(activity.getContract().getInputs()).as("should get 1 input").hasSize(1);
        assertThat(activity.getContract().getInputs().get(0).isMultiple()).as("should not be multiple").isFalse();
        checkBuilder(builder);

    }

    @Test
    void addMultipleInputTest() throws Exception {
        //when
        final ContractDefinitionBuilder builder = contractDefinitionBuilder.addInput(name, type, description, true);

        //then
        assertThat(activity.getContract().getInputs().get(0).isMultiple()).as("should be multiple").isTrue();
        checkBuilder(builder);

    }

    @Test
    void addMultipleSimpleInputTest() throws Exception {
        //when
        final ContractDefinitionBuilder builder = contractDefinitionBuilder.addInput(name, type, description);

        //then
        assertThat(activity.getContract().getInputs()).hasSize(1);
        checkBuilder(builder);

    }

    @Test
    void addComplexInputTest() throws Exception {
        //when
        contractDefinitionBuilder.addComplexInput(name, description);
        contractDefinitionBuilder.addInput("theInput2", Type.TEXT, "desc");

        //then
        assertThat(activity.getContract().getInputs()).hasSize(2);
        assertThat(activity.getContract().getInputs().get(0).isMultiple()).as("should not be multiple").isFalse();
        assertThat(activity.getContract().getInputs().get(1).getName()).isEqualTo("theInput2");
    }

    @Test
    void addComplexInputWithChildren() throws Exception {
        //when
        final ContractDefinitionBuilder builder = contractDefinitionBuilder.addInput("name", Type.TEXT,
                "the name of the user");
        builder.addComplexInput("addresses", "the addresses").addInput("address", Type.TEXT, "the address", true);

        //then
        assertThat(activity.getContract().getInputs()).hasSize(2);
        assertThat(activity.getContract().getInputs().get(0))
                .isEqualTo(new InputDefinitionImpl("name", Type.TEXT, "the name of the user", false));
        assertThat(activity.getContract().getInputs().get(1))
                .usingRecursiveComparison(
                        RecursiveComparisonConfiguration.builder().withIgnoredFields("inputs").build())
                .isEqualTo(new InputDefinitionImpl("addresses", "the addresses", false));
        assertThat(activity.getContract().getInputs().get(1).getInputs()).hasSize(1);
        assertThat(activity.getContract().getInputs().get(1).getInputs().get(0))
                .isEqualTo(new InputDefinitionImpl("address", Type.TEXT, "the address", true));
        checkBuilder(builder);
    }

    @Test
    void addMultipleComplexInputTest() throws Exception {
        //when
        contractDefinitionBuilder.addComplexInput(name, description, true);

        //then
        assertThat(activity.getContract().getInputs()).hasSize(1);
        assertThat(activity.getContract().getInputs().get(0).isMultiple()).as("should be multiple").isTrue();

    }

    @Test
    void addFileComplexInputTest() throws Exception {
        final ContractDefinitionBuilder builder = contractDefinitionBuilder.addFileInput("document",
                "It is a simple document");

        final List<InputDefinition> complexInputs = activity.getContract().getInputs();
        assertThat(complexInputs).hasSize(1);
        assertThat(complexInputs.get(0).getType()).isEqualTo(Type.FILE);
        assertThat(complexInputs.get(0).isMultiple()).isFalse();
        final List<InputDefinition> simpleInputs = complexInputs.get(0).getInputs();
        assertThat(simpleInputs).hasSize(2);
        assertThat(simpleInputs.get(0).getName()).isEqualTo("filename");
        assertThat(simpleInputs.get(0).getType()).isEqualTo(Type.TEXT);
        assertThat(simpleInputs.get(1).getName()).isEqualTo("content");
        assertThat(simpleInputs.get(1).getType()).isEqualTo(Type.BYTE_ARRAY);

        checkBuilder(builder);
    }

    @Test
    void addMultipleFileInputTest() throws Exception {
        final ContractDefinitionBuilder builder = contractDefinitionBuilder.addFileInput("document",
                "It is a simple document", true);

        final List<InputDefinition> complexInputs = activity.getContract().getInputs();
        assertThat(complexInputs).hasSize(1);
        assertThat(complexInputs.get(0).getType()).isEqualTo(Type.FILE);
        assertThat(complexInputs.get(0).isMultiple()).isTrue();
        final List<InputDefinition> simpleInputs = complexInputs.get(0).getInputs();
        assertThat(simpleInputs).hasSize(2);
        assertThat(simpleInputs.get(0).getName()).isEqualTo("filename");
        assertThat(simpleInputs.get(0).getType()).isEqualTo(Type.TEXT);
        assertThat(simpleInputs.get(1).getName()).isEqualTo("content");
        assertThat(simpleInputs.get(1).getType()).isEqualTo(Type.BYTE_ARRAY);

        checkBuilder(builder);
    }

    @Test
    void addRuleTest() throws Exception {
        //when
        final ContractDefinitionBuilder builder = contractDefinitionBuilder.addConstraint(name, "expression",
                "explanation", "inputName");

        //then
        assertThat(activity.getContract().getConstraints()).hasSize(1);
        checkBuilder(builder);
    }

    private void checkBuilder(final InputContainerDefinitionBuilder builder) {
        assertThat(builder).as("should return a builder").isNotNull().isEqualTo(contractDefinitionBuilder);
    }

}
