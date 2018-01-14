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

import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class InflectablePartOfSpeechParser<T extends PartOfSpeech>
        extends PartOfSpeechParser<T> {
    private static final Pattern inflectionPattern =
            Pattern.compile("<inflection\\s+form\\s*=\\s*\"([^\"]+)\"\\s+gracePOS\\s*=\\s*\"([^\"]+)\"[^>]*>");

    public InflectablePartOfSpeechParser(Consumer<Exception> exceptionConsumer) {
        super(exceptionConsumer);
    }

    @Override
    protected Optional<T> parse(String posXml) {
        parseInflections(posXml);

        return parseAfterInflections(posXml);
    }


    private void parseInflections(String posXml) {
        Matcher matcher =
                inflectionPattern.matcher(posXml);

        while (matcher.find()) {
            String inflectionXml =
                    matcher.group(0);

            String form =
                    matcher.group(1);

            String grace =
                    matcher.group(2);


            try {
                consumeInflection(
                        inflectionXml,
                        grace,
                        form
                );
            } catch (Exception ex) {
                exceptionConsumer.accept(ex);
            }
        }
    }

    protected abstract void consumeInflection(
            String inflectionXml,
            String grace,
            String form
    );

    protected abstract Optional<T> parseAfterInflections(String posXml);
}
