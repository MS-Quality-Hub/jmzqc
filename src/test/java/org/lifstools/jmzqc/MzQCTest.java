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

import com.networknt.schema.ValidationMessage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author nilshoffmann
 */
public class MzQCTest {
    private final String baseUrl = "https://raw.githubusercontent.com/HUPO-PSI/mzQC/main/specification_documents/draft_v1/examples/";

    @Test
    public void testReadMetaboBatchesExample() throws IOException {
        URL u = new URL(baseUrl + "metabo-batches.mzQC");
        Coordinate c = Converter.of(u);
        Set<ValidationMessage> messages = Converter.validate(u);
        assertTrue(messages.isEmpty());
        assertNotNull(c);
        assertEquals(226, c.getMzQC().getRunQualities().size());
        assertEquals(226, c.getMzQC().getRunQualities().stream().map(BaseQuality::getQualityMetrics).filter((qm) -> qm != null).mapToInt(List::size).sum());
        assertEquals(2, c.getMzQC().getControlledVocabularies().size());
    }

    @Test
    public void testReadQC2SampleExample() throws IOException {
        URL u = new URL(baseUrl + "QC2-sample-example.mzQC");
        Set<ValidationMessage> messages = Converter.validate(u);
        assertTrue(messages.isEmpty());
        Coordinate d = Converter.of(u);
        assertNotNull(d);
        assertEquals(1, d.getMzQC().getRunQualities().size());
        assertEquals(6, d.getMzQC().getRunQualities().stream().map(BaseQuality::getQualityMetrics).filter((qm) -> qm != null).mapToInt(List::size).sum());
        assertEquals(6, d.getMzQC().getRunQualityMetrics(0).size());
        assertEquals(1, d.getMzQC().getRunQualityMetricsByAccession(0, "MS:1003251").size());
        assertEquals(0, d.getMzQC().getSetQualities().size());
        assertEquals(1, d.getMzQC().getControlledVocabularies().size());
    }

    @Test
    public void testWriteQC2SampleExample() throws IOException {
        URL u = new URL(baseUrl + "QC2-sample-example.mzQC");
        Coordinate d = Converter.of(u);
        Set<ValidationMessage> messages = Converter.validate(u);
        assertTrue(messages.isEmpty());
        assertNotNull(d);
        assertEquals(1, d.getMzQC().getRunQualities().size());
        assertEquals(0, d.getMzQC().getSetQualities().size());
        assertEquals(1, d.getMzQC().getControlledVocabularies().size());
        File testFile = File.createTempFile("QC2-sample-example", ".mzQC");
        File writtenFile = Converter.toJsonFile(d, testFile);
        Set<ValidationMessage> messages2 = Converter.validate(writtenFile);
        assertTrue(messages2.isEmpty());
        Coordinate e = Converter.of(writtenFile);
        assertEquals(d.getMzQC().getRunQualities().size(), e.getMzQC().getRunQualities().size());
        assertEquals(d.getMzQC().getSetQualities().size(), e.getMzQC().getSetQualities().size());
        assertEquals(d.getMzQC().getControlledVocabularies().size(), e.getMzQC().getControlledVocabularies().size());
    }

    @Test
    public void testReadMultiRunExample() throws IOException {
        URL u = new URL(baseUrl + "set-of-runs.mzQC");
        Coordinate c = Converter.of(u);
        Set<ValidationMessage> messages = Converter.validate(u); 
        System.out.println(messages);
        assertTrue(messages.isEmpty());
        assertNotNull(c);
        assertEquals(0, c.getMzQC().getRunQualities().size());
        assertEquals(3, c.getMzQC().getSetQualities().size());
        assertEquals(4, c.getMzQC().getSetQualities().stream().map(BaseQuality::getQualityMetrics).filter((qm) -> qm != null).mapToInt(List::size).sum());
        assertEquals(2, c.getMzQC().getControlledVocabularies().size());
    }

    @Test
    public void testReadSingleRunExample() throws IOException {
        URL u = new URL(baseUrl + "individual-runs.mzQC");
        Coordinate c = Converter.of(u);
        Set<ValidationMessage> messages = Converter.validate(u);
        assertTrue(messages.isEmpty());
        assertNotNull(c);
        assertEquals(1, c.getMzQC().getRunQualities().size());
        assertEquals(5, c.getMzQC().getRunQualities().stream().map(BaseQuality::getQualityMetrics).filter((qm) -> qm != null).mapToInt(List::size).sum());
        assertEquals(1, c.getMzQC().getControlledVocabularies().size());
    }

    @Test
    public void testMtb120Example() throws IOException {
        URL u = new URL(baseUrl + "Mtb-120-outlier-metrics.mzQC");
        Set<ValidationMessage> messages = Converter.validate(u);
        assertTrue(messages.isEmpty());
        Coordinate c = Converter.of(u);
        assertNotNull(c);
        assertEquals(120, c.getMzQC().getRunQualities().size());
        assertEquals(2040, c.getMzQC().getRunQualities().stream().map(BaseQuality::getQualityMetrics).filter((qm) -> qm != null).mapToInt(List::size).sum());
        assertEquals(0, c.getMzQC().getSetQualities().size());
        assertEquals(2, c.getMzQC().getControlledVocabularies().size());
    }
    
    @Test
    public void testMtb120LocalExample() throws IOException {
        URL u = new URL(baseUrl + "Mtb-120-outlier-metrics.mzQC");
        Set<ValidationMessage> messages = Converter.validate(u);
        assertTrue(messages.isEmpty());
        Coordinate c = Converter.of(u);
        assertNotNull(c);
        assertEquals(120, c.getMzQC().getRunQualities().size());
        assertEquals(2040, c.getMzQC().getRunQualities().stream().map(BaseQuality::getQualityMetrics).filter((qm) -> qm != null).mapToInt(List::size).sum());
        assertEquals(0, c.getMzQC().getSetQualities().size());
        assertEquals(2, c.getMzQC().getControlledVocabularies().size());
    }
}
