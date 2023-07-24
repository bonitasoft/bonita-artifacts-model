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
package org.bonitasoft.engine.form;

import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;

import org.bonitasoft.engine.bpm.BaseElement;

/**
 * @author Baptiste Mesta
 */
public class FormMapping implements BaseElement {

    private static final long serialVersionUID = 1L;

    private long id;
    private long processDefinitionId;
    private FormMappingType type;
    private FormMappingTarget target;
    private String task;
    private Long pageId;
    private String pageURL;
    private String pageMappingKey;
    private long lastUpdatedBy;
    private Date lastUpdateDate;
    private boolean formRequired;

    public FormMapping() {
        // required by JAXB
    }

    public FormMapping(long processDefinitionId, FormMappingType type, String task, String pageMappingKey) {
        this.processDefinitionId = processDefinitionId;
        this.type = type;
        this.task = task;
        this.pageMappingKey = pageMappingKey;
    }

    public FormMapping(long processDefinitionId, FormMappingType type, String pageMappingKey) {
        this.type = type;
        this.processDefinitionId = processDefinitionId;
        this.pageMappingKey = pageMappingKey;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(long processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public FormMappingType getType() {
        return type;
    }

    public void setType(FormMappingType type) {
        this.type = type;
    }

    public FormMappingTarget getTarget() {
        return target;
    }

    public void setTarget(FormMappingTarget target) {
        this.target = target;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public String getURL() {
        return pageURL;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    public String getPageMappingKey() {
        return pageMappingKey;
    }

    public void setPageMappingKey(String pageMappingKey) {
        this.pageMappingKey = pageMappingKey;
    }

    public long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public boolean isFormRequired() {
        return formRequired;
    }

    public void setFormRequired(boolean formRequired) {
        this.formRequired = formRequired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        FormMapping that = (FormMapping) o;
        return id == that.id && processDefinitionId == that.processDefinitionId && lastUpdatedBy == that.lastUpdatedBy
                && type == that.type && target == that.target && Objects.equals(task, that.task)
                && Objects.equals(pageId, that.pageId) && Objects.equals(pageURL, that.pageURL)
                && Objects.equals(pageMappingKey, that.pageMappingKey)
                && Objects.equals(lastUpdateDate, that.lastUpdateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, processDefinitionId, type, target, task, pageId, pageURL, pageMappingKey, lastUpdatedBy,
                lastUpdateDate);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FormMapping.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("processDefinitionId=" + processDefinitionId)
                .add("type=" + type)
                .add("target=" + target)
                .add("task='" + task + "'")
                .add("pageId=" + pageId)
                .add("pageURL='" + pageURL + "'")
                .add("pageMappingKey='" + pageMappingKey + "'")
                .add("lastUpdatedBy=" + lastUpdatedBy)
                .add("lastUpdateDate=" + lastUpdateDate)
                .toString();
    }
}
