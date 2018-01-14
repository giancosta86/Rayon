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

package info.gianlucacosta.rayon.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class StringConversionsTest {
    private final String testString = "This is a test";

    private final List<Character> testList = Arrays.asList('T', 'h', 'i', 's', ' ', 'i', 's', ' ', 'a', ' ', 't', 'e', 's', 't');


    @Test
    public void stringToStreamShouldWork() {
        List<Character> retrievedList =
                StringConversions
                        .stringToStream(testString)
                        .collect(Collectors.toList());

        assertThat(
                retrievedList,
                equalTo(testList)
        );
    }


    @Test
    public void streamToStringShouldWork() {
        String retrievedString =
                StringConversions
                        .streamToString(testList.stream());

        assertThat(
                retrievedString,
                equalTo(testString)
        );
    }


    @Test
    public void stringToDequeShouldWork() {
        Deque<Character> retrievedDeque =
                StringConversions.stringToDeque(testString);

        Deque<Character> expectedDeque =
                new LinkedList<>(testList);

        assertThat(
                retrievedDeque,
                equalTo(expectedDeque)
        );
    }


    @Test
    public void stringToListShouldWork() {
        List<Character> retrievedList =
                StringConversions.stringToList(testString);

        assertThat(
                retrievedList,
                equalTo(testList)
        );
    }
}