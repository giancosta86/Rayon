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

/**
 * Generic part of speech (POS) - obtained from a <b>&lt;pos&gt;</b> tag in GLAWI.
 */
public abstract class PartOfSpeech {
    private final String posXml;

    PartOfSpeech(String posXml) {
        this.posXml = posXml;
    }

    /**
     * The original XML tag
     *
     * @return The original <b>&lt;pos...&gt;...&lt;/pos&gt;</b> tag
     */
    public String getPosXml() {
        return posXml;
    }
}
