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

package info.gianlucacosta.rayon.phonetics;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public interface Vowels {
    Set<Character> PLAIN_VOWELS =
            Stream.of('A', 'E', 'I', 'O', 'U', 'Y', 'a', 'e', 'i', 'o', 'u', 'y')
                    .collect(Collectors.toSet());


    Set<Character> DIERESIS_VOWELS =
            Stream.of('Ë', 'ë', 'Ï', 'ï')
                    .collect(Collectors.toSet());


    Set<Character> VOWELS =
            Stream.concat(
                    PLAIN_VOWELS.stream(),
                    DIERESIS_VOWELS.stream()
            )
                    .collect(Collectors.toSet());


    static boolean isPlainVowel(char c) {
        return PLAIN_VOWELS.contains(c);
    }


    static boolean isDieresisVowel(char c) {
        return DIERESIS_VOWELS.contains(c);
    }


    static boolean isVowel(char c) {
        return VOWELS.contains(c);
    }


    static boolean canReceiveDieresis(char c) {
        switch (c) {
            case 'E':
            case 'e':
            case 'I':
            case 'i':
                return true;

            default:
                return false;
        }
    }
}