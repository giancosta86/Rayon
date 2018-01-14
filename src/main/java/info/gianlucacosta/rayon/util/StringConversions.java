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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * General-purpose utilities for converting to/from strings
 */
public interface StringConversions {
    static Stream<Character> stringToStream(String source) {
        return source
                .chars()
                .mapToObj(charCode -> (char) charCode);
    }


    static String streamToString(Stream<Character> source) {
        StringBuilder result = new StringBuilder();

        source.forEach(result::append);

        return result.toString();
    }


    static Deque<Character> stringToDeque(String source) {
        return
                stringToStream(source)
                        .collect(Collectors.toCollection(LinkedList::new));
    }


    static List<Character> stringToList(String source) {
        return
                stringToStream(source)
                        .collect(Collectors.toList());
    }


    static String throwableToString(Throwable throwable) {
        StringWriter stringWriter =
                new StringWriter();

        throwable.printStackTrace(
                new PrintWriter(
                        stringWriter
                )
        );

        return stringWriter.toString();
    }
}