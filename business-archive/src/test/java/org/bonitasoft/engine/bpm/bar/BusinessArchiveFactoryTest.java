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
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.file.Path;

import org.bonitasoft.engine.bpm.process.DesignProcessDefinition;
import org.bonitasoft.engine.bpm.process.InvalidProcessDefinitionException;
import org.bonitasoft.engine.bpm.process.impl.ProcessDefinitionBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class BusinessArchiveFactoryTest {

    @AfterEach
    public void after() throws Exception {
        setEncoding("UTF-8");
    }

    private void setEncoding(String encoding) throws Exception {
        System.setProperty("file.encoding", encoding);
        Field charset = Charset.class.getDeclaredField("defaultCharset");
        charset.setAccessible(true);
        charset.set(null, null);
    }

    @Test
    void should_write_non_UTF8_and_read_UTF8_BAR_works(@TempDir Path temporaryFolder) throws Exception {
        //given
        BusinessArchive businessArchive = createBusinessArchive();
        setEncoding("windows-1252");
        var barFile = temporaryFolder.resolve("test.bar");
        BusinessArchiveFactory.writeBusinessArchiveToFile(businessArchive, barFile.toFile());
        //when
        setEncoding("UTF-8");
        BusinessArchive deserializeBAR = BusinessArchiveFactory.readBusinessArchive(barFile.toFile());
        //then
        assertThat(deserializeBAR.getProcessDefinition().getName()).isEqualTo("说话_éé");
    }

    @Test
    void should_write_UTF8_and_read_non_UTF8_BAR_works(@TempDir Path temporaryFolder) throws Exception {
        //given
        BusinessArchive businessArchive = createBusinessArchive();
        setEncoding("UTF-8");
        var barFile = temporaryFolder.resolve("test.bar");
        BusinessArchiveFactory.writeBusinessArchiveToFile(businessArchive, barFile.toFile());
        //when
        setEncoding("windows-1252");
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
