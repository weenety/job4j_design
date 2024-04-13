package ru.job4j.collection;

import java.util.NoSuchElementException;

public class SimpleQueue<T> {

    private final SimpleStack<T> input = new SimpleStack<>();
    private final SimpleStack<T> output = new SimpleStack<>();
    private int inputCount = 0;
    private int outputCount = 0;

    public T poll() {
        if (outputCount == 0) {
            while (inputCount > 0) {
                output.push(input.pop());
                inputCount--;
                outputCount++;
            }
        }
        if (outputCount == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        outputCount--;
        return output.pop();
    }

    public void push(T value) {
        input.push(value);
        inputCount++;
    }
}

