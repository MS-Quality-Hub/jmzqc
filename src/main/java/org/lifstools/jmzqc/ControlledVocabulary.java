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

/**
 * Element describing a controlled vocabulary used to refer to the source of the used CV
 * terms in qualityMetric objects (and others).
 */
@lombok.Data
public class ControlledVocabulary {
    @lombok.Getter(onMethod_ = {@JsonProperty("name")})
    @lombok.Setter(onMethod_ = {@JsonProperty("name")})
    private String name;
    @lombok.Getter(onMethod_ = {@JsonProperty("uri")})
    @lombok.Setter(onMethod_ = {@JsonProperty("uri")})
    private String uri;
    @lombok.Getter(onMethod_ = {@JsonProperty("version")})
    @lombok.Setter(onMethod_ = {@JsonProperty("version")})
    private String version;
}
