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
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.bonitasoft.engine.bpm.connector.ConnectorEvent;
import org.bonitasoft.engine.bpm.contract.Type;
import org.bonitasoft.engine.bpm.flownode.TimerType;
import org.bonitasoft.engine.bpm.process.InvalidProcessDefinitionException;
import org.bonitasoft.engine.expression.Expression;
import org.bonitasoft.engine.expression.ExpressionBuilder;
import org.bonitasoft.engine.expression.InvalidExpressionException;
import org.bonitasoft.engine.operation.LeftOperandBuilder;
import org.bonitasoft.engine.operation.OperationBuilder;
import org.bonitasoft.engine.operation.OperatorType;
import org.junit.jupiter.api.Test;

class ProcessDefinitionBuilderTest {

    @Test
    void boundaryEventMustHaveOutgoingTransition() throws Exception {
        final Expression timerExpr = new ExpressionBuilder().createConstantLongExpression(100);
        final ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder()
                .createNewInstance("invalid", "1.0");
        processDefinitionBuilder.addStartEvent("start");
        processDefinitionBuilder.addUserTask("step1", "actor").addBoundaryEvent("b1", true)
                .addTimerEventTriggerDefinition(TimerType.DURATION, timerExpr);
        processDefinitionBuilder.addEndEvent("end");
        processDefinitionBuilder.addTransition("start", "step1");
        processDefinitionBuilder.addTransition("step1", "end");

        assertThrows(InvalidProcessDefinitionException.class, () -> processDefinitionBuilder.done());
    }

    @Test
    void boundaryEventsAreAlsoValidatedInsideSubProcess() throws Exception {
        final Expression timerExpr = new ExpressionBuilder().createConstantLongExpression(100);
        final ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder()
                .createNewInstance("invalid", "1.0");
        processDefinitionBuilder.addStartEvent("start");
        processDefinitionBuilder.addAutomaticTask("auto1");
        processDefinitionBuilder.addEndEvent("end");
        processDefinitionBuilder.addTransition("start", "auto1");
        processDefinitionBuilder.addTransition("auto1", "end");
        final SubProcessDefinitionBuilder subProcessBuilder = processDefinitionBuilder.addSubProcess("subProc", true)
                .getSubProcessBuilder();
        subProcessBuilder.addStartEvent("startSub").addSignalEventTrigger("go");
        subProcessBuilder.addUserTask("step1", "actor").addBoundaryEvent("b1")
                .addTimerEventTriggerDefinition(TimerType.DURATION, timerExpr);
        subProcessBuilder.addEndEvent("endSub");
        subProcessBuilder.addTransition("startSub", "step1");
        subProcessBuilder.addTransition("step1", "endSub");

        assertThrows(InvalidProcessDefinitionException.class, () -> processDefinitionBuilder.done());
    }

    @Test
    void boundaryEventMustHaveOutgoingTransitionWithoutCondition() throws Exception {
        final Expression timerExpr = new ExpressionBuilder().createConstantLongExpression(100);
        final Expression trueExpr = new ExpressionBuilder().createConstantBooleanExpression(true);
        final ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder()
                .createNewInstance("invalid", "1.0");
        final String taskWithBoundary = "step1";
        processDefinitionBuilder.addStartEvent("start");
        processDefinitionBuilder.addUserTask(taskWithBoundary, "actor").addBoundaryEvent("b1", true)
                .addTimerEventTriggerDefinition(TimerType.DURATION, timerExpr);
        processDefinitionBuilder.addAutomaticTask("auto2");
        processDefinitionBuilder.addEndEvent("end");
        processDefinitionBuilder.addTransition("start", taskWithBoundary);
        processDefinitionBuilder.addTransition(taskWithBoundary, "end");
        processDefinitionBuilder.addTransition("b1", "auto2", trueExpr);

        assertThrows(InvalidProcessDefinitionException.class, () -> processDefinitionBuilder.done());
    }

    @Test
    void boundaryEventMustHaveATrigger() throws Exception {
        final String taskWithBoundary = "step1";
        final ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder()
                .createNewInstance("invalid", "1.0");
        processDefinitionBuilder.addStartEvent("start");
        processDefinitionBuilder.addUserTask(taskWithBoundary, "actor").addBoundaryEvent("b1", true);
        processDefinitionBuilder.addAutomaticTask("auto2");
        processDefinitionBuilder.addEndEvent("end");
        processDefinitionBuilder.addTransition("start", taskWithBoundary);
        processDefinitionBuilder.addTransition(taskWithBoundary, "end");
        processDefinitionBuilder.addTransition("b1", "auto2");

        assertThrows(InvalidProcessDefinitionException.class, () -> processDefinitionBuilder.done());
    }

    @Test
    void timerBoundaryEventCannotHasTypeCycle() throws Exception {
        final Expression timerValue = new ExpressionBuilder().createConstantStringExpression("0/5 * * * * ?");
        final String taskWithBoundary = "step1";
        final ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder()
                .createNewInstance("invalid", "1.0");
        processDefinitionBuilder.addStartEvent("start");
        processDefinitionBuilder.addUserTask(taskWithBoundary, "actor").addBoundaryEvent("b1")
                .addTimerEventTriggerDefinition(TimerType.CYCLE, timerValue);
        processDefinitionBuilder.addAutomaticTask("auto2");
        processDefinitionBuilder.addEndEvent("end");
        processDefinitionBuilder.addTransition("start", taskWithBoundary);
        processDefinitionBuilder.addTransition(taskWithBoundary, "end");
        processDefinitionBuilder.addTransition("b1", "auto2");

        assertThrows(InvalidProcessDefinitionException.class, () -> processDefinitionBuilder.done());
    }

    // this test must be deleted after implementation of non-interrupting signal event
    @Test
    void nonInterruptingSignalBoundaryEventIsNotSupported() throws Exception {
        final String taskWithBoundary = "step1";
        final ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder()
                .createNewInstance("invalid", "1.0");
        processDefinitionBuilder.addStartEvent("start");
        processDefinitionBuilder.addUserTask(taskWithBoundary, "actor").addBoundaryEvent("b1", false)
                .addSignalEventTrigger("go");
        processDefinitionBuilder.addAutomaticTask("auto2");
        processDefinitionBuilder.addEndEvent("end");
        processDefinitionBuilder.addTransition("start", taskWithBoundary);
        processDefinitionBuilder.addTransition(taskWithBoundary, "end");
        processDefinitionBuilder.addTransition("b1", "auto2");
        assertThrows(InvalidProcessDefinitionException.class, () -> processDefinitionBuilder.done());
    }

    // this test must be deleted after implementation of non-interrupting message event
    @Test
    void nonInterruptingMessageBoundaryEventIsNotSupported() throws Exception {
        final String taskWithBoundary = "step1";
        final ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder()
                .createNewInstance("invalid", "1.0");
        processDefinitionBuilder.addStartEvent("start");
        processDefinitionBuilder.addUserTask(taskWithBoundary, "actor").addBoundaryEvent("b1", false)
                .addMessageEventTrigger("m1");
        processDefinitionBuilder.addAutomaticTask("auto2");
        processDefinitionBuilder.addEndEvent("end");
        processDefinitionBuilder.addTransition("start", taskWithBoundary);
        processDefinitionBuilder.addTransition(taskWithBoundary, "end");
        processDefinitionBuilder.addTransition("b1", "auto2");
        assertThrows(InvalidProcessDefinitionException.class, () -> processDefinitionBuilder.done());
    }

    // an error event is always interrupting
    @Test
    void nonInterruptingErrorBoundaryEventIsNotSupported() throws Exception {
        final String taskWithBoundary = "step1";
        final ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder()
                .createNewInstance("invalid", "1.0");
        processDefinitionBuilder.addStartEvent("start");
        processDefinitionBuilder.addUserTask(taskWithBoundary, "actor").addBoundaryEvent("b1", false)
                .addErrorEventTrigger("e1");
        processDefinitionBuilder.addAutomaticTask("auto2");
        processDefinitionBuilder.addEndEvent("end");
        processDefinitionBuilder.addTransition("start", taskWithBoundary);
        processDefinitionBuilder.addTransition(taskWithBoundary, "end");
        processDefinitionBuilder.addTransition("b1", "auto2");

        assertThrows(InvalidProcessDefinitionException.class, () -> processDefinitionBuilder.done());
    }

    @Test
    void timerBoundaryEventsOnAutomaticTasksAreNotSupported() throws Exception {
        final String taskWithBoundary = "step1";
        final ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder()
                .createNewInstance("invalid", "1.0");
        processDefinitionBuilder.addStartEvent("start");
        processDefinitionBuilder.addAutomaticTask(taskWithBoundary).addBoundaryEvent("b1")
                .addTimerEventTriggerDefinition(TimerType.DURATION,
                        new ExpressionBuilder().createConstantLongExpression(1000));
        processDefinitionBuilder.addAutomaticTask("auto2");
        processDefinitionBuilder.addEndEvent("end");
        processDefinitionBuilder.addTransition("start", taskWithBoundary);
        processDefinitionBuilder.addTransition(taskWithBoundary, "end");
        processDefinitionBuilder.addTransition("b1", "auto2");

        assertThrows(InvalidProcessDefinitionException.class, () -> processDefinitionBuilder.done());
    }

    @Test
    void signalBoundaryEventsOnAutomaticTasksAreNotSupported() throws Exception {
        final String taskWithBoundary = "step1";
        final ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder()
                .createNewInstance("invalid", "1.0");
        processDefinitionBuilder.addStartEvent("start");
        processDefinitionBuilder.addAutomaticTask(taskWithBoundary).addBoundaryEvent("b1").addSignalEventTrigger("go");
        processDefinitionBuilder.addAutomaticTask("auto2");
        processDefinitionBuilder.addEndEvent("end");
        processDefinitionBuilder.addTransition("start", taskWithBoundary);
        processDefinitionBuilder.addTransition(taskWithBoundary, "end");
        processDefinitionBuilder.addTransition("b1", "auto2");

        assertThrows(InvalidProcessDefinitionException.class, () -> processDefinitionBuilder.done());
    }

    @Test
    void messageBoundaryEventsOnAutomaticTasksAreNotSupported() throws Exception {
        final String taskWithBoundary = "step1";
        final ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder()
                .createNewInstance("invalid", "1.0");
        processDefinitionBuilder.addStartEvent("start");
        processDefinitionBuilder.addAutomaticTask(taskWithBoundary).addBoundaryEvent("b1").addMessageEventTrigger("m1");
        processDefinitionBuilder.addAutomaticTask("auto2");
        processDefinitionBuilder.addEndEvent("end");
        processDefinitionBuilder.addTransition("start", taskWithBoundary);
        processDefinitionBuilder.addTransition(taskWithBoundary, "end");
        processDefinitionBuilder.addTransition("b1", "auto2");

        assertThrows(InvalidProcessDefinitionException.class, () -> processDefinitionBuilder.done());
    }

    @Test
    void timerBoundaryEventsOnReceiveTasksAreNotSupported() throws Exception {
        final String taskWithBoundary = "step1";
        final ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder()
                .createNewInstance("invalid", "1.0");
        processDefinitionBuilder.addStartEvent("start");
        processDefinitionBuilder.addReceiveTask(taskWithBoundary, "m1").addBoundaryEvent("b1")
                .addTimerEventTriggerDefinition(TimerType.DURATION,
                        new ExpressionBuilder().createConstantLongExpression(1000));
        processDefinitionBuilder.addAutomaticTask("auto2");
        processDefinitionBuilder.addEndEvent("end");
        processDefinitionBuilder.addTransition("start", taskWithBoundary);
        processDefinitionBuilder.addTransition(taskWithBoundary, "end");
        processDefinitionBuilder.addTransition("b1", "auto2");

        assertThrows(InvalidProcessDefinitionException.class, () -> processDefinitionBuilder.done());
    }

    @Test
    void signalBoundaryEventsOnReceiveTasksAreNotSupported() throws Exception {
        final String taskWithBoundary = "step1";
        final ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder()
                .createNewInstance("invalid", "1.0");
        processDefinitionBuilder.addStartEvent("start");
        processDefinitionBuilder.addReceiveTask(taskWithBoundary, "m1").addBoundaryEvent("b1")
                .addSignalEventTrigger("go");
        processDefinitionBuilder.addAutomaticTask("auto2");
        processDefinitionBuilder.addEndEvent("end");
        processDefinitionBuilder.addTransition("start", taskWithBoundary);
        processDefinitionBuilder.addTransition(taskWithBoundary, "end");
        processDefinitionBuilder.addTransition("b1", "auto2");

        assertThrows(InvalidProcessDefinitionException.class, () -> processDefinitionBuilder.done());
    }

    @Test
    void messageBoundaryEventsOnReceiveTasksAreNotSupported() throws Exception {
        final String taskWithBoundary = "step1";
        final ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder()
                .createNewInstance("invalid", "1.0");
        processDefinitionBuilder.addStartEvent("start");
        processDefinitionBuilder.addReceiveTask(taskWithBoundary, "m1").addBoundaryEvent("b1")
                .addMessageEventTrigger("m1");
        processDefinitionBuilder.addAutomaticTask("auto2");
        processDefinitionBuilder.addEndEvent("end");
        processDefinitionBuilder.addTransition("start", taskWithBoundary);
        processDefinitionBuilder.addTransition(taskWithBoundary, "end");
        processDefinitionBuilder.addTransition("b1", "auto2");

        assertThrows(InvalidProcessDefinitionException.class, () -> processDefinitionBuilder.done());
    }

    @Test
    void timerBoundaryEventsOnSendTasksAreNotSupported() throws Exception {
        final String taskWithBoundary = "step1";
        final ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder()
                .createNewInstance("invalid", "1.0");
        processDefinitionBuilder.addStartEvent("start");
        processDefinitionBuilder
                .addSendTask(taskWithBoundary, "m1", new ExpressionBuilder().createConstantStringExpression("p1"))
                .addBoundaryEvent("b1")
                .addTimerEventTriggerDefinition(TimerType.DURATION,
                        new ExpressionBuilder().createConstantLongExpression(1000));
        processDefinitionBuilder.addAutomaticTask("auto2");
        processDefinitionBuilder.addEndEvent("end");
        processDefinitionBuilder.addTransition("start", taskWithBoundary);
        processDefinitionBuilder.addTransition(taskWithBoundary, "end");
        processDefinitionBuilder.addTransition("b1", "auto2");

        assertThrows(InvalidProcessDefinitionException.class, () -> processDefinitionBuilder.done());
    }

    @Test
    void signalBoundaryEventsOnSendTasksAreNotSupported() throws Exception {
        final String taskWithBoundary = "step1";
        final ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder()
                .createNewInstance("invalid", "1.0");
        processDefinitionBuilder.addStartEvent("start");
        processDefinitionBuilder
                .addSendTask(taskWithBoundary, "m1", new ExpressionBuilder().createConstantStringExpression("p1"))
                .addBoundaryEvent("b1")
                .addSignalEventTrigger("go");
        processDefinitionBuilder.addAutomaticTask("auto2");
        processDefinitionBuilder.addEndEvent("end");
        processDefinitionBuilder.addTransition("start", taskWithBoundary);
        processDefinitionBuilder.addTransition(taskWithBoundary, "end");
        processDefinitionBuilder.addTransition("b1", "auto2");

        assertThrows(InvalidProcessDefinitionException.class, () -> processDefinitionBuilder.done());
    }

    @Test
    void messageBoundaryEventsOnSendTasksAreNotSupported() throws Exception {
        final String taskWithBoundary = "step1";
        final ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder()
                .createNewInstance("invalid", "1.0");
        processDefinitionBuilder.addStartEvent("start");
        processDefinitionBuilder
                .addSendTask(taskWithBoundary, "m1", new ExpressionBuilder().createConstantStringExpression("p1"))
                .addBoundaryEvent("b1")
                .addMessageEventTrigger("m1");
        processDefinitionBuilder.addAutomaticTask("auto2");
        processDefinitionBuilder.addEndEvent("end");
        processDefinitionBuilder.addTransition("start", taskWithBoundary);
        processDefinitionBuilder.addTransition(taskWithBoundary, "end");
        processDefinitionBuilder.addTransition("b1", "auto2");

        assertThrows(InvalidProcessDefinitionException.class, () -> processDefinitionBuilder.done());
    }

    // EventSubProcessDefinitionTest
    @Test
    void eventSubProcessMusntHaveIncommingTransitions() throws Exception {
        final ProcessDefinitionBuilder builder = new ProcessDefinitionBuilder()
                .createNewInstance("ProcessWithEventSubProcess", "1.0");
        builder.addActor("mainActor");
        builder.addUserTask("step1", "mainActor");
        final SubProcessDefinitionBuilder subProcessBuilder = builder.addSubProcess("eventSubProcess", true)
                .getSubProcessBuilder();
        subProcessBuilder.addStartEvent("timerStart").addTimerEventTriggerDefinition(TimerType.DURATION,
                new ExpressionBuilder().createConstantLongExpression(1000));
        builder.addTransition("step1", "eventSubProcess");
        subProcessBuilder.addUserTask("subStep", "mainActor");
        subProcessBuilder.addEndEvent("endSubProcess");
        subProcessBuilder.addTransition("timerStart", "subStep");
        subProcessBuilder.addTransition("subStep", "endSubProcess");

        assertThrows(InvalidProcessDefinitionException.class, () -> builder.done());
    }

    @Test
    void eventSubProcessMusntHaveOutgoingTransitions() throws Exception {
        final ProcessDefinitionBuilder builder = new ProcessDefinitionBuilder()
                .createNewInstance("ProcessWithEventSubProcess", "1.0");
        builder.addActor("mainActor");
        builder.addUserTask("step1", "mainActor");
        builder.addUserTask("step2", "mainActor");
        final SubProcessDefinitionBuilder subProcessBuilder = builder.addSubProcess("eventSubProcess", true)
                .getSubProcessBuilder();
        subProcessBuilder.addStartEvent("timerStart").addTimerEventTriggerDefinition(TimerType.DURATION,
                new ExpressionBuilder().createConstantLongExpression(1000));
        builder.addTransition("eventSubProcess", "step2");
        subProcessBuilder.addUserTask("subStep", "mainActor");
        subProcessBuilder.addEndEvent("endSubProcess");
        subProcessBuilder.addTransition("timerStart", "subStep");
        subProcessBuilder.addTransition("subStep", "endSubProcess");

        assertThrows(InvalidProcessDefinitionException.class, () -> builder.done());
    }

    @Test
    void eventSubProcessMustHaveTrigger() throws Exception {
        final ProcessDefinitionBuilder builder = new ProcessDefinitionBuilder()
                .createNewInstance("ProcessWithEventSubProcess", "1.0");
        builder.addActor("mainActor");
        builder.addUserTask("step1", "mainActor");
        final SubProcessDefinitionBuilder subProcessBuilder = builder.addSubProcess("eventSubProcess", true)
                .getSubProcessBuilder();
        subProcessBuilder.addStartEvent("start");
        subProcessBuilder.addUserTask("subStep", "mainActor");
        subProcessBuilder.addEndEvent("endSubProcess");
        subProcessBuilder.addTransition("start", "subStep");
        subProcessBuilder.addTransition("subStep", "endSubProcess");

        assertThrows(InvalidProcessDefinitionException.class, () -> builder.done());
    }

    @Test
    void eventSubProcessCannotHaveMoreThanOneStartEvent() throws Exception {
        final ProcessDefinitionBuilder builder = new ProcessDefinitionBuilder()
                .createNewInstance("ProcessWithEventSubProcess", "1.0");
        builder.addActor("mainActor");
        builder.addUserTask("step1", "mainActor");
        final SubProcessDefinitionBuilder subProcessBuilder = builder.addSubProcess("eventSubProcess", true)
                .getSubProcessBuilder();
        subProcessBuilder.addStartEvent("timerStart1").addTimerEventTriggerDefinition(TimerType.DURATION,
                new ExpressionBuilder().createConstantLongExpression(1000));
        subProcessBuilder.addStartEvent("timerStart2").addTimerEventTriggerDefinition(TimerType.DURATION,
                new ExpressionBuilder().createConstantLongExpression(2000));
        subProcessBuilder.addUserTask("subStep", "mainActor");
        subProcessBuilder.addEndEvent("endSubProcess");
        subProcessBuilder.addTransition("timerStart1", "subStep");
        subProcessBuilder.addTransition("timerStart2", "subStep");
        subProcessBuilder.addTransition("subStep", "endSubProcess");

        assertThrows(InvalidProcessDefinitionException.class, () -> builder.done());
    }

    @Test
    void eventSubProcessCannotHaveTwoFlowNodesWithTheSameName() throws Exception {
        final ProcessDefinitionBuilder builder = new ProcessDefinitionBuilder()
                .createNewInstance("ProcessWithEventSubProcess", "1.0");
        builder.addActor("mainActor");
        builder.addUserTask("step1", "mainActor");
        final SubProcessDefinitionBuilder subProcessBuilder = builder.addSubProcess("eventSubProcess", true)
                .getSubProcessBuilder();
        subProcessBuilder.addStartEvent("timerStart1").addTimerEventTriggerDefinition(TimerType.DURATION,
                new ExpressionBuilder().createConstantLongExpression(1000));
        subProcessBuilder.addUserTask("step1", "mainActor");
        subProcessBuilder.addEndEvent("endSubProcess");
        subProcessBuilder.addTransition("timerStart1", "step1");
        subProcessBuilder.addTransition("step1", "endSubProcess");

        assertThrows(InvalidProcessDefinitionException.class, () -> builder.done());
    }

    // MessageEventsDefinitionTest
    @Test
    void cannotHaveCorrelationInStartMessageOfRootProcess() throws Exception {
        final ProcessDefinitionBuilder builder = new ProcessDefinitionBuilder().createNewInstance("proc", "1.0");
        final CatchMessageEventTriggerDefinitionBuilder messageEventTrigger = builder.addStartEvent("startMessage")
                .addMessageEventTrigger("m1");
        messageEventTrigger.addCorrelation(new ExpressionBuilder().createConstantStringExpression("key"),
                new ExpressionBuilder().createConstantStringExpression("v1"));
        builder.addAutomaticTask("auto1");
        builder.addEndEvent("end");
        builder.addTransition("startMessage", "auto1");
        builder.addTransition("auto1", "end");

        assertThrows(InvalidProcessDefinitionException.class, () -> builder.done());
    }

    @Test
    void canStartOnIntermediateCatchEventMessage() throws Exception {
        final ProcessDefinitionBuilder processBuilder = new ProcessDefinitionBuilder();
        processBuilder.createNewInstance("processName", "1.0");
        processBuilder.addIntermediateCatchEvent("name").addMessageEventTrigger("messageName");
        assertThat(processBuilder.done()).isNotNull();
    }

    @Test
    void aUserTaskineventSubProcessCannotAssignAndDeleteTheSameBO() throws Exception {
        final Expression expression = new ExpressionBuilder().createConstantDoubleExpression(45d);
        final ProcessDefinitionBuilder builder = new ProcessDefinitionBuilder()
                .createNewInstance("ProcessWithEventSubProcess", "1.0");
        builder.addActor("mainActor");
        builder.addUserTask("step1", "mainActor")
                .addOperation(new LeftOperandBuilder().createBusinessDataLeftOperand("myAddress"),
                        OperatorType.ASSIGNMENT, null, null, expression)
                .addOperation(new LeftOperandBuilder().createBusinessDataLeftOperand("myAddress"),
                        OperatorType.DELETION, null, null, null);

        assertThrows(InvalidProcessDefinitionException.class, () -> builder.done());
    }

    @Test
    void processCanDeleteAndInstantiateDifferentBOsInSameTask() throws Exception {
        final Expression expression = new ExpressionBuilder().createConstantDoubleExpression(45d);

        final ProcessDefinitionBuilder builder = new ProcessDefinitionBuilder().createNewInstance("Process", "1.0");
        builder.addActor("mainActor");
        builder.addUserTask("step1", "mainActor")
                .addOperation(new LeftOperandBuilder().createBusinessDataLeftOperand("myAddress"),
                        OperatorType.DELETION, null, null, null)
                .addOperation(new LeftOperandBuilder().createBusinessDataLeftOperand("myAddress1"),
                        OperatorType.ASSIGNMENT, null, null, expression)
                .addOperation(new LeftOperandBuilder().createBusinessDataLeftOperand("myAddress1"),
                        OperatorType.JAVA_METHOD, null, null, expression);
        assertThat(builder.done()).isNotNull();
    }

    @Test
    void processCannotHaveTwoFlowNodesWithTheSameName() throws Exception {
        final Expression expression = new ExpressionBuilder().createConstantDoubleExpression(45d);

        final ProcessDefinitionBuilder builder = new ProcessDefinitionBuilder()
                .createNewInstance("ProcessWithEventSubProcess", "1.0");
        builder.addActor("mainActor");
        builder.addUserTask("step1", "mainActor")
                .addOperation(new LeftOperandBuilder().createBusinessDataLeftOperand("myAddress1"),
                        OperatorType.ASSIGNMENT, null, null, expression)
                .addOperation(new LeftOperandBuilder().createBusinessDataLeftOperand("myAddress"),
                        OperatorType.JAVA_METHOD, null, null, expression);
        assertThat(builder.done()).isNotNull();
    }

    @Test
    void cant_create_process_definition_with_actor_initiator_without_actor() throws Exception {
        final ProcessDefinitionBuilder builder = new ProcessDefinitionBuilder()
                .createNewInstance("Double-hyphen -- test", "any");
        builder.setActorInitiator("toto");

        assertThrows(InvalidProcessDefinitionException.class, () -> builder.done());
    }

    @Test
    void userTask_with_valid_contract() throws Exception {
        final ProcessDefinitionBuilder builder = new ProcessDefinitionBuilder()
                .createNewInstance("ProcessWithBadContract", "1.0");
        builder.addActor("mainActor");

        //given
        builder.addUserTask("step1", "mainActor")
                .addContract()
                .addInput("innput", Type.TEXT, "should fail")
                .addConstraint("firstConstraint", "input != null", "mandatory", "input");

        //when
        assertThat(builder.done()).isNotNull();
    }

    @SuppressWarnings("deprecation")
    @Test
    void userTask_with_invalid_contract_should_throw_exception() throws Exception {
        final ProcessDefinitionBuilder builder = new ProcessDefinitionBuilder()
                .createNewInstance("ProcessWithBadContract", "1.0");
        builder.addActor("mainActor");

        //given
        builder.addUserTask("step1", "mainActor").addContract()
                .addComplexInput("expenseLine", "expense report line")
                .addInput("date", Type.DATE, "expense date")
                .addInput("amount", Type.DECIMAL, "expense amount")
                .addComplexInput("date", "expense date")
                .addInput("expenseType", Type.TEXT, "describe expense type")
                .addInput("bad input", Type.TEXT, "should fail");

        //when
        assertThrows(InvalidProcessDefinitionException.class, () -> builder.done());
    }

    @Test
    void userTask_with_null_constraint_name_of_contract_should_throw_exception() throws Exception {
        final ProcessDefinitionBuilder builder = new ProcessDefinitionBuilder()
                .createNewInstance("ProcessWithBadContract", "1.0");
        builder.addActor("mainActor");
        builder.addUserTask("step1", "mainActor").addContract()
                .addInput("input", Type.TEXT, "").addConstraint(null, "input != null", "mandatory", "input");

        assertThrows(InvalidProcessDefinitionException.class, () -> builder.done());
    }

    @Test
    void userTask_with_null_constraint_condition_of_contract_should_throw_exception() throws Exception {
        final ProcessDefinitionBuilder builder = new ProcessDefinitionBuilder()
                .createNewInstance("ProcessWithBadContract", "1.0");
        builder.addActor("mainActor");
        builder.addUserTask("step1", "mainActor").addContract()
                .addInput("input", Type.TEXT, "").addConstraint("firstConstraint", null, "mandatory", "input");

        assertThrows(InvalidProcessDefinitionException.class, () -> builder.done());
    }

    @Test
    void validate_contract_with_no_type() throws Exception {
        final ProcessDefinitionBuilder builder = new ProcessDefinitionBuilder().createNewInstance("test", "1");

        builder.addContract().addComplexInput("name", "desc");

        assertThrows(InvalidProcessDefinitionException.class, () -> builder.done(),
                "Type not set on the contract input <name> on the process-level contract");
    }

    @Test
    void validate_contract_with_no_type_on_user_task() throws Exception {
        final ProcessDefinitionBuilder builder = new ProcessDefinitionBuilder().createNewInstance("test", "1");

        builder.addActor("john").addUserTask("step1", "john").addContract().addComplexInput("name", "desc");

        assertThrows(InvalidProcessDefinitionException.class, () -> builder.done(),
                "Type not set on the contract input <name> on the task-level contract for task <step1>");
    }

    @Test
    void validate_contract_with_no_type_sub_children() throws Exception {
        final ProcessDefinitionBuilder builder = new ProcessDefinitionBuilder().createNewInstance("test", "1");

        builder.addContract().addComplexInput("name", "desc").addComplexInput("name", "desc");

        assertThrows(InvalidProcessDefinitionException.class, () -> builder.done(),
                "Type not set on the contract input <name> on the process-level contract");
    }

    @Test
    void validate_should_fail_when_business_data_for_loop_data_input_of_multi_instance_does_not_exists()
            throws Exception {
        //given
        final ProcessDefinitionBuilder builder = new ProcessDefinitionBuilder().createNewInstance("test", "1");
        AutomaticTaskDefinitionBuilder step = builder.addAutomaticTask("multi");
        //when
        step.addMultiInstance(false, "myData");
        //then
        assertThrows(InvalidProcessDefinitionException.class, () -> builder.done(),
                "The activity multi contains a reference myData for the loop data input to an unknown data");
    }

    @Test
    void validate_should_not_fail_when_business_data_for_loop_data_input_of_multi_instance_exists()
            throws Exception {
        //given
        final ProcessDefinitionBuilder builder = new ProcessDefinitionBuilder().createNewInstance("test", "1");
        AutomaticTaskDefinitionBuilder step = builder.addAutomaticTask("multi");
        builder.addBusinessData("myData", String.class.getName(), null).setMultiple(true);
        //when
        step.addMultiInstance(false, "myData");
        //then
        assertThat(builder.done()).isNotNull();
    }

    @Test
    void should_not_be_able_to_set_as_loop_data_input_non_multiple_business_data() throws Exception {
        //given
        final ProcessDefinitionBuilder builder = new ProcessDefinitionBuilder().createNewInstance("test", "1");
        AutomaticTaskDefinitionBuilder step = builder.addAutomaticTask("multi");
        builder.addBusinessData("myData", String.class.getName(), null).setMultiple(false);
        //when
        step.addMultiInstance(false, "myData");
        //then
        assertThrows(InvalidProcessDefinitionException.class, () -> builder.done(),
                "The business data myData used in the multi instance multi must be multiple");
    }

    @Test
    void validate_should_fail_when_business_data_for_data_input_item_of_multi_instance_does_not_exists()
            throws Exception {
        //given
        final ProcessDefinitionBuilder builder = new ProcessDefinitionBuilder().createNewInstance("test", "1");
        AutomaticTaskDefinitionBuilder step = builder.addAutomaticTask("multi");
        builder.addBusinessData("myData", String.class.getName(), null).setMultiple(true);
        //when
        step.addMultiInstance(false, "myData").addDataInputItemRef("myDataInput");
        //then
        assertThrows(InvalidProcessDefinitionException.class, () -> builder.done(),
                "The activity multi contains a reference myDataInput for the data input item to an unknown data");
    }

    @Test
    void validate_should_not_fail_when_business_data_for_data_input_item_of_multi_instance_exists()
            throws Exception {
        //given
        final ProcessDefinitionBuilder builder = new ProcessDefinitionBuilder().createNewInstance("test", "1");
        AutomaticTaskDefinitionBuilder step = builder.addAutomaticTask("multi");
        builder.addBusinessData("myData", String.class.getName(), null).setMultiple(true);
        step.addBusinessData("myDataInput", String.class.getName());
        //when
        step.addMultiInstance(false, "myData").addDataInputItemRef("myDataInput");
        //then
        assertThat(builder.done()).isNotNull();
    }

    @Test
    void validate_should_fail_when_business_data_for_data_output_item_of_multi_instance_does_not_exists()
            throws Exception {
        //given
        final ProcessDefinitionBuilder builder = new ProcessDefinitionBuilder().createNewInstance("test", "1");
        AutomaticTaskDefinitionBuilder step = builder.addAutomaticTask("multi");
        builder.addBusinessData("myData", String.class.getName(), null).setMultiple(true);
        //when
        step.addMultiInstance(false, "myData").addDataOutputItemRef("myDataOutput");
        //then
        assertThrows(InvalidProcessDefinitionException.class, () -> builder.done(),
                "The activity multi contains a reference myDataOutput for the data output item to an unknown data");
    }

    @Test
    void should_not_be_able_to_add_business_data_on_non_multi_instance_step() throws Exception {
        //given
        final ProcessDefinitionBuilder builder = new ProcessDefinitionBuilder().createNewInstance("test", "1");
        AutomaticTaskDefinitionBuilder step = builder.addAutomaticTask("multi");
        //when
        step.addBusinessData("myData", String.class.getName());
        //then
        assertThrows(InvalidProcessDefinitionException.class, () -> builder.done(),
                "The activity multi contains business data but this activity does not have the multiple instance behaviour");
    }

    @Test
    void validate_should_not_fail_when_business_data_for_data_output_item_of_multi_instance_exists()
            throws Exception {
        //given
        final ProcessDefinitionBuilder builder = new ProcessDefinitionBuilder().createNewInstance("test", "1");
        AutomaticTaskDefinitionBuilder step = builder.addAutomaticTask("multi");
        builder.addBusinessData("myData", String.class.getName(), null).setMultiple(true);
        step.addBusinessData("myDataOutput", String.class.getName());
        //when
        step.addMultiInstance(false, "myData").addDataInputItemRef("myDataOutput");
        //then
        assertThat(builder.done()).isNotNull();
    }

    @Test
    void validate_should_fail_when_business_data_for_loop_data_output_of_multi_instance_does_not_exists()
            throws Exception {
        //given
        final ProcessDefinitionBuilder builder = new ProcessDefinitionBuilder().createNewInstance("test", "1");
        AutomaticTaskDefinitionBuilder step = builder.addAutomaticTask("multi");
        //when
        MultiInstanceLoopCharacteristicsBuilder multi = step.addMultiInstance(false,
                new ExpressionBuilder().createConstantIntegerExpression(12));
        multi.addLoopDataOutputRef("myData");
        //then
        assertThrows(InvalidProcessDefinitionException.class, () -> builder.done(),
                "The activity multi contains a reference myData for the loop data output to an unknown data");
    }

    @Test
    void validate_should_not_fail_when_business_data_for_loop_data_output_of_multi_instance_exists()
            throws Exception {
        //given
        final ProcessDefinitionBuilder builder = new ProcessDefinitionBuilder().createNewInstance("test", "1");
        AutomaticTaskDefinitionBuilder step = builder.addAutomaticTask("multi");
        builder.addBusinessData("myData", String.class.getName(), null).setMultiple(true);
        //when
        MultiInstanceLoopCharacteristicsBuilder multi = step.addMultiInstance(false,
                new ExpressionBuilder().createConstantIntegerExpression(12));
        multi.addLoopDataOutputRef("myData");
        //then
        assertThat(builder.done()).isNotNull();
    }

    @Test
    void should_not_be_able_to_add_2_business_data_with_same_name() throws Exception {
        //given
        ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder().createNewInstance("p1",
                "1.0");

        //when
        processDefinitionBuilder.addBusinessData("toto", String.class.getName(), null);
        processDefinitionBuilder.addBusinessData("toto", String.class.getName(), null);
        //then
        assertThrows(InvalidProcessDefinitionException.class, () -> processDefinitionBuilder.done(),
                "The process contains more than one business data with the name toto");
    }

    @Test
    void should_not_be_able_to_add_2_business_data_on_multi_instance_with_same_name() throws Exception {
        //given
        ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder().createNewInstance("p1",
                "1.0");
        processDefinitionBuilder.addBusinessData("listData", String.class.getName(), null).setMultiple(true);
        //when
        AutomaticTaskDefinitionBuilder step = processDefinitionBuilder.addAutomaticTask("step");
        step.addBusinessData("toto", String.class.getName());
        step.addBusinessData("toto", String.class.getName());
        step.addMultiInstance(false, "listData").addDataInputItemRef("toto");
        //then
        assertThrows(InvalidProcessDefinitionException.class, () -> processDefinitionBuilder.done(),
                "The activity step contains more than one business data with the name toto");
    }

    @Test
    void should_fail_when_subprocess_does_not_have_element() throws Exception {
        ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder().createNewInstance("p1",
                "1.0");
        processDefinitionBuilder.addAutomaticTask("auto1");
        processDefinitionBuilder.addSubProcess("sub", true);

        assertThrows(InvalidProcessDefinitionException.class, () -> processDefinitionBuilder.done(),
                "The subprocess sub does not have any element, add at least one element using the builder that " +
                        "can be retrieved on the subprocess activity");
    }

    @Test
    void validateOperations_should_detect_delete_and_instantiate_in_same_task()
            throws InvalidProcessDefinitionException, InvalidExpressionException {

        //given
        ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder().createNewInstance("p1",
                "1.0");
        AutomaticTaskDefinitionBuilder autoTask1 = processDefinitionBuilder.addAutomaticTask("auto1");
        autoTask1.addOperation(new OperationBuilder().deleteBusinessDataOperation("myBusinessData"));
        autoTask1.addOperation(new OperationBuilder().attachBusinessDataSetAttributeOperation("myBusinessData",
                new ExpressionBuilder().createGroovyScriptExpression("assignNewValue",
                        "toto",
                        "com.company.model.Employee")));
        //then
        //when
        assertThrows(InvalidProcessDefinitionException.class, () -> processDefinitionBuilder.done(),
                "The business variable myBusinessData on the current element has been deleted by an operation. " +
                        "Other operations performed on this instance through the same element: auto1 are not allowed");
    }

    @Test
    void validateOperations_should_not_fail_when_delete_and_instantiate_in_different_tasks()
            throws InvalidExpressionException, InvalidProcessDefinitionException {

        // given
        ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder().createNewInstance("p1",
                "1.0");
        AutomaticTaskDefinitionBuilder autoTask1 = processDefinitionBuilder.addAutomaticTask("auto1");
        AutomaticTaskDefinitionBuilder autoTask2 = processDefinitionBuilder.addAutomaticTask("auto2");
        processDefinitionBuilder.addTransition("auto1", "auto2");
        autoTask1.addOperation(new OperationBuilder().deleteBusinessDataOperation("myBusinessData"));
        autoTask1.addOperation(new OperationBuilder().deleteBusinessDataOperation("myBusinessData2"));
        autoTask1.addOperation(new OperationBuilder().deleteBusinessDataOperation("myBusinessData3"));
        autoTask2.addOperation(new OperationBuilder().attachBusinessDataSetAttributeOperation("myBusinessData",
                new ExpressionBuilder().createGroovyScriptExpression("assignNewValue",
                        "toto",
                        "com.company.model.Employee")));
        autoTask2.addOperation(new OperationBuilder().deleteBusinessDataOperation("myBusinessData2"));
        autoTask2.addOperation(new OperationBuilder().attachBusinessDataSetAttributeOperation("myBusinessData3",
                new ExpressionBuilder().createGroovyScriptExpression("assignNewValue",
                        "toto",
                        "com.company.model.Employee")));
        autoTask2.addOperation(new OperationBuilder().attachBusinessDataSetAttributeOperation("myBusinessData4",
                new ExpressionBuilder().createGroovyScriptExpression("assignNewValue",
                        "toto;",
                        "com.company.model.Employee")));

        assertThat(processDefinitionBuilder.done()).isNotNull();
    }

    @Test
    void should_detect_deletion_operations_in_subprocesses_activities()
            throws InvalidExpressionException, InvalidProcessDefinitionException {
        //given
        ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder().createNewInstance("p1",
                "1.0");
        SubProcessDefinitionBuilder subProcessDefinitionBuilder = processDefinitionBuilder
                .addSubProcess("MySubprocess", false).getSubProcessBuilder();
        subProcessDefinitionBuilder.addAutomaticTask("subProcessAutoTask")
                .addOperation(new OperationBuilder().attachBusinessDataSetAttributeOperation("myBusinessData",
                        new ExpressionBuilder().createGroovyScriptExpression("assignNewValue",
                                "toto;",
                                "com.company.model.Employee")))
                .addOperation(new OperationBuilder().deleteBusinessDataOperation("myBusinessData"));
        //when
        assertThrows(InvalidProcessDefinitionException.class, () -> processDefinitionBuilder.done(),
                "The business variable myBusinessData on the current element has been deleted by an operation. " +
                        "Other operations performed on this instance through the same element: subProcessAutoTask are not allowed");
    }

    @Test
    void should_detect_deletion_operations_in_catchMessageEventTriggers()
            throws InvalidExpressionException, InvalidProcessDefinitionException {
        //given
        ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder().createNewInstance("p1",
                "1.0");
        processDefinitionBuilder.addIntermediateCatchEvent("intermediateCatchEvent")
                .addMessageEventTrigger("newMessage")
                .addOperation(new OperationBuilder().attachBusinessDataSetAttributeOperation("myBusinessData",
                        new ExpressionBuilder().createGroovyScriptExpression("assignNewValue",
                                "toto;",
                                "com.company.model.Employee")))
                .addOperation(new OperationBuilder().deleteBusinessDataOperation("myBusinessData"));
        //then
        assertThrows(InvalidProcessDefinitionException.class, () -> processDefinitionBuilder.done(),
                "The business variable myBusinessData on the current element has been deleted by an operation. " +
                        "Other operations performed on this instance through the same element: intermediateCatchEvent are not allowed");
    }

    @Test
    void should_detect_deletion_operations_in_Connectors()
            throws InvalidExpressionException, InvalidProcessDefinitionException {
        //given
        ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder().createNewInstance("p1",
                "1.0");
        ConnectorDefinitionBuilder connectorDefinitionBuilder = processDefinitionBuilder.addConnector("connectorName",
                "257", "1.0", null);
        connectorDefinitionBuilder
                .addOutput(new OperationBuilder().attachBusinessDataSetAttributeOperation("myBusinessData",
                        new ExpressionBuilder().createGroovyScriptExpression("assignNewValue",
                                "tototo",
                                "com.company.model.Employee")))
                .addOutput(new OperationBuilder().deleteBusinessDataOperation("myBusinessData"));
        //then
        assertThrows(InvalidProcessDefinitionException.class, () -> processDefinitionBuilder.done(),
                "The business variable myBusinessData on the current element has been deleted by an operation. " +
                        "Other operations performed on this instance through the same element: connectorName are not allowed");
    }

    @Test
    void should_detect_deletion_operations_in_Connectors_in_Activities()
            throws InvalidProcessDefinitionException, InvalidExpressionException {

        //given
        ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder().createNewInstance("p1",
                "1.0");
        AutomaticTaskDefinitionBuilder autoTask1 = processDefinitionBuilder.addAutomaticTask("auto1");
        autoTask1.addConnector("connector", "245", "0.1", ConnectorEvent.ON_ENTER);
        autoTask1.getActivity().getConnectors().get(0).getOutputs()
                .add(new OperationBuilder().attachBusinessDataSetAttributeOperation("myBusinessData",
                        new ExpressionBuilder().createGroovyScriptExpression("assignNewValue",
                                "toto",
                                "com.company.model.Employee")));
        autoTask1.getActivity().getConnectors().get(0).getOutputs()
                .add(new OperationBuilder().deleteBusinessDataOperation("myBusinessData"));

        //then
        assertThrows(InvalidProcessDefinitionException.class, () -> processDefinitionBuilder.done(),
                "The business variable myBusinessData on the current element has been deleted by an operation. " +
                        "Other operations performed on this instance through the same element: connector are not allowed");
    }

    @Test
    void deleting_twice_the_same_bizData_should_be_allowed()
            throws InvalidProcessDefinitionException, InvalidExpressionException {

        //given
        ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder()
                .createNewInstance("delete Twice", "2.0");
        AutomaticTaskDefinitionBuilder autoTask = processDefinitionBuilder.addAutomaticTask("auto1");
        autoTask.addOperation(new OperationBuilder().deleteBusinessDataOperation("myBusinessData"));
        autoTask.addOperation(new OperationBuilder().deleteBusinessDataOperation("myBusinessData"));

        //when
        assertThat(processDefinitionBuilder.done()).isNotNull();
    }
}
