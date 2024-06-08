package ru.job4j.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    private String directory;
    private String exclude;
    private String output;

    public void packFiles(List<Path> sources, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            for (Path source : sources) {
                File file = source.toFile();
                zip.putNextEntry(new ZipEntry(source.toString()));
                try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file))) {
                    input.transferTo(zip);
                }
                zip.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void packSingleFile(File source, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            zip.putNextEntry(new ZipEntry(source.getPath()));
            try (BufferedInputStream output = new BufferedInputStream(new FileInputStream(source))) {
                zip.write(output.readAllBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void validateArgs(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Arguments not passed to program");
        }
        ArgsName names = ArgsName.of(args);
        directory = names.get("d");
        exclude = names.get("e");
        output = names.get("o");
        if (!Files.exists(Paths.get(directory))) {
            throw new IllegalArgumentException(String.format("Directory '%s' does not exist", directory));
        }
        if (!exclude.startsWith(".")) {
            throw new IllegalArgumentException(String.format("File extension '%s' should start with '.'", exclude));
        }
        if (!output.endsWith(".zip")) {
            throw new IllegalArgumentException(String.format("Output file '%s' should have '.zip' extension", output));
        }
    }

    public static void main(String[] args) throws IOException {
        Zip zip = new Zip();
        zip.validateArgs(args);
        List<Path> sources = Search.search(Paths.get(zip.directory), path -> !path.toFile().getName().endsWith(zip.exclude.substring(2)));
        zip.packSingleFile(
                new File("./pom.xml"),
                new File("./pom.zip")
        );
        zip.packFiles(sources, new File(zip.output));
    }
}
