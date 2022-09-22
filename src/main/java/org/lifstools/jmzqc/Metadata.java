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
 * Metadata describing the QC analysis.
 */
public class Metadata {

    private List<AnalysisSoftware> analysisSoftware = Collections.emptyList();
    ;
    private List<CvParameter> cvParameters = Collections.emptyList();
    ;
    private List<InputFile> inputFiles = Collections.emptyList();
    ;
    private String label;

    public Metadata() {
    }

    public Metadata(List<AnalysisSoftware> analysisSoftware, List<CvParameter> cvParameters, List<InputFile> inputFiles, String label) {
        this.analysisSoftware = analysisSoftware;
        this.cvParameters = cvParameters;
        this.inputFiles = inputFiles;
        this.label = label;
    }

    public List<AnalysisSoftware> getAnalysisSoftware() {
        return analysisSoftware;
    }

    public void setAnalysisSoftware(List<AnalysisSoftware> analysisSoftware) {
        this.analysisSoftware = analysisSoftware;
    }

    public List<CvParameter> getCvParameters() {
        return cvParameters;
    }

    public void setCvParameters(List<CvParameter> cvParameters) {
        this.cvParameters = cvParameters;
    }

    public List<InputFile> getInputFiles() {
        return inputFiles;
    }

    public void setInputFiles(List<InputFile> inputFiles) {
        this.inputFiles = inputFiles;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.analysisSoftware);
        hash = 79 * hash + Objects.hashCode(this.cvParameters);
        hash = 79 * hash + Objects.hashCode(this.inputFiles);
        hash = 79 * hash + Objects.hashCode(this.label);
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
        final Metadata other = (Metadata) obj;
        if (!Objects.equals(this.label, other.label)) {
            return false;
        }
        if (!Objects.equals(this.analysisSoftware, other.analysisSoftware)) {
            return false;
        }
        if (!Objects.equals(this.cvParameters, other.cvParameters)) {
            return false;
        }
        return Objects.equals(this.inputFiles, other.inputFiles);
    }

}
