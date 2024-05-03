/**
 * Copyright (C) 2024 Bonitasoft S.A.
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

import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.util.Objects;

/**
 * {@link AdvancedApplicationNode} specific assertions - Generated by CustomAssertionGenerator.
 */
public class AdvancedApplicationNodeAssert
        extends AbstractObjectAssert<AdvancedApplicationNodeAssert, AdvancedApplicationNode> {

    /**
     * Creates a new <code>{@link AdvancedApplicationNodeAssert}</code> to make assertions on actual AdvancedApplicationNode.
     * 
     * @param actual the AdvancedApplicationNode we want to make assertions on.
     */
    public AdvancedApplicationNodeAssert(AdvancedApplicationNode actual) {
        super(actual, AdvancedApplicationNodeAssert.class);
    }

    /**
     * An entry point for AdvancedApplicationNodeAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
     * With a static import, one can write directly: <code>assertThat(myAdvancedApplicationNode)</code> and get specific assertion with code completion.
     * 
     * @param actual the AdvancedApplicationNode we want to make assertions on.
     * @return a new <code>{@link AdvancedApplicationNodeAssert}</code>
     */
    @org.assertj.core.util.CheckReturnValue
    public static AdvancedApplicationNodeAssert assertThat(AdvancedApplicationNode actual) {
        return new AdvancedApplicationNodeAssert(actual);
    }

    /**
     * Verifies that the actual AdvancedApplicationNode's description is equal to the given one.
     * 
     * @param description the given description to compare the actual AdvancedApplicationNode's description to.
     * @return this assertion object.
     * @throws AssertionError - if the actual AdvancedApplicationNode's description is not equal to the given one.
     */
    public AdvancedApplicationNodeAssert hasDescription(String description) {
        // check that actual AdvancedApplicationNode we want to make assertions on is not null.
        isNotNull();

        // overrides the default error message with a more explicit one
        String assertjErrorMessage = "\nExpecting description of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

        // null safe check
        String actualDescription = actual.getDescription();
        if (!Objects.areEqual(actualDescription, description)) {
            failWithMessage(assertjErrorMessage, actual, description, actualDescription);
        }

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AdvancedApplicationNode's displayName is equal to the given one.
     * 
     * @param displayName the given displayName to compare the actual AdvancedApplicationNode's displayName to.
     * @return this assertion object.
     * @throws AssertionError - if the actual AdvancedApplicationNode's displayName is not equal to the given one.
     */
    public AdvancedApplicationNodeAssert hasDisplayName(String displayName) {
        // check that actual AdvancedApplicationNode we want to make assertions on is not null.
        isNotNull();

        // overrides the default error message with a more explicit one
        String assertjErrorMessage = "\nExpecting displayName of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

        // null safe check
        String actualDisplayName = actual.getDisplayName();
        if (!Objects.areEqual(actualDisplayName, displayName)) {
            failWithMessage(assertjErrorMessage, actual, displayName, actualDisplayName);
        }

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AdvancedApplicationNode's iconPath is equal to the given one.
     * 
     * @param iconPath the given iconPath to compare the actual AdvancedApplicationNode's iconPath to.
     * @return this assertion object.
     * @throws AssertionError - if the actual AdvancedApplicationNode's iconPath is not equal to the given one.
     */
    public AdvancedApplicationNodeAssert hasIconPath(String iconPath) {
        // check that actual AdvancedApplicationNode we want to make assertions on is not null.
        isNotNull();

        // overrides the default error message with a more explicit one
        String assertjErrorMessage = "\nExpecting iconPath of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

        // null safe check
        String actualIconPath = actual.getIconPath();
        if (!Objects.areEqual(actualIconPath, iconPath)) {
            failWithMessage(assertjErrorMessage, actual, iconPath, actualIconPath);
        }

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AdvancedApplicationNode's profile is equal to the given one.
     * 
     * @param profile the given profile to compare the actual AdvancedApplicationNode's profile to.
     * @return this assertion object.
     * @throws AssertionError - if the actual AdvancedApplicationNode's profile is not equal to the given one.
     */
    public AdvancedApplicationNodeAssert hasProfile(String profile) {
        // check that actual AdvancedApplicationNode we want to make assertions on is not null.
        isNotNull();

        // overrides the default error message with a more explicit one
        String assertjErrorMessage = "\nExpecting profile of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

        // null safe check
        String actualProfile = actual.getProfile();
        if (!Objects.areEqual(actualProfile, profile)) {
            failWithMessage(assertjErrorMessage, actual, profile, actualProfile);
        }

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AdvancedApplicationNode's state is equal to the given one.
     * 
     * @param state the given state to compare the actual AdvancedApplicationNode's state to.
     * @return this assertion object.
     * @throws AssertionError - if the actual AdvancedApplicationNode's state is not equal to the given one.
     */
    public AdvancedApplicationNodeAssert hasState(String state) {
        // check that actual AdvancedApplicationNode we want to make assertions on is not null.
        isNotNull();

        // overrides the default error message with a more explicit one
        String assertjErrorMessage = "\nExpecting state of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

        // null safe check
        String actualState = actual.getState();
        if (!Objects.areEqual(actualState, state)) {
            failWithMessage(assertjErrorMessage, actual, state, actualState);
        }

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AdvancedApplicationNode's token is equal to the given one.
     * 
     * @param token the given token to compare the actual AdvancedApplicationNode's token to.
     * @return this assertion object.
     * @throws AssertionError - if the actual AdvancedApplicationNode's token is not equal to the given one.
     */
    public AdvancedApplicationNodeAssert hasToken(String token) {
        // check that actual AdvancedApplicationNode we want to make assertions on is not null.
        isNotNull();

        // overrides the default error message with a more explicit one
        String assertjErrorMessage = "\nExpecting token of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

        // null safe check
        String actualToken = actual.getToken();
        if (!Objects.areEqual(actualToken, token)) {
            failWithMessage(assertjErrorMessage, actual, token, actualToken);
        }

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AdvancedApplicationNode's version is equal to the given one.
     * 
     * @param version the given version to compare the actual AdvancedApplicationNode's version to.
     * @return this assertion object.
     * @throws AssertionError - if the actual AdvancedApplicationNode's version is not equal to the given one.
     */
    public AdvancedApplicationNodeAssert hasVersion(String version) {
        // check that actual AdvancedApplicationNode we want to make assertions on is not null.
        isNotNull();

        // overrides the default error message with a more explicit one
        String assertjErrorMessage = "\nExpecting version of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

        // null safe check
        String actualVersion = actual.getVersion();
        if (!Objects.areEqual(actualVersion, version)) {
            failWithMessage(assertjErrorMessage, actual, version, actualVersion);
        }

        // return the current assertion for method chaining
        return this;
    }

}