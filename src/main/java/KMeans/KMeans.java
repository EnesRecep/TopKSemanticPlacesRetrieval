package KMeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMeans {

    //Number of Clusters. This metric should be related to the number of Locations
    private int NUM_CLUSTERS = 3;
    //Number of Locations
    private int NUM_LocationS = 15;
    //Min and Max X and Y
    private static final int MIN_COORDINATE = 0;
    private static final int MAX_COORDINATE = 10;

    private List<Location> Locations;
    private List<Cluster> clusters;

    public KMeans(List<Location> locations, int numberOfClusters) {
        this.Locations = locations;
        this.NUM_CLUSTERS = numberOfClusters;
        this.clusters = new ArrayList();

    }


    //Initializes the process
    public void init() {
        //Create Locations
        //Create Clusters
        //Set Random Centroids
        for (int i = 0; i < NUM_CLUSTERS; i++) {
            Cluster cluster = new Cluster(i);
            Location centroid = createRandomLocation();
            cluster.setCentroid(centroid);
            clusters.add(cluster);
        }

        //Print Initial state
        plotClusters();
    }

    public void plotClusters() {
        for (int i = 0; i < NUM_CLUSTERS; i++) {
            Cluster c = clusters.get(i);
            c.plotCluster();
        }
    }

    //The process to calculate the K Means, with iterating method.
    public void calculate() {
        boolean finish = false;
        int iteration = 0;

        // Add in new data, one at a time, recalculating centroids with each new one. 
        while(!finish) {
            //Clear cluster state
            clearClusters();

            List<Location> lastCentroids = getCentroids();
            //plotClusters();
            //Assign Locations to the closer cluster
            assignCluster();

            //Calculate new centroids.
            calculateCentroids();

            iteration++;

            List<Location> currentCentroids = getCentroids();

            //Calculates total distance between new and old Centroids
            double distance = 0;
            for(int i = 0; i < lastCentroids.size(); i++) {
                var dist = distance(lastCentroids.get(i),currentCentroids.get(i));
                //System.out.println("DIST :" + i + " = " + dist);
                distance += dist;
            }
            System.out.println("#################");
            System.out.println("Iteration: " + iteration);
            System.out.println("Centroid distances: " + distance);
            //plotClusters();

            if(distance == 0) {
                plotClusters();
                finish = true;
            }
        }
    }

    private void clearClusters() {
        for(Cluster cluster : clusters) {
            cluster.clear();
        }
    }

    private List getCentroids() {
        List centroids = new ArrayList(NUM_CLUSTERS);
        for(Cluster cluster : clusters) {
            Location aux = cluster.getCentroid();
            Location Location = new Location(aux.getX(),aux.getY());
            centroids.add(Location);
        }
        return centroids;
    }

    private void assignCluster() {
        double max = Double.MAX_VALUE;
        double min = max;
        int cluster = 0;
        double distance = 0.0;

        clusters.forEach(c -> c.points.clear());

        for(Location Location : Locations) {
            min = max;
            for(int i = 0; i <  NUM_CLUSTERS; i++) {
                Cluster c = clusters.get(i);
                distance = distance(Location, c.getCentroid());
                if(distance < min){
                    min = distance;
                    cluster = i;
                }
            }
            Location.setCluster(cluster);
            clusters.get(cluster).addPoint(Location);
        }
    }

    private void calculateCentroids() {
        for(Cluster cluster : clusters) {
            double sumX = 0;
            double sumY = 0;
            List<Location> list = cluster.getPoints();
            int n_Locations = list.size();

            for(Location Location : list) {
                sumX += Location.getX();
                sumY += Location.getY();
            }

            Location centroid = cluster.getCentroid();
            if(n_Locations > 0) {
                double newX = sumX / n_Locations;
                double newY = sumY / n_Locations;
                centroid.setX(newX);
                centroid.setY(newY);
            }
        }
    }

    public Location createRandomLocation(){
        Random r = new Random();
        double x = -180 + (180 - (-180)) * r.nextDouble();
        double y = -180 + (180 - (-180)) * r.nextDouble();
        return new Location(x,y);
    }
    public  double distance(Location p, Location centroid) {
        return Math.sqrt(Math.pow((centroid.y - p.y), 2) + Math.pow((centroid.x - p.x), 2));
    }
}
