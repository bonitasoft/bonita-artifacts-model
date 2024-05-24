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
package org.bonitasoft.engine.business.application;

/**
 * Contains the meta information of a legacy Bonita Living Application.
 *
 * @author Elias Ricken de Medeiros
 * @since 6.4.0
 */
public interface Application extends IApplication {

    /**
     * Retrieves the identifier of the {@link ApplicationPage} defined as home page for this application
     *
     * @return the identifier of the {@link ApplicationPage} defined as home page for this application
     * @see ApplicationPage
     */
    Long getHomePageId();

    /**
     * Retrieves the identifier of the {@code Page} used as {@code Application} layout. If no
     * layout is associated to the current
     * {@code Application} the result will be {@code null}.
     *
     * @return the identifier of the {@code Page} used as {@code Application} layout or
     *         {@code null} if the current
     *         {@code Application} has no layout.
     * @see "org.bonitasoft.engine.page.Page"
     * @since 7.0.0
     */
    Long getLayoutId();

    /**
     * Retrieves the identifier of the {@code Page} used as {@code Application} theme. If no
     * theme is associated to the current
     * {@code Application} the result will be {@code null}.
     *
     * @return the identifier of the {@code Page} used as {@code Application} theme or
     *         {@code null} if the current
     *         {@code Application} has no theme.
     * @see "org.bonitasoft.engine.page.Page"
     * @since 7.0.0
     */
    Long getThemeId();
}
