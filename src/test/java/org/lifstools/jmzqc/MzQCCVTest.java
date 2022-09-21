/*
 * Copyright 2022 Nils Hoffmann.
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.biojava.nbio.ontology.Ontology;
import org.biojava.nbio.ontology.OntologyFactory;
import org.biojava.nbio.ontology.io.OboParser;
import org.biojava.nbio.ontology.obo.OboFileHandler;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Nils Hoffmann
 */
public class MzQCCVTest {

    @Test
    public void testReadMetaboBatchesExample() throws IOException {
        URL url = MzQCCVTest.class.getResource("/cv/qc-cv.obo");
        try ( InputStream in = url.openStream()) {
            try ( BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                OboParser parser = new OboParser();
                try {
                    Ontology ontology = parser.parseOBO(br, "mzQC", "mzQC Ontology for Mass Spec QC");
                    assertTrue(ontology.containsTerm("MS:4000000"));
                    assertFalse(ontology.containsTerm("MS:2000000"));
                } catch (ParseException ex) {
                    System.err.println("Parsing exception: " + ex.getLocalizedMessage());
                }
            }

        }

    }
}
