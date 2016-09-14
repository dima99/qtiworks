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
package uk.ac.ed.ph.jqtiplus.node.item.interaction.content;

import java.util.List;

import uk.ac.ed.ph.jqtiplus.group.content.TextOrVariableGroup;
import uk.ac.ed.ph.jqtiplus.node.QtiNode;
import uk.ac.ed.ph.jqtiplus.node.content.basic.InlineStatic;
import uk.ac.ed.ph.jqtiplus.node.content.variable.TextOrVariable;
import uk.ac.ed.ph.jqtiplus.node.item.interaction.choice.AssociableChoice;

/**
 * gap is an inlineStatic element that must only appear within a
 * gapMatchInteraction.
 * Attribute : required [0..1]: boolean = false
 * If true then this gap must be filled by the candidate in order
 * to form a valid response to the interaction.
 *
 * @author Jonathon Hare
 */
public final class GapXML extends AssociableChoice implements InlineStatic {

    private static final long serialVersionUID = 8587068031186931244L;

    /** Name of this class in xml schema. */
    public static final String QTI_CLASS_NAME = "gap";

    public GapXML(final QtiNode parent) {
        super(parent, QTI_CLASS_NAME);

        getNodeGroups().add(new TextOrVariableGroup(this));
    }

    public List<TextOrVariable> getTextOrVariables() {
        return getNodeGroups().getTextOrVariableGroup().getTextOrVariables();
    }
}
