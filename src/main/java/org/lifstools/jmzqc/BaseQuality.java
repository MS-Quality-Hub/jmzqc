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
import java.util.Collections;
import java.util.List;

/**
 * Element containing metadata and qualityMetrics for a single run.
 *
 * Base element from which both runQuality and setQuality elements are derived.
 *
 * Element containing metadata and qualityMetrics for a collection of related runs (set).
 */
@lombok.Data
public class BaseQuality {
    @lombok.Getter(onMethod_ = {@JsonProperty("metadata")})
    @lombok.Setter(onMethod_ = {@JsonProperty("metadata")})
    private Metadata metadata;
    @lombok.Getter(onMethod_ = {@JsonProperty("qualityMetrics")})
    @lombok.Setter(onMethod_ = {@JsonProperty("qualityMetrics")})
    private List<QualityMetric> qualityMetrics = Collections.emptyList();
}
