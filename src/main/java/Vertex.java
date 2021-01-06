
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Point;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    public int ID;
    public String name;
    public List<Integer> connectedNodes = new ArrayList<>();
    public boolean isPlace;
    public Location location = new Location();

    public Vertex(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", connectedNodes=" + connectedNodes +
                ", isPlace=" + isPlace +
                ", location=" + location +
                '}';
    }
}
