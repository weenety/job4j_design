package ru.job4j.io;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class ConfigTest {

    @Test
    void whenConfigTest() {
        String path = "./data/app.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("hibernate.connection.username")).isEqualTo("postgres");
    }

    @Test
    void whenLoadFilterCorrect() {
        String path = "./data/configWithCommentsAndEmptyLines.properties";
        Config config = new Config(path);
        config.load();
        assertThat("org.hibernate.dialect.PostgreSQLDialect")
                .isEqualTo(config.value("hibernate.dialect"));
        assertThat(config.value("")).isNull();
        assertThat(config.value("#")).isNull();
    }

    @Test
    void whenConfigWithExtraEquals() {
        String path = "./data/configWithExtraEquals.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("anotherkey")).isEqualTo("somevalue=");
        assertThat(config.value("key")).isEqualTo("value=1");
    }

    @Test
    void whenConfigWithoutEquals() {
        String path = "./data/configWithoutEquals.properties";
        Config config = new Config(path);
        assertThatThrownBy(config::load).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenConfigWithoutKey() {
        String path = "./data/configWithoutKey.properties";
        Config config = new Config(path);
        assertThatThrownBy(config::load).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenConfigWithoutValue() {
        String path = "./data/configWithoutValue.properties";
        Config config = new Config(path);
        assertThatThrownBy(config::load).isInstanceOf(IllegalArgumentException.class);
    }
}