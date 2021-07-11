package metaheuristics;

import java.io.IOException;

import utils.FileHandler;
import utils.Solution;
import utils.Vertex;

public class SimulatedAnnealing {

    public static void main(String[] args) throws IOException {
        // defining instance
        String filePath = "./instances/att48.tsp.txt";
        Solution currentSolution = FileHandler.read(filePath);
        Utils.shuffleSolution(currentSolution);
        currentSolution.calculateDistance();

        // defining parameters
        int currentIteration = 0;
        int maxIterations = 10000000;
        double currentTemperature = 7;
        double minTemperature = 1e-15;
        double coolingRatio = 0.999;

        // controlling neighbor indexes
        int pathSize = currentSolution.path.length;
        int [] neighborIndexes = {0, 0};
        boolean hasNextNeighbor = nextNeighbor(neighborIndexes, pathSize);

        while (currentTemperature > minTemperature && currentIteration < maxIterations && hasNextNeighbor) {
            double neighborDistance = Utils.getNeighborDistance(currentSolution, neighborIndexes[0], neighborIndexes[1]);

            if (acceptSolution(currentSolution.distance, neighborDistance, currentTemperature)) {
                Vertex temp = currentSolution.path[neighborIndexes[0]];
                currentSolution.path[neighborIndexes[0]] = currentSolution.path[neighborIndexes[1]];
                currentSolution.path[neighborIndexes[1]] = temp;
                currentSolution.distance = neighborDistance;

                neighborIndexes[0] = 0;
                neighborIndexes[1] = 0;
            }

            currentIteration++;
            currentTemperature = decreaseTemperature(currentTemperature, coolingRatio);
            hasNextNeighbor = nextNeighbor(neighborIndexes, pathSize);
        }

        System.out.println(currentSolution);
    }

    public static Solution run(String filePath, int maxIterations, double initialTemperature, double minTemperature, double coolingRatio) throws IOException {
        Solution currentSolution = FileHandler.read(filePath);
        Utils.shuffleSolution(currentSolution);
        currentSolution.calculateDistance();

        int currentIteration = 0;
        double currentTemperature = initialTemperature;
        int pathSize = currentSolution.path.length;
        int [] neighborIndexes = {0, 0};
        boolean hasNextNeighbor = nextNeighbor(neighborIndexes, pathSize);

        while (currentTemperature > minTemperature && currentIteration < maxIterations && hasNextNeighbor) {
            double neighborDistance = Utils.getNeighborDistance(currentSolution, neighborIndexes[0], neighborIndexes[1]);

            if (acceptSolution(currentSolution.distance, neighborDistance, currentTemperature)) {
                Vertex temp = currentSolution.path[neighborIndexes[0]];
                currentSolution.path[neighborIndexes[0]] = currentSolution.path[neighborIndexes[1]];
                currentSolution.path[neighborIndexes[1]] = temp;
                currentSolution.distance = neighborDistance;

                neighborIndexes[0] = 0;
                neighborIndexes[1] = 0;
            }

            currentIteration++;
            currentTemperature = decreaseTemperature(currentTemperature, coolingRatio);
            hasNextNeighbor = nextNeighbor(neighborIndexes, pathSize);
        }


        return currentSolution;
    }

    public static boolean acceptSolution(double currentDistance, double neighborDistance, double currentTemperature) {
        return neighborDistance < currentDistance || Math.random() < Math.exp(-Math.abs((neighborDistance - currentDistance) / currentTemperature));
    }

    public static double decreaseTemperature(double currentTemperature, double coolingRatio) {
        return currentTemperature * coolingRatio;
    }

    public static boolean nextNeighbor(int[] currentIndexes, int pathSize) {
        currentIndexes[1]++;
        if(currentIndexes[1] == pathSize) {
            currentIndexes[0]++;
            if (currentIndexes[0] == pathSize - 1) {
                return false;
            }
            currentIndexes[1] = currentIndexes[0] + 1;
        }
        return true;
    }
}
