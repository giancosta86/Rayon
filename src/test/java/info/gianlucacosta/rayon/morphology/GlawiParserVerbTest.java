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

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;


public class GlawiParserVerbTest extends GlawiParserTestBase {
    @Test
    public void testFaireBecauseItHasOneVeryIrregularVerbAndOneSingularOnlyNoun() {
        testArticle("faire", article -> {
            assertThat(
                    article.getNouns().size(),
                    is(1)
            );

            assertNounCount(
                    article,
                    Optional.of(Gender.MASCULINE),
                    Optional.of("faire"),
                    Optional.empty(),
                    1
            );

            assertThat(
                    article.getAdjectives(),
                    is(empty())
            );

            assertThat(
                    article.getVerbs().size(),
                    is(1)
            );


            Map<VerbInflectionParams, String> expectedInflections =
                    new HashMap<>();


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.INDICATIVE),
                            Optional.of(Tense.PAST),
                            Optional.of(Person.THIRD),
                            Optional.of(Number.PLURAL),
                            Optional.empty()
                    ),

                    "firent"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.SUBJUNCTIVE),
                            Optional.of(Tense.PRESENT),
                            Optional.of(Person.THIRD),
                            Optional.of(Number.SINGULAR),
                            Optional.empty()
                    ),

                    "fasse"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.CONDITIONAL),
                            Optional.of(Tense.PRESENT),
                            Optional.of(Person.THIRD),
                            Optional.of(Number.PLURAL),
                            Optional.empty()
                    ),

                    "feraient"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.INDICATIVE),
                            Optional.of(Tense.PRESENT),
                            Optional.of(Person.FIRST),
                            Optional.of(Number.SINGULAR),
                            Optional.empty()
                    ),

                    "fais"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.SUBJUNCTIVE),
                            Optional.of(Tense.IMPERFECT),
                            Optional.of(Person.FIRST),
                            Optional.of(Number.SINGULAR),
                            Optional.empty()
                    ),

                    "fisse"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.INDICATIVE),
                            Optional.of(Tense.PAST),
                            Optional.of(Person.SECOND),
                            Optional.of(Number.SINGULAR),
                            Optional.empty()
                    ),

                    "fis"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.INDICATIVE),
                            Optional.of(Tense.FUTURE),
                            Optional.of(Person.THIRD),
                            Optional.of(Number.SINGULAR),
                            Optional.empty()
                    ),

                    "fera"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.SUBJUNCTIVE),
                            Optional.of(Tense.IMPERFECT),
                            Optional.of(Person.SECOND),
                            Optional.of(Number.PLURAL),
                            Optional.empty()
                    ),

                    "fissiez"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.INDICATIVE),
                            Optional.of(Tense.FUTURE),
                            Optional.of(Person.FIRST),
                            Optional.of(Number.SINGULAR),
                            Optional.empty()
                    ),

                    "ferai"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.SUBJUNCTIVE),
                            Optional.of(Tense.PRESENT),
                            Optional.of(Person.FIRST),
                            Optional.of(Number.PLURAL),
                            Optional.empty()
                    ),

                    "fassions"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.INDICATIVE),
                            Optional.of(Tense.FUTURE),
                            Optional.of(Person.THIRD),
                            Optional.of(Number.PLURAL),
                            Optional.empty()
                    ),

                    "feront"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.INDICATIVE),
                            Optional.of(Tense.IMPERFECT),
                            Optional.of(Person.FIRST),
                            Optional.of(Number.SINGULAR),
                            Optional.empty()
                    ),

                    "faisais"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.SUBJUNCTIVE),
                            Optional.of(Tense.IMPERFECT),
                            Optional.of(Person.FIRST),
                            Optional.of(Number.PLURAL),
                            Optional.empty()
                    ),

                    "fissions"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.INDICATIVE),
                            Optional.of(Tense.PAST),
                            Optional.of(Person.SECOND),
                            Optional.of(Number.PLURAL),
                            Optional.empty()
                    ),

                    "fîtes"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.SUBJUNCTIVE),
                            Optional.of(Tense.PRESENT),
                            Optional.of(Person.THIRD),
                            Optional.of(Number.PLURAL),
                            Optional.empty()
                    ),

                    "fassent"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.INDICATIVE),
                            Optional.of(Tense.IMPERFECT),
                            Optional.of(Person.THIRD),
                            Optional.of(Number.PLURAL),
                            Optional.empty()
                    ),

                    "faisaient"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.CONDITIONAL),
                            Optional.of(Tense.PRESENT),
                            Optional.of(Person.SECOND),
                            Optional.of(Number.PLURAL),
                            Optional.empty()
                    ),

                    "feriez"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.IMPERATIVE),
                            Optional.of(Tense.PRESENT),
                            Optional.of(Person.SECOND),
                            Optional.of(Number.SINGULAR),
                            Optional.empty()
                    ),

                    "fais"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.INDICATIVE),
                            Optional.of(Tense.IMPERFECT),
                            Optional.of(Person.SECOND),
                            Optional.of(Number.SINGULAR),
                            Optional.empty()
                    ),

                    "faisais"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.INDICATIVE),
                            Optional.of(Tense.PRESENT),
                            Optional.of(Person.SECOND),
                            Optional.of(Number.SINGULAR),
                            Optional.empty()
                    ),

                    "fais"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.SUBJUNCTIVE),
                            Optional.of(Tense.IMPERFECT),
                            Optional.of(Person.SECOND),
                            Optional.of(Number.SINGULAR),
                            Optional.empty()
                    ),

                    "fisses"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.IMPERATIVE),
                            Optional.of(Tense.PRESENT),
                            Optional.of(Person.FIRST),
                            Optional.of(Number.PLURAL),
                            Optional.empty()
                    ),

                    "faisons"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.SUBJUNCTIVE),
                            Optional.of(Tense.IMPERFECT),
                            Optional.of(Person.THIRD),
                            Optional.of(Number.PLURAL),
                            Optional.empty()
                    ),

                    "fissent"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.SUBJUNCTIVE),
                            Optional.of(Tense.PRESENT),
                            Optional.of(Person.SECOND),
                            Optional.of(Number.PLURAL),
                            Optional.empty()
                    ),

                    "fassiez"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.INDICATIVE),
                            Optional.of(Tense.PRESENT),
                            Optional.of(Person.THIRD),
                            Optional.of(Number.PLURAL),
                            Optional.empty()
                    ),

                    "font"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.CONDITIONAL),
                            Optional.of(Tense.PRESENT),
                            Optional.of(Person.FIRST),
                            Optional.of(Number.PLURAL),
                            Optional.empty()
                    ),

                    "ferions"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.INDICATIVE),
                            Optional.of(Tense.IMPERFECT),
                            Optional.of(Person.FIRST),
                            Optional.of(Number.PLURAL),
                            Optional.empty()
                    ),

                    "faisions"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.CONDITIONAL),
                            Optional.of(Tense.PRESENT),
                            Optional.of(Person.THIRD),
                            Optional.of(Number.SINGULAR),
                            Optional.empty()
                    ),

                    "ferait"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.CONDITIONAL),
                            Optional.of(Tense.PRESENT),
                            Optional.of(Person.SECOND),
                            Optional.of(Number.SINGULAR),
                            Optional.empty()
                    ),

                    "ferais"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.INDICATIVE),
                            Optional.of(Tense.PRESENT),
                            Optional.of(Person.THIRD),
                            Optional.of(Number.SINGULAR),
                            Optional.empty()
                    ),

                    "fait"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.IMPERATIVE),
                            Optional.of(Tense.PRESENT),
                            Optional.of(Person.SECOND),
                            Optional.of(Number.PLURAL),
                            Optional.empty()
                    ),

                    "faites"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.SUBJUNCTIVE),
                            Optional.of(Tense.PRESENT),
                            Optional.of(Person.FIRST),
                            Optional.of(Number.SINGULAR),
                            Optional.empty()
                    ),

                    "fasse"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.INDICATIVE),
                            Optional.of(Tense.PAST),
                            Optional.of(Person.THIRD),
                            Optional.of(Number.SINGULAR),
                            Optional.empty()
                    ),

                    "fit"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.INDICATIVE),
                            Optional.of(Tense.PAST),
                            Optional.of(Person.FIRST),
                            Optional.of(Number.PLURAL),
                            Optional.empty()
                    ),

                    "fîmes"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.INDICATIVE),
                            Optional.of(Tense.PRESENT),
                            Optional.of(Person.FIRST),
                            Optional.of(Number.PLURAL),
                            Optional.empty()
                    ),

                    "faisons"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.SUBJUNCTIVE),
                            Optional.of(Tense.PRESENT),
                            Optional.of(Person.SECOND),
                            Optional.of(Number.SINGULAR),
                            Optional.empty()
                    ),

                    "fasses"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.INDICATIVE),
                            Optional.of(Tense.IMPERFECT),
                            Optional.of(Person.THIRD),
                            Optional.of(Number.SINGULAR),
                            Optional.empty()
                    ),

                    "faisait"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.INDICATIVE),
                            Optional.of(Tense.FUTURE),
                            Optional.of(Person.FIRST),
                            Optional.of(Number.PLURAL),
                            Optional.empty()
                    ),

                    "ferons"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.SUBJUNCTIVE),
                            Optional.of(Tense.IMPERFECT),
                            Optional.of(Person.THIRD),
                            Optional.of(Number.SINGULAR),
                            Optional.empty()
                    ),

                    "fît"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.INDICATIVE),
                            Optional.of(Tense.PAST),
                            Optional.of(Person.FIRST),
                            Optional.of(Number.SINGULAR),
                            Optional.empty()
                    ),

                    "fis"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.INDICATIVE),
                            Optional.of(Tense.PRESENT),
                            Optional.of(Person.SECOND),
                            Optional.of(Number.PLURAL),
                            Optional.empty()
                    ),

                    "faites"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.CONDITIONAL),
                            Optional.of(Tense.PRESENT),
                            Optional.of(Person.FIRST),
                            Optional.of(Number.SINGULAR),
                            Optional.empty()
                    ),

                    "ferais"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.INDICATIVE),
                            Optional.of(Tense.FUTURE),
                            Optional.of(Person.SECOND),
                            Optional.of(Number.SINGULAR),
                            Optional.empty()
                    ),

                    "feras"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.INDICATIVE),
                            Optional.of(Tense.FUTURE),
                            Optional.of(Person.SECOND),
                            Optional.of(Number.PLURAL),
                            Optional.empty()
                    ),

                    "ferez"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.INDICATIVE),
                            Optional.of(Tense.IMPERFECT),
                            Optional.of(Person.SECOND),
                            Optional.of(Number.PLURAL),
                            Optional.empty()
                    ),

                    "faisiez"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.PARTICIPLE),
                            Optional.of(Tense.PRESENT),
                            Optional.empty(),
                            Optional.empty(),
                            Optional.empty()
                    ),

                    "faisant"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.PARTICIPLE),
                            Optional.of(Tense.PAST),
                            Optional.empty(),
                            Optional.of(Number.SINGULAR),
                            Optional.of(Gender.MASCULINE)
                    ),

                    "fait"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.PARTICIPLE),
                            Optional.of(Tense.PAST),
                            Optional.empty(),
                            Optional.of(Number.SINGULAR),
                            Optional.of(Gender.FEMININE)
                    ),

                    "faite"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.PARTICIPLE),
                            Optional.of(Tense.PAST),
                            Optional.empty(),
                            Optional.of(Number.PLURAL),
                            Optional.of(Gender.MASCULINE)
                    ),

                    "faits"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.PARTICIPLE),
                            Optional.of(Tense.PAST),
                            Optional.empty(),
                            Optional.of(Number.PLURAL),
                            Optional.of(Gender.FEMININE)
                    ),

                    "faites"
            );


            expectedInflections.put(
                    new VerbInflectionParams(
                            Optional.of(Mood.INFINITIVE),
                            Optional.empty(),
                            Optional.empty(),
                            Optional.empty(),
                            Optional.empty()
                    ),

                    "faire"
            );


            assertVerbCount(
                    article,
                    expectedInflections,
                    1
            );


            assertThat(
                    article.getOtherPos(),
                    is(empty())
            );
        });
    }


}
