/**
 * Copyright (C) 2016 Bonitasoft S.A.
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

import java.io.File;
import java.nio.file.Path;

import org.bonitasoft.engine.bpm.process.DesignProcessDefinition;
import org.bonitasoft.engine.bpm.process.InvalidProcessDefinitionException;
import org.bonitasoft.engine.bpm.process.impl.ProcessDefinitionBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class BusinessArchiveFactoryTest {

    @Test
    void should_write_and_read_BAR_works(@TempDir Path temporaryFolder) throws Exception {
        //given
        BusinessArchive businessArchive = createBusinessArchive();
        var barFile = temporaryFolder.resolve("test.bar");
        BusinessArchiveFactory.writeBusinessArchiveToFile(businessArchive, barFile.toFile());
        //when
        BusinessArchive deserializeBAR = BusinessArchiveFactory.readBusinessArchive(barFile.toFile());
        //then
        assertThat(deserializeBAR.getProcessDefinition().getName()).isEqualTo("说话_éé");
    }

    @Test
    void writeBusinessArchiveToFolder(@TempDir Path tmpDir) throws Exception {
        BusinessArchive businessArchive = createBusinessArchive();

        BusinessArchiveFactory.writeBusinessArchiveToFolder(businessArchive, tmpDir.toFile());

        assertThat(tmpDir.resolve("process-design.xml")).exists().isNotEmptyFile();
        assertThat(tmpDir.resolve("form-mapping.xml")).exists();
        assertThat(tmpDir.resolve("parameters.properties")).exists();
        assertThat(tmpDir.resolve("classpath")).exists().isDirectory();
    }

    @Test
    void saveBusinessArchiveFromFolderToFile(@TempDir Path tmpDir) throws Exception {
        BusinessArchive businessArchive = createBusinessArchive();

        BusinessArchiveFactory.writeBusinessArchiveToFolder(businessArchive, tmpDir.toFile());
        var path = BusinessArchiveFactory.businessArchiveFolderToFile(tmpDir.resolve("toto.bar").toFile(),
                tmpDir.toFile().getAbsolutePath());

        assertThat(new File(path)).exists();
    }

    @Test
    void should_readingBusinessArchiveFromFolderWithJarLessMarker_notAddResource(@TempDir Path tmpDir)
            throws Exception {
        BusinessArchive businessArchive = createBusinessArchive();
        tmpDir.resolve(".jarless").toFile().createNewFile();
        BusinessArchiveFactory.writeBusinessArchiveToFolder(businessArchive, tmpDir.toFile());

        var archive = BusinessArchiveFactory.readBusinessArchive(tmpDir.toFile());

        assertThat(archive).isNotNull();
        // marker has correctly set hasDependencyJars property
        assertThat(archive.hasDependencyJars()).isFalse();
        // .jarless marker is not added as a resource: it is not an external/classpath resource
        assertThat(archive.getResource(".jarless")).isNull();
        assertThat(archive.getProcessDefinition().getName()).isEqualTo("说话_éé");
    }

    @Test
    void readBusinessArchiveFromFolder(@TempDir Path tmpDir) throws Exception {
        BusinessArchive businessArchive = createBusinessArchive();
        BusinessArchiveFactory.writeBusinessArchiveToFolder(businessArchive, tmpDir.toFile());

        var archive = BusinessArchiveFactory.readBusinessArchive(tmpDir.toFile());

        assertThat(archive).isNotNull();
        assertThat(archive.getProcessDefinition().getName()).isEqualTo("说话_éé");
    }

    private BusinessArchive createBusinessArchive()
            throws InvalidBusinessArchiveFormatException, InvalidProcessDefinitionException {
        return new BusinessArchiveBuilder().createNewBusinessArchive()
                .setProcessDefinition(dummyProcessDefintion())
                .done();
    }

    private DesignProcessDefinition dummyProcessDefintion() throws InvalidProcessDefinitionException {
        return new ProcessDefinitionBuilder().createNewInstance("说话_éé", "1.0")
                .addAutomaticTask("说话_éé_task").getProcess();
    }

}
