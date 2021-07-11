package application;

import java.io.IOException;

import metaheuristics.HillClimbing;
import utils.FileHandler;
import utils.Solution;

public class App {
    public static void main(String[] args) throws Exception {
        String filePath = "./instances/bier127.tsp.txt";

        long start = System.currentTimeMillis();
        test1(filePath);
        System.out.println("Fast algorithm ended in " + (System.currentTimeMillis() - start) + "ms");

        start = System.currentTimeMillis();
        test2(filePath);
        System.out.println("Slow algorithm ended in " + (System.currentTimeMillis() - start) + "ms");
    }

    public static void test1(String filePath) throws IOException {
        Solution currentSolution = FileHandler.read(filePath);
        HillClimbing.shuffleSolution(currentSolution);
        currentSolution.calculateDistance();

        Solution bestNeighbor = HillClimbing.getBestNeighbor(currentSolution);
        while (bestNeighbor.distance < currentSolution.distance) {
            currentSolution = bestNeighbor;
            bestNeighbor = HillClimbing.getBestNeighbor(currentSolution);
        }
        System.out.println(currentSolution);
    }

    public static void test2(String filePath) throws IOException {
        Solution currentSolution = FileHandler.read(filePath);
        HillClimbing.shuffleSolution(currentSolution);
        currentSolution.calculateDistance();

        Solution[] neighborhood = HillClimbing.getNeighborhood(currentSolution);
        Solution bestNeighbor = HillClimbing.findBestNeighbor(neighborhood);
        while (bestNeighbor.distance < currentSolution.distance) {
            currentSolution = bestNeighbor;
            neighborhood = HillClimbing.getNeighborhood(currentSolution);
            bestNeighbor = HillClimbing.findBestNeighbor(neighborhood);
        }
        System.out.println(currentSolution);
    }
}
