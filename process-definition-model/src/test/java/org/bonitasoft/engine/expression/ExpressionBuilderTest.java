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
package org.bonitasoft.engine.expression;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class ExpressionBuilderTest {

    private Expression longExpr;

    private Expression intExpr;

    private Expression string;

    @BeforeEach
    void initializeExpressions() throws Exception {
        longExpr = new ExpressionBuilder().createConstantLongExpression(1);
        intExpr = new ExpressionBuilder().createConstantIntegerExpression(1);
        string = new ExpressionBuilder().createConstantStringExpression("string");
    }

    @Test
    void invalidOperator() throws Exception {
        var expressionBuilder = new ExpressionBuilder();

        assertThrows(InvalidExpressionException.class,
                () -> expressionBuilder.createComparisonExpression("comp1", intExpr, "||", intExpr));
    }

    @Test
    void validReturnTypesForBinaryOperator() throws Exception {
        var expressionBuilder = new ExpressionBuilder();

        assertThat(expressionBuilder.createComparisonExpression("comp1", intExpr, ComparisonOperator.GREATER_THAN,
                longExpr)).isNotNull();
    }

    @Test
    void invalidReturnTypesForBinaryOperator() throws Exception {
        var expressionBuilder = new ExpressionBuilder();

        assertThrows(InvalidExpressionException.class, () -> expressionBuilder.createComparisonExpression("comp1",
                intExpr, ComparisonOperator.GREATER_THAN, string));
    }

    @Test
    void invalidReturnTypeForUnaryOperator() throws Exception {
        var expressionBuilder = new ExpressionBuilder();

        assertThrows(InvalidExpressionException.class,
                () -> expressionBuilder.createLogicalComplementExpression("complement", intExpr));
    }

    @ParameterizedTest
    @MethodSource(value = "illegalReturnTypes")
    void shouldThrowIllegalArgumentExcpetionForPrimitiveTypes(String primitiveType) throws Exception {
        var expressionBuilder = new ExpressionBuilder();

        assertThrows(IllegalArgumentException.class, () -> expressionBuilder.setReturnType(primitiveType));
    }

    private static String[] illegalReturnTypes() {
        return new String[] { char.class.getName(),
                byte.class.getName(),
                boolean.class.getName(),
                short.class.getName(),
                double.class.getName(),
                int.class.getName(),
                float.class.getName(),
                long.class.getName()
        };
    }

    @Test
    void setReturnTypeShouldAllowNonPrimitiveBooleanType() throws Exception {
        assertThat(new ExpressionBuilder().createNewInstance("someName").setReturnType(Boolean.class.getName()))
                .isNotNull();
    }

    @Test
    void createContractInputExpressionShouldConstructARightExpression() throws Exception {
        final Expression expression = new ExpressionBuilder().createContractInputExpression("comment",
                String.class.getName());
        assertThat(expression.getName()).isEqualTo("comment");
        assertThat(expression.getContent()).isEqualTo("comment");
        assertThat(expression.getReturnType()).isEqualTo(String.class.getName());
        assertThat(expression.getDependencies()).isEmpty();
        assertThat(expression.getExpressionType()).isEqualTo(ExpressionType.TYPE_CONTRACT_INPUT.toString());
        assertThat(expression.getInterpreter()).isNull();
    }

    @Test
    void createConstantStringExpressionShouldSetADefaultNameIfNotProvided() throws Exception {
        final Expression expression = new ExpressionBuilder().createConstantStringExpression("constant");
        assertThat(expression.getName()).isEqualTo("constant");
    }

    @Test
    void createConstantStringExpressionShouldSetADefaultNameIfNotProvidedEvenIfTheExpressionIsEmpty()
            throws Exception {
        final Expression expression = new ExpressionBuilder().createConstantStringExpression("");
        assertThat(expression.getName()).isEqualTo("empty_expression");
    }

}
