public class Location {

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
