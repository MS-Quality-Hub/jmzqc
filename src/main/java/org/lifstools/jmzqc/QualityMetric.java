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
 * Element containing the value and description of a QC metric defined in a
 * controlled vocabulary.
 *
 * Base element for a term that is defined in a controlled vocabulary, with
 * OPTIONAL value.
 *
 * Type of input file.
 */
public record QualityMetric(
        String accession,
        String description,
        String name,
        Object value,
        Unit unit) {

    @JsonIgnore
    public Long toLong() {
        if (value instanceof Number number) {
            return number.longValue();
        }
        throw new ClassCastException(value.getClass().getName() + " cannot be cast to " + Number.class.getName());
    }

    @JsonIgnore
    public Double toDouble() {
        if (value instanceof Number number) {
            return number.doubleValue();
        }
        throw new ClassCastException(value.getClass().getName() + " cannot be cast to " + Number.class.getName());
    }

    @JsonIgnore
    public Integer toInteger() {
        if (value instanceof Number number) {
            return number.intValue();
        }
        throw new ClassCastException(value.getClass().getName() + " cannot be cast to " + Number.class.getName());
    }
}
