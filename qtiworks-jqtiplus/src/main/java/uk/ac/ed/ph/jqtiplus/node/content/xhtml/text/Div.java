/* Copyright (c) 2012-2013, University of Edinburgh.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice, this
 *   list of conditions and the following disclaimer in the documentation and/or
 *   other materials provided with the distribution.
 *
 * * Neither the name of the University of Edinburgh nor the names of its
 *   contributors may be used to endorse or promote products derived from this
 *   software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *
 * This software is derived from (and contains code from) QTItools and MathAssessEngine.
 * QTItools is (c) 2008, University of Southampton.
 * MathAssessEngine is (c) 2010, University of Edinburgh.
 */
package uk.ac.ed.ph.jqtiplus.node.content.xhtml.text;

import uk.ac.ed.ph.jqtiplus.attribute.value.StringAttribute;
import uk.ac.ed.ph.jqtiplus.group.content.FlowGroup;
import uk.ac.ed.ph.jqtiplus.node.QtiNode;
import uk.ac.ed.ph.jqtiplus.node.content.basic.AbstractFlowBodyElement;
import uk.ac.ed.ph.jqtiplus.node.content.basic.BlockStatic;
import uk.ac.ed.ph.jqtiplus.node.content.basic.Flow;
import uk.ac.ed.ph.jqtiplus.node.content.basic.FlowStatic;

import java.util.List;

/**
 * div
 *
 * @author Jonathon Hare
 */
public final class Div extends AbstractFlowBodyElement implements BlockStatic, FlowStatic {

    private static final long serialVersionUID = -3635119989161671687L;

    /** Name of this class in xml schema. */
    public static final String QTI_CLASS_NAME = "div";

    /** Name of datatype attribute in xml schema. */
    public static final String ATTR_DATA_TYPE_NAME = "data-type";

    public Div(final QtiNode parent) {
        super(parent, QTI_CLASS_NAME);

        getNodeGroups().add(new FlowGroup(this));
        
        getAttributes().add(new StringAttribute(this, ATTR_DATA_TYPE_NAME, false));
    }

    public List<Flow> getFlows() {
        return getNodeGroups().getFlowGroup().getFlows();
    }

    /**
     * Gets value of data-type attribute.
     *
     * @return value of data-type attribute
     */
	public String getDataType() {
        return getAttributes().getStringAttribute(ATTR_DATA_TYPE_NAME).getComputedValue();
	}
    
    /**
     * Sets new value of data-type attribute.
     *
     * @param data-type new value of data-type attribute
     */
    public void setDataType(final String datatype) {
        getAttributes().getStringAttribute(ATTR_DATA_TYPE_NAME).setValue(datatype);
    }

}
