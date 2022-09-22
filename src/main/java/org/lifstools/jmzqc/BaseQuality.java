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

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Element containing metadata and qualityMetrics for a single run.
 *
 * Base element from which both runQuality and setQuality elements are derived.
 *
 * Element containing metadata and qualityMetrics for a collection of related
 * runs (set).
 */
public class BaseQuality {

    private Metadata metadata;
    private List<QualityMetric> qualityMetrics = Collections.emptyList();

    public BaseQuality() {
    }

    public BaseQuality(Metadata metadata, List<QualityMetric> qualityMetrics) {
        this.metadata = metadata;
        this.qualityMetrics = qualityMetrics;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public List<QualityMetric> getQualityMetrics() {
        return qualityMetrics;
    }

    public void setQualityMetrics(List<QualityMetric> qualityMetrics) {
        this.qualityMetrics = qualityMetrics;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.metadata);
        hash = 97 * hash + Objects.hashCode(this.qualityMetrics);
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
        final BaseQuality other = (BaseQuality) obj;
        if (!Objects.equals(this.metadata, other.metadata)) {
            return false;
        }
        return Objects.equals(this.qualityMetrics, other.qualityMetrics);
    }

}
