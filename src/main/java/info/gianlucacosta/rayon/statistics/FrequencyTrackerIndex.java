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

import info.gianlucacosta.rayon.morphology.Subsuming;
import javafx.util.Pair;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Contains FrequencyTracker objects, stored via a key - in a way similar to a map.
 * <p>
 * Just like FrequencyTracker, this object has 2 phases:
 * <ol>
 * <li>
 * In phase 1, just <b>trackOccurrence()</b> - which is <em>thread-safe</em> - should be called
 * </li>
 * <li>
 * In phase 2, all the other methods should be accessed by a single thread
 * </li>
 * </ol>
 * <p>
 * Of particular importance are <b>filterTrackers()</b> and <b>simplify()</b> (which, for performance reasons,
 * should be called in this order).
 *
 * @param <TKey> The type of the key associated with a FrequencyTracker
 * @param <T>    The type of the instances to be tracked by the internal FrequencyTracker objects
 */
public class FrequencyTrackerIndex<TKey extends Subsuming<TKey>, T>
        implements Iterable<Pair<TKey, FrequencyTracker<T>>> {
    private final Map<TKey, FrequencyTracker<T>> frequencyTrackers =
            new ConcurrentHashMap<>();


    /**
     * If a tracker is not already associated with the given key, creates it;
     * anyway, its trackOccurrence(item) method is called
     *
     * @param key  The key
     * @param item The item to be tracked by the tracker related to the key
     */
    public void trackOccurrence(TKey key, T item) {
        FrequencyTracker<T> frequencyTracker =
                frequencyTrackers.computeIfAbsent(
                        key,
                        k -> new FrequencyTracker<>()
                );

        frequencyTracker.trackOccurrence(item);
    }


    /**
     * Returns the tracker associated with the given key
     *
     * @param key The key
     * @return An Optional value - .of() or .empty() - according to the presence of the key
     */
    public Optional<FrequencyTracker<T>> getFrequencyTracker(TKey key) {
        return Optional.ofNullable(frequencyTrackers.get(key));
    }


    /**
     * Deletes all trackers (and each associated key) whose total count is less than the given value
     *
     * @param minimumTotalCountAccepted The minimum total count value accepted for a tracker
     */
    public void filterTrackers(int minimumTotalCountAccepted) {
        frequencyTrackers
                .entrySet()
                .removeIf(entry -> {
                    FrequencyTracker<T> frequencyTracker =
                            entry.getValue();

                    return frequencyTracker.getTotalCount() < minimumTotalCountAccepted;
                });
    }


    /**
     * Deletes from the index all the trackers that satisfy 2 conditions:
     * <ol>
     * <li>
     * Have a key that is subsumed by another key
     * </li>
     * <li>
     * Have a tracker that overlaps - within
     * the chosen percentage tolerance - with the
     * tracker at point 1
     * </li>
     * </ol>
     *
     * @param percentageTolerance The percentage tolerance used to
     *                            detemine whether trackers overlap
     */
    public void simplify(double percentageTolerance) {
        Set<TKey> keySet =
                new HashSet<>(frequencyTrackers.keySet());

        keySet
                .forEach(inspectedKey -> {
                    boolean isInspectedKeyRedundant =
                            frequencyTrackers
                                    .entrySet()
                                    .stream()
                                    .parallel()
                                    .anyMatch(entry -> {
                                        FrequencyTracker<T> inspectedTracker =
                                                frequencyTrackers.get(inspectedKey);

                                        FrequencyTracker<T> currentTracker =
                                                entry.getValue();

                                        return entry.getKey().subsumes(inspectedKey) &&
                                                inspectedTracker.overlapsWith(
                                                        currentTracker,
                                                        percentageTolerance
                                                );
                                    });

                    if (isInspectedKeyRedundant) {
                        frequencyTrackers.remove(inspectedKey);
                    }
                });
    }


    /**
     * FrequencyTrackerIndexes is an Iterable object - of Pair&lt;Key, FrequencyTracker&lt;T&gt;&gt; objects, which are sorted by <i>decreasing relevance</i>
     *
     * @return An iterator of Pair&lt;Key, FrequencyTracker&lt;T&gt;&gt;
     */
    @Override
    public Iterator<Pair<TKey, FrequencyTracker<T>>> iterator() {
        return
                frequencyTrackers
                        .entrySet()
                        .stream()
                        .sorted(
                                Comparator.comparing(
                                        Map.Entry::getValue,
                                        Comparator.reverseOrder()
                                )
                        )
                        .map(entry ->
                                new Pair<>(entry.getKey(), entry.getValue())
                        )
                        .iterator();
    }


    /**
     * Two FrequencyTrackerIndex instances are equal if their keys and the related
     * tracker are equal
     *
     * @param o The other index
     * @return True if they are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FrequencyTrackerIndex)) return false;
        FrequencyTrackerIndex<?, ?> that = (FrequencyTrackerIndex<?, ?>) o;
        return Objects.equals(frequencyTrackers, that.frequencyTrackers);
    }


    @Override
    public int hashCode() {
        return 0;
    }


    @Override
    public String toString() {
        return "FrequencyTrackerIndex{" +
                "frequencyTrackers=" + frequencyTrackers +
                '}';
    }
}
