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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;


public interface ExecutorUtils {
    /**
     * Prevents the executor service from receiving further jobs and waits for its termination
     *
     * @param executorService The executor service to stop
     */
    public static void stopAndJoin(ExecutorService executorService) {
        executorService.shutdown();

        while (true) {
            try {
                if (executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS)) {
                    break;
                }
            } catch (InterruptedException e) {
                //Just do nothing
            }
        }
    }
}
