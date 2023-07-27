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
package org.bonitasoft.engine.bdm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.List;

import org.bonitasoft.engine.bdm.model.BusinessObject;
import org.bonitasoft.engine.bdm.model.Index;
import org.bonitasoft.engine.bdm.model.Query;
import org.bonitasoft.engine.bdm.model.UniqueConstraint;
import org.bonitasoft.engine.bdm.model.field.FieldType;
import org.bonitasoft.engine.bdm.model.field.SimpleField;
import org.junit.jupiter.api.Test;

class BusinessObjectTest {

    @Test
    void setQualifiedNameShouldWorkIfAValidQualifiedName() {
        assertThat(new BusinessObject("com.Employee").getSimpleName()).isEqualTo("Employee");
    }

    @Test
    void setQualifiedNameShouldWorkIfAValidQualifiedNameInLowercase() {
        assertThat(new BusinessObject("com.employee").getSimpleName()).isEqualTo("employee");
    }

    @Test
    void setQualifiedNameShouldWorkWithoutPackageName() {
        assertThat(new BusinessObject("Employee").getSimpleName()).isEqualTo("Employee");
    }

    @Test
    void addUniqueConstraintShouldWorkIfTheFieldExists() {
        final SimpleField field = new SimpleField();
        field.setName("field");
        field.setType(FieldType.STRING);

        final BusinessObject object = new BusinessObject();
        object.addField(field);
        object.addUniqueConstraint("unique", "field");

        final UniqueConstraint expected = new UniqueConstraint();
        expected.setName("unique");
        expected.setFieldNames(List.of("field"));
        assertThat(object.getUniqueConstraints()).containsExactly(expected);
    }

    @Test
    void should_addQuery() {
        final BusinessObject businessObject = new BusinessObject();
        final Query query = businessObject.addQuery("userByName", "SELECT u FROM User u WHERE u.name='romain'",
                List.class.getName());
        assertThat(businessObject.getQueries()).containsExactly(query);
    }

    @Test
    void addIndexShouldWorkIfTheFieldExists() {
        final SimpleField field = new SimpleField();
        field.setName("field");
        field.setType(FieldType.STRING);

        final Index expected = new Index();
        expected.setName("unique");
        expected.setFieldNames(List.of("field"));

        final BusinessObject object = new BusinessObject();
        object.addField(field);
        final Index index = object.addIndex("unique", "field");

        assertThat(index).isEqualTo(expected);
    }

    @Test
    void addIndexShouldThrowAnExceptionIfNoFieldIsSet() {
        final BusinessObject object = new BusinessObject();
        assertThatIllegalArgumentException().isThrownBy(() -> object.addIndex("unique", (String[]) null));
    }

    @Test
    void addIndexShouldThrowAnExceptionIfTheListOfFieldsIsEmpty() {
        final BusinessObject object = new BusinessObject();
        assertThatIllegalArgumentException().isThrownBy(() -> object.addIndex("unique"));
    }

}
