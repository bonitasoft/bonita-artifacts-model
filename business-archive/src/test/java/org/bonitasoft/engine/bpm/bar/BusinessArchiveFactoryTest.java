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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
    void readBusinessArchiveFromFolder(@TempDir Path tmpDir) throws Exception {
        BusinessArchive businessArchive = createBusinessArchive();
        BusinessArchiveFactory.writeBusinessArchiveToFolder(businessArchive, tmpDir.toFile());

        var archive = BusinessArchiveFactory.readBusinessArchive(tmpDir.toFile());

        assertThat(archive).isNotNull();
        assertThat(archive.getProcessDefinition().getName()).isEqualTo("说话_éé");
    }

    @Test
    void readBusinessArchive_should_reject_file_entry_outside_of_extraction_folder() throws Exception {
        //given
        var fileName = "bar-entry-" + UUID.randomUUID() + ".txt";
        // the BAR is extracted in a sub-folder of java.io.tmpdir: '../' targets java.io.tmpdir itself
        var fileOutsideOfExtractionFolder = Path.of(System.getProperty("java.io.tmpdir"), fileName);
        var archive = zipWithSingleEntry("../" + fileName, "content".getBytes(StandardCharsets.UTF_8));
        try {
            //when-then
            assertThatThrownBy(() -> BusinessArchiveFactory.readBusinessArchive(new ByteArrayInputStream(archive)))
                    .isInstanceOf(InvalidBusinessArchiveFormatException.class)
                    .cause()
                    .isInstanceOf(IOException.class)
                    .hasMessage("Zip entry is outside of the target directory");
            assertThat(fileOutsideOfExtractionFolder).doesNotExist();
        } finally {
            Files.deleteIfExists(fileOutsideOfExtractionFolder);
        }
    }

    @Test
    void readBusinessArchive_should_reject_directory_entry_outside_of_extraction_folder() throws Exception {
        //given
        var directoryName = "bar-entry-" + UUID.randomUUID();
        var directoryOutsideOfExtractionFolder = Path.of(System.getProperty("java.io.tmpdir"), directoryName);
        var archive = zipWithSingleEntry("../" + directoryName + "/", new byte[0]);
        try {
            //when-then
            assertThatThrownBy(() -> BusinessArchiveFactory.readBusinessArchive(new ByteArrayInputStream(archive)))
                    .isInstanceOf(InvalidBusinessArchiveFormatException.class)
                    .cause()
                    .isInstanceOf(IOException.class)
                    .hasMessage("Zip entry is outside of the target directory");
            assertThat(directoryOutsideOfExtractionFolder).doesNotExist();
        } finally {
            Files.deleteIfExists(directoryOutsideOfExtractionFolder);
        }
    }

    @Test
    void readBusinessArchive_should_read_entries_located_in_sub_folders(@TempDir Path tmpDir) throws Exception {
        //given
        var jarContent = "jar content".getBytes(StandardCharsets.UTF_8);
        var businessArchive = new BusinessArchiveBuilder().createNewBusinessArchive()
                .setProcessDefinition(dummyProcessDefintion())
                .addClasspathResource(new BarResource("lib.jar", jarContent))
                .done();
        var barFile = tmpDir.resolve("withClasspath.bar").toFile();
        BusinessArchiveFactory.writeBusinessArchiveToFile(businessArchive, barFile);

        //when
        var archive = BusinessArchiveFactory.readBusinessArchive(barFile);

        //then
        assertThat(archive.getResource("classpath/lib.jar")).isEqualTo(jarContent);
    }

    private static byte[] zipWithSingleEntry(String entryName, byte[] content) throws IOException {
        var output = new ByteArrayOutputStream();
        try (var zip = new ZipOutputStream(output)) {
            zip.putNextEntry(new ZipEntry(entryName));
            zip.write(content);
            zip.closeEntry();
        }
        return output.toByteArray();
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
