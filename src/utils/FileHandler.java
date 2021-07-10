package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileHandler {
    public static Solution read(String filePath) throws IOException {
        int pathSize = Integer.parseInt(filePath.replaceAll("\\D", ""));
        Vertex[] path = new Vertex[pathSize];
        int vertexIndex = 0;

        BufferedReader buffRead = new BufferedReader(new FileReader(filePath));
        String linha = buffRead.readLine();

        do {

            if (linha == "" || linha == "\n") {
                linha = null;
            }

            else {

                String[] linhaList = linha.strip().split(" ");
                double[] coords = new double[3];
                int j = 0;

                for (int i = 0; i < linhaList.length; i++) {

                    linhaList[i] = linhaList[i].strip();

                    if (linhaList[i] != "" && linhaList[i] != "\n" && linhaList[i] != " ") {
                        coords[j] = Double.parseDouble(linhaList[i]);
                        j++;
                    }
                }

                path[vertexIndex] = new Vertex((int) coords[0], coords[1], coords[2]);
                vertexIndex++;
            }

            linha = buffRead.readLine();

        } while (linha != null);

        buffRead.close();

        return new Solution(path);
    }
}