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

import javafx.util.Pair;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class Adjective extends PartOfSpeech {
    private final Map<Pair<Gender, Number>, String> inflections;

    Adjective(String posXml, Map<Pair<Gender, Number>, String> inflections) {
        super(posXml);

        this.inflections =
                Collections.unmodifiableMap(inflections);
    }


    public Optional<String> getInflection(Gender gender, Number number) {
        Pair<Gender, Number> pair =
                new Pair<>(gender, number);

        return Optional.ofNullable(
                inflections.get(pair)
        );
    }


    public Optional<String> getMasculine() {
        return getInflection(Gender.MASCULINE, Number.SINGULAR);
    }


    public Optional<String> getFeminine() {
        return getInflection(Gender.FEMININE, Number.SINGULAR);
    }
}
