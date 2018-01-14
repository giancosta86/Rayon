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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

class AdjectiveParser extends InflectablePartOfSpeechParser<Adjective> {
    private final Map<Pair<Gender, Number>, String> inflections =
            new HashMap<>();


    public AdjectiveParser(Consumer<Exception> exceptionConsumer) {
        super(exceptionConsumer);
    }

    @Override
    protected void consumeInflection(String inflectionXml, String grace, String form) {
        String inflectionGraceCode =
                new Ending(grace, 2).getExpression();

        char genderGraceCode =
                inflectionGraceCode.charAt(0);

        Gender gender =
                GraceAnnotated.getFromGrace(genderGraceCode, Gender.class);


        char numberGraceCode =
                inflectionGraceCode.charAt(1);

        Number number =
                GraceAnnotated.getFromGrace(numberGraceCode, Number.class);


        Pair<Gender, Number> inflectionKey =
                new Pair<>(gender, number);


        inflections.put(
                inflectionKey,
                form
        );
    }

    @Override
    protected Optional<Adjective> parseAfterInflections(String posXml) {
        if (inflections.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(
                    new Adjective(posXml, inflections)
            );
        }
    }
}
