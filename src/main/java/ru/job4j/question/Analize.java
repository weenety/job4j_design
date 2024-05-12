package ru.job4j.question;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Analize {

    public static Info diff(Set<User> previous, Set<User> current) {
        int added = 0;
        int changed = 0;
        int deleted;
        Map<Integer, User> mapForPrevious = new HashMap<>();
        for (User user : previous) {
            mapForPrevious.put(user.getId(), user);
        }
        for (User user : current) {
            User previousUser = mapForPrevious.get(user.getId());
            if (previousUser == null) {
                added++;
            } else {
                if (!user.getName().equals(previousUser.getName())) {
                    changed++;
                }
            }
            mapForPrevious.remove(user.getId());
        }
        deleted = mapForPrevious.size();
        return new Info(added, changed, deleted);
    }
}
