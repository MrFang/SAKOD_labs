import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import list.PrintableList;

public class App {
    static List<Integer> list = new PrintableList<Integer>();
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int answer;

        do {

            System.out.println("Please type the command number");
            System.out.println("0 -- Exit");
            System.out.println("1 -- Add element");
            System.out.println("2 -- Remove element");
            System.out.println("3 -- Print list");
            System.out.println("4 -- Sort list");

            answer = input.nextInt();

            switch (answer) {
                case 1:
                    addElement();
                    break;
                case 2:
                    removeElement();
                    break;
                case 3:
                    printList();
                    break;
                case 4:
                    sortList();
            }

        } while (answer != 0);
    }

    static void addElement() {
        System.out.println("Type value to add");
        int value = input.nextInt();
        list.add(value);
    }

    static void removeElement() {
        System.out.println("Do you want to remove by value or by index?");
        System.out.println("0 -- Cancel");
        System.out.println("1 -- By value");
        System.out.println("2 -- By index");
        int answer = input.nextInt();

        switch (answer) {
            case 0:
                return;
            case 1:
                System.out.println("Type value");
                list.remove((Integer)input.nextInt());
                break;
            case 2:
                System.out.println("Type index (" + 0 + "-" + (list.size()-1) + ")");
                list.remove(input.nextInt());
                break;
        }
    }

    static void printList() {
        System.out.println(list);
    }

    static void sortList() {
        list.sort(Comparator.comparingInt(o -> o));
    }
}
