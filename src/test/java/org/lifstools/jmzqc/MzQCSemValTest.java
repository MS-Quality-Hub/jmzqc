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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import org.apache.commons.lang3.tuple.Pair;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.lifstools.jmzqc.semantic.SemanticValidator;

/**
 *
 * @author nilshoffmann
 */
public class MzQCSemValTest {

    private final String baseUrl = "https://raw.githubusercontent.com/HUPO-PSI/mzQC/main/specification_documents/examples/";

    @Test
    public void testLoadObo() throws IOException {
        SemanticValidator sv = new SemanticValidator();
        InputStream is = SemanticValidator.class.getClassLoader().getResourceAsStream("cv/psi-ms.obo");
        sv.loadOntology(new InputStreamReader(is));
        URL u = new URL(baseUrl + "intro_qc2.mzQC");
        MzQC d = Converter.of(u);
        List<Pair<QualityMetric, Boolean>> validationResults = d.runQualities().stream().map(
                (bq) -> bq.qualityMetrics().stream().map((qm) -> Pair.of(qm, sv.validateQualityMetric(qm)))
        ).collect(Collectors.flatMapping(str -> str, toList()));
        List<Pair<QualityMetric, Boolean>> failedValidationResults = validationResults.stream().filter(pair -> !pair.getValue()).toList();
        System.out.println(failedValidationResults);
        System.out.println(sv.getValidationErrors());
        assertEquals(0, failedValidationResults.size());
    }

}
