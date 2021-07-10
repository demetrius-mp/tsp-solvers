package application;

import utils.Solution;
import utils.Vertex;

public class App {
    public static void main(String[] args) throws Exception {
        Vertex v1 = new Vertex(1, 1, 1);
        Vertex v2 = new Vertex(2, 2, 2);

        System.out.println("Test 1: Building solution with path size");
        Solution s1 = new Solution(2);
        s1.addVertex(v1);
        s1.addVertex(v2);
        System.out.println(s1);

        System.out.println();

        System.out.println("Test 2: Building solution with array of vertexes");
        Vertex[] path = {v1, v2};
        s1 = new Solution(path);
        System.out.println(s1);
    }
}
