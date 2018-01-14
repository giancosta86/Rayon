/*^
  ===========================================================================
  Rayon
  ===========================================================================
  Copyright (C) 2018 Gianluca Costa
  ===========================================================================
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ===========================================================================
*/

package info.gianlucacosta.rayon.morphology;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class Verb extends PartOfSpeech {
    private final Map<VerbInflectionParams, String> inflections;

    Verb(String posXml, Map<VerbInflectionParams, String> inflections) {
        super(posXml);

        this.inflections =
                Collections.unmodifiableMap(inflections);
    }


    public Map<VerbInflectionParams, String> getInflections() {
        return inflections;
    }


    public Optional<String> getInflection(
            Optional<Mood> mood,
            Optional<Tense> tense,
            Optional<Person> person,
            Optional<Number> number,
            Optional<Gender> gender
    ) {
        VerbInflectionParams inflectionParams =
                new VerbInflectionParams(
                        mood,
                        tense,
                        person,
                        number,
                        gender
                );

        return getInflection(inflectionParams);
    }


    public Optional<String> getInflection(VerbInflectionParams inflectionParams) {
        return Optional.ofNullable(
                inflections.get(inflectionParams)
        );
    }
}
