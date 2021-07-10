package utils;

public class Solution {
    public Vertex[] path;
    public double distance;

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
    }
}
