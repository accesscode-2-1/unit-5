import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MinMaxWithList {

    /**
     * Prompts for numbers and collects them into a list, until the value zero.
     */
    private static List<Integer> readNumbers() {
        final Scanner scanner = new Scanner(System.in);
        final List<Integer> numbers = new ArrayList<>();
        while (true) {
            System.out.print("? ");
            final int number = scanner.nextInt();
            if (number == 0) {
                break;
            }
            else {
                numbers.add(number);
            }
        }
        return numbers;
    }

    /**
     * Given a list of numbers, prints the smallest and largest, unless there aren't any.
     */
    private static void printMinMax(List<Integer> numbers) {
        if (numbers.size() == 0) {
            System.out.println("no numbers entered");
        }
        else {
            int min = numbers.get(0);
            int max = numbers.get(0);
            for (int number : numbers) {
                if (number < min)
                    min = number;
                if (number > max)
                    max = number;
            }
            System.out.println("smallest: " + min);
            System.out.println("largest: " + max);
        }
    }

    public static void main(String[] args) {
        System.out.println("This program finds the largest and smallest numbers.");
        final List<Integer> numbers = readNumbers();
        printMinMax(numbers);
    }

}
