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

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class EndingTest {
    @Test
    public void emptyEndingsShouldBeCreated() {
        assertThat(
                new Ending("").getLength(),
                is(0)
        );
    }


    @Test
    public void endingHavingZeroLengthShouldBeCreated() {
        assertThat(
                new Ending("Test", 0).getLength(),
                is(0)
        );
    }


    @Test
    public void endingHavingNegativeLengthShouldNotBeCreated() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Ending("Test", -1);
        });
    }


    @Test
    public void endingHavingLengthOneShouldBeCreated() {
        Ending ending =
                new Ending("Test", 1);

        assertThat(
                ending.getExpression(),
                equalTo("t")
        );

        assertThat(
                ending.getLength(),
                equalTo(1)
        );
    }


    @Test
    public void endingHavingLengthTwoShouldBeCreated() {
        Ending ending =
                new Ending("Test", 2);

        assertThat(
                ending.getExpression(),
                equalTo("st")
        );

        assertThat(
                ending.getLength(),
                equalTo(2)
        );
    }


    @Test
    public void endingHavingLengthThreeShouldBeCreated() {
        Ending ending =
                new Ending("Test", 3);

        assertThat(
                ending.getExpression(),
                equalTo("est")
        );

        assertThat(
                ending.getLength(),
                equalTo(3)
        );
    }


    @Test
    public void endingHavingLengthEqualToTheSourceShouldBeEqualToTheSource() {
        Ending ending =
                new Ending("Test", 4);

        assertThat(
                ending.getExpression(),
                equalTo("Test")
        );

        assertThat(
                ending.getLength(),
                equalTo(4)
        );
    }


    @Test
    public void endingHavingLengthGreaterThanTheSourceShouldNotBeCreated() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Ending("Test", 900);
        });
    }


    @Test
    public void endingsHavingTheSameExpressionShouldBeEqual() {
        Ending endingA =
                new Ending("est");

        Ending endingB =
                new Ending("est");

        assertThat(
                endingA,
                is(equalTo(endingB))
        );
    }


    @Test
    public void endingsHavingDifferentExpressionShouldNotBeEqual() {
        Ending endingA =
                new Ending("est");

        Ending endingB =
                new Ending("t");

        assertThat(
                endingA,
                is(not(equalTo(endingB)))
        );
    }


    @Test
    public void equalEndingsShouldHaveTheSameHashCode() {
        Ending endingA =
                new Ending("est");

        Ending endingB =
                new Ending("est");

        assertThat(
                endingA,
                is(equalTo(endingB))
        );

        assertThat(
                endingA.hashCode(),
                is(equalTo(endingB.hashCode()))
        );
    }


    @Test
    public void subsumesShouldReturnTrueWhenThisEndingExpressionIsStrictlyIncludedAtTheEndOfTheOther() {
        Ending contained =
                new Ending("st");

        Ending containing =
                new Ending("est");

        assertThat(
                contained.subsumes(containing),
                is(true)
        );
    }


    @Test
    public void subsumesShouldReturnFalseWhenOneEndingIsNotAnchoredAtTheEnd() {
        Ending containing =
                new Ending("test");

        Ending inTheMiddle =
                new Ending("es");

        assertThat(
                inTheMiddle.subsumes(containing),
                is(false)
        );
    }


    @Test
    public void subsumesShouldNotBeSymmetric() {
        Ending containing =
                new Ending("est");

        Ending contained =
                new Ending("st");

        assertThat(
                containing.subsumes(contained),
                is(false)
        );
    }


    @Test
    public void subsumesShouldReturnFalseIfThereIsNoStrictInclusion() {
        Ending endingA =
                new Ending("Test", 3);

        Ending endingB =
                new Ending("Alpha", 2);


        assertThat(
                endingA.subsumes(endingB),
                is(false)
        );
    }


    @Test
    public void subsumesShouldReturnFalseWhenEndingsAreEqual() {
        Ending endingA =
                new Ending("est");

        Ending endingB =
                new Ending("est");

        assertThat(
                endingA,
                is(equalTo(endingB))
        );

        assertThat(
                endingA.subsumes(endingB),
                is(false)
        );
    }


    @Test
    public void appliesToShouldReturnTrueForStringsHavingTheGivenEnding() {
        Ending ending =
                new Ending("eta");

        assertThat(
                ending.appliesTo("AlphaBeta"),
                is(true)
        );
    }


    @Test
    public void appliesToShouldReturnFalseForStringsNotHavingTheGivenEnding() {
        Ending ending =
                new Ending("eta");

        assertThat(
                ending.appliesTo("AlphaOmega"),
                is(false)
        );
    }


    @Test
    public void removeFromShouldWorkOnStringsHavingTheGivenEnding() {
        Ending ending =
                new Ending("eta");

        assertThat(
                ending.removeFrom("AlphaBeta"),
                is(equalTo("AlphaB"))
        );
    }


    @Test
    public void removeFromShouldFailOnStringsNotHavingTheGivenEnding() {
        Ending ending =
                new Ending("eta");

        assertThrows(IllegalArgumentException.class, () -> {
            ending.removeFrom("AlphaOmega");
        });
    }


    @Test
    public void addToShouldWork() {
        Ending ending =
                new Ending("Beta");

        assertThat(
                ending.addTo("Alpha"),
                is(equalTo("AlphaBeta"))
        );
    }
}
