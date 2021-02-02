import java.io.Serializable;

public class Location implements Serializable {

    public Location() {
    }

    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public double x;
    public double y;
}
