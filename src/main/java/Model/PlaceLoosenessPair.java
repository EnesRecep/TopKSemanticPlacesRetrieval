package Model;

import java.io.Serializable;

public class PlaceLoosenessPair implements Comparable<PlaceLoosenessPair>, Serializable {

    public PlaceLoosenessPair() {
    }

    public PlaceLoosenessPair(int place, int looseness) {
        this.place = place;
        this.looseness = looseness;
    }

    public int place;
    public int looseness;

    @Override
    public String toString() {
        return "PlaceLoosenessPair{" +
                "place=" + place +
                ", looseness=" + looseness +
                '}';
    }

    @Override
    public int compareTo(PlaceLoosenessPair o) {
        if(place > o.place)
            return 1;
        else if(place == o.place)
            return 0;
        else
            return -1;
    }

    @Override
    public boolean equals(Object obj) {
        return place == ((PlaceLoosenessPair)obj).place;
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(place).hashCode();
    }
}
