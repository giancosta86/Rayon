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


import java.util.function.BiFunction;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public abstract class TransformTestBase<T extends Transform> {
    private final BiFunction<String, String, T> transformRetriever;

    public TransformTestBase(BiFunction<String, String, T> transformRetriever) {
        this.transformRetriever = transformRetriever;
    }

    protected void testTransformRetrievalAndApplication(
            String origin,
            String result,
            T expectedTransform
    ) {
        T retrievedTransform =
                transformRetriever.apply(origin, result);

        assertThat(
                retrievedTransform,
                equalTo(expectedTransform)
        );

        String transformResult =
                retrievedTransform.apply(origin);

        assertThat(
                transformResult,
                equalTo(result)
        );
    }
}