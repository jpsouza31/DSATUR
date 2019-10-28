package dsatur;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static HashMap<Integer, List<Integer>> mapCSV = readCSV("/home/joaopc/Documentos/DSATUR/src/csv/grafo07.csv");

    public static void main(String[] args) {

        List<Integer> notColored = new ArrayList<>();
        List<Integer> colored = new ArrayList<>();

        for (int i = 1; i <= mapCSV.size(); i++){
            notColored.add(i);
        }

        HashMap<Integer, Integer> coloredMap = new HashMap<>();

        Integer highestDegree = getHighestDegree(mapCSV);

        coloredMap.put(highestDegree, 0);

        notColored.remove(highestDegree);
        colored.add(highestDegree);


    }

    public static Integer getNextToBeColored(){

        return 0;
    }

    private static Integer getHighestDegree(HashMap<Integer, List<Integer>> map){
        Integer maior = 0;
        Integer key = 1;
        for(int i = 1; i <= map.size(); i++){
            if(map.get(i).size() > maior){
                maior = map.get(i).size();
                key = i;
            }
        }
        return key;
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
