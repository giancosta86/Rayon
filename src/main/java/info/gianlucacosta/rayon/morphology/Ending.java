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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Ending of a string expression, containing useful methods
 */
public class Ending implements Subsuming<Ending> {
    /**
     * Given an expression, returns all of its endings of increasing size,
     * starting from the minimum value passed - up to the maximum value passed.
     *
     * @param expression      The source expression
     * @param minEndingLength The minimum length of the endings
     * @param maxEndingLength The maximum length of the endings -
     *                        internally capped by the actual length of the source expression
     * @return A list of endings, sorted by length
     */
    public static List<Ending> getEndings(
            String expression,
            int minEndingLength,
            int maxEndingLength
    ) {
        if (minEndingLength < 0) {
            throw new IllegalArgumentException();
        }

        if (maxEndingLength < minEndingLength) {
            throw new IllegalArgumentException();
        }


        int actualMaxEndingLength =
                Math.min(
                        maxEndingLength,
                        expression.length()
                );


        return IntStream
                .rangeClosed(minEndingLength, actualMaxEndingLength)
                .mapToObj(endingLength ->
                        new Ending(expression, endingLength)
                )
                .collect(
                        Collectors.toList()
                );
    }


    /**
     * @param expression      The source expression
     * @param maxEndingLength The maximum ending length - capped by the actual length of the
     *                        source expression
     * @return The list of endings, with length starting from 1
     */
    public static List<Ending> getEndings(
            String expression,
            int maxEndingLength
    ) {
        return getEndings(
                expression,
                1,
                maxEndingLength
        );
    }


    private final String expression;


    /**
     * Creates an ending having the given internal expression
     *
     * @param expression The internal expression
     */
    public Ending(String expression) {
        this.expression = expression;
    }


    /**
     * Retrieves the ending - having the given length - of the given source expression
     *
     * @param sourceExpression The source expression
     * @param endingLength     The requested length of the ending. Must not exceed the length of the source expression
     */
    public Ending(String sourceExpression, int endingLength) {
        if (endingLength < 0) {
            throw new IllegalArgumentException();
        }

        if (endingLength > sourceExpression.length()) {
            throw new IllegalArgumentException();
        }

        this.expression =
                sourceExpression.substring(
                        sourceExpression.length() - endingLength
                );
    }


    public String getExpression() {
        return expression;
    }


    public int getLength() {
        return expression.length();
    }


    public boolean isEmpty() {
        return expression.isEmpty();
    }


    /**
     * Checks if the given target ends with this ending
     *
     * @param target The target string
     * @return True if the string has this ending
     */
    public boolean appliesTo(String target) {
        return target.endsWith(expression);
    }


    /**
     * Removes this ending from the given target
     *
     * @param target The expression from which this ending should be removed. Must end with this ending
     * @return The target, without this ending
     */
    public String removeFrom(String target) {
        if (!appliesTo(target)) {
            throw new IllegalArgumentException(
                    String.format("'%s' does not end with '%s'",
                            target,
                            expression
                    )
            );
        }

        return target.substring(
                0,
                target.length() - expression.length()
        );
    }


    /**
     * Appends this ending to the given target
     *
     * @param target The target expression
     * @return The target expression, now including this ending
     */
    public String addTo(String target) {
        return target + expression;
    }


    /**
     * Checks whether this ending subsumes the other ending
     *
     * @param other Another ending
     * @return True if this ending expression subsumes - i.e., is
     * strictly included - in that ending expression
     */
    @Override
    public boolean subsumes(Ending other) {
        return other.expression.endsWith(expression) &&
                other.getLength() > getLength();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ending)) return false;
        Ending ending = (Ending) o;
        return Objects.equals(expression, ending.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression);
    }


    @Override
    public String toString() {
        return expression;
    }
}
