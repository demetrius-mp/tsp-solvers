package utils;

public class Vertex {
    public int label;
    public double x;
    public double y;

    public Vertex(int label, double x, double y) {
        this.label = label;
        this.x = x;
        this.y = y;
    }

    public double distanceFrom(Vertex other) {
        double r1, r2;

        r1 = Math.pow(this.x - other.x, 2);
        r2 = Math.pow(this.y - other.y, 2);

        return Math.sqrt(r1 + r2);
    }

    @Override
    public String toString() {
        return "" + this.label;
    }
}
