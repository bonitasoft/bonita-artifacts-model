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
package org.bonitasoft.engine.business.application.xml;

import static org.bonitasoft.engine.business.application.xml.ApplicationPageNodeAssert.assertThat;

import org.junit.jupiter.api.Test;

class ApplicationPageNodeTest {

    @Test
    void simple_getters_and_setters_work_fine() {
        //given
        ApplicationPageNode pageNode = new ApplicationPageNode();
        pageNode.setToken("hr");
        pageNode.setCustomPage("hrPage");

        //then
        assertThat(pageNode).hasToken("hr").hasCustomPage("hrPage");
    }

    @Test
    void equals_should_return_true_on_different_pages_with_same_content() {
        ApplicationPageNode page1 = ApplicationNodeBuilder.newApplicationPage("Page", "Token").create();
        ApplicationPageNode page2 = ApplicationNodeBuilder.newApplicationPage("Page", "Token").create();
        assertThat(page1).isEqualTo(page2);
    }

    @Test
    void equals_should_return_false_on_different_pages_with_different_content() {
        ApplicationPageNode page1 = ApplicationNodeBuilder.newApplicationPage("Page", "Token").create();
        ApplicationPageNode page2 = ApplicationNodeBuilder.newApplicationPage("Page", "Token_diff").create();
        assertThat(page1).isNotEqualTo(page2);
    }

}
