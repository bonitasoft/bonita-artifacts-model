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
package org.bonitasoft.engine.business.application.exporter;

import static org.assertj.core.api.Assertions.*;
import static org.bonitasoft.engine.business.application.xml.ApplicationNodeBuilder.newApplication;
import static org.bonitasoft.engine.business.application.xml.ApplicationNodeBuilder.newApplicationContainer;

import org.bonitasoft.engine.business.application.xml.ApplicationNodeContainer;
import org.junit.jupiter.api.Test;

import jakarta.xml.bind.UnmarshalException;

class ApplicationNodeContainerConverterTest {

    private final ApplicationNodeContainerConverter converter = new ApplicationNodeContainerConverter();

    @Test
    void should_marshall_applicationContainer_to_xml() throws Exception {
        try (var inputStream = ApplicationNodeContainerConverterTest.class.getResourceAsStream("/application.xml")) {
            assertThat(inputStream).isNotNull();
            var expectedXml = new String(inputStream.readAllBytes());

            final byte[] xml = converter.marshallToXML(newApplicationContainer()
                    .havingApplications(newApplication("myApp", "My App", "1.0")).create());

            assertThat(xml).isNotNull();
            assertThat(new String(xml)).isEqualTo(expectedXml);
        }
    }

    @Test
    void should_unmarshall_xml_into_applicationContainer() throws Exception {
        try (var inputStream = ApplicationNodeContainerConverterTest.class.getResourceAsStream("/application.xml")) {
            assertThat(inputStream).isNotNull();
            final ApplicationNodeContainer container = converter.unmarshallFromXML(inputStream.readAllBytes());

            assertThat(container).isNotNull();
            assertThat(container.getApplications()).extracting("token", "displayName", "version")
                    .contains(tuple("myApp", "My App", "1.0"));
        }
    }

    @Test
    void should_fail_unmarshall_xml_into_applicationContainer() throws Exception {
        try (var inputStream = ApplicationNodeContainerConverterTest.class.getResourceAsStream("/badApplication.xml")) {
            assertThat(inputStream).isNotNull();
            assertThatExceptionOfType(UnmarshalException.class)
                    .isThrownBy(() -> converter.unmarshallFromXML(inputStream.readAllBytes()));
        }
    }

}
