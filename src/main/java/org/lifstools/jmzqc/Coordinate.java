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
 * JSON schema specifying the mzQC format v1.0.0 developed by the HUPO-PSI Quality Control
 * working group (http://psidev.info/groups/quality-control).
 */
@lombok.Data
public class Coordinate {
    @lombok.Getter(onMethod_ = {@JsonProperty("mzQC")})
    @lombok.Setter(onMethod_ = {@JsonProperty("mzQC")})
    private MzQC mzQC;
}
