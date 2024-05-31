package ru.job4j.io.duplicates;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DuplicatesFinder extends SimpleFileVisitor<Path> {

    private HashMap<FileProperty, List<Path>> fileMap = new HashMap<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        FileProperty fileProperty = new FileProperty(attrs.size(), file.getFileName().toString());
        fileMap.computeIfAbsent(fileProperty, k -> new ArrayList<>()).add(file);
        return FileVisitResult.CONTINUE;
    }

    private void printDuplicates() {
        for (List<Path> paths : fileMap.values()) {
            if (paths.size() > 1) {
                for (Path path : paths) {
                    System.out.println(path.toAbsolutePath());
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        DuplicatesFinder finder = new DuplicatesFinder();
        Files.walkFileTree(Path.of("./"), finder);
        finder.printDuplicates();
    }
}
