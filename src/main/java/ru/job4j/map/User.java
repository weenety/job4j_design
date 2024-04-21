package ru.job4j.map;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class User {

    private String name;
    private int children;
    private Calendar birthday;

    public User(String name, int children, Calendar birthday) {
        this.name = name;
        this.children = children;
        this.birthday = birthday;
    }

    public static void main(String[] args) {
        Map<User, Object> map = new HashMap<>(16);
        Calendar birthday = Calendar.getInstance();
        User user1 = new User("Pavel", 23, birthday);
        User user2 = new User("Pavel", 23, birthday);
        map.put(user1, new Object());
        map.put(user2, new Object());
        /*
        Поскольку методы hashCode() и equals() не переопределены в классе User
        каждый объект будет иметь уникальный хеш-код,
        и, следовательно, они будут считаться разными ключами, даже если их поля идентичны.
         */
        System.out.println(map);
    }
}
