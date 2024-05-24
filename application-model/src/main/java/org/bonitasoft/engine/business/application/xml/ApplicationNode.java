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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * Application node for legacy Bonita Living Application.
 * 
 * @author Elias Ricken de Medeiros
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ApplicationNode extends AbstractApplicationNode {

    @XmlAttribute
    private String homePage;

    @XmlAttribute
    private String layout;

    @XmlAttribute
    private String theme;

    @XmlElementWrapper(name = "applicationPages")
    @XmlElement(name = "applicationPage")
    private List<ApplicationPageNode> applicationPages = new ArrayList<>();

    @XmlElementWrapper(name = "applicationMenus")
    @XmlElement(name = "applicationMenu")
    private List<ApplicationMenuNode> applicationMenus = new ArrayList<>();

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(final String layout) {
        this.layout = layout;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(final String theme) {
        this.theme = theme;
    }

    public List<ApplicationPageNode> getApplicationPages() {
        return applicationPages;
    }

    public void setApplicationPages(List<ApplicationPageNode> applicationPages) {
        this.applicationPages = applicationPages;
    }

    public void addApplicationPage(ApplicationPageNode applicationPage) {
        this.applicationPages.add(applicationPage);
    }

    public List<ApplicationMenuNode> getApplicationMenus() {
        return applicationMenus;
    }

    public void setApplicationMenus(List<ApplicationMenuNode> applicationMenus) {
        this.applicationMenus = applicationMenus;
    }

    public void addApplicationMenu(ApplicationMenuNode applicationMenu) {
        applicationMenus.add(applicationMenu);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || !super.equals(o))
            return false;
        ApplicationNode that = (ApplicationNode) o;
        return Objects.equals(homePage, that.homePage) && Objects.equals(layout, that.layout)
                && Objects.equals(theme, that.theme)
                && Objects.equals(applicationPages, that.applicationPages)
                && Objects.equals(applicationMenus, that.applicationMenus);
    }

    @Override
    public int hashCode() {
        // keep same as previous hash code
        return Objects.hash(getToken(), getVersion(), getDisplayName(), getDescription(), getProfile(), homePage,
                getState(), layout, theme, getIconPath(), applicationPages, applicationMenus);
    }
}
