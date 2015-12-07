import java.util.Scanner;

public class Pythagorean {

    public static void main(String[] args) {
        System.out.println("Enter values to compute the Pythagorean theorem.");

        // Prompt for 'a' and read it, then likewise for 'b'.
        final Scanner scanner = new Scanner(System.in);
        System.out.print("a: ");
        final double a = scanner.nextDouble();
        System.out.print("b: ");
        final double b = scanner.nextDouble();

        // Compute the result.  We could use Math.pow(), but squaring with multiplication is easy.
        final double c = Math.sqrt(a * a + b * b);

        // Print the result.
        System.out.println("c = " + c);
    }

}
