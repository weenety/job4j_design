package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BackwardArrayIterator implements Iterator<Integer> {

    private final int[] data;
    private int point;

    public BackwardArrayIterator(int[] data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        return data.length > point;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return data[data.length - data[point++]];
    }
}
