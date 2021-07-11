package application;

import metaheuristics.HillClimbing;

public class App {
    public static void main(String[] args) throws Exception {
        String filePath = "./instances/att48.tsp.txt";

        System.out.println(HillClimbing.run(filePath));
    }
}
