package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class NonCollisionMap<K, V> implements SimpleMap<K, V> {

    private static final float LOAD_FACTOR = 0.75f;
    private int capacity = 8;
    private int count = 0;
    private int modCount = 0;
    private MapEntry<K, V>[] table = new MapEntry[capacity];

    @Override
    public boolean put(K key, V value) {
        if (count >= capacity * LOAD_FACTOR) {
            expand();
        }
        if (table[findIndex(key)] == null) {
            table[findIndex(key)] = new MapEntry<>(key, value);
            count++;
            modCount++;
            return true;
        }
        return false;
    }

    private int hash(int hashCode) {
        return hashCode ^ (hashCode >>> 16);
    }

    private int indexFor(int hash) {
        return hash & (capacity - 1);
    }

    private void expand() {
        capacity *= 2;
        MapEntry<K, V>[] newTable = new MapEntry[capacity];
        for (MapEntry<K, V> entry : table) {
            if (entry != null) {
                int hash = hash(Objects.hashCode(entry.key));
                int index = indexFor(hash);
                newTable[index] = entry;
            }
        }
        table = newTable;
    }

    private int findIndex(K key) {
        int hash = hash(Objects.hashCode(key));
        return indexFor(hash);
    }

    @Override
    public V get(K key) {
        if (table[findIndex(key)] != null) {
            if (Objects.equals(Objects.hashCode(table[findIndex(key)].key), Objects.hashCode(key))) {
                if (Objects.equals(table[findIndex(key)].key, key)) {
                    return table[findIndex(key)].value;
                }
            }
        }
        return null;
    }

    @Override
    public boolean remove(K key) {
        if (table[findIndex(key)] != null) {
            if (Objects.equals(Objects.hashCode(table[findIndex(key)].key), Objects.hashCode(key))) {
                if (Objects.equals(table[findIndex(key)].key, key)) {
                    table[findIndex(key)] = null;
                    count--;
                    modCount++;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Iterator<K> iterator() {

        return new Iterator<K>() {

            private int currentIndex = 0;
            private int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                while (currentIndex < table.length && table[currentIndex] == null) {
                    currentIndex++;
                }
                return currentIndex < table.length;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return table[currentIndex++].key;
            }
        };
    }

    private static class MapEntry<K, V> {

        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
