package org.lifstools.jmzqc.semantic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simple parser for OBO format files that provides semantic validation capabilities.
 */
public class OboParser {
    private final Map<String, String> termDefinitions;
    private final Map<String, String> termRelationships;
    private static final Pattern TERM_PATTERN = Pattern.compile("\\[Term\\]");
    private static final Pattern ID_PATTERN = Pattern.compile("id:\\s*(\\S+)");
    private static final Pattern DEF_PATTERN = Pattern.compile("def:\\s*\"([^\"]+)\"");
    private static final Pattern REL_PATTERN = Pattern.compile("relationship:\\s*(\\S+)\\s+(\\S+)");

    public OboParser() {
        this.termDefinitions = new HashMap<>();
        this.termRelationships = new HashMap<>();
    }

    /**
     * Loads and parses an OBO file.
     *
     * @param oboFile The OBO file to parse
     * @throws IOException if there's an error reading the file
     */
    public void loadOboFile(File oboFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(oboFile))) {
            String line;
            String currentTermId = null;
            StringBuilder currentDef = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("!")) {
                    continue;
                }

                if (TERM_PATTERN.matcher(line).matches()) {
                    if (currentTermId != null && currentDef.length() > 0) {
                        termDefinitions.put(currentTermId, currentDef.toString());
                        currentDef = new StringBuilder();
                    }
                    currentTermId = null;
                    continue;
                }

                if (currentTermId == null) {
                    Matcher idMatcher = ID_PATTERN.matcher(line);
                    if (idMatcher.find()) {
                        currentTermId = idMatcher.group(1);
                    }
                }

                Matcher defMatcher = DEF_PATTERN.matcher(line);
                if (defMatcher.find()) {
                    currentDef.append(defMatcher.group(1)).append(" ");
                }

                Matcher relMatcher = REL_PATTERN.matcher(line);
                if (relMatcher.find() && currentTermId != null) {
                    String relType = relMatcher.group(1);
                    String targetTerm = relMatcher.group(2);
                    String relKey = currentTermId + "->" + relType + "->" + targetTerm;
                    termRelationships.put(relKey, relType);
                }
            }

            // Add the last term definition if exists
            if (currentTermId != null && currentDef.length() > 0) {
                termDefinitions.put(currentTermId, currentDef.toString().trim());
            }
        }
    }

    /**
     * Validates if a term exists in the loaded OBO file.
     *
     * @param termId The term ID to validate
     * @return true if the term exists, false otherwise
     */
    public boolean validateTerm(String termId) {
        return termDefinitions.containsKey(termId);
    }

    /**
     * Gets the definition of a term.
     *
     * @param termId The term ID
     * @return The term definition or null if not found
     */
    public String getTermDefinition(String termId) {
        return termDefinitions.get(termId);
    }

    /**
     * Validates if a relationship exists between two terms.
     *
     * @param sourceTerm The source term ID
     * @param targetTerm The target term ID
     * @param relationshipType The type of relationship
     * @return true if the relationship exists, false otherwise
     */
    public boolean validateRelationship(String sourceTerm, String targetTerm, String relationshipType) {
        String relKey = sourceTerm + "->" + relationshipType + "->" + targetTerm;
        return termRelationships.containsKey(relKey);
    }
} 