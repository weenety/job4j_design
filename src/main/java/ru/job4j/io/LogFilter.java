package ru.job4j.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LogFilter {

    private final String file;

    public LogFilter(String file) {
        this.file = file;
    }

    public List<String> filter() {
        List<String> result = new ArrayList<>();
        try (BufferedReader input = new BufferedReader(new FileReader(file))) {
            result = input.lines()
                    .filter(string -> {
                        String[] parts = string.split(" ");
                        return "404".equals(parts[parts.length - 2]);
                    }).toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void saveTo(String out) {
        var data = filter();
        try (PrintWriter output = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(out)
                ))) {
            for (String line : data) {
                output.printf("%s%n", line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LogFilter logFilter = new LogFilter("data/log.txt");
        logFilter.filter().forEach(System.out::println);
        new LogFilter("data/log.txt").saveTo("data/404.txt");
    }
}
