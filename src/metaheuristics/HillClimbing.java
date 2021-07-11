package metaheuristics;

import java.util.Random;

import utils.Solution;
import utils.Vertex;

public class HillClimbing {

    public static void shuffleSolution(Solution solution) {
        Vertex[] path = solution.path;

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

    private static double getNeighborDistance2(Solution actual, int i, int j) {
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
            // could change the last 2 lines of if else with
            // difference -= current[j].distanceFrom(current[(j + 1) % current.length]);
            // difference += current[i].distanceFrom(current[(j + 1) % current.length]);
            // but checking the lengh is less expensive than mod operation
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

    private static double getNeighborDistance(Solution actual, Vertex[] other, int i, int j) {
        double difference = 0;
        Vertex[] current = actual.path;
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
                //Vertex[] neighborPath = new Vertex[pathSize];
                //System.arraycopy(currentSolution.path, 0, neighborPath, 0, pathSize);

                //neighborPath[i] = currentSolution.path[j];
                //neighborPath[j] = currentSolution.path[i];

                //double neighborDistance = HillClimbing.getNeighborDistance(currentSolution, neighborPath, i, j);
                double neighborDistance = HillClimbing.getNeighborDistance2(currentSolution, i, j);
                //double neighborDistance = Solution.getDistance(neighborPath);

                if (neighborDistance < bestDistance) {
                    if (oldI != -1) {
                        bestNeighbor.path[oldI] = currentSolution.path[oldI];
                        bestNeighbor.path[oldJ] = currentSolution.path[oldJ];
                    }
                    oldI = i;
                    oldJ = j;

                    System.out.println(neighborDistance);
                    bestNeighbor.path[i] = currentSolution.path[j];
                    bestNeighbor.path[j] = currentSolution.path[i];
                    bestDistance = neighborDistance;
                    bestNeighbor.distance = neighborDistance;
                    //bestPath = neighborPath;
                }
            }
        }
        return bestNeighbor;
    }
}
