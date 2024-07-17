package ru.job4j.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

public class CSVReader {

    private static void validateArgs(ArgsName argsName) {
        String path = argsName.get("path");
        String delimiter = argsName.get("delimiter");
        String out = argsName.get("out");
        String filter = argsName.get("filter");
        if (path.isEmpty() || !Files.exists(Paths.get(path))) {
            throw new IllegalArgumentException(String.format("Path '%s' does not exist", path));
        }
        if (delimiter.isEmpty()) {
            throw new IllegalArgumentException(String.format("Delimiter '%s' is empty", delimiter));
        }
        if (out.isEmpty() || (!"stdout".equals(out) && !Paths.get(out).getParent().toFile().exists())) {
            throw new IllegalArgumentException("Invalid output: " + out);
        }
        if (filter.isEmpty()) {
            throw new IllegalArgumentException("Filter cannot be empty");
        }
    }

    public static void handle(ArgsName argsName) throws Exception {
        validateArgs(argsName);
        String path = argsName.get("path");
        String delimiter = argsName.get("delimiter");
        String out = argsName.get("out");
        String filter = argsName.get("filter");
        List<Integer> filteredIndexes = new ArrayList<>();
        List<String> filteredLines = new ArrayList<>();
        String[] filters = filter.split(",");
        try (Scanner scanner = new Scanner(new File(path))) {
            if (scanner.hasNextLine()) {
                String headerLine = scanner.nextLine();
                String[] headers = headerLine.split(delimiter);
                for (String filterColumn : filters) {
                    for (int i = 0; i < headers.length; i++) {
                        if (headers[i].equals(filterColumn)) {
                            filteredIndexes.add(i);
                            break;
                        }
                    }
                }
                StringJoiner headerJoiner = new StringJoiner(delimiter);
                for (int filteredIndex : filteredIndexes) {
                    headerJoiner.add(headers[filteredIndex]);
                }
                filteredLines.add(headerJoiner.toString());
                while (scanner.hasNextLine()) {
                    String[] row = scanner.nextLine().split(delimiter);
                    StringJoiner joiner = new StringJoiner(delimiter);
                    for (int index : filteredIndexes) {
                        joiner.add(row[index]);
                    }
                    filteredLines.add(joiner.toString());
                }
            }
        }
        if ("stdout".equals(out)) {
            for (String line : filteredLines) {
                System.out.println(line);
            }
        } else {
            try (PrintStream printStream = new PrintStream(new FileOutputStream(out))) {
                for (String line : filteredLines) {
                    printStream.println(line);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ArgsName argsName = ArgsName.of(args);
        handle(argsName);
    }
}