package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Analysis {

    public void unavailable(String source, String target) {
        try (BufferedReader reader = new BufferedReader(new FileReader(source));
             PrintWriter writer = new PrintWriter(new FileWriter(target))) {
            String line;
            boolean serverUnavailable = false;
            String[] parts;
            while ((line = reader.readLine()) != null) {
                parts = line.split(" ");
                if (("400".equals(parts[0]) || "500".equals(parts[0])) && !serverUnavailable) {
                    writer.print(parts[1]);
                    writer.print(";");
                    serverUnavailable = true;
                } else if (("200".equals(parts[0]) || "300".equals(parts[0])) && serverUnavailable) {
                    writer.print(parts[1]);
                    writer.println(";");
                    serverUnavailable = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Analysis analysis = new Analysis();
        analysis.unavailable("data/server.log", "data/target.csv");
    }
}
