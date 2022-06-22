/*
 * Copyright 2022 Nils Hoffmann.
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
package org.lifstools.jmzqc.cv;

import java.util.Map;
import org.lifstools.jmzqc.CvParameter;

/**
 *
 * @author Nils Hoffmann
 */
public abstract class OboTerm {
    private Map<String, CvParameter> units;
    private Map<String, CvParameter> categories;
    private Map<String, CvParameter> relations;
}