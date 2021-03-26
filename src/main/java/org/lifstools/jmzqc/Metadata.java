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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Metadata describing the QC analysis.
 */
@lombok.Data
public class Metadata {
    @lombok.Getter(onMethod_ = {@JsonProperty("analysisSoftware")})
    @lombok.Setter(onMethod_ = {@JsonProperty("analysisSoftware")})
    private List<AnalysisSoftwareElement> analysisSoftware = Collections.emptyList();
    @lombok.Getter(onMethod_ = {@JsonProperty("cvParameters")})
    @lombok.Setter(onMethod_ = {@JsonProperty("cvParameters")})
    private List<CvParameter> cvParameters = Collections.emptyList();
    @lombok.Getter(onMethod_ = {@JsonProperty("inputFiles")})
    @lombok.Setter(onMethod_ = {@JsonProperty("inputFiles")})
    private List<InputFile> inputFiles = Collections.emptyList();
    @lombok.Getter(onMethod_ = {@JsonProperty("label")})
    @lombok.Setter(onMethod_ = {@JsonProperty("label")})
    private String label;
}
