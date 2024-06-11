package ru.job4j.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ConsoleChat {

    private static final String OUT = "закончить";
    private static final String STOP = "стоп";
    private static final String CONTINUE = "продолжить";
    private final String path;
    private final String botAnswers;

    public ConsoleChat(String path, String botAnswers) {
        this.path = path;
        this.botAnswers = botAnswers;
    }

    public void run() {
        List<String> botAnswers = readPhrases();
        List<String> log = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        boolean isSilent = false;
        Random random = new Random();
        String userInput = "";
        while (!OUT.equalsIgnoreCase(userInput)) {
            userInput = scanner.nextLine();
            log.add(userInput);
            switch (userInput.toLowerCase()) {
                case OUT:
                    break;
                case CONTINUE:
                    isSilent = false;
                    break;
                case STOP:
                    isSilent = true;
                    break;
                default:
                    if (!isSilent && !botAnswers.isEmpty()) {
                        int randomIndex = random.nextInt(botAnswers.size());
                        String botResponse = botAnswers.get(randomIndex);
                        System.out.println("Ответ бота: " + botResponse);
                        log.add(botResponse);
                    }
                    break;
            }
        }
        saveLog(log);
    }

    private List<String> readPhrases() {
        List<String> phrases = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(botAnswers))) {
            String line;
            while ((line = reader.readLine()) != null) {
                phrases.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return phrases;
    }

    private void saveLog(List<String> log) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (String s : log) {
                writer.write(s);
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String logFilePath = "data/logFile.txt";
        String botAnswersFilePath = "data/botAnswers.txt";
        ConsoleChat consoleChat = new ConsoleChat(logFilePath, botAnswersFilePath);
        consoleChat.run();
    }
}
