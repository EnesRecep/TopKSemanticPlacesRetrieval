package Model;

import java.io.Serializable;

public class WordLoosenessPair implements Comparable<WordLoosenessPair> , Serializable {

    public WordLoosenessPair() {
    }

    public WordLoosenessPair(String word, int looseness) {
        this.word = word;
        this.looseness = looseness;
    }

    public String word;
    public int looseness;

    @Override
    public String toString() {
        return "WordLoosenessPair{" +
                "word=" + word +
                ", looseness=" + looseness +
                '}';
    }

    @Override
    public int compareTo(WordLoosenessPair o) {
        return word.compareTo(o.word);
    }

    @Override
    public boolean equals(Object obj) {
        return word.equals(((WordLoosenessPair)obj).word);
    }
}
