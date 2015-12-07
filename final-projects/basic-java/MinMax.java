import java.util.Scanner;

public class MinMax {

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Prompts for and reads an integer.
     */
    private static int readValue() {
        System.out.print("? ");
        return scanner.nextInt();
    }

    public static void main(String[] args) {
        System.out.println("This program finds the largest and smallest numbers.");

        // Read the first value.
        int value = readValue();

        if (value == 0) {
            // No first value; bail.
            System.out.println("no values entered");
        }
        else {
            // Initialize the min and max to the first value.  (If no further values are given,
            // both the min and max are eqaul to this one value.)
            int max = value;
            int min = value;
            // Read more values until we get zero.
            for (value = readValue(); value != 0; value = readValue()) {
                // Update the min and the max.
                if (value < min) {
                    min = value;
                }
                if (value > max) {
                    max = value;
                }
            }
            // Report the results.
            System.out.println("smallest: " + min);
            System.out.println("largest: " + max);
        }
    }

}
