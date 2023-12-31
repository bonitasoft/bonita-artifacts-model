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
package org.bonitasoft.engine.bpm.internal;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.bonitasoft.engine.bpm.BaseElement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Danila Mazour
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BaseDefinitionElementImpl implements BaseElement {

    private static final long serialVersionUID = 1L;

    @XmlID
    @XmlJavaTypeAdapter(type = long.class, value = LongToStringAdapter.class)
    @XmlAttribute
    private long id = generateId();

    private long generateId() {
        return Math.abs(UUID.randomUUID().getLeastSignificantBits());
    }
}
