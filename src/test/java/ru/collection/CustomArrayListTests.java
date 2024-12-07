package ru.collection;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.collections.CustomArrayList;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomArrayListTests {

    private CustomArrayList<String> list;

    @BeforeEach
    public void beforeEach() {
        list = new CustomArrayList<>();
        list.add(0, "first");
        list.add(1, "second");
        list.add(2, "third");
        list.add(3, "forth");
        list.add(4, "fifth");
    }

    @Test
    public void add_givenValidIndex_expectSuccess() {
        list.add(5, "sixth");

        String str = list.get(5);

        assertNotNull(str);
        assertEquals("sixth", str);
    }

    @Test
    public void add_givenInvalidIndex_expectOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(99, "sixth"));
    }

    @Test
    public void addAll_givenValidData_expectSuccess() {
        list.addAll(List.of("sixth", "seventh"));

        String sixth = list.get(5);
        assertNotNull(sixth);
        assertEquals("sixth", sixth);

        String seventh = list.get(6);
        assertNotNull(seventh);
        assertEquals("seventh", seventh);
    }

    @Test
    public void clear_expectSuccess() throws NoSuchFieldException, IllegalAccessException {
        list.clear();

        Field sizeField = CustomArrayList.class.getDeclaredField("size");
        sizeField.setAccessible(true);
        int size = (int) sizeField.get(list);

        assertEquals(size, 0);
    }

    @Test
    public void getByIndex_givenValidIndex_expectSuccess() {
        String str = list.get(3);

        assertNotNull(str);
        assertEquals("forth", str);
    }

    @Test
    public void getByIndex_givenInvalidIndex_expectOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(99));
    }

    @Test
    public void isEmpty_filledList_expectFalse() {
        boolean isEmpty = list.isEmpty();

        assertFalse(isEmpty);
    }

    @Test
    public void isEmpty_afterClear_expectTrue() {
        list.clear();
        boolean isEmpty = list.isEmpty();

        assertTrue(isEmpty);
    }

    @Test
    public void removeById_givenValidId_expectSuccess() {
        list.remove(2);


        String forth = list.get(2);
        assertNotNull(forth);
        assertEquals("forth", forth);

        String fifth = list.get(3);
        assertNotNull(fifth);
        assertEquals("fifth", fifth);
    }

    @Test
    public void removeByValue_givenValidId_expectSuccess() {
        list.remove("third");

        String forth = list.get(2);
        assertNotNull(forth);
        assertEquals("forth", forth);

        String fifth = list.get(3);
        assertNotNull(fifth);
        assertEquals("fifth", fifth);
    }

    @Test
    public void sort_naturalOrder_expectSuccess() {
        list.sort(Comparator.naturalOrder());

        assertEquals("fifth", list.get(0));
        assertEquals("first", list.get(1));
        assertEquals("forth", list.get(2));
        assertEquals("second", list.get(3));
        assertEquals("third", list.get(4));
    }

    @Test
    public void sort_reversedOrder_expectSuccess() {
        list.sort(Comparator.reverseOrder());

        assertEquals("third", list.get(0));
        assertEquals("second", list.get(1));
        assertEquals("forth", list.get(2));
        assertEquals("first", list.get(3));
        assertEquals("fifth", list.get(4));
    }
}
