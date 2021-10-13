package tp2;

public class HashMap<KeyType, DataType> {

    private static final int DEFAULT_CAPACITY = 20;
    private static final float DEFAULT_LOAD_FACTOR = 0.5f;
    private static final int CAPACITY_INCREASE_FACTOR = 2;

    private Node<KeyType, DataType>[] map;
    private int size = 0;
    private int capacity;
    private final float loadFactor; // Compression factor

    /**
     * Constructeur par défaut
     */
    public HashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Constructeur par parametre
     * 
     * @param initialCapacity
     */
    public HashMap(int initialCapacity) {
        this(initialCapacity > 0 ? initialCapacity : DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Constructeur par parametres
     * 
     * @param initialCapacity
     * @param loadFactor
     */
    public HashMap(int initialCapacity, float loadFactor) {
        capacity = initialCapacity;
        this.loadFactor = 1 / loadFactor;
        map = new Node[capacity];
    }

    /**
     * Finds the index attached to a particular key This is the hashing function
     * ("Fonction de dispersement")
     * 
     * @param key Value used to access to a particular instance of a DataType within
     *            map
     * @return Index value where this key should be placed in attribute map
     */
    private int hash(KeyType key) {
        int keyHash = key.hashCode() % capacity;
        return Math.abs(keyHash);
    }

    /**
     * @return if map should be rehashed
     */
    private boolean needRehash() {
        return size * loadFactor > capacity;
    }

    /**
     * @return Number of elements currently in the map
     */
    public int size() {
        return size;
    }

    /**
     * @return Current reserved space for the map
     */
    public int capacity() {
        return capacity;
    }

    /**
     * @return if map does not contain any element
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 
     * @param n
     * @return
     */
    private static int nextPrime(int n) {
        if (n % 2 == 0)
            n++;

        for (; !isPrime(n); n += 2)
            ;
        return n;
    }

    /**
     * 
     * @param n
     * @return
     */
    private static boolean isPrime(int n) {
        if (n == 2 || n == 3)
            return true;

        if (n == 1 || n % 2 == 0)
            return false;

        for (int i = 3; i * i <= n; i += 2)
            if (n % i == 0)
                return false;

        return true;

    }

    /**
     * TODO: Increases capacity by CAPACITY_INCREASE_FACTOR (multiplication) and
     * reassigns all contained values within the new map
     */
    private void rehash() {

        Node<KeyType, DataType> oldArray[] = map;
        map = new Node[nextPrime(HashMap.CAPACITY_INCREASE_FACTOR * oldArray.length)];

        for (int i = 0; i < oldArray.length; i++) {
            map[i] = oldArray[i];
        }

        return;
    }

    /**
     * TODO: Finds if map contains a key
     * 
     * @param key Key which we want to know if exists within map
     * @return if key is already used in map
     */
    public boolean containsKey(KeyType key) {
        return getNode(key) != null;
    }

    /**
     * TODO: Finds the value attached to a key
     * 
     * @param key Key which we want to have its value
     * @return DataType instance attached to key (null if not found)
     */
    public DataType get(KeyType key) {
        /**
         * try { Node<KeyType, DataType> currentNode = map[hash(key)]; while
         * (currentNode != null && !currentNode.key.equals(key)) { currentNode =
         * currentNode.next; } return currentNode.data; } catch (NullPointerException e)
         * { return null; }
         */

        try {
            return getNode(key).data;
        } catch (NullPointerException e) {
            return null;
        }

    }

    /**
     * TODO: Assigns a value to a key
     * 
     * @param key Key which will have its value assigned or reassigned
     * @return Old DataType instance at key (null if none existed)
     */
    public DataType put(KeyType key, DataType value) {
        DataType oldData = null;
        Node<KeyType, DataType> currentNode;
        if (containsKey(key)) {
            currentNode = getNode(key);
            oldData = currentNode.data;
            currentNode.data = value;
        } else {
            currentNode = map[hash(key)];
            if (currentNode != null) {
                while (currentNode.next != null) {
                    currentNode = currentNode.next;
                }
                currentNode.next = new Node<KeyType, DataType>(key, value);
            }else {
                currentNode= new Node<KeyType, DataType>(key, value);
            }
            size++;
            if (needRehash()) {
                rehash();
            }
        }

        return oldData;
    }

    /**
     * TODO: Removes the node attached to a key
     * 
     * @param key Key which is contained in the node to remove
     * @return Old DataType instance at key (null if none existed)
     */
    public DataType remove(KeyType key) {
        DataType oldData = null;
        if (containsKey(key)) {
            Node<KeyType, DataType> currentNode = map[hash(key)];
            if (currentNode!=null && !currentNode.key.equals(key)) {
                while (!currentNode.next.equals(key)) {
                    currentNode = currentNode.next;
                }
                oldData = currentNode.data;
                currentNode.next = null;
                // currentNode.next=currentNode.next.next;
            } else {
                map[hash(key)] = null;
                // sa ou l'autre d'avant? map[hash(key)] =map[hash(key)].next;
            }
            size--;
        }

        return oldData;
    }

    private Node<KeyType, DataType> getNode(KeyType key) {
        Node<KeyType, DataType> currentNode = map[hash(key)];
        while (currentNode != null && !currentNode.key.equals(key)) {
            currentNode = currentNode.next;
        }

        return currentNode;
    }

    /**
     * TODO: Removes all nodes contained within the map
     */
    public void clear() {
        for (int i = 0; i < map.length; i++) {
            map[i] = null;
        }
        size = 0;
    }

    /**
     * Definition et implementation de la classe Node
     */
    static class Node<KeyType, DataType> {
        final KeyType key;
        DataType data;
        Node<KeyType, DataType> next;// Pointer to the next node within a Linked List
        // boolean hashNext;

        Node(KeyType key, DataType data) {
            this.key = key;
            this.data = data;
            next = null;
            // hashNext = false;
        }

    }
}