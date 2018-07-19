/*
 * (c) Kitodo. Key to digital objects e. V. <contact@kitodo.org>
 *
 * This file is part of the Kitodo project.
 *
 * It is licensed under GNU General Public License version 3 or later.
 *
 * For the full copyright and license information, please read the
 * GPL3-License.txt file that was distributed with this source code.
 */

package org.kitodo.dto;

import java.io.InputStream;

public class TemplateDTO extends BaseTemplateDTO {

    private InputStream diagramImage;
    private WorkflowDTO workflow;
    private boolean containsUnreachableSteps;

    /**
     * Get diagram image.
     *
     * @return value of diagramImage
     */
    public InputStream getDiagramImage() {
        return diagramImage;
    }

    /**
     * Set diagram image.
     *
     * @param diagramImage as java.io.InputStream
     */
    public void setDiagramImage(InputStream diagramImage) {
        this.diagramImage = diagramImage;
    }

    /**
     * Set workflow.
     *
     * @param workflow as org.kitodo.dto.WorkflowDTO
     */
    public void setWorkflow(WorkflowDTO workflow) {
        this.workflow = workflow;
    }

    /**
     * Get workflow.
     *
     * @return value of workflow
     */
    public WorkflowDTO getWorkflow() {
        return workflow;
    }

    /**
     * Get information if process contains unreachable tasks.
     *
     * @return true or false
     */
    public boolean isContainsUnreachableSteps() {
        return containsUnreachableSteps;
    }

    /**
     * Set information if process contains unreachable tasks.
     *
     * @param containsUnreachableSteps
     *            as boolean
     */
    public void setContainsUnreachableSteps(boolean containsUnreachableSteps) {
        this.containsUnreachableSteps = containsUnreachableSteps;
    }
}
