package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import javax.naming.Name;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NameLoadTest {
    @Test
    void checkEmpty() {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(nameLoad::getMap)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("no data");
    }

    @Test
    void checkArray() {
        NameLoad nameLoad = new NameLoad();
        String[] names = {};
        assertThatThrownBy(() -> nameLoad.parse(names))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Names array is empty");
    }

    @Test
    void validateNameWithoutEqualsSymbol() {
        NameLoad nameLoad = new NameLoad();
        String name = "Bob:Smith";
        assertThatThrownBy(() -> nameLoad.parse(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("^.+")
                .hasMessageContaining(name);
    }

    @Test
    void validateNameWithoutKey() {
        NameLoad nameLoad = new NameLoad();
        String name = "=Smith";
        assertThatThrownBy(() -> nameLoad.parse(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("^.+")
                .hasMessageContaining(name);
    }

    @Test
    void validateNameWithoutValue() {
        NameLoad nameLoad = new NameLoad();
        String name = "Bob=";
        assertThatThrownBy(() -> nameLoad.parse(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("^.+")
                .hasMessageContaining(name);
    }
}