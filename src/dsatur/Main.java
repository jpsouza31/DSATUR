package dsatur;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        HashMap<Integer, List<Integer>> mapCSV = readCSV("/home/joaopc/Documentos/DSATUR/src/csv/grafo07.csv");


    }

    private static HashMap<Integer, List<Integer>> readCSV(String csvFile){
        HashMap<Integer, List<Integer>> mapCSV = new HashMap<>();
        Integer node = null;
        BufferedReader br = null;
        String line = "";
        String csvDivisor = ",";

        try {
            br = new BufferedReader(new FileReader(csvFile));

            while ((line = br.readLine()) != null) {
                String[] all = line.split(csvDivisor);

                node = Integer.valueOf(all[0].trim());

                List<Integer> verticesAdj = new ArrayList<Integer>();
                for(int i = 1; i < all.length; i++){
                    verticesAdj.add(Integer.valueOf(all[i].trim()));
                }

                mapCSV.put(node, verticesAdj);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapCSV;
    }
}
