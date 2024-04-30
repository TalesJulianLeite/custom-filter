package com.tales.leite.commons.abstracts;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CustomListImplTest {

    @Test
    void testSize() {
        List<String> data = Arrays.asList("a", "b", "c");
        CustomListImpl<String> customList = new CustomListImpl<>(data);
        assertEquals(3, customList.size());
    }

    @Test
    void testAddAll() {
        List<String> data = new ArrayList<>();
        CustomListImpl<String> customList = new CustomListImpl<>(data);
        assertTrue(customList.addAll(Arrays.asList("a", "b")));
        assertEquals(2, customList.size());
    }

    @Test
    void testGet() {
        List<String> data = Arrays.asList("a", "b", "c");
        CustomListImpl<String> customList = new CustomListImpl<>(data);
        assertEquals("b", customList.get(1));
    }

    @Test
    void testAdd() {
        List<String> data = new ArrayList<>(Arrays.asList("a", "b"));
        CustomListImpl<String> customList = new CustomListImpl<>(data);
        customList.add(1, "c");
        assertEquals("c", customList.get(1));
    }

    @Test
    void testSet() {
        List<String> data = new ArrayList<>(Arrays.asList("a", "b", "c"));
        CustomListImpl<String> customList = new CustomListImpl<>(data);
        assertEquals("b", customList.set(1, "d"));
        assertEquals("d", customList.get(1));
    }

    @Test
    void testRemove() {
        List<String> data = new ArrayList<>(Arrays.asList("a", "b", "c"));
        CustomListImpl<String> customList = new CustomListImpl<>(data);
        assertTrue(customList.remove("b"));
        assertFalse(customList.contains("b"));
    }

    // Implementação concreta da classe abstrata para fins de teste
    private static class CustomListImpl<E> extends CustomAbstractList<E> {
        public CustomListImpl(List<E> values) {
            super(values);
        }
    }
}
