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

import info.gianlucacosta.rayon.morphology.Ending;

import java.util.Objects;

/**
 * Transform affecting the ending of the origin expression
 * in order to get a result expression.
 * <p>
 * For example, "sportif" becomes "sportive" by applying
 * an f -&gt; ve ending transform.
 */
public class EndingTransform implements Transform {
    private final Ending endingToRemove;
    private final Ending endingToAdd;


    /**
     * Given the origin and the result, returns the ending transform
     * turning the former into the latter
     *
     * @param origin The origin string
     * @param result The result string
     * @return The ending transform from origin to result
     */
    public static EndingTransform compute(String origin, String result) {
        int firstDifferentCharIndex;

        for (
                firstDifferentCharIndex = 0;
                firstDifferentCharIndex < origin.length();
                firstDifferentCharIndex++
                ) {
            char originChar =
                    origin.charAt(firstDifferentCharIndex);

            char resultChar =
                    result.charAt(firstDifferentCharIndex);

            if (originChar != resultChar) {
                break;
            }
        }


        String removedEnding =
                origin.substring(firstDifferentCharIndex);

        String addedEnding =
                result.substring(firstDifferentCharIndex);

        return new EndingTransform(removedEnding, addedEnding);
    }


    public EndingTransform(String endingToRemove, String endingToAdd) {
        this(
                new Ending(endingToRemove),
                new Ending(endingToAdd)
        );
    }


    public EndingTransform(Ending endingToRemove, Ending endingToAdd) {
        Objects.requireNonNull(endingToRemove);
        Objects.requireNonNull(endingToAdd);

        this.endingToRemove = endingToRemove;
        this.endingToAdd = endingToAdd;
    }

    public Ending getEndingToRemove() {
        return endingToRemove;
    }


    public Ending getEndingToAdd() {
        return endingToAdd;
    }


    @Override
    public String apply(String origin) {
        String originWithoutEnding =
                endingToRemove.removeFrom(origin);

        return endingToAdd.addTo(originWithoutEnding);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EndingTransform)) return false;
        EndingTransform that = (EndingTransform) o;
        return Objects.equals(endingToRemove, that.endingToRemove) &&
                Objects.equals(endingToAdd, that.endingToAdd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(endingToRemove, endingToAdd);
    }

    @Override
    public String toString() {
        if (endingToAdd.isEmpty()) {
            return "-";
        } else {
            if (endingToRemove.isEmpty()) {
                return "-" + endingToAdd;
            } else {
                return String.format(
                        "-%s ⇒ -%s",
                        endingToRemove,
                        endingToAdd
                );
            }
        }
    }
}