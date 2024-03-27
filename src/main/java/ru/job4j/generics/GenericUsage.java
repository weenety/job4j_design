package ru.job4j.generics;

import java.util.*;

public class GenericUsage {

    public void printInfo(Collection<? extends Person> collection) {
        for (Iterator<? extends Person> iterator = collection.iterator(); iterator.hasNext();) {
            Person next = iterator.next();
            System.out.println(next);
        }
    }

    public static void main(String[] args) {
        List list = new ArrayList();
        list.add("first");
        list.add("second");
        list.add("third");
        list.add(new Person("name", 21, new Date(913716000000L)));
        System.out.println("Количество элементов в списке: " + list.size());
    }
}
