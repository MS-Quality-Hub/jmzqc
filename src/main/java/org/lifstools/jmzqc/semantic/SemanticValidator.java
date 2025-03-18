package org.lifstools.jmzqc.semantic;

import org.lifstools.jmzqc.CvParameter;
import org.lifstools.jmzqc.QualityMetric;
import org.lifstools.jmzqc.Unit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Validator for semantic validation of mzQC data structures using OBO ontologies.
 */
public class SemanticValidator {
    private final OboParser oboParser;
    private final List<String> validationErrors;

    public SemanticValidator() {
        this.oboParser = new OboParser();
        this.validationErrors = new ArrayList<>();
    }

    /**
     * Loads the OBO ontology file for validation.
     *
     * @param oboFile The OBO file to load
     * @throws IOException if there's an error reading the file
     */
    public void loadOntology(File oboFile) throws IOException {
        try {
            oboParser.loadOboFile(oboFile);
        } catch (Exception e) {
            validationErrors.add("Failed to load OBO file: " + e.getMessage());
        }
    }

    /**
     * Validates a CV parameter against the loaded ontology.
     *
     * @param cvParam The CV parameter to validate
     * @return true if the parameter is valid, false otherwise
     */
    public boolean validateCvParameter(CvParameter cvParam) {
        if (cvParam == null || cvParam.accession() == null) {
            validationErrors.add("Invalid CV parameter: null or missing accession");
            return false;
        }

        boolean isValid = oboParser.validateTerm(cvParam.accession());
        if (!isValid) {
            validationErrors.add("Invalid CV parameter accession: " + cvParam.accession());
        }
        return isValid;
    }

    /**
     * Validates a quality metric against the loaded ontology.
     *
     * @param metric The quality metric to validate
     * @return true if the metric is valid, false otherwise
     */
    public boolean validateQualityMetric(QualityMetric metric) {
        if (metric == null) {
            validationErrors.add("Invalid quality metric: null");
            return false;
        }

        boolean isValid = true;
        if (metric.accession() != null) {
            isValid &= oboParser.validateTerm(metric.accession());
            if (!isValid) {
                validationErrors.add("Invalid quality metric accession: " + metric.accession());
            }
        }

        Unit unit = metric.unit();
        if (unit != null) {
            if (unit.cvParameterValue() != null) {
                isValid &= validateCvParameter(unit.cvParameterValue());
            }
            if (unit.cvParameterArrayValue() != null) {
                for (CvParameter cvParam : unit.cvParameterArrayValue()) {
                    isValid &= validateCvParameter(cvParam);
                }
            }
        }

        return isValid;
    }

    /**
     * Gets the list of validation errors that occurred during validation.
     *
     * @return List of validation error messages
     */
    public List<String> getValidationErrors() {
        return new ArrayList<>(validationErrors);
    }

    /**
     * Clears all validation errors.
     */
    public void clearValidationErrors() {
        validationErrors.clear();
    }
} 