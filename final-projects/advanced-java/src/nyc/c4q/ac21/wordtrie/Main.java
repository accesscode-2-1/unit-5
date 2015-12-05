package nyc.c4q.ac21.wordtrie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

    /** Path to the system dictionary file.  */
    public static final String DICTIONARY_FILE = "/usr/share/dict/words";

    /**
     * Returns true if 'string' consists entirely of the letters A through Z.
     */
    public static boolean isAlphabetic(String string) {
        for (int i = 0; i < string.length(); ++i) {
            final char c = string.charAt(i);
            if (!('A' <= c && c <= 'Z'))
                return false;
        }
        return true;
    }

    /**
     * Loads words in the system dictionary.
     *
     * Words are converted to upper case.  Only words that are entirely alphabetic are included.
     *
     * @return
     *   A set containing all words in the dictionary.
     */
    public static Set<String> loadDictionary() {
        Set<String> dictionary = new HashSet<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(DICTIONARY_FILE));
            for (String word = reader.readLine(); word != null; word = reader.readLine()) {
                word = word.toUpperCase();
                if (isAlphabetic(word))
                    dictionary.add(word);
            }
        }
        catch (IOException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
        return dictionary;
    }

    public static void main(String[] args) {
        // Load the words in the dictionary.
        final Set<String> dictionary = loadDictionary();

        // Print the dictionary.
        for (String word : dictionary)
            System.out.println(word);
        System.out.println();

        // Loop for user input.
        final Scanner scanner = new Scanner(System.in);
        while (true) {
            // Ask for a word.
            System.out.print("word? ");
            final String word = scanner.nextLine().toUpperCase();

            if (word.equals(""))
                // Empty input: all done.
                break;
            else if (!isAlphabetic(word))
                System.out.println("'" + word + "' is not alphabetic");
            else {
                final boolean inDictionary = dictionary.contains(word);
                System.out.println("'" + word + "' " + (inDictionary ? "is" : "is not") +
                        " in the dictionary");
            }
        }
    }

}
