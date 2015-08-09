public class DequeTest {
    public static void checkCondition(boolean cond) {
        if (cond) {
            StdOut.println("+ passed\n");
        } else {
            StdOut.println("--- FAILED\n");
        }

    }
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<Integer>();


        StdOut.println("Check if just created deque is empty");
        checkCondition(d.isEmpty() && d.size() == 0);


        StdOut.println("Check order consistency of addFirst and removeFirst");
        int testSetSize = 5;
        for (int i = 0; i < testSetSize; i++) {
            d.addFirst(i);
        }
        for (int i = 0; i < testSetSize - 1; i++) {
            d.removeFirst();
        }
        checkCondition(d.removeFirst() == 0 && d.size() == 0);


        StdOut.println("Check order consistency of addLast and removeLast");
        for (int i = 0; i < testSetSize; i++) {
            d.addLast(i);
        }
        for (int i = 0; i < testSetSize - 1; i++) {
            d.removeLast();
        }
        checkCondition(d.removeLast() == 0 && d.size() == 0);


        StdOut.println("Mixed pops and pushs");
        // 1
        // 1 2
        // 1 2 3
        // 4 1 2 3
        // 5 4 1 2 3
        // 2 3
        // 2 3 6
        // 3
        d.addLast(1);
        d.addLast(2);
        d.addLast(3);
        d.addFirst(4);
        d.addFirst(5);
        d.removeFirst();
        d.removeFirst();
        d.removeFirst();
        d.addLast(6);
        d.removeLast();
        d.removeFirst();
        checkCondition(d.removeLast() == 3 && d.size() == 0);

        // if (!StdIn.isEmpty()) {
        //     while (!StdIn.isEmpty()) {
        //         String s = StdIn.readString();
        //         d.addFirst(s);
        //     }
        //     d.removeFirst();
        //     d.addFirst("one");
        //     d.removeLast();
        //     d.addLast("three");

        //     System.out.println("result size: " + d.size());
        //     for (String s : d) {
        //         System.out.println(s);
        //     }
        // }
    }
}
