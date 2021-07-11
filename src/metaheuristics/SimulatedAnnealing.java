package metaheuristics;

import java.io.IOException;

import utils.FileHandler;
import utils.Solution;
import utils.Vertex;

public class SimulatedAnnealing {

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        // defining instance
        String filePath = "./instances/att48.tsp.txt";
        Solution currentSolution = FileHandler.read(filePath);
        Utils.shuffleSolution(currentSolution);
        currentSolution.calculateDistance();

        // defining parameters
        int currentIteration = 0;
        int maxIterations = 10000000;
        double currentTemperature = Math.sqrt(currentSolution.path.length);
        double minTemperature = 1e-15;
        double coolingRatio = 0.999;

        // controlling neighbor indexes
        int pathSize = currentSolution.path.length;
        int [] neighborIndexes = {0, 0};
        int i = 0;
        int j = 0;
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
        System.out.println("Ended in " + (System.currentTimeMillis() - start) + "ms");
    }

    private static boolean acceptSolution(double currentDistance, double neighborDistance, double currentTemperature) {
        return neighborDistance < currentDistance || Math.random() < Math.exp(-Math.abs((neighborDistance - currentDistance) / currentTemperature));
    }

    private static double decreaseTemperature(double currentTemperature, double coolingRatio) {
        return currentTemperature * coolingRatio;
    }

    private static boolean nextNeighbor(int[] currentIndexes, int pathSize) {
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

    private static double getNeighborDistance(Solution actual, int i, int j) {
        double difference = 0;
        Vertex[] current = actual.path;
        if (i == 0) {
            if (j < 3) {
                difference -= current[j].distanceFrom(current[j + 1]);
                difference += current[i].distanceFrom(current[j + 1]);

                difference -= current[current.length - 1].distanceFrom(current[i]);
                difference += current[current.length - 1].distanceFrom(current[j]);
            }

            else if (j < current.length - 1) {
                // left side of j vertex
                difference -= current[j - 1].distanceFrom(current[j]);
                difference += current[j - 1].distanceFrom(current[i]);
                // right side of j vertex
                difference -= current[j].distanceFrom(current[j + 1]);
                difference += current[i].distanceFrom(current[j + 1]);


                // last to first vertex
                difference -= current[current.length - 1].distanceFrom(current[i]);
                difference += current[current.length - 1].distanceFrom(current[j]);

                // first to second vertex
                difference -= current[i].distanceFrom(current[i + 1]);
                difference += current[j].distanceFrom(current[i + 1]);
            }

            else {
                difference -= current[i].distanceFrom(current[i + 1]);
                difference += current[j].distanceFrom(current[i + 1]);

                difference -= current[j - 1].distanceFrom(current[j]);
                difference += current[j - 1].distanceFrom(current[i]);
            }
        }

        else {
            int jLastIndex = j == current.length - 1 ? 0 : j + 1;
            if (i == j - 1) {
                difference -= current[i - 1].distanceFrom(current[i]);
                difference += current[i - 1].distanceFrom(current[j]);

                difference -= current[j].distanceFrom(current[jLastIndex]);
                difference += current[i].distanceFrom(current[jLastIndex]);
            }

            else if (j == current.length - 1) {
                difference -= current[i - 1].distanceFrom(current[i]);
                difference += current[i - 1].distanceFrom(current[j]);

                difference -= current[i].distanceFrom(current[i + 1]);
                difference += current[j].distanceFrom(current[i + 1]);

                difference -= current[j - 1].distanceFrom(current[j]);
                difference += current[j - 1].distanceFrom(current[i]);

                difference -= current[j].distanceFrom(current[jLastIndex]);
                difference += current[i].distanceFrom(current[jLastIndex]);
            }

            else {
                difference -= current[i - 1].distanceFrom(current[i]);
                difference += current[i - 1].distanceFrom(current[j]);

                difference -= current[i].distanceFrom(current[i + 1]);
                difference += current[j].distanceFrom(current[i + 1]);

                difference -= current[j - 1].distanceFrom(current[j]);
                difference += current[j - 1].distanceFrom(current[i]);

                difference -= current[j].distanceFrom(current[jLastIndex]);
                difference += current[i].distanceFrom(current[jLastIndex]);
            }
        }

        return actual.distance + difference;
    }

    public static Solution[] getNeighborhood(Solution currentSolution) {
        int n = currentSolution.path.length;
        Solution[] neighborhood = new Solution[(n * (n - 1)) / 2];
        int index = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Vertex[] neighborPath = new Vertex[n];
                System.arraycopy(currentSolution.path, 0, neighborPath, 0, n);
                neighborPath[i] = currentSolution.path[j];
                neighborPath[j] = currentSolution.path[i];

                neighborhood[index++] = new Solution(neighborPath);
            }
        }

        return neighborhood;
    }

    public static Solution findBestNeighbor(Solution[] neighborhood) {
        double bestDistance = Solution.getDistance(neighborhood[0].path);
        Solution bestNeighbor = new Solution(neighborhood[0].path, bestDistance);

        for (int i = 1; i < neighborhood.length; i++) {
            neighborhood[i].calculateDistance();

            if (neighborhood[i].distance < bestDistance) {
                bestDistance = neighborhood[i].distance;
                bestNeighbor = neighborhood[i];
            }
        }

        return bestNeighbor;
    }
}
