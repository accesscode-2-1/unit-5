package nyc.c4q.ac21.wordtrie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TrieMain {

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

    public static WordTrie loadDictionary() {
        WordTrie dictionary = new WordTrie();
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
            return null;
        }
        return dictionary;
    }

    public static void main(String[] args) {
        final WordTrie dictionary = loadDictionary();

        for (String word : dictionary.toList())
            System.out.println(word);
        System.out.println();

        final Scanner scanner = new Scanner(System.in);
        while (true) {
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
