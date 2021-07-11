package utils;

public class Solution {
    public Vertex[] path;
    public double distance;
    private int i;

    public Solution() {}

    public Solution(Vertex[] path, double distance) {
        this.path = path;
        this.distance = distance;
    }

    public Solution(Vertex[] path) {
        this.path = path;
    }

    public Solution(int pathSize) {
        this.path = new Vertex[pathSize];
        this.i = 0;
        this.distance = 0;
    }

    public void addVertex(Vertex v) {
        if (this.i == this.path.length) {
            throw new IndexOutOfBoundsException("Array is already full");
        }

        if (this.i != 0) {
            this.distance += this.path[this.i - 1].distanceFrom(v);
        }

        this.path[this.i] = v;
        this.i++;
    }

    public void calculateDistance() {
        this.distance = getDistance(this.path);
    }

    public static double getDistance(Vertex[] path) {
        double distance = 0;
        int i;
        for (i = 0; i < path.length - 1; i++) {
            distance += path[i].distanceFrom(path[i + 1]);
        }
        distance += path[0].distanceFrom(path[i]);

        return distance;
    }

    @Override
    public String toString() {
        return "Solution [distance=" + distance + "]";
    }

}
