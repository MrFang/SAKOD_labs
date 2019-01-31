package list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;



class PrintableListTest {
    private List<String> list = new PrintableList<String>();

    @BeforeEach
    void setUp() {
        list.add("Вася");
        list.add("Коля");
        list.add("Петя");
    }

    @Test
    void sizeTest() {
        assertEquals(list.size(), 3);
    }

    @Test
    void isEmptyTest() {
        assertFalse(list.isEmpty());
    }

    @Test
    void containsTest() {
        assertTrue(list.contains("Вася"));
        assertFalse(list.contains("Саша"));
    }

    @Test
    void foeEachTest() {

        StringBuilder str = new StringBuilder();
        for(String s : list) {
            str.append(s);
        }

        assertEquals("ВасяКоляПетя", str.toString());
    }

    @Test
    void addAllTest() {
        ArrayList<String> a = new ArrayList<String>();
        a.add("Маша");
        a.add("Катя");
        list.addAll(a);

        assertTrue(list.contains("Маша"));
        assertTrue(list.contains("Катя"));
        assertEquals(5, list.size());
    }

    @Test
    void removeTest() {
        list.remove("Вася");

        assertFalse(list.contains("Вася"));
        assertEquals(2, list.size());
    }
}
