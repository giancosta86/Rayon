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

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GlawiParserTest extends GlawiParserTestBase {
    @Test
    public void testJournalisteBecauseItHasJustOneNoun() {
        testArticle("journaliste", article -> {
            assertThat(
                    article.getNouns().size(),
                    is(1)
            );

            assertNounCount(
                    article,
                    Optional.of(Gender.EPICENE),
                    Optional.of("journaliste"),
                    Optional.of("journalistes"),
                    1
            );


            assertThat(
                    article.getAdjectives(),
                    is(empty())
            );


            assertThat(
                    article.getVerbs(),
                    is(empty())
            );


            assertThat(
                    article.getOtherPos(),
                    is(empty())
            );
        });
    }


    @Test
    public void testFiançaillesBecauseItHasOnePluralOnlyNoun() {
        testArticle("fiançailles", article -> {
            assertThat(
                    article.getNouns().size(),
                    is(1)
            );

            assertNounCount(
                    article,
                    Optional.of(Gender.FEMININE),
                    Optional.empty(),
                    Optional.of("fiançailles"),
                    1
            );


            assertThat(
                    article.getAdjectives(),
                    is(empty())
            );


            assertThat(
                    article.getVerbs(),
                    is(empty())
            );


            assertThat(
                    article.getOtherPos(),
                    is(empty())
            );
        });
    }


    @Test
    public void testGlacialBecauseItHasJustOneAdjective() {
        testArticle("glacial", article -> {
            assertThat(
                    article.getNouns(),
                    is(empty())
            );


            assertThat(
                    article.getAdjectives().size(),
                    is(1)
            );

            assertAdjectiveCount(
                    article,
                    Optional.of("glacial"),
                    Optional.of("glaciale"),
                    Optional.of("glacials"),
                    Optional.of("glaciales"),
                    1
            );


            assertThat(
                    article.getVerbs(),
                    is(empty())
            );


            assertThat(
                    article.getOtherPos(),
                    is(empty())
            );
        });
    }


    @Test
    public void testBanalBecauseItHasTwoAdjectives() {
        testArticle("banal", article -> {
            assertThat(
                    article.getNouns(),
                    is(empty())
            );


            assertThat(
                    article.getAdjectives().size(),
                    is(2)
            );


            assertAdjectiveCount(
                    article,
                    Optional.of("banal"),
                    Optional.of("banale"),
                    Optional.of("banals"),
                    Optional.of("banales"),
                    1
            );


            assertAdjectiveCount(
                    article,
                    Optional.of("banal"),
                    Optional.of("banale"),
                    Optional.of("banaux"),
                    Optional.of("banales"),
                    1
            );


            assertThat(
                    article.getVerbs(),
                    is(empty())
            );


            assertThat(
                    article.getOtherPos(),
                    is(empty())
            );
        });
    }


    @Test
    public void testDirectorBecauseItHasBothNounAndAdjective() {
        testArticle("directeur", article -> {
            assertThat(
                    article.getNouns().size(),
                    is(1)
            );

            assertNounCount(
                    article,
                    Optional.of(Gender.MASCULINE),
                    Optional.of("directeur"),
                    Optional.of("directeurs"),
                    1
            );


            assertThat(
                    article.getAdjectives().size(),
                    is(1)
            );


            assertAdjectiveCount(
                    article,
                    Optional.of("directeur"),
                    Optional.of("directrice"),
                    Optional.of("directeurs"),
                    Optional.of("directrices"),
                    1
            );


            assertThat(
                    article.getVerbs(),
                    is(empty())
            );


            assertThat(
                    article.getOtherPos(),
                    is(empty())
            );
        });
    }


    @Test
    public void testMousseBecauseItHasManyNounsOneAdjectiveWithOnlySingularAndOneNonLemmaVerb() {
        testArticle("mousse", article -> {
            assertThat(
                    article.getNouns().size(),
                    is(3)
            );

            assertNounCount(
                    article,
                    Optional.of(Gender.MASCULINE),
                    Optional.of("mousse"),
                    Optional.of("mousses"),
                    2
            );

            assertNounCount(
                    article,
                    Optional.of(Gender.FEMININE),
                    Optional.of("mousse"),
                    Optional.of("mousses"),
                    1
            );


            assertThat(
                    article.getAdjectives().size(),
                    is(1)
            );


            assertAdjectiveCount(
                    article,
                    Optional.of("mousse"),
                    Optional.of("mousse"),
                    Optional.empty(),
                    Optional.empty(),
                    1
            );


            assertThat(
                    article.getVerbs(),
                    is(empty())
            );


            assertThat(
                    article.getOtherPos(),
                    is(empty())
            );
        });
    }


    @Test
    public void testParmiBecauseItOnlyContainsOneOtherPartOfSpeech() {
        testArticle("parmi", article -> {
            assertThat(
                    article.getNouns(),
                    is(empty())
            );


            assertThat(
                    article.getAdjectives(),
                    is(empty())
            );


            assertThat(
                    article.getVerbs(),
                    is(empty())
            );


            assertThat(
                    article.getOtherPos().size(),
                    is(1)
            );

            assertOtherPartOfSpeechCount(
                    article,
                    "préposition",
                    1
            );
        });
    }


    @Test
    public void nounsWithNoInflectionsShouldStillBeProcessed() {
        testArticle("journaliste_no_inflections", article -> {
            assertThat(
                    article.getNouns(),
                    is(empty())
            );


            assertThat(
                    article.getAdjectives(),
                    is(empty())
            );


            assertThat(
                    article.getVerbs(),
                    is(empty())
            );

            assertThat(
                    article.getOtherPos(),
                    is(empty())
            );
        });
    }


    @Test
    public void adjectivesWithNoInflectionsShouldNotBeProcessedButTheParsingShouldContinue() {
        testArticle("banal_partial_inflections", article -> {
            assertThat(
                    article.getNouns(),
                    is(empty())
            );


            assertThat(
                    article.getAdjectives().size(),
                    is(1)
            );


            assertAdjectiveCount(
                    article,
                    Optional.of("banal"),
                    Optional.of("banale"),
                    Optional.of("banals"),
                    Optional.of("banales"),
                    1
            );


            assertThat(
                    article.getVerbs(),
                    is(empty())
            );


            assertThat(
                    article.getOtherPos(),
                    is(empty())
            );
        });
    }


    @Test
    public void verbsWithNoInflectionsShouldNotBeParsed() {
        testArticle("faire_partial_inflections", article -> {
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
                    article.getVerbs(),
                    is(empty())
            );


            assertThat(
                    article.getOtherPos(),
                    is(empty())
            );
        });
    }


    @Test
    public void articleTitleShouldWork() {
        testArticle("directeur", article -> {
            assertThat(
                    article.getTitle(),
                    is(equalTo("directeur"))
            );
        });
    }


    @Test
    public void articleXmlDocumentShouldBeFullyAvailable() {
        testArticle("directeur", article -> {
            assertThat(
                    article.getXml(),

                    startsWith("<article>")
            );


            assertThat(
                    article.getXml(),

                    endsWith("</article>")
            );
        });
    }


    @Test
    public void partOfSpeechXmlShouldBeFullyAvailable() {
        testArticle("parmi", article -> {
            OtherPartOfSpeech parmi =
                    article
                            .getOtherPos()
                            .stream()
                            .findFirst()
                            .get();

            assertThat(
                    parmi.getPosXml(),
                    startsWith("<pos")
            );

            assertThat(
                    parmi.getPosXml(),
                    endsWith("</pos>")
            );
        });
    }


    @Test
    public void parseEmptyArticlesToTestParsedArticlesCount() {
        testParser("emptyArticles", parser -> {
            Queue<LemmaArticle> articles =
                    new ConcurrentLinkedQueue<>();

            parser.parse(articles::add);


            assertThat(
                    parser.getParsedArticlesCount(),
                    is(5L)
            );

            assertThat(
                    articles,
                    is(empty())
            );

        });
    }
}
