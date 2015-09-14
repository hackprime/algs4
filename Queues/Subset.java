// Subset client. Write a client program Subset.java
// that takes a command-line integer k;
// reads in a sequence of N strings from standard input using StdIn.readString();
// and prints out exactly k of them, uniformly at random.
// Each item from the sequence can be printed out at most once.
// You may assume that 0 ≤ k ≤ N, where N is the number of string on standard input.

// % echo A B C D E F G H I | java Subset 3
// C
// G
// A
//

// % echo A B C D E F G H I | java Subset 3
// E
// F
// G

// % echo AA BB BB BB BB BB CC CC | java Subset 8
// BB
// AA
// BB
// CC

// BB
// BB
// CC
// BB

// The running time of Subset must be linear in the size of the input.
// You may use only a constant amount of memory plus either one
// Deque or RandomizedQueue object of maximum size at most N,
// where N is the number of strings on standard input.
// (For an extra challenge, use only one Deque or RandomizedQueue object
// of maximum size at most k.) It should have the following API.

// echo A B C D E F G H I | java -cp $CLASSPATH:`pwd` Subset 3


public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> structure = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            structure.enqueue(s);
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(structure.dequeue());
        }
    }
}
