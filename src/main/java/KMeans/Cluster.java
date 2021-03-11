package KMeans;
import java.util.ArrayList;
import java.util.List;

public class Cluster {

    public List<Location> points;
    public Location centroid;
    public int id;

    //Creates a new Cluster
    public Cluster(int id) {
        this.id = id;
        this.points = new ArrayList();
        this.centroid = null;
    }

    public List getPoints() {
        return points;
    }

    public void addPoint(Location point) {
        points.add(point);
    }

    public void setPoints(List points) {
        this.points = points;
    }

    public Location getCentroid() {
        return centroid;
    }

    public void setCentroid(Location centroid) {
        this.centroid = centroid;
    }

    public int getId() {
        return id;
    }

    public void clear() {
        points.clear();
    }

    public void plotCluster() {
        System.out.print("[Cluster: " + id+"]");
        System.out.print("[Centroid: " + centroid + "]");
        System.out.print("[Number of Points: " + points.size());
//        System.out.println("[Points: \n");
//        for(Location p : points) {
//            System.out.print(p + " -- ");
//        }
        System.out.println("]");
    }

}