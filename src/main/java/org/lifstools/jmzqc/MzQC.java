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
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Root element of an mzQC file.
 */
public class MzQC {

    private String contactAddress;
    private String contactName;
    private List<ControlledVocabulary> controlledVocabularies = Collections.emptyList();
    private OffsetDateTime creationDate;
    private String description;
    private List<BaseQuality> runQualities = Collections.emptyList();
    private List<BaseQuality> setQualities = Collections.emptyList();
    private String version;

    public MzQC() {
    }

    public MzQC(String contactAddress, String contactName, List<ControlledVocabulary> controlledVocabularies, OffsetDateTime creationDate, String description, List<BaseQuality> runQualities, List<BaseQuality> setQualities, String version) {
        this.contactAddress = contactAddress;
        this.contactName = contactName;
        this.controlledVocabularies = controlledVocabularies;
        this.creationDate = creationDate;
        this.description = description;
        this.runQualities = runQualities;
        this.setQualities = setQualities;
        this.version = version;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public List<ControlledVocabulary> getControlledVocabularies() {
        return controlledVocabularies;
    }

    public void setControlledVocabularies(List<ControlledVocabulary> controlledVocabularies) {
        this.controlledVocabularies = controlledVocabularies;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<BaseQuality> getRunQualities() {
        return runQualities;
    }

    public void setRunQualities(List<BaseQuality> runQualities) {
        this.runQualities = runQualities;
    }

    public List<BaseQuality> getSetQualities() {
        return setQualities;
    }

    public void setSetQualities(List<BaseQuality> setQualities) {
        this.setQualities = setQualities;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.contactAddress);
        hash = 47 * hash + Objects.hashCode(this.contactName);
        hash = 47 * hash + Objects.hashCode(this.controlledVocabularies);
        hash = 47 * hash + Objects.hashCode(this.creationDate);
        hash = 47 * hash + Objects.hashCode(this.description);
        hash = 47 * hash + Objects.hashCode(this.runQualities);
        hash = 47 * hash + Objects.hashCode(this.setQualities);
        hash = 47 * hash + Objects.hashCode(this.version);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MzQC other = (MzQC) obj;
        if (!Objects.equals(this.contactAddress, other.contactAddress)) {
            return false;
        }
        if (!Objects.equals(this.contactName, other.contactName)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.version, other.version)) {
            return false;
        }
        if (!Objects.equals(this.controlledVocabularies, other.controlledVocabularies)) {
            return false;
        }
        if (!Objects.equals(this.creationDate, other.creationDate)) {
            return false;
        }
        if (!Objects.equals(this.runQualities, other.runQualities)) {
            return false;
        }
        return Objects.equals(this.setQualities, other.setQualities);
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
