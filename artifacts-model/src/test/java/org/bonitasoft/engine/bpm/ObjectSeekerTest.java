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
package org.bonitasoft.engine.bpm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.bonitasoft.engine.bpm.internal.NamedElementImpl;
import org.junit.jupiter.api.Test;

class ObjectSeekerTest {

    @Test
    void seekANamedElementObject() {
        final List<NamedElement> namedElements = new ArrayList<>();
        final NamedElementImpl namedElementImpl = new NamedElementImpl("var1");
        namedElements.add(namedElementImpl);
        namedElements.add(new NamedElementImpl("var2"));

        final NamedElement namedElement = ObjectSeeker.getNamedElement(namedElements, "var1");

        assertThat(namedElement).isEqualTo(namedElementImpl);
    }

    @Test
    void seekANullObjectReturnsNull() {
        final List<NamedElement> namedElements = new ArrayList<>();
        namedElements.add(new NamedElementImpl("var1"));
        namedElements.add(new NamedElementImpl("var2"));

        final NamedElement namedElement = ObjectSeeker.getNamedElement(namedElements, null);

        assertThat(namedElement).isNull();
    }

    @Test
    void seekAnUnknownObjectReturnsNull() {
        final List<NamedElement> namedElements = new ArrayList<>();
        namedElements.add(new NamedElementImpl("var1"));
        namedElements.add(new NamedElementImpl("var2"));

        final NamedElement namedElement = ObjectSeeker.getNamedElement(namedElements, "var3");

        assertThat(namedElement).isNull();
    }

    @Test
    void seekAObjectInANullListReturnsNull() {
        final NamedElement namedElement = ObjectSeeker.getNamedElement(null, "var1");

        assertThat(namedElement).isNull();
    }

    @Test
    void seekAnObjectInAnEmptyListReturnNull() {
        final List<NamedElement> namedElements = new ArrayList<>();

        final NamedElement namedElement = ObjectSeeker.getNamedElement(namedElements, "var3");

        assertThat(namedElement).isNull();
    }
}
