package ru.job4j.list;

import java.util.ArrayList;
import java.util.List;

public class ListUsage {

    public static void main(String[] args) {
        List<String> result = new ArrayList<>();
        result.add("one");
        result.add("two");
        result.add("three");
        result.add(0, "four");
        result.addAll(result);
        for (String string : result) {
            System.out.println(string);
        }
    }
}
