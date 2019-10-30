package dsatur;

import java.io.*;
import java.util.*;

//Created By João Pedro Souza - 2090325

public class Main {

    //Global Variables
    static HashMap<Integer, List<Integer>> mapCSV = readCSV("/home/joaopc/Documentos/DSATUR/src/csv/grafo07.csv");
    static Integer mapSize = mapCSV.size();
    static HashMap<Integer, Integer> mapDegree = new HashMap<>();
    static HashMap<Integer, Integer> saturationDegree = new HashMap<>();

    public static void main(String[] args) {

        //HashMap of not colored nodes
        HashMap<Integer, List<Integer>> notColored = mapCSV;

        //List of already coloreds nodes
        List<Integer> colored = new ArrayList<>();

        //HashMap of colored nodes to print on csv file
        HashMap<Integer, Integer> coloredMap = new HashMap<>();

        calculateDegree();

        //get the first node to be colored
        Integer highestDegree = getHighestDegree();

        coloredMap.put(highestDegree, 0);

        notColored.remove(highestDegree);
        colored.add(highestDegree);

        for(int i = 1; i <= mapSize; i++){
            saturationDegree.put(i, 0);
        }

        while (notColored.size() > 0){
            Integer nextColor = 0;

            Integer nextToBeColored = getNextToBeColored(colored, notColored);

            List<Integer> colorList = new ArrayList<>();

            for(Integer key : notColored.get(nextToBeColored)){
                if(coloredMap.containsKey(key)){
                    colorList.add(coloredMap.get(key));
                }
            }

            //Find the color to the next node to be colored
            while (colorList.contains(nextColor)){
                    nextColor++;
            }

            coloredMap.put(nextToBeColored, nextColor);
            colored.add(nextToBeColored);
            notColored.remove(nextToBeColored);
        }

        System.out.println("Node - Color");
        for(Integer aux : coloredMap.keySet()){
            System.out.println(aux + " - " + coloredMap.get(aux));
        }
        writeCSV(coloredMap);
    }

    private static Integer getNextToBeColored(List<Integer> coloredList, HashMap<Integer, List<Integer>> notColoredList){
        Integer highestDegree = 0;
        Integer nextToBeColored = -1;
        List<Integer> highestSaturationDegreeList = new ArrayList<>();

        //calculation of the saturation degree for each node
        for(Integer colored : coloredList){
            for(Integer i : notColoredList.keySet()){
                for(Integer j : notColoredList.get(i)){
                    if(colored == j){
                        saturationDegree.put(i, saturationDegree.get(i) + 1);
                    }
                }
            }
        }

        //Set -1 to saturation degree for the nodes that already been colored
        for(Integer colored : coloredList){
            saturationDegree.put(colored, -1);
        }

        //Get the list of nodes with the highest saturation degree
        Integer highestSaturationDegree = getHighestSaturationDegree(saturationDegree);
        for(Integer key : saturationDegree.keySet()){
            if(saturationDegree.get(key) == highestSaturationDegree){
                highestSaturationDegreeList.add(key);
            }
        }

        //find the node with highest degree of the highestSaturationDegreeList
        //this node will be the next to be colored
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

    public static void writeCSV(HashMap<Integer, Integer> coloredMap) {

        try {
            FileWriter fw = new FileWriter("/home/joaopc/Documentos/DSATUR/src/csv/out.csv");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append("Node - Color\n");
            for (Integer key : coloredMap.keySet()) {
                bw.append(String.valueOf(key)).append(" - ").append(String.valueOf(coloredMap.get(key))).append("\n");
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
