package ru.job4j.generics;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

class RoleStoreTest {

    @Test
    void whenAddAndFindThenUsernameIsPetr() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", "Petr"));
        Role result = roleStore.findById("1");
        assertThat(result.getUsername()).isEqualTo("Petr");
    }

    @Test
    void whenAddAndFindThenUserIsNull() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", "Petr"));
        Role result = roleStore.findById("10");
        assertThat(result).isNull();
    }

    @Test
    void whenAddDuplicateAndFindUsernameIsPetr() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", "Petr"));
        roleStore.add(new Role("1", "Maxim"));
        Role result = roleStore.findById("1");
        assertThat(result.getUsername()).isEqualTo("Petr");
    }

    @Test
    void whenReplaceThenUsernameIsMaxim() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", "Petr"));
        roleStore.replace("1", new Role("1", "Maxim"));
        Role result = roleStore.findById("1");
        assertThat(result.getUsername()).isEqualTo("Maxim");
    }

    @Test
    void whenNoReplaceUserThenNoChangeUsername() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", "Petr"));
        roleStore.replace("10", new Role("10", "Maxim"));
        Role result = roleStore.findById("1");
        assertThat(result.getUsername()).isEqualTo("Petr");
    }

    @Test
    void whenDeleteUserThenUserIsNull() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", "Petr"));
        roleStore.delete("1");
        Role result = roleStore.findById("1");
        assertThat(result).isNull();
    }

    @Test
    void whenNoDeleteUserThenUsernameIsPetr() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", "Petr"));
        roleStore.delete("10");
        Role result = roleStore.findById("1");
        assertThat(result.getUsername()).isEqualTo("Petr");
    }

    @Test
    void whenReplaceOkThenTrue() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", "Petr"));
        boolean result = roleStore.replace("1", new Role("1", "Maxim"));
        assertThat(result).isTrue();
    }

    @Test
    void whenReplaceNotOkThenFalse() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", "Petr"));
        boolean result = roleStore.replace("10", new Role("10", "Maxim"));
        assertThat(result).isFalse();
    }
}