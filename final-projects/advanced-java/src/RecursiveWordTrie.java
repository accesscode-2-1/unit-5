package nyc.c4q.ac21.wordtrie;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a node in a trie.  The root node represents the trie itself.
 */
public class RecursiveWordTrie {

    public static final char[] LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public void add(String word) {
        if (word.isEmpty())
            // We're in the leaf node.  Mark it as a member.
            isMember = true;
        else {
            if (children == null)
                // No children yet.  Allocate the trie for the children.
                children = new RecursiveWordTrie[LETTERS.length];

            final int childIndex = getIndex(word.charAt(0));
            RecursiveWordTrie child = children[childIndex];
            if (child == null)
                child = children[childIndex] = new RecursiveWordTrie();

            // Recurse into the child, skipping the leading letter.
            child.add(word.substring(1));
        }
    }

    public boolean contains(String word) {
        if (word.isEmpty())
            return isMember;
        else {
            if (children == null)
                // No children at all.
                return false;
            final int childIndex = getIndex(word.charAt(0));
            final RecursiveWordTrie child = children[childIndex];
            // Recurse into the child, if it exists, skipping the leading letter.
            return child != null && child.contains(word.substring(1));
        }
    }

    public List<String> toList() {
        List<String> results = new ArrayList<>();
        if (isMember)
            results.add("");
        if (children != null) {
            for (char letter : LETTERS) {
                final int childIndex = getIndex(letter);
                final RecursiveWordTrie child = children[childIndex];
                if (child != null)
                    for (String word : child.toList())
                        results.add(letter + word);
            }
        }
        return results;
    }

    /** Returns the array index corresponding to a letter.  */
    private static int getIndex(char letter) {
        return letter - 'A';
    }

    /** True if this node corresponds to a string in the trie, not just a prefix. */
    private boolean isMember = false;

    /** Array of 26 children, one for each next letter; null if all missing.  */
    private RecursiveWordTrie[] children = null;

}
