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

package info.gianlucacosta.rayon.morphology.transform;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class EndingTransformTest extends TransformTestBase<EndingTransform> {
    public EndingTransformTest() {
        super(EndingTransform::compute);
    }


    @Test
    public void identityTransformShouldBeDetected() {
        testTransformRetrievalAndApplication(
                "journaliste",
                "journaliste",
                new EndingTransform(
                        "",
                        ""
                )
        );
    }


    @Test
    public void addingOnlyTransformShouldBeDetected() {
        testTransformRetrievalAndApplication(
                "livre",
                "livres",
                new EndingTransform(
                        "",
                        "s"
                )
        );
    }


    @Test
    public void addingWithDieresisTransformShouldBeDetected() {
        testTransformRetrievalAndApplication(
                "ambigu",
                "ambiguë",
                new EndingTransform(
                        "",
                        "ë"
                )
        );
    }


    @Test
    public void replacingOnlyTransformShouldBeDetected() {
        testTransformRetrievalAndApplication(
                "sportif",
                "sportive",
                new EndingTransform(
                        "f",
                        "ve"
                )
        );
    }


    @Test
    public void moreComplexReplacementsShouldBeDetected() {
        testTransformRetrievalAndApplication(
                "travail",
                "travaux",
                new EndingTransform(
                        "il",
                        "ux"
                )
        );
    }


    @Test
    public void existingDieresisShouldNotAlterTheResult() {
        testTransformRetrievalAndApplication(
                "naïf",
                "naïve",

                new EndingTransform(
                        "f",
                        "ve"
                )
        );
    }


    @Test
    public void testEquality() {
        EndingTransform transform =
                EndingTransform.compute("sportif", "sportive");

        EndingTransform verySameTransform =
                EndingTransform.compute("sportif", "sportive");

        assertThat(
                verySameTransform,
                equalTo(transform)
        );
    }


    @Test
    public void testHashCode() {
        EndingTransform transform =
                EndingTransform.compute("sportif", "sportive");

        EndingTransform verySameTransform =
                EndingTransform.compute("sportif", "sportive");

        assertThat(
                verySameTransform.hashCode(),
                equalTo(transform.hashCode())
        );
    }


    @Test
    public void creatingNonExtensiveEndingTransformBetweenTwoUnrelatedStringsShouldWork() {
        String origin =
                "very, very, very long string";

        String result =
                "short result";


        EndingTransform transform =
                EndingTransform.compute(origin, result);


        assertThat(
                transform.getEndingToRemove().getExpression(),
                is(equalTo(origin))
        );


        assertThat(
                transform.getEndingToAdd().getExpression(),
                is(equalTo(result))
        );
    }


    @Test
    public void applyingTransformsToStringsShorterThanTheSuffixToRemoveShouldFail() {
        EndingTransform suffixTransform =
                new EndingTransform(
                        "alpha",
                        "omegax");

        assertThrows(IllegalArgumentException.class, () -> {
            suffixTransform.apply("a");
        });


    }


    @Test
    public void applyingTransformsToStringsNotHavingTheSuffixToRemoveShouldFail() {
        EndingTransform suffixTransform =
                new EndingTransform(
                        "f",
                        "ve");

        assertThrows(IllegalArgumentException.class, () -> {
            suffixTransform.apply("précieux");
        });
    }


    @Test
    public void toStringForNonAlteringTransformShouldWork() {
        EndingTransform suffixTransform =
                new EndingTransform(
                        "",
                        ""
                );

        assertThat(
                suffixTransform.toString(),
                equalTo("-")
        );
    }


    @Test
    public void toStringForAddingOnlyTransformShouldWork() {
        EndingTransform suffixTransform =
                new EndingTransform(
                        "",
                        "s"
                );

        assertThat(
                suffixTransform.toString(),
                equalTo("-s")
        );
    }


    @Test
    public void toStringForAddingWithDieresisTransformShouldWork() {
        EndingTransform suffixTransform =
                new EndingTransform(
                        "",
                        "ë"
                );

        assertThat(
                suffixTransform.toString(),
                equalTo("-ë")
        );
    }


    @Test
    public void toStringForReplacingOnlyTransformShouldWork() {
        EndingTransform suffixTransform =
                new EndingTransform(
                        "f",
                        "ve"
                );

        assertThat(
                suffixTransform.toString(),
                equalTo("-f ⇒ -ve")
        );
    }


    @Test
    public void toStringForMoreComplexReplacementsShouldWork() {
        EndingTransform suffixTransform =
                new EndingTransform(
                        "nt",
                        "mment"
                );

        assertThat(
                suffixTransform.toString(),
                equalTo("-nt ⇒ -mment")
        );
    }
}
