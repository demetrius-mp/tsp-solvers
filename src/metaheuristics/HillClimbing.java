package metaheuristics;

import java.io.IOException;

import utils.FileHandler;
import utils.Solution;
import utils.Vertex;

public class HillClimbing {

    public static void main(String[] args) throws IOException {
        String filePath = "./instances/att48.tsp.txt";
        Solution currentSolution = FileHandler.read(filePath);
        Utils.shuffleSolution(currentSolution);
        currentSolution.calculateDistance();

        Solution bestNeighbor = HillClimbing.getBestNeighbor(currentSolution);
        while (bestNeighbor.distance < currentSolution.distance) {
            currentSolution = bestNeighbor;
            bestNeighbor = HillClimbing.getBestNeighbor(currentSolution);
        }
        System.out.println(currentSolution);
    }

    public static Solution run(String filePath) throws IOException {
        Solution currentSolution = FileHandler.read(filePath);
        Utils.shuffleSolution(currentSolution);
        currentSolution.calculateDistance();

        Solution bestNeighbor = HillClimbing.getBestNeighbor(currentSolution);
        while (bestNeighbor.distance < currentSolution.distance) {
            currentSolution = bestNeighbor;
            bestNeighbor = HillClimbing.getBestNeighbor(currentSolution);
        }

        return currentSolution;
    }

    public static Solution getBestNeighbor(Solution currentSolution) {
        int pathSize = currentSolution.path.length;

        double bestDistance = currentSolution.distance;
        Vertex[] bestPath = new Vertex[pathSize];
        System.arraycopy(currentSolution.path, 0, bestPath, 0, pathSize);
        Solution bestNeighbor = new Solution(bestPath, bestDistance);

        int oldI = -1;
        int oldJ = -1;

        for (int i = 0; i < pathSize; i++) {
            for (int j = i + 1; j < pathSize; j++) {
                double neighborDistance = Utils.getNeighborDistance(currentSolution, i, j);

                if (neighborDistance < bestDistance) {
                    if (oldI != -1) {
                        bestNeighbor.path[oldI] = currentSolution.path[oldI];
                        bestNeighbor.path[oldJ] = currentSolution.path[oldJ];
                    }
                    oldI = i;
                    oldJ = j;

                    bestNeighbor.path[i] = currentSolution.path[j];
                    bestNeighbor.path[j] = currentSolution.path[i];
                    bestDistance = neighborDistance;
                    bestNeighbor.distance = neighborDistance;
                }
            }
        }
        return bestNeighbor;
    }
}
