
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Vertex {
    public int ID;
    public String name;
    public List<Integer> connectedNodes = new ArrayList<>();
    public boolean isPlace;
    public Point location = new Point();

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
