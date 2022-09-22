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

import com.fasterxml.jackson.annotation.*;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Root element of an mzQC file.
 */
public record MzQC(
        String contactAddress,
        String contactName,
        List<ControlledVocabulary> controlledVocabularies,
        OffsetDateTime creationDate,
        String description,
        List<BaseQuality> runQualities,
        List<BaseQuality> setQualities,
        String version) {

    public MzQC        {
        if (controlledVocabularies == null) {
            controlledVocabularies = Collections.emptyList();
        }
        if (runQualities == null) {
            runQualities = Collections.emptyList();
        }
        if (setQualities == null) {
            setQualities = Collections.emptyList();
        }
    }

    /**
     * Returns a reference to the internal list of quality metrics for the given
     * run. Modifications are reflected in the object graph.
     *
     * @param index the zero-based array index of a run.
     * @return the underlying list of qc metrics
     * @throws IllegalArgumentException if arguments are invalid
     */
    @JsonIgnore
    public List<QualityMetric> getRunQualityMetrics(int index) {
        if (index >= 0 && index < runQualities().size()) {
            return runQualities().get(index).qualityMetrics();
        }
        throw new IllegalArgumentException("Can not access run " + index);
    }

    /**
     * Returns a reference to the internal list of quality metrics for the given
     * run. Modifications are reflected in the object graph.
     *
     * @param index the zero-based array index of a run.
     * @param accession the cv accession for qc metrics to return
     * @return the underlying list of qc metrics, filtered for accession
     * @throws IllegalArgumentException if arguments are invalid
     */
    @JsonIgnore
    public List<QualityMetric> getRunQualityMetricsByAccession(int index, String accession) {
        if (index >= 0 && index < runQualities().size()) {
            return runQualities().get(index).qualityMetrics().stream().filter((qm) -> {
                return accession.equals(qm.accession());
            }).collect(Collectors.toList());
        }
        throw new IllegalArgumentException("Can not access run " + index);
    }
}
