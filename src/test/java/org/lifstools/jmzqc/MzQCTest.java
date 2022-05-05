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

import java.io.File;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

/**
 *
 * @author nilshoffmann
 */
public class MzQCTest {

    private final String baseUrl = "https://raw.githubusercontent.com/HUPO-PSI/mzQC/main/doc/examples/";

    @Test
    public void testReadMetaboBatchesExample() throws IOException {
        Coordinate c = Converter.fromUrlString(baseUrl + "metabo-batches.mzQC");
        assertNotNull(c);
        assertEquals(226, c.getMzQC().getRunQualities().size());
        assertEquals(226, c.getMzQC().getRunQualities().stream().map(BaseQuality::getQualityMetrics).filter((qm) -> qm!=null).mapToInt(List::size).sum());
        assertEquals(2, c.getMzQC().getControlledVocabularies().size());
    }

    @Test
    public void testReadQC2SampleExample() throws IOException {
        Coordinate d = Converter.fromUrlString(baseUrl + "QC2-sample-example.mzQC");
        assertNotNull(d);
        assertEquals(1, d.getMzQC().getRunQualities().size());
        assertEquals(6, d.getMzQC().getRunQualities().stream().map(BaseQuality::getQualityMetrics).filter((qm) -> qm!=null).mapToInt(List::size).sum());
        assertEquals(6, d.getMzQC().getRunQualityMetrics(0).size());
        assertEquals(1, d.getMzQC().getRunQualityMetricsByAccession(0, "MS:1003251").size());
        assertEquals(0, d.getMzQC().getSetQualities().size());
        assertEquals(1, d.getMzQC().getControlledVocabularies().size());
    }

    @Test
    public void testWriteQC2SampleExample() throws IOException {
        Coordinate d = Converter.fromUrlString(baseUrl + "QC2-sample-example.mzQC");
        assertNotNull(d);
        assertEquals(1, d.getMzQC().getRunQualities().size());
        assertEquals(0, d.getMzQC().getSetQualities().size());
        assertEquals(1, d.getMzQC().getControlledVocabularies().size());
        File testFile = File.createTempFile("QC2-sample-example", ".mzQC");
        File writtenFile = Converter.toJsonFile(d, testFile);
        Coordinate e = Converter.fromFile(writtenFile);
        assertEquals(d.getMzQC().getRunQualities().size(), e.getMzQC().getRunQualities().size());
        assertEquals(d.getMzQC().getSetQualities().size(), e.getMzQC().getSetQualities().size());
        assertEquals(d.getMzQC().getControlledVocabularies().size(), e.getMzQC().getControlledVocabularies().size());
    }

    @Test
    public void testReadMultiRunExample() throws IOException {
        Coordinate c = Converter.fromUrlString(baseUrl + "set-of-runs.mzQC");
        assertNotNull(c);
        assertEquals(0, c.getMzQC().getRunQualities().size());
        assertEquals(3, c.getMzQC().getSetQualities().size());
        assertEquals(4, c.getMzQC().getSetQualities().stream().map(BaseQuality::getQualityMetrics).filter((qm) -> qm!=null).mapToInt(List::size).sum());
        assertEquals(2, c.getMzQC().getControlledVocabularies().size());
    }

    @Test
    public void testReadSingleRunExample() throws IOException {
        Coordinate c = Converter.fromUrlString(baseUrl + "individual-runs.mzQC");
        assertNotNull(c);
        assertEquals(1, c.getMzQC().getRunQualities().size());
        assertEquals(5, c.getMzQC().getRunQualities().stream().map(BaseQuality::getQualityMetrics).filter((qm) -> qm!=null).mapToInt(List::size).sum());
        assertEquals(1, c.getMzQC().getControlledVocabularies().size());
    }

    @Test
    public void testMtb120Example() throws IOException {
        Coordinate c = Converter.fromUrlString(baseUrl + "Mtb-120-outlier-metrics.mzQC");
        assertNotNull(c);
        assertEquals(120, c.getMzQC().getRunQualities().size());
        assertEquals(2040, c.getMzQC().getRunQualities().stream().map(BaseQuality::getQualityMetrics).filter((qm) -> qm!=null).mapToInt(List::size).sum());
        assertEquals(0, c.getMzQC().getSetQualities().size());
        assertEquals(2, c.getMzQC().getControlledVocabularies().size());
    }
}
