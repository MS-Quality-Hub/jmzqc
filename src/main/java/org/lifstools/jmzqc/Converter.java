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

import com.fasterxml.jackson.core.JsonFactoryBuilder;
import java.io.IOException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * Provides convenience functions to parse date and time strings and to
 * serialize and deserialize mzQC from and to a variety of sources.
 */
public class Converter {
    // Date-time helpers

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_INSTANT)
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    /**
     * Parse a date and time string using the default date and time formatter.
     * @param str the date time string
     * @return an {@link OffsetDateTime} object.
     */
    public static OffsetDateTime parseDateTimeString(String str) {
        return ZonedDateTime.from(Converter.DATE_TIME_FORMATTER.parse(str)).toOffsetDateTime();
    }

    private static final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_TIME)
            .appendOptional(DateTimeFormatter.ISO_OFFSET_TIME)
            .parseDefaulting(ChronoField.YEAR, 2020)
            .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    /**
     * Parse a time string using the default time formatter.
     *
     * @param str the time string
     * @return an {@link OffsetTime} object.
     */
    public static OffsetTime parseTimeString(String str) {
        return ZonedDateTime.from(Converter.TIME_FORMATTER.parse(str)).toOffsetDateTime().toOffsetTime();
    }
    // Serialize/deserialize helpers

    /**
     * Return a Coordinate holding an MzQC object from a file.
     *
     * @param file the file containing the mzQC json data.
     * @return {@link Coordinate} holding the MzQC object.
     * @throws IOException
     */
    public static Coordinate fromFile(File file) throws IOException {
        return fromJsonString(Files.readString(file.toPath(), StandardCharsets.UTF_8));
    }

    /**
     * Return a Coordinate holding an MzQC object from a URL.
     *
     * @param url the url from where to load the mzQC json data.
     * @return {@link Coordinate} holding the MzQC object.
     * @throws IOException
     */
    public static Coordinate fromUrl(URL url) throws IOException {
        try ( InputStream in = url.openStream()) {
            byte[] bytes = in.readAllBytes();
            return fromJsonString(new String(bytes, StandardCharsets.UTF_8));
        }
    }

    /**
     * Return a Coordinate holding an MzQC object from a URL string.
     *
     * @param urlString the urlString from where to load the mzQC json data.
     * @return {@link Coordinate} holding the MzQC object.
     * @throws IOException
     */
    public static Coordinate fromUrlString(String urlString) throws MalformedURLException, IOException {
        URL url = new URL(urlString);
        return fromUrl(url);
    }

    /**
     * Return a Coordinate holding an MzQC object from a string holding the JSON
     * representation of an mzQC data.
     *
     * @param json the string containing the mzQC json data.
     * @return {@link Coordinate} holding the MzQC object.
     * @throws IOException
     */
    public static Coordinate fromJsonString(String json) throws IOException {
        return getObjectReader().readValue(json);
    }

    /**
     * Serialize the Coordinate holding an MzQC object to a JSON string.
     *
     * @param obj the coordinate
     * @return an (unformatted) JSON string representation.
     * @throws JsonProcessingException
     */
    public static String toJsonString(Coordinate obj) throws JsonProcessingException {
        return getObjectWriter().writeValueAsString(obj);
    }

    /**
     * Serialize the Coordinate holding an MzQC object to a file containing the
     * data as a JSON string.
     *
     * @param obj the coordinate
     * @param file the file to write to
     * @return an (unformatted) JSON string representation.
     * @throws JsonProcessingException
     */
    public static File toJsonFile(Coordinate obj, File file) throws JsonProcessingException, IOException {
        return Files.writeString(file.toPath(), toJsonString(obj), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING).toFile();
    }

    private static ObjectReader reader;
    private static ObjectWriter writer;

    private static void instantiateMapper() {
        JsonFactoryBuilder jfb = new JsonFactoryBuilder().
                enable(JsonReadFeature.ALLOW_TRAILING_COMMA);
        ObjectMapper mapper = new ObjectMapper(jfb.build());
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(OffsetDateTime.class, new JsonDeserializer<OffsetDateTime>() {
            @Override
            public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
                String value = jsonParser.getText();
                return Converter.parseDateTimeString(value);
            }
        });
        mapper.registerModule(module);
        reader = mapper.readerFor(Coordinate.class);
        writer = mapper.writerFor(Coordinate.class);
    }

    private static ObjectReader getObjectReader() {
        if (reader == null) {
            instantiateMapper();
        }
        return reader;
    }

    private static ObjectWriter getObjectWriter() {
        if (writer == null) {
            instantiateMapper();
        }
        return writer;
    }
}
