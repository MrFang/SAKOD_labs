package list;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class PrintableListTest {
    private List<String> list = new PrintableList<String>();

    @Before
    public void setUp() {
        list.add("Вася");
        list.add("Коля");
        list.add("Петя");
    }

    @Test
    public void sizeTest() {
        assertEquals(list.size(), 3);
    }

    @Test
    public void isEmptyTest() {
        assertFalse(list.isEmpty());
    }

    @Test
    public void containsTest() {
        assertTrue(list.contains("Вася"));
        assertFalse(list.contains("Саша"));
    }

    @Test
    public void foeEachTest() {
        String str = "";

        for(String s : list) {
            str+=s;
        }

        assertEquals("ВасяКоляПетя", str);
    }

    @Test
    public void addAllTest() {
        ArrayList<String> a = new ArrayList<String>();
        a.add("Маша");
        a.add("Катя");
        list.addAll(a);

        assertTrue(list.contains("Маша"));
        assertTrue(list.contains("Катя"));
        assertEquals(5, list.size());
    }

    @Test
    public void removeTest() {
        list.remove("Вася");

        assertFalse(list.contains("Вася"));
        assertEquals(2, list.size());
    }
}
