package ru.job4j.io;

import java.io.*;

public class Buffered {

    public static void main(String[] args) {
        try (BufferedReader input = new BufferedReader(new FileReader("data/newData.txt"));
             BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream("data/output.txt", true))) {
            output.write(input.readLine().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
