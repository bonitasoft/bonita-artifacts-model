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
package org.bonitasoft.engine.bpm.flownode.impl.internal;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.bonitasoft.engine.bpm.BaseElement;
import org.junit.jupiter.api.Test;

class ElementFinderTest {

    @Test
    void shouldHaveEqualInstances() throws Exception {
        assertThat(new ElementFinder()).isEqualTo(new ElementFinder());
    }

    @Test
    void shouldNotHaveEqualInstances() throws Exception {
        assertThat(new ElementFinder()).isNotEqualTo(new Object());
        assertThat(new ElementFinder()).isNotEqualTo(null);
    }

    @Test
    void shouldGetElementById() throws Exception {
        var finder = new ElementFinder();

        var result = finder.getElementById(List.of(element(1L), element(2L)), 2L);

        assertThat(result.getId()).isEqualTo(2);
    }

    @Test
    void shouldReturnNullIfNoElementFound() throws Exception {
        var finder = new ElementFinder();

        var result = finder.getElementById(List.of(element(1L), element(2L)), 3L);

        assertThat(result).isNull();
    }

    private BaseElement element(long id) {
        return () -> id;
    }

}
