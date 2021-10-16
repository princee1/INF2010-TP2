package tp2;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public final class Interview {

    /**
     * This function returns if the two texts are similar based on if they have a
     * similar entropy of the HashMap
     * 
     * @return boolean based on if the entropy is similar
     */
    public static Double compareEntropies(String filename1, String filename2) throws IOException {
        return Math.abs(calculateEntropy(getFrequencyHashTable(readFile(filename1)))
                - calculateEntropy(getFrequencyHashTable(readFile(filename2))));
    }

    /**
     * This function returns the difference in frequencies of two HashMaps which
     * corresponds to the sum of the differences of frequencies for each letter.
     * 
     * @return the difference in frequencies of two HashMaps
     */
    public static Integer compareFrequencies(String filename1, String filename2) throws IOException {

        int diff = 0;
        HashMap<Character, Integer> mapFile1 = getFrequencyHashTable(readFile(filename1));
        HashMap<Character, Integer> mapFile2 = getFrequencyHashTable(readFile(filename2));
        for (Character c : mapFile1.keySet()) {
            if (mapFile2.containsKey(c))
                diff += Math.abs(mapFile2.get(c) - mapFile1.get(c));
            else
                diff += mapFile1.get(c);
        }
        for (Character c : mapFile2.keySet()) {
            if (!mapFile1.containsKey(c))
                diff += mapFile2.get(c);
        }
        return diff;
    }

    /**
     * 
     * @return This function returns the entropy of the HashMap
     */
    public static Double calculateEntropy(HashMap<Character, Integer> map) {
        double entropy = 0;
        double nbTotalLettre = 0;
        for (Character c : map.keySet()) {
            nbTotalLettre = nbTotalLettre + map.get(c);
        }
        for (Character c : map.keySet()) {
            double period = map.get(c) / nbTotalLettre;
            entropy += (period * ((Math.log(1 / period)) / (Math.log(2))));
        }
        return entropy;
    }

    /**
     * 
     * This function reads a text file {filenamme} and returns the appended string
     * of all lines in the text file
     */
    public static String readFile(String filename) throws IOException {
        BufferedReader bReader;
        String line;
        String returnString = "";
        try {
            bReader = new BufferedReader(new FileReader(new File(filename)));
            line = bReader.readLine();
            while (line != null) {
                returnString += line;
                line = bReader.readLine();
            }
            bReader.close();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }

        return returnString;
    }

    /**
     * T This function takes a string as a parameter and creates and returns a
     * HashTable of character frequencies
     */
    public static HashMap<Character, Integer> getFrequencyHashTable(String text) {
        
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (isAlphabetic(c)) {
                if (map.containsKey(c)) {
                    int value = map.get(c);
                    value++;
                    map.put(c, value);
                } else {
                    map.put(c, 1);
                }
            }
        }
        return map;
    }

    /**
     * This function takes a character as a parameter and returns if it is a letter
     * in the alphabet
     */
    public static Boolean isAlphabetic(Character c) {
        return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'));
    }
}
