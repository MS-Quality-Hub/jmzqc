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
import java.util.Objects;

/**
 * Element containing the value and description of a QC metric defined in a
 * controlled vocabulary.
 *
 * Base element for a term that is defined in a controlled vocabulary, with
 * OPTIONAL value.
 *
 * Type of input file.
 */
public class QualityMetric {

    private String accession;
    private String description;
    private String name;
    private Object value;
    private Unit unit;

    public QualityMetric() {
    }

    public QualityMetric(String accession, String description, String name, Object value, Unit unit) {
        this.accession = accession;
        this.description = description;
        this.name = name;
        this.value = value;
        this.unit = unit;
    }

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.accession);
        hash = 37 * hash + Objects.hashCode(this.description);
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + Objects.hashCode(this.value);
        hash = 37 * hash + Objects.hashCode(this.unit);
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
        final QualityMetric other = (QualityMetric) obj;
        if (!Objects.equals(this.accession, other.accession)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return Objects.equals(this.unit, other.unit);
    }

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
