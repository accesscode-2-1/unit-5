# Final Java project (advanced)

This is a final Java programming project for students who are comfortable with Java syntax. The problem will give you some practice implementing some tricky logic in Java, and also in designing and implementing a complex data structure.

This project introduces an advanced data structure called a _trie_, and walks you through implementing a special case of it.

## Motivation

Most implementations of a set assume that the elements in the set are completely independent. For example, a hash set of strings will store the words "ACTIVE", "ACTIVATION", and "ACTIVIST" as three distinct strings, even though they share a common prefix "ACTIV". We'd like to take advantage of this commonality to produce a more efficient implementation of a set of strings that doesn't store the common prefix more than once.

We might decide to store the set as a tree, where the value represented by a node is always a prefix of the values represented by its children.

```
       / ACTIVATION
      /
ACTIV -- ACTIVE
      \
       \ ACTIVIST
```

In this case, we don't need to store the "ACTIV" prefix for the children at all, as it is implicit from their parent.

```
       / ATION
      /
ACTIV -- E
      \
       \ IST
```

If we want to add the word "ACTIVITY" to this tree, we note that it shares a longer prefix "ACTIVI" with "ACTIVIST", and add an intermediate node.

```
       / ATION
      /
ACTIV -- E
      \
       \ I -- ST
           \
            \ TY
```

## Tries

A _trie_ (pronounced, unfortunately, the same as "tree") is this data structure taken to the logical extreme: each node in the tree represents only a _single letter_.  It may have up to 26 children, representing each of the subsequent letters of the alphabet.

(In general, a node in a trie may represent a different fixed unit of information.  For example, in a _binary trie_, each node represents onebit and may have two children, while in a _decimal trie_, each node represents one decimal digit and may have ten children.)

Our trie, then, looks like this:

```
                    A - T - I - O - N
                  /
A - C - T - I - V - E
                  \ 
                    I - S - T
                      \ 
                        T - Y
```

Each letter in the diagram above is one node in the trie. Some nodes have zero childen (such as 'E'), others have one (such as 'C'), and 'V' has more than one. In principle, a node may have up to 26 children.

## Setup

For this problem, we will use a dictionary of words included with OSX and most other UNIX-like systems in [`/usr/share/dict/words`](words). This file contains about 230,000 English words and names, one per line, including variants such as plural and possessive ("apostrophe s") forms.

To keep things simple, we will convert all words to upper case, and omit any words that contains non-alphabetic characters.

> **Step 1:** Look at `/usr/share/dict/words`.

We've provided a simple test program that loads the words in this dictionary, prints them out, and then enters into a loop that asks you for a word and checks if it is in the dictionary.  Simply give an empty input to end the program.  The test program uses a `HashSet<String>` to store the words.

The sample program is in `nyc.c4q.ac21.wordtrie.Main`.

> **Step 2:** Compile, run, and try out the sample program. Read it over, and make sure you understand how it works.

## Implementation

Your goal for this problem will be to replace `HashSet<String>` with a new class that implements the necessary behavior with a trie.  

We've provided a skeleton class `WordTrie` that shows the methods you must implement. You may of course add a constructor and any helper methods you may need, in addition to attributes.

> **Step 3:** Replace `HashSet<String>` with `WordTrie` in the `Main` class. 
> 
> When printing out the contents of the dictionary, you'll have to use the `WordTrie.toList()` method, since `WordTrie` isn't directly iterable with Java's "for each" statement.

Now if you run the program, the dictionary will appear to be empty, as `add()` doesn't actually add anything.

> **Step 4:** Decide how to implement a node in your trie. You may choose to create a new class for this. Each node should use an array of exactly 26 elements to store its children. If you create a new class, `WordTrie` should have a `root` attribute that references the root node.
>
> Once you have represented a node, implement the `add()` method. It must create and add nodes to the trie as necessary to represent the string being added.

If you are stuck at this point, see the hints below.

> **Step 5:** Implement the `contains()` method. The logic should resemble that of `add()`.

Now try out your trie. It won't print out the full list of words, but test whether it recognizes words in the dictionary correctly. If not, debug it!

## Challenge problem

If you complete the above in less than three hours, attempt this part as well.

> **Step 6:** Implement `toList()`. This method returns a list containing all of the words represented by the trie.
> 
> This method is probably much easier to write as a recursive method.

Unlike a hash set, your method should return the words in alphabetical order. This will occur naturally!

## Hints

The key to this problem is choosing the right representation for your node. We recommend that each node has an array of exactly 26 elements for its children. Each element in this array corresponds to the letter of the alphabet that the child node represents, _i.e._ element 0 is for the 'A' child, element 1 is for the 'B' child, and element 25 is for the 'Z' child. 

If the array contains `null` in a position, that means it has no child for that letter, so there are no words in the array with the corresponding prefix. For example, if the for "PRO" will have children for "PROG" and "PROP", but not for "PROQ".

You will have to do a bit of `char` arithmetic to convert between the letter and the corresponding index position. Remember that you can substract `char` variables to find the number of positions between them.

If you choose this representation, there is no need to store the character iteself on each node! The character each node represents is implicit in the path you take through the trie to reach the node. (You may store the character if you wish, but this will only take up more memory and make your code longer.)

A few additional hints:

- The root node of your trie should represent the empty string. Thus, its 26 children represent words / prefixes comprising a single letter.

- Be careful about incorrectly interpreting a prefix as a member of the trie. For example, "H", "HE", "HELL", "HELLO" are in the dictionary, but "HEL" is not. Your trie will have a node corresponding to "HEL", though, since it is a prefix of "HELL" and "HELLO". The node corresponding to "HELL" represents both an actual word and a prefix for other words.

  One simple way to address this is to add a variable that indicates whether a node represents a word or only a prefix.

- For a _leaf node_ (a node with no children), you do not need to allocate the array of children at all, since all of the array elements will be `null`.  If you can, avoid allocating this array until the node has at least one child.

- Ambitious students may attempt to implement `add()` and `contains()` recursively. If you are accustomed to recursive programming, this is straightforward, but you might find it easier to loop over characters in the string instead. However, `toList()` might be easier to implement recursively that iteratively.

- It may occur to you that you don't need a separate node class at all! You might choose to make `WordTrie` a _recursive data structure_, a data structure that contains additional instances of itself as attributes. This is a very nice property, but completely optional for this problem.

## Cleanup

When you are done, test your word carefully. Make sure that it both contains the words in the dictionary and does not contain words not in the dictionary. You may choose to write some unit tests to verify this.

Also, go through your code and verify the following:

- All classes, methods, attributes, parameters, and variables should have clear names.
- Any particularly tricky logic should have a comment explaining it.
- Any attributes and variables that can be `final` should be.
- Any class members that can be `static` should be.
- All class members should have the most restrictive permission possible, so don't make something `public` if it can be `private` instead.

