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

import com.networknt.schema.ValidationMessage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;

/**
 * Create a new command line parser for parsing of lipid names.
 *
 * @author Dominik Kopczynski
 * @author Nils Hoffmann
 *
 */
public class CmdLineParser {

    private static String getAppInfo() throws IOException {
        Properties p = new Properties();
        p.load(CmdLineParser.class.getResourceAsStream(
                "/application.properties"));
        StringBuilder sb = new StringBuilder();
        String buildDate = p.getProperty("app.build.date", "no build date");
        if (!"no build date".equals(buildDate)) {
            Instant instant = Instant.ofEpochMilli(Long.parseLong(buildDate));
            buildDate = instant.toString();
        }
        /*
         *Property keys are in src/main/resources/application.properties
         */
        sb.append("Running ").
                append(p.getProperty("app.name", "undefined app")).
                append("\n\r").
                append(" version: '").
                append(p.getProperty("app.version", "unknown version")).
                append("'").
                append("\n\r").
                append(" build-date: '").
                append(buildDate).
                append("'").
                append("\n\r").
                append(" scm-location: '").
                append(p.getProperty("scm.location", "no scm location")).
                append("'").
                append("\n\r").
                append(" commit: '").
                append(p.getProperty("scm.commit.id", "no commit id")).
                append("'").
                append("\n\r").
                append(" branch: '").
                append(p.getProperty("scm.branch", "no branch")).
                append("'").
                append("\n\r");
        return sb.toString();
    }

    /**
     * <p>
     * Runs the command line validation for jmzqc.</p>
     *
     * Run with the {@code -h} or {@code --help} option to see more options.
     *
     * @param args an array of {@link java.lang.String} lipid names.
     * @throws java.lang.Exception if any unexpected errors occur.
     */
    @SuppressWarnings("static-access")
    public static void main(String[] args) throws Exception {
        CommandLineParser parser = new PosixParser();
        Options options = new Options();
        String helpOpt = addHelpOption(options);
        String versionOpt = addVersionOption(options);
        String inputFileOpt = addFileInputOption(options);
        String outputToFileOpt = addOutputToFileOption(options);

        CommandLine line = parser.parse(options, args);
        if (line.getOptions().length == 0 || line.hasOption(helpOpt)) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("jmzqc", options);
        } else if (line.hasOption(versionOpt)) {
            System.out.println(getAppInfo());
        } else {
            boolean toFile = false;
            String outputFile = "jmzqc-out.tsv";
            if (line.hasOption(outputToFileOpt)) {
                toFile = true;
                outputFile = line.getOptionValue(outputToFileOpt);
            }
            Optional<File> inputFile = Optional.empty();
            if (line.hasOption(inputFileOpt)) {
                inputFile = Optional.of(new File(line.getOptionValue(inputFileOpt)));
            }
            List<ValidationMessage> results = Collections.emptyList();
            if (inputFile.isPresent()) {
                try {
                    results = validate(inputFile.get());
                } catch (NoSuchFileException ex) {
                    System.err.println("File " + inputFile.get() + " does not exist!");
                    System.exit(1);
                }
            }
            if (results.isEmpty()) {
                System.out.println("Validation successful!");
                System.exit(0);
            }
            if (toFile) {
                System.out.println("Saving output to '" + outputFile + "'.");
                boolean successful = writeToFile(new File(outputFile), results);
                System.exit(1);
            } else {
                System.out.println("Echoing output to stderr.");
                boolean successful = writeToStdOut(results);
                System.exit(1);
            }
        }
    }

    private static boolean writeToStdOut(List<ValidationMessage> results) {

        try ( StringWriter sw = new StringWriter()) {
            try ( BufferedWriter bw = new BufferedWriter(sw)) {
                writeToWriter(bw, results);
            }
            sw.flush();
            sw.close();
            System.err.println(sw.toString());
            return true;
        } catch (IOException ex) {
            System.err.println("Caught exception while trying to write validation results string!");
            ex.printStackTrace(System.err);
            return false;
        }
    }

    private static boolean writeToFile(File f, List<ValidationMessage> results) {

        try ( BufferedWriter bw = Files.newBufferedWriter(f.toPath())) {
            writeToWriter(bw, results);
            return true;
        } catch (IOException ex) {
            System.err.println("Caught exception while trying to write validation results to file " + f);
            ex.printStackTrace(System.err);
            return false;
        }
    }

    private static String toTable(List<ValidationMessage> results) {
        StringBuilder sb = new StringBuilder();
        HashSet<String> keys = new LinkedHashSet<>();
        List<Map<String, String>> entries = results.stream().map((t) -> {
            Map<String, String> m = new LinkedHashMap<>();
            m.put("Code", t.getCode());
            m.put("Message", t.getMessage());
            m.put("Path", t.getPath());
            m.put("SchemaPath", t.getSchemaPath());
            m.put("Type", t.getType());
            m.put("MessageString", t.toString());
            keys.addAll(m.keySet());
            return m;
        }).toList();
        sb.append(keys.stream().collect(Collectors.joining("\t"))).append("\n");
        for (Map<String, String> m : entries) {
            List<String> l = new LinkedList();
            for (String key : keys) {
                l.add(m.getOrDefault(key, ""));
            }
            sb.append(l.stream().collect(Collectors.joining("\t"))).append("\n");
        }
        return sb.toString();
    }

    private static void writeToWriter(BufferedWriter bw, List<ValidationMessage> results) {
        try {
            bw.write(toTable(results));
            bw.newLine();
        } catch (IOException ex) {
            System.err.println("Caught exception while trying to write validation results to buffered writer.");
            ex.printStackTrace(System.err);
        }
    }

    private static List<ValidationMessage> validate(File inputFile) throws IOException {
        return new LinkedList<>(Converter.validate(inputFile));
    }

    protected static String addFileInputOption(Options options) {
        String fileOpt = "file";
        options.addOption("f", fileOpt, true, "Input a file name to read from for lipid name for parsing. Each lipid name must be on a separate line.");
        return fileOpt;
    }

    protected static String addVersionOption(Options options) {
        String versionOpt = "version";
        options.addOption("v", versionOpt, false, "Print version information.");
        return versionOpt;
    }

    protected static String addHelpOption(Options options) {
        String helpOpt = "help";
        options.addOption("h", helpOpt, false, "Print help message.");
        return helpOpt;
    }

    protected static String addOutputToFileOption(Options options) {
        String outputToFileOpt = "outputFile";
        options.addOption("o", outputToFileOpt, true, "Write output to provided file in tsv format instead of to std out.");
        return outputToFileOpt;
    }

}
