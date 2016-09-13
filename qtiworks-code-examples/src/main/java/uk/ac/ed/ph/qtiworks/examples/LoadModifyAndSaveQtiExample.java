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
 * This software is derived from (and contains code from) QTITools and MathAssessEngine.
 * QTITools is (c) 2008, University of Southampton.
 * MathAssessEngine is (c) 2010, University of Edinburgh.
 */
package uk.ac.ed.ph.qtiworks.examples;

import uk.ac.ed.ph.jqtiplus.JqtiExtensionManager;
import uk.ac.ed.ph.jqtiplus.SimpleJqtiFacade;
import uk.ac.ed.ph.jqtiplus.exception.QtiModelException;
import uk.ac.ed.ph.jqtiplus.node.LoadingContext;
import uk.ac.ed.ph.jqtiplus.node.content.ItemBody;
import uk.ac.ed.ph.jqtiplus.node.content.basic.TextRun;
import uk.ac.ed.ph.jqtiplus.node.content.xhtml.text.Div;
import uk.ac.ed.ph.jqtiplus.node.content.xhtml.text.P;
import uk.ac.ed.ph.jqtiplus.node.content.xhtml.video.Video;
import uk.ac.ed.ph.jqtiplus.node.item.AssessmentItem;
import uk.ac.ed.ph.jqtiplus.node.item.CorrectResponse;
import uk.ac.ed.ph.jqtiplus.node.item.interaction.ChoiceInteraction;
import uk.ac.ed.ph.jqtiplus.node.item.interaction.Prompt;
import uk.ac.ed.ph.jqtiplus.node.item.interaction.choice.SimpleChoice;
import uk.ac.ed.ph.jqtiplus.node.item.response.declaration.ResponseDeclaration;
import uk.ac.ed.ph.jqtiplus.node.item.response.processing.ResponseProcessing;
import uk.ac.ed.ph.jqtiplus.node.shared.FieldValue;
import uk.ac.ed.ph.jqtiplus.reading.QtiModelBuildingError;
import uk.ac.ed.ph.jqtiplus.reading.QtiObjectReadResult;
import uk.ac.ed.ph.jqtiplus.reading.QtiXmlInterpretationException;
import uk.ac.ed.ph.jqtiplus.reading.QtiXmlReader;
import uk.ac.ed.ph.jqtiplus.serialization.QtiSerializer;
import uk.ac.ed.ph.jqtiplus.types.Identifier;
import uk.ac.ed.ph.jqtiplus.value.BaseType;
import uk.ac.ed.ph.jqtiplus.value.Cardinality;
import uk.ac.ed.ph.jqtiplus.value.IdentifierValue;
import uk.ac.ed.ph.jqtiplus.xmlutils.XmlResourceNotFoundException;
import uk.ac.ed.ph.jqtiplus.xmlutils.XmlResourceReader;
import uk.ac.ed.ph.jqtiplus.xmlutils.locators.ClassPathResourceLocator;
import uk.ac.ed.ph.jqtiplus.xmlutils.locators.ResourceLocator;

import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 * This example builds a JQTI+ Object model from a bundled <code>minimal.xml</code> QTI XML file,
 * then uses the JQTI+ to enhance the object model by adding a responseDeclaration and a choiceInteraction.
 * The resulting model is then serialized to XML and printed out.
 *
 * <h3>How to run</h3>
 *
 * You can run this via Maven as follows:
 * <pre>
 * mvn exec:java -Dexec.mainClass=uk.ac.ed.ph.qtiworks.examples.LoadModifyAndSaveQtiExample2
 * </pre>
 * You should also be able to run this inside your favourite IDE if you have loaded the QTIWorks
 * source code into it.
 *
 * @see LoadModifyAndSaveQtiExample2 for another version of this example using lower-level APIs
 *
 * @author David McKain
 */
public final class LoadModifyAndSaveQtiExample {

	public static void main(final String[] args) throws Exception {
        /* We'll be loading a bundled example file called minimal.xml, which you can find
         * in src/main/resources and is included in the ClassPath when this project is built.
         * We use a ClassPathResourceLocator to load this, using the <code>classpath:</code>
         * pseudo-URI.
         */
        final ResourceLocator inputResourceLocator = new ClassPathResourceLocator();
        final URI inputUri = URI.create("classpath:/minimal.xml");

        /* Load the QTI XML, perform schema validation, and build a JQTI+ Object model from it,
         * expecting an AssessmentItem.
         *
         * (We're going to use SimpleJqtiFacade here)
         */
        final SimpleJqtiFacade simpleJqtiFacade = new SimpleJqtiFacade();
        QtiObjectReadResult<AssessmentItem> readResult;
        try {
            readResult = simpleJqtiFacade.readQtiRootNode(inputResourceLocator, inputUri,
                    true, /* = perform schema validation */
                    AssessmentItem.class);
        }
        catch (final XmlResourceNotFoundException e) {
            /* This Exception will be thrown the example file could not be found
             * using the ResourceLocator we set up above.
             *
             * This should not happen if the ClassPath is set up properly, so I'll let this
             * Exception propagate upwards here.
             */
            throw e;
        }
        catch (final QtiXmlInterpretationException e) {
            /* This is thrown if a JQTI+ Object model could not be constructed from the QTI XML,
             * or the resulting model wasn't an AssessmentItem.
             *
             * This shouldn't happen here as the XML we've loaded should be good,
             * so I'll propagate this one up.
             */
            throw e;
        }

        /* We can now extract the built AssessmentItem Object */
        final AssessmentItem assessmentItem = readResult.getRootNode();

        /* Add a new response declaration called RESPONSE.
         *
         * (The process for adding nodes into the Object model is slightly asymmetric, which I
         * now think I should have done differently! If you're adding a "child" of type C to a
         * "parent" p of type P then you'll generally have to do something like:
         *
         * C c = new C(p);
         * p.getSomeGroupOfChildNodes().add(c);
         */
        final ResponseDeclaration responseDeclaration = new ResponseDeclaration(assessmentItem);
        assessmentItem.getResponseDeclarations().add(responseDeclaration);
        responseDeclaration.setIdentifier(Identifier.assumedLegal("RESPONSE"));
        responseDeclaration.setCardinality(Cardinality.SINGLE);
        responseDeclaration.setBaseType(BaseType.IDENTIFIER);
        final CorrectResponse correctResponse = new CorrectResponse(responseDeclaration);
        responseDeclaration.setCorrectResponse(correctResponse);
        correctResponse.getFieldValues().add(new FieldValue(correctResponse, new IdentifierValue("CHOICEA")));

        /* Add a choiceInteraction with a prompt and 2 simpleChoices,
         * linked to the RESPONSE variable we declared above */
        final ItemBody itemBody = assessmentItem.getItemBody();
        
        Div div = new Div(itemBody);
        div.setDataType("asdf");
        itemBody.getBlocks().add(div);
        
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();

		String xmlString = "<c><p>dgf<span>dfg</span>dfg</p></c>";
		Document doc = builder.parse(new InputSource(new StringReader(xmlString)));

        final LoadingContext loadingContext = new LoadingContext() {
        	QtiXmlReader qtiXmlReader = new QtiXmlReader(); 

        	@Override
			public JqtiExtensionManager getJqtiExtensionManager() {
				return qtiXmlReader.getJqtiExtensionManager();
			}
			@Override
			public void modelBuildingError(QtiModelException exception, Node badNode) {
			}
        };
        div.load(doc.getDocumentElement(), loadingContext);
        
        Video video = new Video(itemBody);
        video.setSrc(new URI("aaa"));
        video.setWidth("100%");
        itemBody.getBlocks().add(video);
        
        final ChoiceInteraction choiceInteraction = new ChoiceInteraction(itemBody);
        itemBody.getBlocks().add(choiceInteraction);
        choiceInteraction.setResponseIdentifier(responseDeclaration.getIdentifier());
        choiceInteraction.setShuffle(true);
        choiceInteraction.setMaxChoices(1);
        final Prompt prompt = new Prompt(choiceInteraction);
        choiceInteraction.setPrompt(prompt);
        prompt.getInlineStatics().add(new TextRun(prompt, "Pick the correct answer"));
        final SimpleChoice simpleChoice1 = new SimpleChoice(choiceInteraction);
        simpleChoice1.setIdentifier(Identifier.assumedLegal("CHOICE1"));
        simpleChoice1.getFlowStatics().add(new TextRun(simpleChoice1, "Choice 1"));
        choiceInteraction.getSimpleChoices().add(simpleChoice1);
        final SimpleChoice simpleChoice2 = new SimpleChoice(choiceInteraction);
        simpleChoice2.setIdentifier(Identifier.assumedLegal("CHOICE2"));
        simpleChoice2.getFlowStatics().add(new TextRun(simpleChoice1, "Choice 2"));
        choiceInteraction.getSimpleChoices().add(simpleChoice2);

        /* We'll add a reponseProcessing as well, using one of the default templates */
        final ResponseProcessing responseProcessing = new ResponseProcessing(assessmentItem);
        responseProcessing.setTemplate(URI.create("http://www.imsglobal.org/question/qti_v2p1/rptemplates/match_correct"));
        assessmentItem.setResponseProcessing(responseProcessing);

        /* Finally we serialize the updated assessmentItem to an XML string and print it out */
        final QtiSerializer qtiSerializer = simpleJqtiFacade.createQtiSerializer();
        System.out.println(qtiSerializer.serializeJqtiObject(assessmentItem));
    }

}
