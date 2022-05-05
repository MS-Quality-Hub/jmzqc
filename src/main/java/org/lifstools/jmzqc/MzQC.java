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
@lombok.Data
public class MzQC {

    @lombok.Getter(onMethod_ = {
        @JsonProperty("contactAddress")})
    @lombok.Setter(onMethod_ = {
        @JsonProperty("contactAddress")})
    private String contactAddress;
    @lombok.Getter(onMethod_ = {
        @JsonProperty("contactName")})
    @lombok.Setter(onMethod_ = {
        @JsonProperty("contactName")})
    private String contactName;
    @lombok.Getter(onMethod_ = {
        @JsonProperty("controlledVocabularies")})
    @lombok.Setter(onMethod_ = {
        @JsonProperty("controlledVocabularies")})
    private List<ControlledVocabulary> controlledVocabularies = Collections.emptyList();
    @lombok.Getter(onMethod_ = {
        @JsonProperty("creationDate")})
    @lombok.Setter(onMethod_ = {
        @JsonProperty("creationDate")})
    private OffsetDateTime creationDate;
    @lombok.Getter(onMethod_ = {
        @JsonProperty("description")})
    @lombok.Setter(onMethod_ = {
        @JsonProperty("description")})
    private String description;
    @lombok.Getter(onMethod_ = {
        @JsonProperty("runQualities")})
    @lombok.Setter(onMethod_ = {
        @JsonProperty("runQualities")})
    private List<BaseQuality> runQualities = Collections.emptyList();
    @lombok.Getter(onMethod_ = {
        @JsonProperty("setQualities")})
    @lombok.Setter(onMethod_ = {
        @JsonProperty("setQualities")})
    private List<BaseQuality> setQualities = Collections.emptyList();
    @lombok.Getter(onMethod_ = {
        @JsonProperty("version")})
    @lombok.Setter(onMethod_ = {
        @JsonProperty("version")})
    private String version;

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
        if (index >= 0 && index < getRunQualities().size()) {
            return getRunQualities().get(index).getQualityMetrics();
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
        if (index >= 0 && index < getRunQualities().size()) {
            return getRunQualities().get(index).getQualityMetrics().stream().filter((qm) -> {
                return accession.equals(qm.getAccession());
            }).collect(Collectors.toList());
        }
        throw new IllegalArgumentException("Can not access run " + index);
    }
}
