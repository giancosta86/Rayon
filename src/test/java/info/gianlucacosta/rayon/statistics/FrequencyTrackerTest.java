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

import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class FrequencyTrackerTest {
    public static final String class1 = "Alpha";
    public static final String class2 = "Beta";
    public static final String class3 = "Gamma";


    public static FrequencyTracker<String> buildFilledTracker(int classOneItems) {
        FrequencyTracker<String> tracker =
                new FrequencyTracker<>();

        IntStream.rangeClosed(1, classOneItems)
                .forEach(i -> {
                    tracker.trackOccurrence(class1);
                });

        IntStream.rangeClosed(1, 2)
                .forEach(i -> {
                    tracker.trackOccurrence(class2);
                });

        IntStream.rangeClosed(1, 5)
                .forEach(i -> {
                    tracker.trackOccurrence(class3);
                });

        return tracker;
    }


    @Test
    public void getFrequenciesShouldReturnAnEmptyMapForEmptyTrackers() {
        FrequencyTracker<String> emptyTracker =
                new FrequencyTracker<>();

        assertThat(
                emptyTracker.getFrequencies(),
                is(anEmptyMap())
        );
    }


    @Test
    public void getTotalCountShouldReturnZeroForEmptyTrackers() {
        FrequencyTracker<String> emptyTracker =
                new FrequencyTracker<>();

        assertThat(
                emptyTracker.getTotalCount(),
                is(0L)
        );
    }


    @Test
    public void getPercentagesShouldReturnAnEmptyMapForEmptyTrackers() {
        FrequencyTracker<String> emptyTracker =
                new FrequencyTracker<>();

        assertThat(
                emptyTracker.getPercentages(),
                is(anEmptyMap())
        );
    }


    @Test
    public void getMaximumPercentageShouldReturnZeroForEmptyTrackers() {
        FrequencyTracker<String> emptyTracker =
                new FrequencyTracker<>();

        assertThat(
                emptyTracker.getMaximumPercentage(),
                is(0.0)
        );
    }


    @Test
    public void getMainClassShouldReturnEmptyForEmptyTrackers() {
        FrequencyTracker<String> emptyTracker =
                new FrequencyTracker<>();

        assertThat(
                emptyTracker.getMainClass(),
                is(Optional.empty())
        );
    }


    @Test
    public void getFrequenciesShouldReturnTheCorrectMap() {
        FrequencyTracker<String> filledTracker =
                buildFilledTracker(3);

        Map<String, Long> frequencies =
                filledTracker.getFrequencies();

        assertThat(
                frequencies.get(class1),
                is(3L)
        );

        assertThat(
                frequencies.get(class2),
                is(2L)
        );

        assertThat(
                frequencies.get(class3),
                is(5L)
        );
    }


    @Test
    public void getFrequencyShouldReturnTheCorrectFrequencyIfTracked() {
        FrequencyTracker<String> filledTracker =
                buildFilledTracker(9);

        assertThat(
                filledTracker.getFrequency(class1),
                is(9L)
        );
    }


    @Test
    public void getFrequencyShouldReturnZeroForNonTrackedObjects() {
        FrequencyTracker<String> filledTracker =
                buildFilledTracker(9);

        assertThat(
                filledTracker.getFrequency("INEXISTING"),
                is(0L)
        );
    }


    @Test
    public void totalCountShouldReturnTheSumOfTheFrequencies() {
        FrequencyTracker<String> filledTracker =
                buildFilledTracker(3);

        assertThat(
                filledTracker.getTotalCount(),
                is(10L)
        );
    }


    @Test
    public void getMainClassShouldReturnTheClassHavingTheHighestPercentage() {
        FrequencyTracker<String> filledTracker =
                buildFilledTracker(9000);

        assertThat(
                filledTracker.getMainClass(),
                is(equalTo(
                        Optional.of(class1)
                ))
        );
    }


    @Test
    public void getPercentagesShouldReturnActualPercentages() {
        FrequencyTracker<String> filledTracker =
                buildFilledTracker(3);

        Map<String, Double> expectedPercentages =
                new HashMap<>();

        expectedPercentages.put(class1, 30.0);
        expectedPercentages.put(class2, 20.0);
        expectedPercentages.put(class3, 50.0);

        assertThat(
                filledTracker.getPercentages(),
                equalTo(expectedPercentages)
        );
    }


    @Test
    public void getPercentageShouldReturnTheCorrectPercentageIfTracked() {
        FrequencyTracker<String> filledTracker =
                buildFilledTracker(3);

        assertThat(
                filledTracker.getPercentage(class1),
                is(30.0)
        );
    }


    @Test
    public void getPercentageShouldReturnZeroForNonTrackedObjects() {
        FrequencyTracker<String> filledTracker =
                buildFilledTracker(3);

        assertThat(
                filledTracker.getPercentage("INEXISTING"),
                is(0.0)
        );
    }


    @Test
    public void getMaximumPercentageShouldWork() {
        FrequencyTracker<String> filledTracker =
                buildFilledTracker(3);

        assertThat(
                filledTracker.getMaximumPercentage(),
                is(50.0)
        );
    }


    @Test
    public void overlapsWithShouldAlwaysReturnTrueForTrackersHavingEqualFrequencies() {
        FrequencyTracker<String> filledTracker =
                buildFilledTracker(3);

        FrequencyTracker<String> equalTracker =
                buildFilledTracker(3);


        assertThat(
                filledTracker.overlapsWith(equalTracker, 0),
                is(true)
        );
    }


    @Test
    public void overlapsWithShouldReturnTrueIfPercentageToleranceIsRespected() {
        FrequencyTracker<String> filledTracker =
                buildFilledTracker(3);

        FrequencyTracker<String> similarTracker =
                buildFilledTracker(4);


        assertThat(
                filledTracker.overlapsWith(similarTracker, 10),
                is(true)
        );
    }


    @Test
    public void overlapsWithShouldReturnFalseIfPercentageToleranceIsNotRespected() {
        FrequencyTracker<String> filledTracker =
                buildFilledTracker(3);

        FrequencyTracker<String> similarTracker =
                buildFilledTracker(4);


        assertThat(
                filledTracker.overlapsWith(similarTracker, 5),
                is(false)
        );
    }


    @Test
    public void overlapsShouldNotCrashIfTrackersHaveDifferentItems() {
        FrequencyTracker<String> trackerA =
                new FrequencyTracker<>();

        trackerA.trackOccurrence("Alpha");


        FrequencyTracker<String> trackerB =
                new FrequencyTracker<>();

        trackerB.trackOccurrence("Beta");


        assertThat(
                trackerA.overlapsWith(trackerB, 0),
                is(false)
        );
    }


    @Test
    public void trackersHavingEqualFrequenciesShouldBeEqual() {
        FrequencyTracker<String> trackerA =
                buildFilledTracker(3);

        FrequencyTracker<String> trackerB =
                buildFilledTracker(3);


        assertThat(
                trackerA,
                equalTo(trackerB)
        );
    }


    @Test
    public void trackersHavingDifferentFrequenciesShouldNotBeEqual() {
        FrequencyTracker<String> trackerA =
                buildFilledTracker(3);

        FrequencyTracker<String> trackerB =
                buildFilledTracker(4);


        assertThat(
                trackerA,
                not(equalTo(trackerB))
        );
    }


    @Test
    public void trackersShouldBeOrderedByRelevance() {
        FrequencyTracker<String> trackerA =
                buildFilledTracker(900);

        FrequencyTracker<String> trackerB =
                buildFilledTracker(800);

        FrequencyTracker<String> trackerC =
                buildFilledTracker(700);

        FrequencyTracker<String> trackerD =
                buildFilledTracker(600);

        FrequencyTracker<String> trackerE =
                buildFilledTracker(50);

        List<FrequencyTracker<String>> expectedTrackers =
                Arrays.asList(
                        trackerE,
                        trackerD,
                        trackerC,
                        trackerB,
                        trackerA
                );


        List<FrequencyTracker<String>> trackers =
                Stream.of(
                        trackerC,
                        trackerE,
                        trackerA,
                        trackerB,
                        trackerD
                )
                        .sorted()
                        .collect(Collectors.toList());

        assertThat(
                trackers,
                equalTo(expectedTrackers)
        );
    }


    @Test
    public void filterFrequenciesShouldWork() {
        FrequencyTracker<String> tracker =
                buildFilledTracker(14);

        tracker.filterFrequencies(5);

        Map<String, Long> expectedFrequencies =
                Stream.of(
                        new Pair<>(class1, 14L),
                        new Pair<>(class3, 5L)
                )
                        .collect(
                                Collectors.toMap(
                                        Pair::getKey,
                                        Pair::getValue
                                )
                        );

        assertThat(
                tracker.getFrequencies(),
                is(equalTo(expectedFrequencies))
        );
    }


    @Test
    public void classesInTrackerShouldBeReturnedByFrequencyDecreasing() {
        FrequencyTracker<String> tracker =
                buildFilledTracker(3);

        List<Pair<String, Long>> expectedSortedClasses =
                Arrays.asList(
                        new Pair<>(class3, 5L),
                        new Pair<>(class1, 3L),
                        new Pair<>(class2, 2L)
                );


        List<Pair<String, Long>> iteratorResult =
                StreamSupport.stream(
                        tracker.spliterator(),
                        false
                )
                        .collect(Collectors.toList());

        assertThat(
                iteratorResult,
                is(equalTo(expectedSortedClasses))
        );
    }
}
