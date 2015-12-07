import java.util.Random;
import java.util.Scanner;

public class Hangman {

    public static void play(String word, int numGuesses) {
        word = word.toUpperCase();

        // Initialize the clue to all dashes. The number must match the length of the word.
        String clue = "";
        for (int i = 0; i < word.length(); ++i)
            clue += '-';

        System.out.println("Welcome to Hangman!");
        final Scanner scanner = new Scanner(System.in);

        while (true) {
            // Prompt for the next guess.
            System.out.println("The word now looks like this: " + clue);
            System.out.println("You have " + numGuesses + " guesses left.");

            // Read the next guess.  We need a loop in case the user enters an invalid value.
            char guess;
            while (true) {
                System.out.print("Your guess: ");
                final String guessString = scanner.next();
                if (guessString.length() != 1) {
                    // Bad guess.  Continue in the loop.
                    System.out.println("Your guess should be a single character. Try again.");
                } else {
                    // Looks good.  Break out of the loop.
                    guess = guessString.toUpperCase().charAt(0);
                    break;
                }
            }

            // Update the clue to show any occurrences of the guess character.
            if (word.indexOf(guess) == -1) {
                // Bad guess.
                System.out.println("There are no " + guess + "'s in the word.");
                --numGuesses;
                if (numGuesses == 0) {
                    System.out.println("You're completely hung.");
                    System.out.println("The word was: " + word);
                    System.out.println("You lose.");
                    break;
                }
            }
            else {
                // Good guess.
                System.out.println("That guess is correct.");
                // Go through the word, replacing hyphens for occurrences of the guess
                for (int i = 0; i < word.length(); ++i) {
                    if (word.charAt(i) == guess) {
                        clue = clue.substring(0, i) + guess + clue.substring(i + 1);
                    }
                }

                // Check if we've won: if the clue contains no hyphens.
                if (!clue.contains("-")) {
                    System.out.println("You guessed the word: " + word);
                    System.out.println("You win.");
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        // Choose a random word.
        final Random random = new Random();
        final String word = WORDS[random.nextInt(WORDS.length)];

        play(word, NUM_GUESSES);
    }

    private static final int NUM_GUESSES = 8;

    private static final String[] WORDS = {
            "fuzzy",
            "computer",
            "program",
            "donut",
            "apple",
            "android",
            "logic",
    };

}
