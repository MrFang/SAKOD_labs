import java.util.Scanner;

public class App {
    static Scanner input = new Scanner(System.in);


    public static void main(String[] args) {
        int answer;

        System.out.println("Please type the command number");
        System.out.println("0 -- Exit");
        System.out.println("1 -- List demo");
        System.out.println("2 -- Hash table demo");

        answer = input.nextInt();

        switch (answer) {
            case 0:
                return;
            case 1:
                listDemo();
                break;
            case 2:
                hashTableDemo();
                break;
        }

    }

    private static void listDemo() {
        ListDemonstration.start();
    }

    private static void hashTableDemo(){
        HashTableDemonstration.start();
    }
}
