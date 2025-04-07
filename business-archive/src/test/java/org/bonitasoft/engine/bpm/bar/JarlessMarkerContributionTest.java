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
package org.bonitasoft.engine.bpm.bar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JarlessMarkerContributionTest {

    @Mock
    BusinessArchive businessArchive;

    @Test
    void should_not_be_mandatory_contribution() {
        //given
        JarlessMarkerContribution contribution = new JarlessMarkerContribution();

        //when then
        assertThat(contribution.isMandatory()).isFalse();

    }

    @Test
    void should_have_a_name() {
        //given
        JarlessMarkerContribution contribution = new JarlessMarkerContribution();

        //when then
        assertThat(contribution.getName()).isEqualTo(".jarless");
    }

    @Test
    void should_default_bar_not_be_jarless() throws Exception {
        //given
        JarlessMarkerContribution contribution = new JarlessMarkerContribution();
        File barFolder = Paths.get(this.getClass().getResource("/barRoot").toURI()).toFile();

        //when
        boolean present = contribution.readFromBarFolder(businessArchive, barFolder);

        //then
        assertThat(present).isFalse();
        verify(businessArchive, never()).addResource(eq(contribution.getName()), notNull());
        verify(businessArchive, never()).tagWithoutDependencyJars();
    }

    @Test
    void should_jarless_bar_have_marker(@TempDir Path tmpFolder) throws Exception {
        //given
        JarlessMarkerContribution contribution = new JarlessMarkerContribution();
        File barFolder = tmpFolder.toFile();
        new File(barFolder, contribution.getName()).createNewFile();

        //when
        boolean present = contribution.readFromBarFolder(businessArchive, barFolder);

        //then
        assertThat(present).isTrue();
        verify(businessArchive).tagWithoutDependencyJars();
    }
}
