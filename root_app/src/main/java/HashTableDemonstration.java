import table.HashTable;

import java.util.Scanner;

class HashTableDemonstration {
    static Scanner input = new Scanner(System.in);
    static HashTable<String, String> table = new HashTable<String, String>();

    static void start() {
        int answer;

        do {

            System.out.println("Please type the command number");
            System.out.println("0 -- Exit");
            System.out.println("1 -- Add element");
            System.out.println("2 -- Remove element");
            System.out.println("3 -- Print");
            System.out.println("4 -- Find element");

            answer = input.nextInt();

            switch (answer) {
                case 1:
                    addElement();
                    break;
                case 2:
                    removeElement();
                    break;
                case 3:
                    print();
                    break;
                case 4:
                    find();
                    break;

            }

        } while (answer != 0);
    }

    private static void addElement(){
        System.out.println("Type key to add");
        String key = input.next();
        System.out.println("Type value to add");
        String value = input.next();
        table.add(key, value);
    }

    private static void removeElement() {
        System.out.println("Type key to remove");
        String key = input.next();
        table.remove(key);
    }

    private static void print() {
        System.out.println(table);
    }

    private static void find() {
        System.out.println("Type key to find");
        String key = input.next();
        System.out.println(table.find(key));
    }
}