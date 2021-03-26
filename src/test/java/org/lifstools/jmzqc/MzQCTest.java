/* 
 * Copyright 2021 Nils Hoffmann.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lifstools.jmzqc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

/**
 *
 * @author nilshoffmann
 */
public class MzQCTest {

    @Test
    public void testReadMetaboBatchesExample() throws IOException {
        Coordinate c = Converter.fromJsonString(new String(getClass().getClassLoader().getResourceAsStream("examples/metabo-batches.mzQC").readAllBytes(), StandardCharsets.UTF_8));
        assertNotNull(c);
        assertEquals(226, c.getMzQC().getRunQualities().size());
        assertEquals(2, c.getMzQC().getControlledVocabularies().size());
    }

    @Test
    public void testReadQC2SampleExample() throws IOException {
        String url = "https://raw.githubusercontent.com/HUPO-PSI/mzQC/master/doc/examples/QC2-sample-example.mzQC";
        Coordinate d = Converter.fromUrlString(url);
        assertNotNull(d);
        assertEquals(1, d.getMzQC().getRunQualities().size());
        assertEquals(0, d.getMzQC().getSetQualities().size());
        assertEquals(2, d.getMzQC().getControlledVocabularies().size());

        Coordinate c = Converter.fromJsonString(new String(getClass().getClassLoader().getResourceAsStream("examples/QC2-sample-example.mzQC").readAllBytes(), StandardCharsets.UTF_8));
        assertNotNull(c);
        assertEquals(1, c.getMzQC().getRunQualities().size());
        assertEquals(0, c.getMzQC().getSetQualities().size());
        assertEquals(2, c.getMzQC().getControlledVocabularies().size());
    }

    @Test
    public void testReadMultiRunExample() throws IOException {
        Coordinate c = Converter.fromJsonString(new String(getClass().getClassLoader().getResourceAsStream("examples/multi-run.mzQC").readAllBytes(), StandardCharsets.UTF_8));
        assertNotNull(c);
        assertEquals(0, c.getMzQC().getRunQualities().size());
        assertEquals(1, c.getMzQC().getSetQualities().size());
        assertEquals(2, c.getMzQC().getControlledVocabularies().size());
    }

    @Test
    public void testReadSingleRunExample() throws IOException {
        Coordinate c = Converter.fromJsonString(new String(getClass().getClassLoader().getResourceAsStream("examples/single-run.mzQC").readAllBytes(), StandardCharsets.UTF_8));
        assertNotNull(c);
        assertEquals(1, c.getMzQC().getRunQualities().size());
        assertEquals(2, c.getMzQC().getControlledVocabularies().size());
    }
}
