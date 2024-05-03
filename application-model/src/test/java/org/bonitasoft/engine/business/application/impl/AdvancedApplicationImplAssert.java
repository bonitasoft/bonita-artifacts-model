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
package org.bonitasoft.engine.business.application.impl;

import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.util.Objects;

/**
 * {@link AdvancedApplicationImpl} specific assertions - Generated by CustomAssertionGenerator.
 */
public class AdvancedApplicationImplAssert
        extends AbstractObjectAssert<AdvancedApplicationImplAssert, AdvancedApplicationImpl> {

    /**
     * Creates a new <code>{@link AdvancedApplicationImplAssert}</code> to make assertions on actual AdvancedApplicationImpl.
     * 
     * @param actual the AdvancedApplicationImpl we want to make assertions on.
     */
    public AdvancedApplicationImplAssert(AdvancedApplicationImpl actual) {
        super(actual, AdvancedApplicationImplAssert.class);
    }

    /**
     * An entry point for AdvancedApplicationImplAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
     * With a static import, one can write directly: <code>assertThat(myAdvancedApplicationImpl)</code> and get specific assertion with code completion.
     * 
     * @param actual the AdvancedApplicationImpl we want to make assertions on.
     * @return a new <code>{@link AdvancedApplicationImplAssert}</code>
     */
    @org.assertj.core.util.CheckReturnValue
    public static AdvancedApplicationImplAssert assertThat(AdvancedApplicationImpl actual) {
        return new AdvancedApplicationImplAssert(actual);
    }

    /**
     * Verifies that the actual AdvancedApplicationImpl's createdBy is equal to the given one.
     * 
     * @param createdBy the given createdBy to compare the actual AdvancedApplicationImpl's createdBy to.
     * @return this assertion object.
     * @throws AssertionError - if the actual AdvancedApplicationImpl's createdBy is not equal to the given one.
     */
    public AdvancedApplicationImplAssert hasCreatedBy(long createdBy) {
        // check that actual AdvancedApplicationImpl we want to make assertions on is not null.
        isNotNull();

        // overrides the default error message with a more explicit one
        String assertjErrorMessage = "\nExpecting createdBy of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

        // check
        long actualCreatedBy = actual.getCreatedBy();
        if (actualCreatedBy != createdBy) {
            failWithMessage(assertjErrorMessage, actual, createdBy, actualCreatedBy);
        }

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AdvancedApplicationImpl's creationDate is equal to the given one.
     * 
     * @param creationDate the given creationDate to compare the actual AdvancedApplicationImpl's creationDate to.
     * @return this assertion object.
     * @throws AssertionError - if the actual AdvancedApplicationImpl's creationDate is not equal to the given one.
     */
    public AdvancedApplicationImplAssert hasCreationDate(java.util.Date creationDate) {
        // check that actual AdvancedApplicationImpl we want to make assertions on is not null.
        isNotNull();

        // overrides the default error message with a more explicit one
        String assertjErrorMessage = "\nExpecting creationDate of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

        // null safe check
        java.util.Date actualCreationDate = actual.getCreationDate();
        if (!Objects.areEqual(actualCreationDate, creationDate)) {
            failWithMessage(assertjErrorMessage, actual, creationDate, actualCreationDate);
        }

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AdvancedApplicationImpl's description is equal to the given one.
     * 
     * @param description the given description to compare the actual AdvancedApplicationImpl's description to.
     * @return this assertion object.
     * @throws AssertionError - if the actual AdvancedApplicationImpl's description is not equal to the given one.
     */
    public AdvancedApplicationImplAssert hasDescription(String description) {
        // check that actual AdvancedApplicationImpl we want to make assertions on is not null.
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
     * Verifies that the actual AdvancedApplicationImpl's displayName is equal to the given one.
     * 
     * @param displayName the given displayName to compare the actual AdvancedApplicationImpl's displayName to.
     * @return this assertion object.
     * @throws AssertionError - if the actual AdvancedApplicationImpl's displayName is not equal to the given one.
     */
    public AdvancedApplicationImplAssert hasDisplayName(String displayName) {
        // check that actual AdvancedApplicationImpl we want to make assertions on is not null.
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
     * Verifies that the actual AdvancedApplicationImpl is editable.
     * 
     * @return this assertion object.
     * @throws AssertionError - if the actual AdvancedApplicationImpl is not editable.
     */
    public AdvancedApplicationImplAssert isEditable() {
        // check that actual AdvancedApplicationImpl we want to make assertions on is not null.
        isNotNull();

        // check that property call/field access is true
        if (!actual.isEditable()) {
            failWithMessage("\nExpecting that actual AdvancedApplicationImpl is editable but is not.");
        }

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AdvancedApplicationImpl is not editable.
     * 
     * @return this assertion object.
     * @throws AssertionError - if the actual AdvancedApplicationImpl is editable.
     */
    public AdvancedApplicationImplAssert isNotEditable() {
        // check that actual AdvancedApplicationImpl we want to make assertions on is not null.
        isNotNull();

        // check that property call/field access is false
        if (actual.isEditable()) {
            failWithMessage("\nExpecting that actual AdvancedApplicationImpl is not editable but is.");
        }

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AdvancedApplicationImpl has icon.
     * 
     * @return this assertion object.
     * @throws AssertionError - if the actual AdvancedApplicationImpl does not have icon.
     */
    public AdvancedApplicationImplAssert hasIcon() {
        // check that actual AdvancedApplicationImpl we want to make assertions on is not null.
        isNotNull();

        // check that property call/field access is true
        if (!actual.hasIcon()) {
            failWithMessage("\nExpecting that actual AdvancedApplicationImpl has icon but does not have.");
        }

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AdvancedApplicationImpl does not have icon.
     * 
     * @return this assertion object.
     * @throws AssertionError - if the actual AdvancedApplicationImpl has icon.
     */
    public AdvancedApplicationImplAssert doesNotHaveIcon() {
        // check that actual AdvancedApplicationImpl we want to make assertions on is not null.
        isNotNull();

        // check that property call/field access is false
        if (actual.hasIcon()) {
            failWithMessage("\nExpecting that actual AdvancedApplicationImpl does not have icon but has.");
        }

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AdvancedApplicationImpl's iconPath is equal to the given one.
     * 
     * @param iconPath the given iconPath to compare the actual AdvancedApplicationImpl's iconPath to.
     * @return this assertion object.
     * @throws AssertionError - if the actual AdvancedApplicationImpl's iconPath is not equal to the given one.
     */
    public AdvancedApplicationImplAssert hasIconPath(String iconPath) {
        // check that actual AdvancedApplicationImpl we want to make assertions on is not null.
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
     * Verifies that the actual AdvancedApplicationImpl's id is equal to the given one.
     * 
     * @param id the given id to compare the actual AdvancedApplicationImpl's id to.
     * @return this assertion object.
     * @throws AssertionError - if the actual AdvancedApplicationImpl's id is not equal to the given one.
     */
    public AdvancedApplicationImplAssert hasId(long id) {
        // check that actual AdvancedApplicationImpl we want to make assertions on is not null.
        isNotNull();

        // overrides the default error message with a more explicit one
        String assertjErrorMessage = "\nExpecting id of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

        // check
        long actualId = actual.getId();
        if (actualId != id) {
            failWithMessage(assertjErrorMessage, actual, id, actualId);
        }

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AdvancedApplicationImpl's lastUpdateDate is equal to the given one.
     * 
     * @param lastUpdateDate the given lastUpdateDate to compare the actual AdvancedApplicationImpl's lastUpdateDate to.
     * @return this assertion object.
     * @throws AssertionError - if the actual AdvancedApplicationImpl's lastUpdateDate is not equal to the given one.
     */
    public AdvancedApplicationImplAssert hasLastUpdateDate(java.util.Date lastUpdateDate) {
        // check that actual AdvancedApplicationImpl we want to make assertions on is not null.
        isNotNull();

        // overrides the default error message with a more explicit one
        String assertjErrorMessage = "\nExpecting lastUpdateDate of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

        // null safe check
        java.util.Date actualLastUpdateDate = actual.getLastUpdateDate();
        if (!Objects.areEqual(actualLastUpdateDate, lastUpdateDate)) {
            failWithMessage(assertjErrorMessage, actual, lastUpdateDate, actualLastUpdateDate);
        }

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AdvancedApplicationImpl's profileId is equal to the given one.
     * 
     * @param profileId the given profileId to compare the actual AdvancedApplicationImpl's profileId to.
     * @return this assertion object.
     * @throws AssertionError - if the actual AdvancedApplicationImpl's profileId is not equal to the given one.
     */
    public AdvancedApplicationImplAssert hasProfileId(Long profileId) {
        // check that actual AdvancedApplicationImpl we want to make assertions on is not null.
        isNotNull();

        // overrides the default error message with a more explicit one
        String assertjErrorMessage = "\nExpecting profileId of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

        // null safe check
        Long actualProfileId = actual.getProfileId();
        if (!Objects.areEqual(actualProfileId, profileId)) {
            failWithMessage(assertjErrorMessage, actual, profileId, actualProfileId);
        }

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AdvancedApplicationImpl's state is equal to the given one.
     * 
     * @param state the given state to compare the actual AdvancedApplicationImpl's state to.
     * @return this assertion object.
     * @throws AssertionError - if the actual AdvancedApplicationImpl's state is not equal to the given one.
     */
    public AdvancedApplicationImplAssert hasState(String state) {
        // check that actual AdvancedApplicationImpl we want to make assertions on is not null.
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
     * Verifies that the actual AdvancedApplicationImpl's token is equal to the given one.
     * 
     * @param token the given token to compare the actual AdvancedApplicationImpl's token to.
     * @return this assertion object.
     * @throws AssertionError - if the actual AdvancedApplicationImpl's token is not equal to the given one.
     */
    public AdvancedApplicationImplAssert hasToken(String token) {
        // check that actual AdvancedApplicationImpl we want to make assertions on is not null.
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
     * Verifies that the actual AdvancedApplicationImpl's updatedBy is equal to the given one.
     * 
     * @param updatedBy the given updatedBy to compare the actual AdvancedApplicationImpl's updatedBy to.
     * @return this assertion object.
     * @throws AssertionError - if the actual AdvancedApplicationImpl's updatedBy is not equal to the given one.
     */
    public AdvancedApplicationImplAssert hasUpdatedBy(long updatedBy) {
        // check that actual AdvancedApplicationImpl we want to make assertions on is not null.
        isNotNull();

        // overrides the default error message with a more explicit one
        String assertjErrorMessage = "\nExpecting updatedBy of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

        // check
        long actualUpdatedBy = actual.getUpdatedBy();
        if (actualUpdatedBy != updatedBy) {
            failWithMessage(assertjErrorMessage, actual, updatedBy, actualUpdatedBy);
        }

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual AdvancedApplicationImpl's version is equal to the given one.
     * 
     * @param version the given version to compare the actual AdvancedApplicationImpl's version to.
     * @return this assertion object.
     * @throws AssertionError - if the actual AdvancedApplicationImpl's version is not equal to the given one.
     */
    public AdvancedApplicationImplAssert hasVersion(String version) {
        // check that actual AdvancedApplicationImpl we want to make assertions on is not null.
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

    /**
     * Verifies that the actual AdvancedApplicationImpl's visibility is equal to the given one.
     * 
     * @param visibility the given visibility to compare the actual AdvancedApplicationImpl's visibility to.
     * @return this assertion object.
     * @throws AssertionError - if the actual AdvancedApplicationImpl's visibility is not equal to the given one.
     */
    public AdvancedApplicationImplAssert hasVisibility(
            org.bonitasoft.engine.business.application.ApplicationVisibility visibility) {
        // check that actual AdvancedApplicationImpl we want to make assertions on is not null.
        isNotNull();

        // overrides the default error message with a more explicit one
        String assertjErrorMessage = "\nExpecting visibility of:\n  <%s>\nto be:\n  <%s>\nbut was:\n  <%s>";

        // null safe check
        org.bonitasoft.engine.business.application.ApplicationVisibility actualVisibility = actual.getVisibility();
        if (!Objects.areEqual(actualVisibility, visibility)) {
            failWithMessage(assertjErrorMessage, actual, visibility, actualVisibility);
        }

        // return the current assertion for method chaining
        return this;
    }

}