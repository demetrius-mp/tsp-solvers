package metaheuristics;

import java.util.Random;

import utils.Solution;
import utils.Vertex;

public class HillClimbing {
    public Solution currentSolution;

    public HillClimbing() {
    }

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

    private double getNeighborDistance(Vertex[] other, int i, int j) {
        double difference = 0;
        Vertex[] current = this.currentSolution.path;
        if (i == 0) {
            if (j < 3) {
                difference -= current[j].distanceFrom(current[j + 1]);
                difference += other[j].distanceFrom(other[j + 1]);

                difference -= current[current.length - 1].distanceFrom(current[i]);
                difference += other[other.length - 1].distanceFrom(other[i]);
            }

            else if (j < current.length - 1) {

                // surroundings of the j vertex
                for (int k = j - 1; k < j + 1; k++) {
                    difference -= current[k].distanceFrom(current[k + 1]);
                    difference += other[k].distanceFrom(other[k + 1]);
                }

                // last to first vertex
                difference -= current[current.length - 1].distanceFrom(current[i]);
                difference += other[other.length - 1].distanceFrom(other[i]);

                // first to second vertex
                difference -= current[i].distanceFrom(current[i + 1]);
                difference += other[i].distanceFrom(other[i + 1]);
            }

            else {
                difference -= current[i].distanceFrom(current[i + 1]);
                difference += other[i].distanceFrom(other[i + 1]);

                difference -= current[j - 1].distanceFrom(current[j]);
                difference += other[j - 1].distanceFrom(other[j]);
            }
        }

        else {
            if (j == current.length - 1) {
                for (int k = i - 1; k < i + 1; k++) {
                    difference -= current[k].distanceFrom(current[k + 1]);
                    difference += other[k].distanceFrom(other[k + 1]);
                }

                difference -= current[j - 1].distanceFrom(current[j]);
                difference += other[j - 1].distanceFrom(other[j]);

                difference -= current[j].distanceFrom(current[0]);
                difference += other[j].distanceFrom(other[0]);
            }

            else {
                int k1 = i - 1;
                int k2 = j - 1;
                int k = 0;
                while (k < 2) {
                    difference -= current[k1].distanceFrom(current[k1 + 1]);
                    difference += other[k1].distanceFrom(other[k1 + 1]);
                    k1++;

                    difference -= current[k2].distanceFrom(current[k2 + 1]);
                    difference += other[k2].distanceFrom(other[k2 + 1]);
                    k2++;

                    k++;
                }
            }
        }

        return this.currentSolution.distance + difference;
    }

    public Solution getBestNeighborhood() {
        Vertex[] currentPath = this.currentSolution.path;
        double bestDistance = Double.POSITIVE_INFINITY;
        Solution bestNeighbor = new Solution();

        for (int i = 1; i < currentPath.length; i++) {
            for (int j = i + 1; j < currentPath.length; j++) {

                // neighbor path = copy of current path, but with 2 interchanged positions
                Vertex[] neighborPath = new Vertex[currentPath.length];
                System.arraycopy(currentPath, 0, neighborPath, 0, currentPath.length);

                neighborPath[i] = currentPath[j];
                neighborPath[j] = currentPath[i];

                double neighborDistance = getNeighborDistance(neighborPath, i, j);
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
