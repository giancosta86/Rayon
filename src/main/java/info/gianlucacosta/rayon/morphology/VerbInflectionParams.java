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

import java.util.Objects;
import java.util.Optional;

/**
 * Parameters required to get the inflected form of a verb.
 */
public final class VerbInflectionParams implements GraceAnnotated {
    private final Optional<Mood> moodOption;
    private final Optional<Tense> tenseOption;
    private final Optional<Person> personOption;
    private final Optional<Number> numberOption;
    private final Optional<Gender> genderOption;


    /**
     * Creates an InflectionParams object based on the "Vm..." GRACE code of
     * a verb inflection
     *
     * @param verbInflectionGrace The Vm... GRACE code
     * @return The parsed InflectionParams object
     */
    public static VerbInflectionParams getFromGrace(String verbInflectionGrace) {
        char moodCode =
                verbInflectionGrace.charAt(2);

        Optional<Mood> moodOption =
                moodCode != '-' ?
                        Optional.of(GraceAnnotated.getFromGrace(moodCode, Mood.class))
                        :
                        Optional.empty();


        char tenseCode =
                verbInflectionGrace.charAt(3);

        Optional<Tense> tenseOption =
                tenseCode != '-' ?
                        Optional.of(GraceAnnotated.getFromGrace(tenseCode, Tense.class))
                        :
                        Optional.empty();


        char personCode =
                verbInflectionGrace.charAt(4);

        Optional<Person> personOption =
                personCode != '-' ?
                        Optional.of(GraceAnnotated.getFromGrace(personCode, Person.class))
                        :
                        Optional.empty();


        char numberCode =
                verbInflectionGrace.charAt(5);

        Optional<Number> numberOption =
                numberCode != '-' ?
                        Optional.of(GraceAnnotated.getFromGrace(numberCode, Number.class))
                        :
                        Optional.empty();


        char genderCode =
                verbInflectionGrace.charAt(6);

        Optional<Gender> genderOption =
                genderCode != '-' ?
                        Optional.of(GraceAnnotated.getFromGrace(genderCode, Gender.class))
                        :
                        Optional.empty();


        return new VerbInflectionParams(
                moodOption,
                tenseOption,
                personOption,
                numberOption,
                genderOption
        );
    }


    public VerbInflectionParams(
            Optional<Mood> moodOption,
            Optional<Tense> tenseOption,
            Optional<Person> personOption,
            Optional<Number> numberOption,
            Optional<Gender> genderOption
    ) {
        this.moodOption = moodOption;
        this.tenseOption = tenseOption;
        this.personOption = personOption;
        this.numberOption = numberOption;
        this.genderOption = genderOption;
    }


    public Optional<Mood> getMood() {
        return moodOption;
    }

    public Optional<Tense> getTense() {
        return tenseOption;
    }

    public Optional<Person> getPerson() {
        return personOption;
    }

    public Optional<Number> getNumber() {
        return numberOption;
    }

    public Optional<Gender> getGender() {
        return genderOption;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VerbInflectionParams)) return false;
        VerbInflectionParams that = (VerbInflectionParams) o;
        return Objects.equals(moodOption, that.moodOption) &&
                Objects.equals(tenseOption, that.tenseOption) &&
                Objects.equals(personOption, that.personOption) &&
                Objects.equals(numberOption, that.numberOption) &&
                Objects.equals(genderOption, that.genderOption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moodOption, tenseOption, personOption, numberOption, genderOption);
    }


    @Override
    public String getGraceCode() {
        return String.format(
                "Vm%s%s%s%s%s",
                moodOption.map(Mood::getGraceCode).orElse("-"),
                tenseOption.map(Tense::getGraceCode).orElse("-"),
                personOption.map(Person::getGraceCode).orElse("-"),
                numberOption.map(Number::getGraceCode).orElse("-"),
                genderOption.map(Gender::getGraceCode).orElse("-")
        );
    }


    @Override
    public String toString() {
        return getGraceCode();
    }
}
