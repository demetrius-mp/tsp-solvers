package metaheuristics;

import java.util.Random;

import utils.Solution;
import utils.Vertex;

public class HillClimbing {
    public Solution currentSolution;

    public HillClimbing() {}

    public HillClimbing(Solution solution) {
        this.currentSolution = solution;
    }

    public void shuffleSolution() {
        Vertex[] path = this.currentSolution.path;

        Random random = new Random();
        Vertex tmp;
        int j;

        for (int i = 0; i < path.length; i++) {
            j = random.nextInt(path.length);
            tmp = path[i];
            path[i] = path[j];
            path[j] = tmp;
        }
    }

    public Solution getBestNeighborhood() {
        Vertex[] currentPath = this.currentSolution.path;
        double bestDistance = Double.POSITIVE_INFINITY;
        Solution bestNeighbor = new Solution();

        for (int i = 0; i < currentPath.length; i++) {
            for (int j = i + 1; j < currentPath.length; j++) {

                // neighbor path = copy of current path, but with 2 interchanged positions
                Vertex[] neighborPath = new Vertex[currentPath.length];
                System.arraycopy(currentPath, 0, neighborPath, 0, currentPath.length);
                
                neighborPath[i] = currentPath[j];
                neighborPath[j] = currentPath[i];

                double neighborDistance = Solution.getDistance(neighborPath);
                if (neighborDistance < bestDistance) {
                    bestDistance = neighborDistance;

                    bestNeighbor.path = neighborPath;
                    bestNeighbor.distance = neighborDistance;
                }
            }
        }

        return bestNeighbor;
    }
}
