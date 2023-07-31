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
package com.bonitasoft.engine.bdm.accesscontrol.builder;

import static java.util.Arrays.stream;

import com.bonitasoft.engine.bdm.accesscontrol.model.AccessRule;
import com.bonitasoft.engine.bdm.accesscontrol.model.BusinessObjectRule;

/**
 * @author Adrien
 */
public class BusinessObjectRuleBuilder {

    private final BusinessObjectRule businessObjectRule;

    public BusinessObjectRuleBuilder(String businessObjectQualifiedName) {
        this.businessObjectRule = new BusinessObjectRule();
        businessObjectRule.setBusinessObjectQualifiedName(businessObjectQualifiedName);
    }

    public static BusinessObjectRuleBuilder aBoRule(String businessObjectQualifiedName) {
        return new BusinessObjectRuleBuilder(businessObjectQualifiedName);
    }

    public BusinessObjectRuleBuilder withRules(AccessRule... rules) {
        businessObjectRule.addRules(rules);
        return this;
    }

    public BusinessObjectRuleBuilder withRules(AccessRuleBuilder... rules) {
        stream(rules).map(AccessRuleBuilder::create).forEach(businessObjectRule::addRules);
        return this;
    }

    public BusinessObjectRule create() {
        return businessObjectRule;
    }
}
