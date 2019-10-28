package dsatur;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static HashMap<Integer, List<Integer>> mapCSV = readCSV("C:\\Users\\jpsou\\Google Drive\\UTFPR\\materias\\matematica discreta\\DSATUR\\src\\csv\\grafo07.csv");
    static Integer mapSize = mapCSV.size();
    static HashMap<Integer, Integer> mapDegree = new HashMap<>();

    public static void main(String[] args) {
        HashMap<Integer, List<Integer>> notColored = mapCSV;
        List<Integer> colored = new ArrayList<>();

        HashMap<Integer, Integer> coloredMap = new HashMap<>();

        calculateDegree();
        Integer highestDegree = getHighestDegree();

        coloredMap.put(highestDegree, 0);

        notColored.remove(highestDegree);
        colored.add(highestDegree);

        while (notColored.size() > 0){

        }

//        getNextToBeColored(colored, notColored);

//        System.out.println(getNextToBeColored(colored, notColored));
    }

    private static Integer getNextToBeColored(List<Integer> coloredList, HashMap<Integer, List<Integer>> notColoredList){
        HashMap<Integer, Integer> saturationDegree = new HashMap<>();
        Integer highestDegree = 0;
        Integer nextToBeColored = -1;
        List<Integer> highestSaturationDegreeList = new ArrayList<>();

        for(int i = 1; i <= mapSize; i++){
            saturationDegree.put(i, 0);
        }

        for(Integer colored : coloredList){
            for(int i = 1; i <= mapSize; i++){
                if(i != colored){
                    for(Integer j : notColoredList.get(i)){
                        if(colored == j){
                            saturationDegree.put(i, saturationDegree.get(i) + 1);
                        }
                    }
                }else{
                    saturationDegree.put(i, -1);
                }
            }
        }

        Integer highestSaturationDegree = getHighestSaturationDegree(saturationDegree);

        for(Integer key : saturationDegree.keySet()){
            if(saturationDegree.get(key) == highestSaturationDegree){
                highestSaturationDegreeList.add(key);
            }
        }

        for(Integer aux : highestSaturationDegreeList){
            if(mapDegree.get(aux) > highestDegree){
                highestDegree = mapDegree.get(aux);
                nextToBeColored = aux;
            }
        }

        return nextToBeColored;
    }

    private static Integer getHighestSaturationDegree(HashMap<Integer, Integer> saturationDegreeList){
        Integer highestSaturationDegree = -2;
        for(Integer saturationDegreeKey : saturationDegreeList.keySet()){
            if(saturationDegreeList.get(saturationDegreeKey) > highestSaturationDegree){
                highestSaturationDegree = saturationDegreeList.get(saturationDegreeKey);
            }
        }
        return highestSaturationDegree;
    }

    private static Integer getHighestDegree(){
        Integer maior = 0;
        Integer key = 1;
        for (Integer aux: mapDegree.keySet()){
            if(mapDegree.get(aux) > maior){
                maior = mapDegree.get(aux);
                key = aux;
            }
        }
        return key;
    }

    private static void calculateDegree(){
        for(Integer key : mapCSV.keySet()){
            mapDegree.put(key, mapCSV.get(key).size());
        }
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
