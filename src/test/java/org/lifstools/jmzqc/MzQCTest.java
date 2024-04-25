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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.networknt.schema.ValidationMessage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.OffsetDateTime;
import static java.util.Arrays.asList;
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

    private final String baseUrl = "https://raw.githubusercontent.com/HUPO-PSI/mzQC/main/specification_documents/examples/";

    @Test
    public void testReadMetaboBatchesExample() throws IOException {
        URL u = new URL(baseUrl + "metabo-batches.mzQC");
        MzQC c = Converter.of(u);
        Set<ValidationMessage> messages = Converter.validate(u);
        assertTrue(messages.isEmpty());
        assertNotNull(c);
        assertEquals(226, c.runQualities().size());
        assertEquals(226, c.runQualities().stream().map(BaseQuality::qualityMetrics).filter((qm) -> qm != null).mapToInt(List::size).sum());
        assertEquals(2, c.controlledVocabularies().size());
    }

    @Test
    public void testReadQC2SampleExample() throws IOException {
        URL u = new URL(baseUrl + "QC2-sample-example.mzQC");
        Set<ValidationMessage> messages = Converter.validate(u);
        assertTrue(messages.isEmpty());
        MzQC d = Converter.of(u);
        assertNotNull(d);
        assertEquals(1, d.runQualities().size());
        assertEquals(6, d.runQualities().stream().map(BaseQuality::qualityMetrics).filter((qm) -> qm != null).mapToInt(List::size).sum());
        assertEquals(6, d.getRunQualityMetrics(0).size());
        assertEquals(1, d.getRunQualityMetricsByAccession(0, "MS:1003251").size());
        assertEquals(0, d.setQualities().size());
        assertEquals(1, d.controlledVocabularies().size());
    }

    @Test
    public void testWriteQC2SampleExample() throws IOException {
        URL u = new URL(baseUrl + "QC2-sample-example.mzQC");
        MzQC d = Converter.of(u);
        Set<ValidationMessage> messages = Converter.validate(u);
        assertTrue(messages.isEmpty());
        assertNotNull(d);
        assertEquals(1, d.runQualities().size());
        assertEquals(0, d.setQualities().size());
        assertEquals(1, d.controlledVocabularies().size());
        File testFile = File.createTempFile("QC2-sample-example", ".mzQC");
        File writtenFile = Converter.toJsonFile(d, testFile);
        Set<ValidationMessage> messages2 = Converter.validate(writtenFile);
        assertTrue(messages2.isEmpty());
        MzQC e = Converter.of(writtenFile);
        assertEquals(d.runQualities().size(), e.runQualities().size());
        assertEquals(d.setQualities().size(), e.setQualities().size());
        assertEquals(d.controlledVocabularies().size(), e.controlledVocabularies().size());
    }

    @Test
    public void testReadMultiRunExample() throws IOException {
        URL u = new URL(baseUrl + "set-of-runs.mzQC");
        MzQC c = Converter.of(u);
        Set<ValidationMessage> messages = Converter.validate(u);
        System.out.println(messages);
        assertTrue(messages.isEmpty());
        assertNotNull(c);
        assertEquals(0, c.runQualities().size());
        assertEquals(3, c.setQualities().size());
        assertEquals(4, c.setQualities().stream().map(BaseQuality::qualityMetrics).filter((qm) -> qm != null).mapToInt(List::size).sum());
        assertEquals(2, c.controlledVocabularies().size());
    }

    @Test
    public void testReadIntroRunExample() throws IOException {
        URL u = new URL(baseUrl + "intro_run.mzQC");
        MzQC c = Converter.of(u);
        Set<ValidationMessage> messages = Converter.validate(u);
        assertTrue(messages.isEmpty());
        assertNotNull(c);
        assertEquals(1, c.runQualities().size());
        assertEquals(5, c.runQualities().stream().map(BaseQuality::qualityMetrics).filter((qm) -> qm != null).mapToInt(List::size).sum());
        assertEquals(1, c.controlledVocabularies().size());
    }

    @Test
    public void testMtb120Example() throws IOException {
        URL u = new URL(baseUrl + "Mtb-120-outlier-metrics.mzQC");
        Set<ValidationMessage> messages = Converter.validate(u);
        assertTrue(messages.isEmpty());
        MzQC c = Converter.of(u);
        assertNotNull(c);
        assertEquals(120, c.runQualities().size());
        assertEquals(2040, c.runQualities().stream().map(BaseQuality::qualityMetrics).filter((qm) -> qm != null).mapToInt(List::size).sum());
        assertEquals(0, c.setQualities().size());
        assertEquals(2, c.controlledVocabularies().size());
    }

    @Test
    public void testMtb120LocalExample() throws IOException {
        URL u = new URL(baseUrl + "Mtb-120-outlier-metrics.mzQC");
        Set<ValidationMessage> messages = Converter.validate(u);
        assertTrue(messages.isEmpty());
        MzQC c = Converter.of(u);
        assertNotNull(c);
        assertEquals(120, c.runQualities().size());
        assertEquals(2040, c.runQualities().stream().map(BaseQuality::qualityMetrics).filter((qm) -> qm != null).mapToInt(List::size).sum());
        assertEquals(0, c.setQualities().size());
        assertEquals(2, c.controlledVocabularies().size());
    }

    @Test
    public void testCreateMzQCFile() throws URISyntaxException, JsonProcessingException, IOException {
        var inputFileCvParams = asList(new CvParameter("MS:1000747", null, "completion time", "2017-12-08-T15:38:57Z"));
        var infi = new InputFile(new CvParameter("MS:1000584", null, "mzML format", null), inputFileCvParams, new URI("file:///dev/null"), "file.raw");
        var anso = new AnalysisSoftware("QC:9999999", null, "bigwhopqc", null, new URI("file:///dev/null"), "1.2.3");//   # isn't requiring a uri a bit too much?
        var meta = new Metadata(asList(anso), null, asList(infi), "test_metadata");
        var qm = new QualityMetric("QC:4000053", null, "RT duration", 99, null);
        var rq = new BaseQuality(meta, asList(qm));
        var sq = new BaseQuality(meta, asList(qm));
        var cv = new ControlledVocabulary("TEST", new URI("https://www.eff.off"), null);
        var mzqc = new MzQC(
                null,
                null,
                asList(cv),
                OffsetDateTime.now(),
                "pytest-test file",
                asList(rq),
                asList(sq),
                "1.0.0");
        var messages = Converter.validate(Converter.toJsonString(mzqc));
        assertTrue(messages.isEmpty());
        var file = File.createTempFile("python-sample-example", ".mzQC");
        Converter.toJsonFile(mzqc, file);
        var messages2 = Converter.validate(file);
        assertTrue(messages2.isEmpty());
    }
}
