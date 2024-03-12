package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;

class BoxTest {

    @Test
    void isThisSphere() {
        Box box = new Box(0, 10);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Sphere");
        assertThat(name).isNotEqualTo("Tetrahedron");
    }

    @Test
    void isThisTetrahedron() {
        Box box = new Box(4, 10);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Tetrahedron");
        assertThat(name).isNotEqualTo("Sphere");
    }

    @Test
    void howManyNumberOfVertices() {
        Box box = new Box(4, 10);
        int number = box.getNumberOfVertices();
        assertThat(number).isEqualTo(4);
        assertThat(number).isGreaterThan(3);
    }

    @Test
    void isExist() {
        Box box = new Box(-1, 10);
        boolean isExist = box.isExist();
        assertThat(isExist).isFalse();
        assertThat(isExist).isNotEqualTo(true);
    }

    @Test
    void isAreaValueEqual173dot2() {
        Box box = new Box(4, 10);
        double area = box.getArea();
        assertThat(area).isEqualTo(173.2, withPrecision(0.01d));
        assertThat(area).isCloseTo(173.2, withPrecision(0.01d));
    }
}