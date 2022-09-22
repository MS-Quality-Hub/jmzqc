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

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Input file used to generate the QC metrics.
 */
public class InputFile {

    private CvParameter fileFormat;
    private List<CvParameter> fileProperties = Collections.emptyList();
    private URI location;
    private String name;

    public InputFile() {
    }

    public InputFile(CvParameter fileFormat, List<CvParameter> fileProperties, URI location, String name) {
        this.fileFormat = fileFormat;
        this.fileProperties = fileProperties;
        this.location = location;
        this.name = name;
    }

    public CvParameter getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(CvParameter fileFormat) {
        this.fileFormat = fileFormat;
    }

    public List<CvParameter> getFileProperties() {
        return fileProperties;
    }

    public void setFileProperties(List<CvParameter> fileProperties) {
        this.fileProperties = fileProperties;
    }

    public URI getLocation() {
        return location;
    }

    public void setLocation(URI location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.fileFormat);
        hash = 83 * hash + Objects.hashCode(this.fileProperties);
        hash = 83 * hash + Objects.hashCode(this.location);
        hash = 83 * hash + Objects.hashCode(this.name);
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
        final InputFile other = (InputFile) obj;
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.fileFormat, other.fileFormat)) {
            return false;
        }
        return Objects.equals(this.fileProperties, other.fileProperties);
    }

}
