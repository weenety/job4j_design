package ru.job4j.io;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

public class Search {

    public static void main(String[] args) throws IOException {
        if (validateArgs(args)) {
            Path start = Paths.get(args[0]);
            search(start, path -> path.toFile().getName().endsWith(args[1])).forEach(System.out::println);
        }
    }

    public static List<Path> search(Path root, Predicate<Path> condition) throws IOException {
        SearchFiles searcher = new SearchFiles(condition);
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();
    }

    private static boolean validateArgs(String[] args) {
        if (args.length == 2) {
            if (args[0].isEmpty()) {
                throw new IllegalArgumentException("First argument is empty");
            }
            if (!Files.isDirectory(Paths.get(args[0]))) {
                throw new IllegalArgumentException("Start path not exist");
            }
            if (args[1].isEmpty()) {
                throw new IllegalArgumentException("Second argument is empty");
            }
            if (!args[1].matches("^\\.[a-zA-Z0-9]+$")) {
                throw new IllegalArgumentException("Second argument must contain the file extension");
            }
        } else {
            throw new IllegalArgumentException("Invalid arguments. Usage: start path, file extension");
        }
        return true;
    }
}