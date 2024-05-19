package ru.job4j.io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Analysis {

    public void unavailable(String source, String target) {
        try {
            List<String> sourceLines = Files.readAllLines(Paths.get(source));
            String startTime = null;
            boolean serverUnavailable = false;
            List<String> unavailabilityPeriods = new ArrayList<>();
            for (String line : sourceLines) {
                String[] parts = line.split(" ");
                String status = parts[0];
                String currentTime = parts[1];
                if (("400".equals(status) || "500".equals(status)) && !serverUnavailable) {
                    startTime = currentTime;
                    serverUnavailable = true;
                } else if (("200".equals(status) || "300".equals(status)) && serverUnavailable) {
                    unavailabilityPeriods.add(startTime + ";" + currentTime + ";");
                    serverUnavailable = false;
                }
            }
            try (PrintWriter printWriter = new PrintWriter(new FileWriter(target))) {
                for (String interval : unavailabilityPeriods) {
                    printWriter.println(interval);
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
