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
import java.util.Objects;

/**
 * One or more controlled vocabulary elements describing the unit of the metric.
 */
@JsonDeserialize(using = Unit.Deserializer.class)
@JsonSerialize(using = Unit.Serializer.class)
public class Unit {

    public CvParameter cvParameterValue;
    public List<CvParameter> cvParameterArrayValue = Collections.emptyList();

    public Unit() {
    }

    public Unit(CvParameter cvParameterValue, List<CvParameter> cvParameterArrayValue) {
        this.cvParameterValue = cvParameterValue;
        this.cvParameterArrayValue = cvParameterArrayValue;
    }

    public CvParameter getCvParameterValue() {
        return cvParameterValue;
    }

    public void setCvParameterValue(CvParameter cvParameterValue) {
        this.cvParameterValue = cvParameterValue;
    }

    public List<CvParameter> getCvParameterArrayValue() {
        return cvParameterArrayValue;
    }

    public void setCvParameterArrayValue(List<CvParameter> cvParameterArrayValue) {
        this.cvParameterArrayValue = cvParameterArrayValue;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.cvParameterValue);
        hash = 71 * hash + Objects.hashCode(this.cvParameterArrayValue);
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
        final Unit other = (Unit) obj;
        if (!Objects.equals(this.cvParameterValue, other.cvParameterValue)) {
            return false;
        }
        return Objects.equals(this.cvParameterArrayValue, other.cvParameterArrayValue);
    }

    static class Deserializer extends JsonDeserializer<Unit> {

        @Override
        public Unit deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            Unit value = new Unit();
            switch (jsonParser.currentToken()) {
                case VALUE_NULL:
                    break;
                case START_ARRAY:
                    value.cvParameterArrayValue = jsonParser.readValueAs(new TypeReference<List<CvParameter>>() {
                    });
                    break;
                case START_OBJECT:
                    value.cvParameterValue = jsonParser.readValueAs(CvParameter.class);
                    break;
                default:
                    throw new IOException("Cannot deserialize Unit");
            }
            return value;
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
