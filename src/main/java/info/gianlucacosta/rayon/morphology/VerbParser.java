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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

class VerbParser extends InflectablePartOfSpeechParser<Verb> {
    private final Map<VerbInflectionParams, String> inflections =
            new HashMap<>();


    public VerbParser(Consumer<Exception> exceptionConsumer) {
        super(exceptionConsumer);
    }


    @Override
    protected void consumeInflection(String inflectionXml, String grace, String form) {
        VerbInflectionParams inflectionParams =
                VerbInflectionParams.getFromGrace(grace);

        inflections.put(inflectionParams, form);
    }

    @Override
    protected Optional<Verb> parseAfterInflections(String posXml) {
        if (inflections.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(
                    new Verb(posXml, inflections)
            );
        }
    }
}
