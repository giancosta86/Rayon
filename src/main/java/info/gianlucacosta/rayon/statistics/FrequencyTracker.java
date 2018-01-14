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

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Associates instances of a class with their tracked frequency - somehow like a Map - in addition to providing
 * statistics.
 * <p>
 * This class <b>must</b> be used in a 2-step process:
 * <ol>
 * <li>
 * In the first step, <b>trackOccurrence(item)</b> can be safely used in a <em>multi-threaded</em> context
 * to increment by 1, at every call, the frequency associated with the given item
 * </li>
 * <li>
 * In the end, a single thread should access all the other methods - that are dedicated to statistics and
 * also perform caching
 * </li>
 * </ol>
 *
 * @param <T> The class of the items to track
 */
public class FrequencyTracker<T> implements Comparable<FrequencyTracker<T>>, Iterable<Pair<T, Long>> {
    private final Map<T, Long> frequencies =
            new ConcurrentHashMap<>();

    private Optional<Long> totalCount =
            Optional.empty();

    private Optional<Map<T, Double>> percentages =
            Optional.empty();


    /**
     * The <b>thread-safe</b> method used to track one item of the given class
     *
     * @param item The item to track
     */
    public void trackOccurrence(T item) {
        if (totalCount.isPresent()) {
            throw new IllegalStateException("trackOccurrence() must be called before any statistical method");
        }

        frequencies.merge(
                item,
                1L,
                (oldValue, value) -> oldValue + 1
        );
    }


    /**
     * @return The tracked frequencies, as a Map
     */
    public Map<T, Long> getFrequencies() {
        return Collections.unmodifiableMap(frequencies);
    }


    /**
     * Returns how many times an item has been tracked
     *
     * @param item The item to check
     * @return The frequency associated with the given item; 0 if not tracked
     */
    public Long getFrequency(T item) {
        Long frequency =
                frequencies.get(item);

        return frequency != null ?
                frequency
                :
                0;
    }


    /**
     * @return The total count of tracked item instances
     */
    public Long getTotalCount() {
        if (!totalCount.isPresent()) {
            totalCount =
                    Optional.of(
                            frequencies
                                    .values()
                                    .stream()
                                    .mapToLong(l -> l)
                                    .sum()
                    );
        }

        return totalCount.get();
    }


    /**
     * @return A map of percentages (that is, double values in the range [0; 100]) of each item's frequency
     * in relation to the total frequency count
     */
    public Map<T, Double> getPercentages() {
        if (!percentages.isPresent()) {
            Long totalCount =
                    getTotalCount();

            percentages =
                    Optional.of(
                            frequencies
                                    .entrySet()
                                    .stream()
                                    .parallel()
                                    .collect(
                                            Collectors.toMap(
                                                    Map.Entry::getKey,
                                                    entry -> entry.getValue() * 100.0 / totalCount
                                            )
                                    )
                    );
        }

        return percentages.get();
    }


    /**
     * Gets the percentage for the given item
     *
     * @param item The item
     * @return The actual percentage of the item, if tracked; 0 if not tracked
     */
    public double getPercentage(T item) {
        Double percentage =
                getPercentages().get(item);

        return percentage != null ?
                percentage
                :
                0;
    }

    /**
     * @return The maximum percentage, or 0 if no item was tracked
     */
    public double getMaximumPercentage() {
        return getPercentages()
                .values()
                .stream()
                .mapToDouble(d -> d)
                .max()
                .orElse(0);
    }


    /**
     * @return The instance of T having the highest percentage
     */
    public Optional<T> getMainClass() {
        return getPercentages()
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey);
    }


    /**
     * Tests whether this tracker and another tracker overlap - i.e., their corresponding percentages
     * are equal - or, at least, the absolute value of their difference is not superior to the given
     * percentage tolerance
     *
     * @param other               The other frequency tracker
     * @param percentageTolerance The tolerance - as a percentage - that allows two percentage values to be considered overlapping
     * @return True if the percentages overlap
     */
    public boolean overlapsWith(FrequencyTracker<T> other, double percentageTolerance) {
        Map<T, Double> otherPercentages =
                other.getPercentages();

        return getPercentages()
                .entrySet()
                .stream()
                .parallel()
                .allMatch(entry -> {
                    double thisPercentage =
                            entry.getValue();

                    Double otherPercentage =
                            otherPercentages.get(entry.getKey());


                    double percentageDelta =
                            Math.abs(
                                    thisPercentage - (otherPercentage != null ? otherPercentage : 0)
                            );

                    return percentageDelta <= percentageTolerance;
                });
    }


    /**
     * @return The relevance of this tracker - for now, it coincides with the maximum percentage.
     */
    public double getRelevance() {
        return getMaximumPercentage();
    }


    /**
     * Compares this tracker with another one; trackers are ordered by <i>increasing relevance</i>
     *
     * @param other The other tracker
     * @return The expected compareTo() result
     */
    @Override
    public int compareTo(FrequencyTracker<T> other) {
        return Double.compare(getRelevance(), other.getRelevance());
    }


    /**
     * Deletes all the items whose frequency is less then the given threshold
     *
     * @param minimumFrequencyAllowed The minimum frequency allowed
     */
    public void filterFrequencies(long minimumFrequencyAllowed) {
        frequencies
                .values()
                .removeIf(frequency ->
                        frequency < minimumFrequencyAllowed
                );
    }


    /**
     * FrequencyTracker is an Iterable object - of Pair&lt;T, Long&gt; objects, which are sorted by <i>decreasing frequency</i>
     *
     * @return An iterator of Pair&lt;T, Long&gt;
     */
    @Override
    public Iterator<Pair<T, Long>> iterator() {
        return frequencies
                .entrySet()
                .stream()
                .map(entry -> new Pair<>(entry.getKey(), entry.getValue()))
                .sorted(
                        Comparator.comparing(
                                Pair::getValue,
                                Comparator.reverseOrder()
                        )
                )
                .iterator();
    }


    /**
     * Two trackers are equal if they have the very same frequencies
     *
     * @param o The other tracker
     * @return True if they have the same frequency values
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FrequencyTracker)) return false;
        FrequencyTracker<?> that = (FrequencyTracker<?>) o;
        return Objects.equals(frequencies, that.frequencies);
    }


    @Override
    public int hashCode() {
        return 0;
    }


    @Override
    public String toString() {
        return "FrequencyTracker{" +
                "frequencies=" + frequencies +
                " max percentage: " + getMaximumPercentage() + "%" +
                '}';
    }
}
