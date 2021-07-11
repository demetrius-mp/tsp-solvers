package application;

import metaheuristics.HillClimbing;
import metaheuristics.SimulatedAnnealing;

public class App {
    public static void main(String[] args) throws Exception {
        String filePath = "./instances/att48.tsp.txt";

        System.out.println(HillClimbing.run(filePath));
        System.out.println(SimulatedAnnealing.run(filePath, 100000, 7, 1e-15, 0.99999));
    }
}
