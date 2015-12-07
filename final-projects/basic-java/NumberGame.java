import java.util.Scanner;

public class NumberGame {

    public static void main(String[] args) {
        // Prompt for and read the number.
        final Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int number = scanner.nextInt();

        int steps = 0;
        while (number != 1) {
            int nextNumber;
            if (number % 2 == 0) {
                // Number is even.
                nextNumber = number / 2;
                System.out.println(number + " is even, so I take half: " + nextNumber);
            }
            else {
                // Number is odd.
                nextNumber = number * 3 + 1;
                System.out.println(number + " is odd, so I make 3n+1: " + nextNumber);
            }
            number = nextNumber;
            // Keep count of how many steps it took us.
            ++steps;
        }
        System.out.println("The process took " + steps + " to reach 1");
    }

}
