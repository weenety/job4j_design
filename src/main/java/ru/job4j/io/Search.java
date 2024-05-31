package ru.job4j.io;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

public class Search {

    public static void main(String[] args) throws IOException {
        if (!validateArgs(args)) {
            throw new IllegalArgumentException("Invalid arguments. Usage: start path, file extension");
        }
        Path start = Paths.get(args[0]);
        search(start, path -> path.toFile().getName().endsWith(args[1])).forEach(System.out::println);
    }

    public static List<Path> search(Path root, Predicate<Path> condition) throws IOException {
        SearchFiles searcher = new SearchFiles(condition);
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();
    }

    private static boolean validateArgs(String[] args) {
        boolean isArgsCorrect = false;
        if (args.length > 1) {
            if ((!args[0].isEmpty() && Files.isDirectory(Paths.get(args[0]))) && !args[1].isEmpty()) {
                isArgsCorrect = true;
            }
        }
        return isArgsCorrect;
    }
}