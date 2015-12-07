package nyc.c4q.ac21.wordtrie;

import java.util.ArrayList;
import java.util.List;

public class WordTrie {

    public static final char[] LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public void add(String word) {
        // Start at the root and descend as we iterate through the string.
        Node node = root;
        for (int i = 0; i < word.length(); ++i) {
            if (node.children == null) {
                // No children so far; allocate an array for them.
                node.children = new Node[LETTERS.length];
            }
            final int childIndex = getIndex(word.charAt(i));
            Node child = node.children[childIndex];
            if (child == null)
                // No child node for this letter; create it.
                child = node.children[childIndex] = new Node();

            // Descend to the child for this letter.
            node = child;
        }

        // We've reached the node for this word.  Mark it as a member.
        node.isMember = true;
    }

    public boolean contains(String word) {
        // Start at the root and descend as we iterate through the string.
        Node node = root;
        for (int i = 0; i < word.length(); ++i) {
            if (node.children == null)
                // This node has no children at all.
                return false;

            // Descend to the child node for this letter.
            node = node.children[getIndex(word.charAt(i))];
            if (node == null)
                // No child for this letter.
                return false;

        }

        // We've reached the node for this word.  Is it a member or just a prefix?
        return node.isMember;
    }

    public List<String> toList() {
        List<String> results = new ArrayList<>();
        addToList(root, "", results);
        return results;
    }

    /**
     * Recursive helper method for `toList()`.
     * @param node
     *   The node to add.
     * @param prefix
     *   The string prefix corresponding to this node.
     * @param results
     *   The list of results to add to.
     */
    private static void addToList(Node node, String prefix, List<String> results) {
        if (node.isMember)
            // This node corresponds to a word in the trie.  Add it.
            results.add(prefix);

        if (node.children != null)
            // Recurse into children.
            for (char letter : LETTERS) {
                final Node child = node.children[getIndex(letter)];
                if (child != null)
                    // Each child represents a prefix one letter longer than this one.
                    addToList(child, prefix + letter, results);
            }
    }

    /** Returns the array index corresponding to a letter.  */
    private static int getIndex(char letter) {
        return letter - 'A';
    }

    private final static class Node {
        /** True if this node corresponds to a string in the trie, not just a prefix. */
        boolean isMember = false;
        /** Array of 26 children, one for each next letter; null if all missing.  */
        Node[] children;
    }

    /** The root note, which corresponds to the empty string.  */
    private final Node root = new Node();

}
