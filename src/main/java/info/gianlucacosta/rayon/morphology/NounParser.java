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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class NounParser extends InflectablePartOfSpeechParser<Noun> {
    private static final Pattern genderPattern =
            Pattern.compile("<pos\\s+.*?gender=\"([mfe])\"[^>]*>");

    private Optional<Gender> gender =
            Optional.empty();

    private final Map<Number, String> inflections =
            new HashMap<>();


    public NounParser(Consumer<Exception> exceptionConsumer) {
        super(exceptionConsumer);
    }

    @Override
    protected void consumeInflection(String inflectionXml, String grace, String form) {
        if (grace.endsWith("s")) {
            inflections.put(Number.SINGULAR, form);
        } else if (grace.endsWith("p")) {
            inflections.put(Number.PLURAL, form);
        }
    }

    @Override
    protected Optional<Noun> parseAfterInflections(String posXml) {
        if (inflections.isEmpty()) {
            return Optional.empty();
        } else {
            Matcher genderMatcher =
                    genderPattern.matcher(posXml);

            if (genderMatcher.find()) {
                String genderCode =
                        genderMatcher.group(1);

                gender =
                        Optional.of(
                                GraceAnnotated.getFromGrace(genderCode, Gender.class)
                        );
            }

            return Optional.of(
                    new Noun(posXml, gender, inflections)
            );
        }
    }
}
