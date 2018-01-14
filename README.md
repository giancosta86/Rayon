# Rayon

*Java library for French linguistics*


**Rayon** is a Java library dedicated to French linguistics - based on the excellent GLAWI lexicon (http://redac.univ-tlse2.fr/lexiques/glawi.html).

Rayon employs several advanced technologies so as to deliver an efficient and customizable experience; in particular, it relies on:

* Streamed NIO2 line-reading, ExecutorService, regular expressions and Java 8 new-style closures, to foster efficiency without losing extensibility in terms of lemma analysis

* immutable, functional class tree, focusing on the most important parts of speech (noun, adjective, verb) - still ready for more detailed morphological inspections, as the original XML is kept

* API designed to be simple and usable



## Requirements

Java 8u151 or later compatible is recommended to employ the library.



## Referencing the library

Rayon is available on [Hephaestus](https://bintray.com/giancosta86/Hephaestus) and can be declared as a Gradle or Maven dependency; please refer to [its dedicated page](https://bintray.com/giancosta86/Hephaestus/Rayon).

Alternatively, you could download the JAR file from Hephaestus and manually add it to your project structure.



## Example usage

The very heart of Rayon is the **GlawiParser** class, that contains the **parse(Consumer\<LemmaArticle\>)** method and can be used like this:

```java
try (GlawiParser parser = new GlawiParser(
                Files.newBufferedReader(sourcePath)
        )) {
            parser.parse(lemmaArticle -> {
                //Process the article, by calling its dedicated methods
            });
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
```

Other interesting aspects of the library are:

* **Ending**, to have a simple way to deal with word endings

* **Transform** and **EndingTransform**, to deal with *morphological transforms*

* **FrequencyTracker** and **FrequencyTrackerIndex** to perform *multithreaded statistical analysis*



## Special thanks

Rayon is designed to parse the files produced by the excellent [GLAWI](http://redac.univ-tlse2.fr/lexicons/glawi_en.html) project.

GLAWI is available under a [Creative Commons By-SA 3.0 license](http://creativecommons.org/licenses/by-sa/3.0/) (the same license as Wiktionary, from which it has been extracted).

For further information, please visit [GLAWI's website](http://redac.univ-tlse2.fr/lexicons/glawi_en.html).



## Further references

* [Elysium](https://github.com/giancosta86/Elysium)

* [Esprit](https://github.com/giancosta86/Esprit)

* [Marianne](https://github.com/giancosta86/Marianne)

* [Facebook page](https://www.facebook.com/Elysium-Exploring-French-1864049787240701/)

* [GLAWI](http://redac.univ-tlse2.fr/lexicons/glawi_en.html)
