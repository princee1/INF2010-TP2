package tp2;

import tests.Corrector;

import java.io.IOException;

import tp2.HashMap.Node;;

public class Main {
    private static int capacity = 6;

    public static void main(String[] args) throws IOException {
        System.out.println("Bienvenue au deuxieme labo de INF2010!");
         Corrector.executeTester("AllTesters", Corrector::start, 16.0);

        


        // test();
    }

    private static void test() {

        Node<String, Integer> node1 = new Node<String, Integer>("1", 1);
        Node<String, Integer> node2 = new Node<String, Integer>("7", 4);
        Node<String, Integer> node3 = new Node<String, Integer>("13", 9);
        Node<String, Integer> node4 = new Node<String, Integer>("19", 16);
        Node<String, Integer> node5 = new Node<String, Integer>("25", 25);
        Node<String, Integer> node6 = new Node<String, Integer>("31", 36);

        Node<String, Integer>[] map = new Node[capacity];

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;

        HashMap<String, Integer> map2 = new HashMap<String, Integer>(7);
        String key = "1";
        int oldData = -1;
        int hashKey = map2.hash(key);

        // System.out.println(hashKey);
        map2.put("10", 7);
        map2.put("17", 43);
        map2.put("24", 3232);
        map2.put("31", 777);

        System.out.println(map2.get("31"));
        // map2.put("10", 80);
        // map2.put("7", 10);
        // map2.put("15", 322);
        // map2.put("16", 999);

        for (int i = 0; i < map2.map.length; i++) {
            if (map2.map[i] == null) {
                System.out.println(i + "-null");
            } else
                System.out.println(i + "-" + map2.map[i].data);
        }

        // System.out.println(map2.get("15"));
        // map[hash(node1.key)] = node1;

        // System.out.println(map2.put(key, 15));
        // System.out.println(map2.put(key, 25));
        // System.out.println(map2.get(key));
        // Node<String, Integer> currentNode = map[hashKey];
        // System.out.println(oldData);
        try {
            // System.out.println(currentNode.data);

        } catch (Exception e) {
            System.out.println("null");
        }

    }

}
