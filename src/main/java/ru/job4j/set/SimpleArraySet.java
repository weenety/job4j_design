package ru.job4j.set;

import ru.job4j.collection.SimpleArrayList;

import java.util.Iterator;
import java.util.Objects;

public class SimpleArraySet<T> implements SimpleSet<T> {

    private SimpleArrayList<T> set = new SimpleArrayList<>(0);

    @Override
    public boolean add(T value) {
        boolean isAdded = false;
        if (!contains(value)) {
            set.add(value);
            isAdded = true;
        }
        return isAdded;
    }

    @Override
    public boolean contains(T value) {
        Iterator<T> iterator = iterator();
        boolean found = false;
        while (iterator.hasNext()) {
            if (Objects.equals(iterator.next(), value)) {
                found = true;
                break;
            }
        }
        return found;
    }

    @Override
    public Iterator<T> iterator() {
        return set.iterator();
    }
}
