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
package uk.ac.ed.ph.jqtiplus.value;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests <code>FileValue</code> implementation of <code>equals</code> and <code>hashCode</code> methods.
 *
 * @see uk.ac.ed.ph.jqtiplus.value.FileValue
 */
@RunWith(Parameterized.class)
public class FileValueTest extends ValueTest {

    /**
     * Creates test data for this test.
     *
     * @return test data for this test
     */
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                // NullValue
                {false, ValueTestUtils.createTestFileValue("file"), NullValue.INSTANCE},
                // IdentifierValue
                {false, ValueTestUtils.createTestFileValue("file"), new IdentifierValue("identifier")},
                // BooleanValue
                {false, ValueTestUtils.createTestFileValue("file"), BooleanValue.TRUE},
                {false, ValueTestUtils.createTestFileValue("file"), BooleanValue.FALSE},
                // IntegerValue
                {false, ValueTestUtils.createTestFileValue("file"), new IntegerValue(1)},
                // FloatValue
                {false, ValueTestUtils.createTestFileValue("file"), new FloatValue(1)},
                // StringValue
                {false, ValueTestUtils.createTestFileValue("file"), new StringValue("string")},
                // PointValue
                {false, ValueTestUtils.createTestFileValue("file"), new PointValue(1, 1)},
                // PairValue
                {false, ValueTestUtils.createTestFileValue("file"), new PairValue("ident1", "ident2")},
                // DirectedPairValue
                {false, ValueTestUtils.createTestFileValue("file"), new DirectedPairValue("ident1", "ident2")},
                // DurationValue
                {false, ValueTestUtils.createTestFileValue("file"), new DurationValue(1)},
                // FileValue
                {true, ValueTestUtils.createTestFileValue("file"), ValueTestUtils.createTestFileValue("file")},
                {false, ValueTestUtils.createTestFileValue("file"), ValueTestUtils.createTestFileValue("File")},
                {false, ValueTestUtils.createTestFileValue("file"), ValueTestUtils.createTestFileValue("FILE")},
                // UriValue
                {false, ValueTestUtils.createTestFileValue("file"), new UriValue("uri")},
                // MultipleValue
                {false, ValueTestUtils.createTestFileValue("file"), MultipleValue.emptyValue()},
                {false, ValueTestUtils.createTestFileValue("file"), MultipleValue.createMultipleValue(ValueTestUtils.createTestFileValue("file"))},
                // OrderedValue
                {false, ValueTestUtils.createTestFileValue("file"), OrderedValue.emptyValue()},
                {false, ValueTestUtils.createTestFileValue("file"), OrderedValue.createOrderedValue(ValueTestUtils.createTestFileValue("file"))},
                // RecordValue
                {false, ValueTestUtils.createTestFileValue("file"), RecordValue.emptyRecord()}
        });
    }

    /**
     * Constructs this test.
     *
     * @param equals true if given values are equal; false otherwise
     * @param value1 first value
     * @param value2 second value
     */
    public FileValueTest(final boolean equals, final Value value1, final Value value2) {
        super(equals, value1, value2);
    }
}
