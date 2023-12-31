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
package org.bonitasoft.engine.bdm.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.bonitasoft.engine.bdm.builder.BusinessObjectBuilder.aBO;
import static org.bonitasoft.engine.bdm.builder.FieldBuilder.*;
import static org.bonitasoft.engine.bdm.model.assertion.BusinessObjectAssert.assertThat;

import java.util.List;

import org.bonitasoft.engine.bdm.model.field.Field;
import org.bonitasoft.engine.bdm.model.field.RelationField;
import org.junit.jupiter.api.Test;

/**
 * @author Colin PUY
 */
class BusinessObjectTest {

    @Test
    void should_have_a_qualifiedName_and_at_least_one_field() {
        BusinessObject businessObject = new BusinessObject();
        businessObject.setQualifiedName("aQualifiedName");

        assertThat(businessObject).cannotBeMarshalled();

        //
        businessObject = new BusinessObject();
        businessObject.addField(aBooleanField("aField"));

        assertThat(businessObject).cannotBeMarshalled();

        //
        businessObject = new BusinessObject();
        businessObject.setQualifiedName("aQualifiedName");
        businessObject.addField(aBooleanField("aField"));

        assertThat(businessObject).canBeMarshalled();
    }

    @Test
    void could_have_simpleFields_and_relationFields() {
        final BusinessObject businessObject = new BusinessObject();
        businessObject.setQualifiedName("aQualifiedName");
        businessObject.addField(aBooleanField("aSimpleField"));
        businessObject
                .addField(
                        anAggregationField("aggregationField",
                                aBO("boName").withField(aBooleanField("aField")).build()));

        assertThat(businessObject).canBeMarshalled();
    }

    @Test
    void could_have_relationFields_referencing_itself() {
        final BusinessObject bo = aBO("aBo").build();

        bo.addField(anAggregationField("itselfRef", bo));

        assertThat(bo).canBeMarshalled();
    }

    @Test
    void could_have_optional_uniqueConstraints() {
        final BusinessObject bo = aBO("aBo").withField(aBooleanField("field1")).withField(aBooleanField("field2"))
                .build();

        bo.addUniqueConstraint("const", "field1");
        bo.addUniqueConstraint("const2", "field2");

        assertThat(bo).canBeMarshalled();
    }

    @Test
    void could_have_optional_queries() {
        final BusinessObject bo = aBO("aBo").withField(aBooleanField("field")).build();

        bo.addQuery("query", "select something from something", "returnType");

        assertThat(bo).canBeMarshalled();
    }

    @Test
    void should_addQuery() {
        final BusinessObject businessObject = new BusinessObject();

        final Query query = businessObject.addQuery("userByName", "SELECT u FROM User u WHERE u.name='romain'",
                List.class.getName());

        assertThat(businessObject.getQueries()).containsExactly(query);
    }

    @Test
    void isARelationField_should_be_false_with_an_emtpy_object() {
        final BusinessObject bo = new BusinessObject();

        assertThat(bo.isARelationField("any")).isFalse();
    }

    @Test
    void isARelationField_should_be_false_with_an_object_without_relation_field_but_with_the_right_name() {
        final BusinessObject bo = new BusinessObject();
        bo.addField(aBooleanField("bool"));

        assertThat(bo.isARelationField("bool")).isFalse();
    }

    @Test
    void isARelationField_should_be_false_with_an_object_without_relation_field() {
        final BusinessObject bo = new BusinessObject();
        bo.addField(aBooleanField("bool"));

        assertThat(bo.isARelationField("any")).isFalse();
    }

    @Test
    void isARelationField_should_be_true_with_an_object_with_relation_field() {
        final BusinessObject bo = new BusinessObject();
        final RelationField aggregationMultiple = aRelationField().withName("address").aggregation()
                .referencing(addressBO())
                .build();
        bo.addField(aggregationMultiple);

        assertThat(bo.isARelationField("address")).isTrue();
    }

    private BusinessObject addressBO() {
        return aBO("Address").withField(aStringField("street").build()).withField(aStringField("city").build()).build();
    }

    @Test
    void should_return_simple_name() {
        //given
        final BusinessObject businessObject = new BusinessObject();
        businessObject.setQualifiedName("com.company.model.Employee");

        //when then
        assertThat(businessObject.getSimpleName()).as("should return simple name").isEqualTo("Employee");
    }

    @Test
    void should_to_string_return_return_all_field() {
        //given
        final BusinessObject businessObject = aBO("aBo").withField(aBooleanField("field1"))
                .withField(aBooleanField("field2")).build();
        UniqueConstraint uniqueConstraint = new UniqueConstraint();
        uniqueConstraint.setName("const");
        uniqueConstraint.setDescription("desc");
        uniqueConstraint.setFieldNames(List.of("field1"));
        businessObject.addUniqueConstraint(uniqueConstraint);
        businessObject.setDescription("description");
        businessObject.addQuery("queryName", "select * from Employee", String.class.getName());

        businessObject.setQualifiedName("com.company.model.Employee");
        //when then
        assertThat(businessObject.toString())
                .as("should return simple name")
                .contains("field1", "field2", "queryName");
    }

    @Test
    void getField_should_return_field_matching_the_given_name() {
        //given
        final BusinessObject businessObject = aBO("aBo").withField(aBooleanField("field1"))
                .withField(aBooleanField("field2")).withField(aBooleanField("field3")).build();
        businessObject.setQualifiedName("com.company.model.Employee");

        //when
        Field field = businessObject.getField("field2");

        //then
        assertThat(field).isNotNull();
        assertThat(field.getName()).isEqualTo("field2");
    }

    @Test
    void getField_should_return_null_when_no_field_exists_for_the_given_name() {
        //given
        final BusinessObject businessObject = aBO("aBo").withField(aBooleanField("field1")).build();
        businessObject.setQualifiedName("com.company.model.Employee");

        //when
        Field field = businessObject.getField("thisFieldDoesNotExist");

        //then
        assertThat(field).isNull();
    }

}
