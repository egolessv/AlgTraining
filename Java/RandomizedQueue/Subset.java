import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
/**
 * This program takes a command-line integer k,
 * reads in a sequence of N strings from standard
 * input and print out exactly k of them.
 *
 * @author Xu Yu
 * @since 2015-10-12
 */
public class Subset {

    public static void main(String[] args) {
        int i = 0;
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rQueue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String in = StdIn.readString();
            int r = StdRandom.uniform(i+1);
            if (i < k) {
                rQueue.enqueue(in);
            } else if (r < k) {
                rQueue.dequeue();
                rQueue.enqueue(in);
            }
            i++;
        }
        for (String s : rQueue) {
            System.out.println(s);
        }
    }
}
