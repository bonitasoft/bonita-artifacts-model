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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.bonitasoft.engine.bpm.process.DesignProcessDefinition;
import org.bonitasoft.engine.bpm.process.InvalidProcessDefinitionException;
import org.bonitasoft.engine.bpm.process.impl.ProcessDefinitionBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class BusinessArchiveFactoryTest {

    private final List<Path> pathsOutsideOfExtractionFolder = new ArrayList<>();

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

    @AfterEach
    void deletePathsOutsideOfExtractionFolder() throws IOException {
        for (var path : pathsOutsideOfExtractionFolder) {
            Files.deleteIfExists(path);
        }
    }

    @Test
    void readBusinessArchive_should_reject_file_entry_outside_of_extraction_folder() throws Exception {
        //given
        var fileName = "bar-entry-" + UUID.randomUUID() + ".txt";
        var fileOutsideOfExtractionFolder = outsideOfExtractionFolder(fileName);
        var archive = zipWithSingleEntry("../" + fileName, "content".getBytes(StandardCharsets.UTF_8));

        //when-then
        assertThatThrownBy(() -> BusinessArchiveFactory.readBusinessArchive(new ByteArrayInputStream(archive)))
                .isInstanceOf(InvalidBusinessArchiveFormatException.class)
                .cause()
                .isInstanceOf(IOException.class)
                .hasMessage("Zip entry is outside of the target directory");
        assertThat(fileOutsideOfExtractionFolder).doesNotExist();
    }

    @Test
    void readBusinessArchive_should_reject_directory_entry_outside_of_extraction_folder() throws Exception {
        //given
        var directoryName = "bar-entry-" + UUID.randomUUID();
        var directoryOutsideOfExtractionFolder = outsideOfExtractionFolder(directoryName);
        var archive = zipWithSingleEntry("../" + directoryName + "/", new byte[0]);

        //when-then
        assertThatThrownBy(() -> BusinessArchiveFactory.readBusinessArchive(new ByteArrayInputStream(archive)))
                .isInstanceOf(InvalidBusinessArchiveFormatException.class)
                .cause()
                .isInstanceOf(IOException.class)
                .hasMessage("Zip entry is outside of the target directory");
        assertThat(directoryOutsideOfExtractionFolder).doesNotExist();
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

    /**
     * The BAR is extracted in a sub-folder of java.io.tmpdir, so an entry starting with '../' targets java.io.tmpdir.
     * The returned path is deleted after the test, in case the entry was extracted there.
     */
    private Path outsideOfExtractionFolder(String name) {
        var path = Path.of(System.getProperty("java.io.tmpdir"), name);
        pathsOutsideOfExtractionFolder.add(path);
        return path;
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
