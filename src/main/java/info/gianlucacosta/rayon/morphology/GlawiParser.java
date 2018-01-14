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

import info.gianlucacosta.rayon.util.ExecutorUtils;
import info.gianlucacosta.rayon.util.StringConversions;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * Parses a GLAWI XML source, calling a Consumer whenever an &lt;article&gt; has been parsed.
 */
public class GlawiParser implements AutoCloseable {
    private static final Logger logger =
            Logger.getLogger(GlawiParser.class.getName());

    private static final Consumer<Exception> defaultExceptionConsumer =
            (exception) -> logger.severe(() ->
                    StringConversions.throwableToString(exception)
            );


    private final BufferedReader sourceReader;
    private final Consumer<Exception> exceptionConsumer;

    private final StringBuilder articleXmlBuilder =
            new StringBuilder();

    private final ExecutorService parserService =
            Executors.newCachedThreadPool();

    private final AtomicLong parsedArticlesCounter =
            new AtomicLong();


    /**
     * Creates the parser from the given source stream.
     * In case of exceptions, they are logged as SEVERE.
     *
     * @param sourceStream The source stream
     */
    public GlawiParser(InputStream sourceStream) {
        this(
                new BufferedReader(
                        new InputStreamReader(
                                sourceStream
                        )
                )
        );
    }


    /**
     * Creates the parser from the given source reader.
     * In case of exceptions, they are logged as SEVERE.
     *
     * @param sourceReader The source reader
     */
    public GlawiParser(BufferedReader sourceReader) {
        this(sourceReader, defaultExceptionConsumer);
    }


    /**
     * Creates the parser from the given source reader
     *
     * @param sourceReader      The source reader
     * @param exceptionConsumer The consumer called whenever a parsing exception is thrown
     */
    public GlawiParser(BufferedReader sourceReader, Consumer<Exception> exceptionConsumer) {
        this.sourceReader = sourceReader;

        this.exceptionConsumer = exceptionConsumer;
    }


    /**
     * Parses the GLAWI XML - calling the given consumer whenever an article has been parsed.
     * <p>
     * <strong>Please, note:</strong> it is up to you to ensure that the data structure
     * accessed by the consumer are thread-safe - as multiple consumer instances will
     * most probably run in parallel.
     *
     * @param articleConsumer The consumer called whenever an article is ready
     */
    public void parse(Consumer<LemmaArticle> articleConsumer) {
        AtomicBoolean inArticle =
                new AtomicBoolean();

        sourceReader.lines()
                .forEach(line -> {
                    String actualLine =
                            line.trim();

                    switch (actualLine) {
                        case "<article>":
                            inArticle.set(true);
                            articleXmlBuilder.append(actualLine + "\n");
                            break;


                        case "</article>":
                            articleXmlBuilder.append(actualLine);

                            String articleXml =
                                    articleXmlBuilder.toString();


                            parserService.submit(() -> {
                                try {
                                    parsedArticlesCounter.incrementAndGet();

                                    LemmaArticleParser articleParser =
                                            new LemmaArticleParser(exceptionConsumer);

                                    Optional<LemmaArticle> articleOption =
                                            articleParser.parse(articleXml);

                                    articleOption.ifPresent(articleConsumer);
                                } catch (Exception ex) {
                                    exceptionConsumer.accept(ex);
                                }
                            });

                            inArticle.set(false);
                            articleXmlBuilder.setLength(0);

                            break;


                        default:
                            if (inArticle.get()) {
                                articleXmlBuilder.append(actualLine + "\n");
                            }

                            break;
                    }
                });


        ExecutorUtils.stopAndJoin(parserService);
    }


    /**
     * @return The total number of articles that were parsed - even if they were
     * not actually consumed, for example because of parsing errors
     */
    public long getParsedArticlesCount() {
        return parsedArticlesCounter.get();
    }


    /**
     * Closes the parser and the underlying source
     *
     * @throws Exception Any exception thrown while closing
     */
    @Override
    public void close() throws Exception {
        sourceReader.close();
    }
}
