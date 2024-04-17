package ru.job4j.iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

class ListUtilsTest {

    private List<Integer> input;

    @BeforeEach
    void setUp() {
        input = new ArrayList<>(Arrays.asList(1, 3));
    }

    @Test
    void whenAddBefore() {
        ListUtils.addBefore(input, 1, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenAddBeforeWithInvalidIndex() {
        assertThatThrownBy(() -> ListUtils.addBefore(input, 3, 2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void whenAddAfter() {
        ListUtils.addAfter(input, 0, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenRemoveIfRemoveAll() {
        ListUtils.removeIf(input, x -> x > 0);
        assertThat(input).isEmpty();
    }

    @Test
    void whenRemoveIfRemoveSpecific() {
        ListUtils.removeIf(input, x -> x == 3);
        assertThat(input).hasSize(1).containsExactly(1);
    }

    @Test
    void whenReplaceIfSpecific() {
        ListUtils.replaceIf(input, x -> x == 3, 4);
        assertThat(input).hasSize(2).containsExactly(1, 4);
    }

    @Test
    void whenReplaceIfReplaceAll() {
        ListUtils.replaceIf(input, x -> x > 0, 0);
        assertThat(input).hasSize(2).containsExactly(0, 0);
    }

    @Test
    void whenRemoveAllWithListOfElements() {
        List<Integer> elements = new ArrayList<>(Arrays.asList(1, 3));
        ListUtils.removeAll(input, elements);
        assertThat(input).hasSize(0);
    }

    @Test
    void whenRemoveAllWithEmptyToRemoveThenListUnchanged() {
        ListUtils.removeAll(input, Collections.emptyList());
        assertThat(input).hasSize(2).containsExactly(1, 3);
    }
}