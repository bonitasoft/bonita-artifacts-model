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
import java.util.stream.Collectors;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * @author Elias Ricken de Medeiros
 */
@XmlRootElement(name = "applications")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApplicationNodeContainer {

    @XmlElements({
            @XmlElement(name = "applicationLink", type = ApplicationLinkNode.class),
            @XmlElement(name = "application", type = ApplicationNode.class) })
    private final List<AbstractApplicationNode> allApplications;

    public ApplicationNodeContainer() {
        this.allApplications = new ArrayList<>();
    }

    public List<ApplicationLinkNode> getApplicationLinks() {
        return allApplications.stream().filter(ApplicationLinkNode.class::isInstance)
                .map(ApplicationLinkNode.class::cast).collect(Collectors.toList());
    }

    public List<ApplicationNode> getApplications() {
        return allApplications.stream().filter(ApplicationNode.class::isInstance).map(ApplicationNode.class::cast)
                .collect(Collectors.toList());
    }

    public List<AbstractApplicationNode> getAllApplications() {
        return allApplications;
    }

    public void addApplication(AbstractApplicationNode application) {
        allApplications.add(application);
    }
}
