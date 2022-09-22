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

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * One or more controlled vocabulary elements describing the unit of the metric.
 */
@JsonDeserialize(using = Unit.Deserializer.class)
        @JsonSerialize(using = Unit.Serializer.class)
        public record Unit(
        CvParameter cvParameterValue,
        List<CvParameter> cvParameterArrayValue) {

    public Unit  {
        if (cvParameterArrayValue == null) {
            cvParameterArrayValue = Collections.emptyList();
        }
    }

    static class Deserializer extends JsonDeserializer<Unit> {

        @Override
        public Unit deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            switch (jsonParser.currentToken()) {
                case START_ARRAY -> {
                    return new Unit(null, jsonParser.readValueAs(new TypeReference<List<CvParameter>>() {
                    }));
                }
                case START_OBJECT -> {
                    return new Unit(jsonParser.readValueAs(CvParameter.class), null);
                }
                default ->
                    throw new IOException("Cannot deserialize Unit");
            }
        }
    }

    static class Serializer extends JsonSerializer<Unit> {

        @Override
        public void serialize(Unit obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (obj.cvParameterValue != null) {
                jsonGenerator.writeObject(obj.cvParameterValue);
                return;
            }
            if (obj.cvParameterArrayValue != null) {
                jsonGenerator.writeObject(obj.cvParameterArrayValue);
                return;
            }
            jsonGenerator.writeNull();
        }
    }
}
