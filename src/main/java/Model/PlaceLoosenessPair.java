package Model;

public class PlaceLoosenessPair implements Comparable<PlaceLoosenessPair>{
    public int place;
    public int  looseness;

    public PlaceLoosenessPair() {
    }

    public PlaceLoosenessPair(int place, int looseness) {
        this.place = place;
        this.looseness = looseness;
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
}
