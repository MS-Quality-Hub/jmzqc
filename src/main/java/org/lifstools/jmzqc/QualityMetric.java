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
import java.util.List;

/**
 * Element containing the value and description of a QC metric defined in a controlled
 * vocabulary.
 *
 * Base element for a term that is defined in a controlled vocabulary, with OPTIONAL value.
 *
 * Type of input file.
 */
@lombok.Data
public class QualityMetric {
    @lombok.Getter(onMethod_ = {@JsonProperty("accession")})
    @lombok.Setter(onMethod_ = {@JsonProperty("accession")})
    private String accession;
    @lombok.Getter(onMethod_ = {@JsonProperty("description")})
    @lombok.Setter(onMethod_ = {@JsonProperty("description")})
    private String description;
    @lombok.Getter(onMethod_ = {@JsonProperty("name")})
    @lombok.Setter(onMethod_ = {@JsonProperty("name")})
    private String name;
    @lombok.Getter(onMethod_ = {@JsonProperty("value")})
    @lombok.Setter(onMethod_ = {@JsonProperty("value")})
    private Object value;
    @lombok.Getter(onMethod_ = {@JsonProperty("unit")})
    @lombok.Setter(onMethod_ = {@JsonProperty("unit")})
    private Unit unit;
}
