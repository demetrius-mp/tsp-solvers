package metaheuristics;

import java.util.Random;

import utils.Solution;
import utils.Vertex;

public class Utils {

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

    public static double getNeighborDistance(Solution actual, int i, int j) {
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
                difference -= current[j - 1].distanceFrom(current[j]);
                difference += current[j - 1].distanceFrom(current[i]);

                difference -= current[j].distanceFrom(current[j + 1]);
                difference += current[i].distanceFrom(current[j + 1]);

                difference -= current[current.length - 1].distanceFrom(current[i]);
                difference += current[current.length - 1].distanceFrom(current[j]);

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
