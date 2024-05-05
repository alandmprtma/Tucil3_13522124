package wordladder.tucil3_13522124;

public class Pair<K extends Comparable<K>, V> implements Comparable<Pair<K, V>> {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public int compareTo(Pair<K, V> o) {
        return this.key.compareTo(o.key);
    }
}
