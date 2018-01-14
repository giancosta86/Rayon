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

package info.gianlucacosta.rayon.statistics;

import info.gianlucacosta.rayon.morphology.Ending;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class FrequencyTrackerIndexTest {
    private static final Ending ending1 =
            new Ending("Test", 3);

    private static final Ending ending2 =
            new Ending("Test", 2);

    private static final Ending ending3 =
            new Ending("Hello", 3);


    private static void fillIndexTrackerForEnding(FrequencyTrackerIndex<Ending, String> index, Ending ending, int classOneItems) {
        IntStream.rangeClosed(1, classOneItems)
                .forEach(i -> {
                    index.trackOccurrence(ending, FrequencyTrackerTest.class1);
                });

        IntStream.rangeClosed(1, 2)
                .forEach(i -> {
                    index.trackOccurrence(ending, FrequencyTrackerTest.class2);
                });

        IntStream.rangeClosed(1, 5)
                .forEach(i -> {
                    index.trackOccurrence(ending, FrequencyTrackerTest.class3);
                });
    }


    @Test
    public void getFrequencyTrackerShouldReturnEmptyWhenTheKeyIsNotFound() {
        FrequencyTrackerIndex<Ending, String> emptyIndex =
                new FrequencyTrackerIndex<>();

        assertThat(
                emptyIndex.getFrequencyTracker(ending1),
                is(Optional.empty())
        );
    }


    @Test
    public void trackOccurrenceShouldCorrectlyCreateTrackers() {
        FrequencyTrackerIndex<Ending, String> index =
                new FrequencyTrackerIndex<>();

        fillIndexTrackerForEnding(index, ending2, 3);


        FrequencyTracker<String> expectedTracker =
                FrequencyTrackerTest.buildFilledTracker(3);


        assertThat(
                index.getFrequencyTracker(ending2),
                equalTo(Optional.of(expectedTracker))
        );
    }


    @Test
    public void simplifyShouldAlwaysWorkWithPerfectlyMatchingPercentages() {
        FrequencyTrackerIndex<Ending, String> index =
                new FrequencyTrackerIndex<>();

        fillIndexTrackerForEnding(index, ending1, 10);
        fillIndexTrackerForEnding(index, ending2, 10);
        fillIndexTrackerForEnding(index, ending3, 10);

        Stream.of(ending1, ending2, ending3)
                .forEach(ending -> {
                    assertThat(
                            index.getFrequencyTracker(ending),
                            is(not(Optional.empty()))
                    );
                });


        index.simplify(0);


        assertThat(
                index.getFrequencyTracker(ending1),
                is(Optional.empty())
        );

        Stream.of(ending2, ending3)
                .forEach(ending -> {
                    assertThat(
                            index.getFrequencyTracker(ending),
                            is(not(Optional.empty()))
                    );
                });
    }


    @Test
    public void simplifyShouldWorkIfPercentageToleranceIsRespected() {
        FrequencyTrackerIndex<Ending, String> index =
                new FrequencyTrackerIndex<>();

        fillIndexTrackerForEnding(index, ending1, 3);
        fillIndexTrackerForEnding(index, ending2, 4);
        fillIndexTrackerForEnding(index, ending3, 50);

        Stream.of(ending1, ending2, ending3)
                .forEach(ending -> {
                    assertThat(
                            index.getFrequencyTracker(ending),
                            is(not(Optional.empty()))
                    );
                });


        index.simplify(10);


        assertThat(
                index.getFrequencyTracker(ending1),
                is(Optional.empty())
        );

        Stream.of(ending2, ending3)
                .forEach(ending -> {
                    assertThat(
                            index.getFrequencyTracker(ending),
                            is(not(Optional.empty()))
                    );
                });
    }


    @Test
    public void simplifyShouldDoNothingIfPercentageToleranceIsNotRespected() {
        FrequencyTrackerIndex<Ending, String> index =
                new FrequencyTrackerIndex<>();

        fillIndexTrackerForEnding(index, ending1, 3);
        fillIndexTrackerForEnding(index, ending2, 4);
        fillIndexTrackerForEnding(index, ending3, 50);

        Stream.of(ending1, ending2, ending3)
                .forEach(ending -> {
                    assertThat(
                            index.getFrequencyTracker(ending),
                            is(not(Optional.empty()))
                    );
                });


        index.simplify(5);

        Stream.of(ending1, ending2, ending3)
                .forEach(ending -> {
                    assertThat(
                            index.getFrequencyTracker(ending),
                            is(not(Optional.empty()))
                    );
                });
    }


    @Test
    public void filterTrackersShouldWork() {
        FrequencyTrackerIndex<Ending, String> index =
                new FrequencyTrackerIndex<>();

        fillIndexTrackerForEnding(index, ending1, 800);
        fillIndexTrackerForEnding(index, ending2, 2000);
        fillIndexTrackerForEnding(index, ending3, 1000);

        Stream.of(ending1, ending2, ending3)
                .forEach(ending -> {
                    assertThat(
                            index.getFrequencyTracker(ending),
                            is(not(Optional.empty()))
                    );
                });


        index.filterTrackers(1400);


        Stream.of(ending1, ending3)
                .forEach(ending -> {
                    assertThat(
                            index.getFrequencyTracker(ending),
                            is(Optional.empty())
                    );
                });


        assertThat(
                index.getFrequencyTracker(ending2),
                is(not(Optional.empty()))
        );
    }


    @Test
    public void iteratorShouldReturnTrackersDecreasing() {
        FrequencyTrackerIndex<Ending, String> index =
                new FrequencyTrackerIndex<>();

        fillIndexTrackerForEnding(index, ending1, 800);
        fillIndexTrackerForEnding(index, ending2, 2000);
        fillIndexTrackerForEnding(index, ending3, 1000);

        FrequencyTracker<String> tracker1 =
                FrequencyTrackerTest.buildFilledTracker(800);

        FrequencyTracker<String> tracker2 =
                FrequencyTrackerTest.buildFilledTracker(2000);

        FrequencyTracker<String> tracker3 =
                FrequencyTrackerTest.buildFilledTracker(1000);

        List<FrequencyTracker<String>> expectedTrackers =
                Arrays.asList(
                        tracker2,
                        tracker3,
                        tracker1
                );


        List<FrequencyTracker<String>> iteratorList =
                StreamSupport.stream(
                        index.spliterator(),
                        false
                )
                        .map(Pair::getValue)
                        .collect(Collectors.toList());

        assertThat(
                iteratorList,
                equalTo(expectedTrackers)
        );
    }


    @Test
    public void indexesHavingTheSameTrackersShouldBeEqual() {
        FrequencyTrackerIndex<Ending, String> indexA =
                new FrequencyTrackerIndex<>();

        fillIndexTrackerForEnding(indexA, ending1, 14);
        fillIndexTrackerForEnding(indexA, ending2, 17);
        fillIndexTrackerForEnding(indexA, ending3, 39);


        FrequencyTrackerIndex<Ending, String> indexB =
                new FrequencyTrackerIndex<>();

        fillIndexTrackerForEnding(indexB, ending1, 14);
        fillIndexTrackerForEnding(indexB, ending2, 17);
        fillIndexTrackerForEnding(indexB, ending3, 39);


        assertThat(
                indexA,
                equalTo(indexB)
        );
    }


    @Test
    public void indexesHavingJustOneDifferenceShouldNotBeEqual() {
        FrequencyTrackerIndex<Ending, String> indexA =
                new FrequencyTrackerIndex<>();

        fillIndexTrackerForEnding(indexA, ending1, 14);
        fillIndexTrackerForEnding(indexA, ending2, 17);
        fillIndexTrackerForEnding(indexA, ending3, 39);


        FrequencyTrackerIndex<Ending, String> indexB =
                new FrequencyTrackerIndex<>();

        fillIndexTrackerForEnding(indexB, ending1, 14);
        fillIndexTrackerForEnding(indexB, ending2, 17);
        fillIndexTrackerForEnding(indexB, ending3, 40);


        assertThat(
                indexA,
                not(equalTo(indexB))
        );
    }
}
