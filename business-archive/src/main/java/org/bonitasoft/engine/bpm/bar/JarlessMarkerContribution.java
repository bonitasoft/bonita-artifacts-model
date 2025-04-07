/**
 * Copyright (C) 2025 Bonitasoft S.A.
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Contributes the marker file for bars without dependency jars.
 */
public class JarlessMarkerContribution extends GenericFileContribution {

    protected static final String JAR_LESS_MARKER_RESOURCE_PATH = ".jarless";

    @Override
    public String getFileName() {
        return JAR_LESS_MARKER_RESOURCE_PATH;
    }

    @Override
    public String getName() {
        return getFileName();
    }

    @Override
    public boolean readFromBarFolder(BusinessArchive businessArchive, File barFolder) {
        // file is always empty
        var file = barFolder.toPath().resolve(JAR_LESS_MARKER_RESOURCE_PATH);
        if (Files.exists(file)) {
            businessArchive.tagWithoutDependencyJars();
            return true;
        }
        return false;
    }

    @Override
    public void saveToBarFolder(BusinessArchive businessArchive, File barFolder) throws IOException {
        // file is always empty
        if (!businessArchive.hasDependencyJars()) {
            Files.write(barFolder.toPath().resolve(JAR_LESS_MARKER_RESOURCE_PATH), new byte[] {});
        }
    }

    @Override
    public boolean isMandatory() {
        return false;
    }

}
