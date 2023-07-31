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
package org.bonitasoft.engine.bdm.validator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import org.glassfish.hk2.osgiresourcelocator.ResourceFinder;

/**
 * @author Romain Bioteau
 */
public class SQLNameValidator {

    private static final int DEFAULT_MAX_LENGTH = 255;
    private static final String SQL_KEYWORDS_RESOURCE = "/sql_keywords";

    private final int maxLength;

    static Set<String> sqlKeywords = new HashSet<>();

    public SQLNameValidator() {
        this(DEFAULT_MAX_LENGTH);
    }

    public SQLNameValidator(final int maxLength) {
        this.maxLength = maxLength;
        if (sqlKeywords.isEmpty()) {
            initializeSQLKeywords();
        }
    }

    private void initializeSQLKeywords() {
        try (Scanner scanner = new Scanner(getSQLKeywordsResource())) {
            while (scanner.hasNext()) {
                final String word = scanner.nextLine();
                sqlKeywords.add(word.trim());
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private InputStream getSQLKeywordsResource() throws IOException {
        var sqlKeywordsResource = Optional.ofNullable(ResourceFinder.findEntry(SQL_KEYWORDS_RESOURCE))
                .orElseGet(() -> SQLNameValidator.class.getResource(SQL_KEYWORDS_RESOURCE));
        if (sqlKeywordsResource == null) {
            throw new FileNotFoundException("SQL Keywords resource not found");
        }
        return sqlKeywordsResource.openStream();
    }

    public boolean isValid(final String name) {
        return name.matches("[a-zA-Z][\\d\\w#@]{0," + maxLength + "}$") && !isSQLKeyword(name);
    }

    public boolean isSQLKeyword(final String name) {
        return sqlKeywords.contains(name.toUpperCase());
    }

}
