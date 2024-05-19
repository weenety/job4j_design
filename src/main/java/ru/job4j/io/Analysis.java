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
            StringBuilder result = new StringBuilder();
            boolean serverUnavailable = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if ((parts[0].equals("400") || parts[0].equals("500")) && !serverUnavailable) {
                    result.append(parts[1]).append(";");
                    serverUnavailable = true;
                } else if ((parts[0].equals("200") || parts[0].equals("300")) && serverUnavailable) {
                    result.append(parts[1]).append(";");
                    writer.println(result);
                    result.setLength(0);
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
