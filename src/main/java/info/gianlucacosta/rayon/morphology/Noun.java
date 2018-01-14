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

public class Noun extends PartOfSpeech {
    private final Optional<Gender> genderOption;
    private final Map<Number, String> inflections;

    Noun(String posXml, Optional<Gender> genderOption, Map<Number, String> inflections) {
        super(posXml);
        this.genderOption = genderOption;

        this.inflections =
                Collections.unmodifiableMap(inflections);
    }


    public Optional<Gender> getGender() {
        return genderOption;
    }


    public Optional<String> getInflection(Number number) {
        return Optional.ofNullable(
                inflections.get(number)
        );
    }


    public Optional<String> getSingular() {
        return getInflection(Number.SINGULAR);
    }


    public Optional<String> getPlural() {
        return getInflection(Number.PLURAL);
    }
}
