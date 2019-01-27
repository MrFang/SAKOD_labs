import list.PrintableList;

import java.util.List;

public class App {
    public static void main(String[] args) {
        List<String> list = new PrintableList<String>();
        list.add("Вася");
        list.add("Коля");
        list.add("Петя");
        System.out.print(list);
    }
}
