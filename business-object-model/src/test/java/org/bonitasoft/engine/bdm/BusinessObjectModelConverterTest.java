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
package org.bonitasoft.engine.bdm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.bind.JAXBException;

import org.bonitasoft.engine.bdm.builder.BusinessObjectModelBuilder;
import org.bonitasoft.engine.bdm.model.BusinessObjectModel;
import org.junit.jupiter.api.Test;

class BusinessObjectModelConverterTest {

    private final BusinessObjectModelConverter convertor = new BusinessObjectModelConverter();

    @Test
    void zipThenUnzipBOMShouldReturnTheOriginalBOM() throws Exception {
        final BusinessObjectModel bom = new BusinessObjectModelBuilder().buildDefaultBOM();
        final byte[] zip = convertor.zip(bom);
        final BusinessObjectModel actual = convertor.unzip(zip);

        assertThat(actual).isEqualTo(bom);
    }

    @Test
    void zipThenUnzipBOMShouldReturnTheOriginalBOMWithUniqueConstraint() throws Exception {
        final BusinessObjectModel bom = new BusinessObjectModelBuilder().buildBOMWithUniqueConstraint();
        final byte[] zip = convertor.zip(bom);
        final BusinessObjectModel actual = convertor.unzip(zip);

        assertThat(actual).isEqualTo(bom);
    }

    @Test
    void zipThenUnzipBOMShouldReturnTheOriginalBOMWithQuery() throws Exception {
        final BusinessObjectModel bom = new BusinessObjectModelBuilder().buildBOMWithQuery();
        final byte[] zip = convertor.zip(bom);
        final BusinessObjectModel actual = convertor.unzip(zip);

        assertThat(actual).isEqualTo(bom);
    }

    @Test
    void zipAnBOMWithAnEmptyShouldThrowAnException() {
        final BusinessObjectModel bom = new BusinessObjectModelBuilder().buildBOMWithAnEmptyEntity();
        assertThatExceptionOfType(JAXBException.class).isThrownBy(() -> convertor.zip(bom));
    }

    @Test
    void zipAnBOMWithAnEmptyFieldShouldThrowAnException() {
        final BusinessObjectModel bom = new BusinessObjectModelBuilder().buildBOMWithAnEmptyField();
        assertThatExceptionOfType(JAXBException.class).isThrownBy(() -> convertor.zip(bom));
    }

    @Test
    void unzipADifferentZipThrowAnException() throws Exception {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try (final ZipOutputStream zos = new ZipOutputStream(stream)) {
            zos.putNextEntry(new ZipEntry("bonita"));
            zos.write("bpm".getBytes());
        }
        final byte[] zip = stream.toByteArray();
        assertThatExceptionOfType(IOException.class).isThrownBy(() -> convertor.unzip(zip))
                .withMessage("the file bom.xml is missing in the zip");
    }

    @Test
    void should_be_backward_compatible() throws Exception {
        try (var resource = BusinessObjectModelConverterTest.class.getResourceAsStream("/bom_6.3.0.xml")) {
            assertThat(resource).isNotNull();
            final byte[] xml = resource.readAllBytes();
            final BusinessObjectModel bom = convertor.unmarshall(xml);
            // expect no unmarshalling exception

            assertThat(bom.getModelVersion()).isNullOrEmpty();
            assertThat(bom.getProductVersion()).isNullOrEmpty();
        }
    }

    @Test
    void should_unmarshall_bom_with_namespace() throws Exception {
        try (var resource = BusinessObjectModelConverterTest.class.getResourceAsStream("/bom_7.11.0.xml")) {
            assertThat(resource).isNotNull();
            final byte[] xml = resource.readAllBytes();
            final BusinessObjectModel bom = convertor.unmarshall(xml);
            // expect no unmarshalling exception

            assertThat(bom.getModelVersion()).isEqualTo("1.0");
            assertThat(bom.getProductVersion()).isEqualTo("7.11.0");
        }
    }

    @Test
    void should_add_namespace_when_missing() throws Exception {
        try (var resource = BusinessObjectModelConverterTest.class.getResourceAsStream("/bom_6.3.0.xml")) {
            assertThat(resource).isNotNull();
            byte[] xml = resource.readAllBytes();
            String str = new String(xml);
            assertThat(str).doesNotContain("xmlns=\"http://documentation.bonitasoft.com/bdm-xml-schema/1.0\"");

            BusinessObjectModel bom = convertor.unmarshall(xml);

            xml = convertor.marshall(bom);
            str = new String(xml);
            assertThat(str).contains("xmlns=\"http://documentation.bonitasoft.com/bdm-xml-schema/1.0\"");
        }
    }

    @Test
    void should_be_backward_compatible_with_version() throws Exception {
        try (var resource = BusinessObjectModelConverterTest.class.getResourceAsStream("/bom_7.2.0.xml")) {
            assertThat(resource).isNotNull();
            final byte[] xml = resource.readAllBytes();
            final BusinessObjectModel bom = convertor.unmarshall(xml);
            // expect no unmarshalling exception

            assertThat(bom.getModelVersion()).isEqualTo("1.0");
            assertThat(bom.getProductVersion()).isEqualTo("7.2.0");

            final BusinessObjectModel unmarshallBom = convertor.unmarshall(convertor.marshall(bom));

            assertThat(unmarshallBom.getModelVersion()).isEqualTo("1.0");
            assertThat(unmarshallBom.getProductVersion()).isEqualTo("7.2.0");
        }
    }

    @Test
    void should_set_current_model_version_when_marshalling() throws Exception {
        final BusinessObjectModel bom = new BusinessObjectModelBuilder().buildBOMWithQuery();
        assertThat(bom.getModelVersion()).isNullOrEmpty();
        assertThat(bom.getProductVersion()).isNullOrEmpty();
        final BusinessObjectModel unmarshalledBom = convertor.unmarshall(convertor.marshall(bom));

        assertThat(unmarshalledBom.getModelVersion()).isEqualTo(BusinessObjectModel.CURRENT_MODEL_VERSION);
        assertThat(bom.getProductVersion()).isNotNull().isNotEmpty();
    }

    @Test
    void zipThenUnzipBOMShouldReturnTheOriginalBOMWithIndex() throws Exception {
        final BusinessObjectModel bom = new BusinessObjectModelBuilder().buildBOMWithIndex();
        final byte[] zip = convertor.zip(bom);
        final BusinessObjectModel actual = convertor.unzip(zip);

        assertThat(actual).isEqualTo(bom);
    }

    @Test
    void marshall_and_unmarshall_should_return_the_same_object() throws Exception {
        final BusinessObjectModel expected = new BusinessObjectModelBuilder().buildDefaultBOM();
        final byte[] xml = convertor.marshallObjectToXML(expected);
        final BusinessObjectModel actual = convertor.unmarshallXMLtoObject(xml);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void marshall_a_null_object_returns_empty() throws Exception {
        final byte[] xml = convertor.marshallObjectToXML(null);

        assertThat(xml).isEmpty();
    }

    @Test
    void marshall_an_empty_object_throws_a_JAXBException() {
        assertThatExceptionOfType(JAXBException.class)
                .isThrownBy(() -> convertor.marshallObjectToXML(new BusinessObjectModel()));
    }

    @Test
    void unmarshall_a_null_object_returns_null() throws Exception {
        final BusinessObjectModel object = convertor.unmarshallXMLtoObject(null);

        assertThat(object).isNull();
    }

    @Test
    void unmarshall_an_empty_object_throws_a_JAXBException() {
        assertThatExceptionOfType(JAXBException.class)
                .isThrownBy(() -> convertor.unmarshallXMLtoObject(new byte[0]));
    }
}
