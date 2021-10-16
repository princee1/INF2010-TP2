package tp2;

import java.security.KeyStore;

import javax.lang.model.element.Element;

public class HashMap<KeyType, DataType> {

    private static final int DEFAULT_CAPACITY = 20;
    private static final float DEFAULT_LOAD_FACTOR = 0.5f;
    private static final int CAPACITY_INCREASE_FACTOR = 2;

    public Node<KeyType, DataType>[] map;
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
    public int hash(KeyType key) {
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
     * TODO: Increases capacity by CAPACITY_INCREASE_FACTOR (multiplication) and
     * reassigns all contained values within the new map
     */
    private void rehash() {
        Node<KeyType, DataType> oldArray[] = map;
        Node<KeyType, DataType> currentNode;
        this.capacity = HashMap.CAPACITY_INCREASE_FACTOR * oldArray.length;
        map = new Node[this.capacity];
        size = 0;

        for (int i = 0; i < oldArray.length; i++) {
            currentNode = oldArray[i];
            while (currentNode != null) {
                put(currentNode.key, currentNode.data);
                currentNode = currentNode.next;
                if (currentNode == null)
                    break;
            }
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
        return getCurrentNode(key) != null;
    }

    /**
     * TODO: Finds the value attached to a key
     * 
     * @param key Key which we want to have its value
     * @return DataType instance attached to key (null if not found)
     */
    public DataType get(KeyType key) {
        try {
            return getCurrentNode(key).data;
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
        int hashKey = hash(key);
        Node<KeyType, DataType> currentNode = map[hashKey];
        if (currentNode != null) {
            while (!currentNode.key.equals(key)) {
                if (currentNode.next == null) {
                    currentNode.next = new Node<KeyType, DataType>(key, value);
                    size++;
                    if (needRehash())
                        rehash();
                    return null;
                } else
                    currentNode = currentNode.next;
            }
            oldData = currentNode.data;
            currentNode.data = value;
        } else {
            map[hashKey] = new Node<KeyType, DataType>(key, value);
            size++;
            if (needRehash())
                rehash();
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

        DataType oldDataType;
        int hashKey = hash(key);
        Node<KeyType, DataType> currentNode = map[hashKey];
        if (currentNode != null) {
            while (!currentNode.key.equals(key) && currentNode.next != null) {
                if (currentNode.next.key.equals(key)) {
                    oldDataType = currentNode.next.data;
                    currentNode = currentNode.next.next;
                    size--;
                    return oldDataType;
                } else
                    currentNode = currentNode.next;
            }
            if (currentNode.key.equals(key)) {

                oldDataType = currentNode.data;
                currentNode = currentNode.next;
                size--;
                return oldDataType;
            }
            oldDataType = map[hashKey].data;
            map[hashKey] = null;
            size--;
            return oldDataType;
        } else {
            return null;
        }

    }

    /**
     * TODO: Removes all nodes contained within the map
     */
    public void clear() {
        for (int i = 0; i < this.capacity; i++) {
            map[i] = null;
        }
        this.size = 0;
    }

    private Node<KeyType, DataType> getCurrentNode(KeyType key) {
        Node<KeyType, DataType> currentNode = map[hash(key)];
        while (currentNode != null && !currentNode.key.equals(key)) {
            currentNode = currentNode.next;
            if (currentNode == null)
                break;
        }
        return currentNode;
    }

    /**
     * Definition et implementation de la classe Node
     */
    static class Node<KeyType, DataType> {
        final KeyType key;
        DataType data;
        Node<KeyType, DataType> next; // Pointer to the next node within a Linked List

        Node(KeyType key, DataType data) {
            this.key = key;
            this.data = data;
            next = null;
        }
    }
}