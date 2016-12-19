import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Perform T independent experiments on an N-by-N grid.
 */
public class PercolationStats {
    /** keep track of each trial */
    private double[] trials;

    /**
     * @param N grid size
     * @param T number of trials
     */
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        trials = new double[T];
        double thresholdSum = 0.0;
        for (int i = 0; i < T; i++) {
            Percolation perl = new Percolation(N);
            int openCount = 0;
            while (!perl.percolates()) {
                int x = StdRandom.uniform(1, N+1);
                int y = StdRandom.uniform(1, N+1);
                if (!perl.isOpen(x, y)) {
                    perl.open(x, y);
                    openCount++;
                }
            }
            trials[i] = (double) openCount / (N * N);
        }
    }

    /**
     * @return mean value of T trials
     */
    public double mean() {
        return StdStats.mean(trials);
    }

    /**
     * @return sample standard deviation of percolation threshold
     */
    public double stddev() {
        return StdStats.stddev(trials);
    }

    /**
     * @return low end point of 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(trials.length);
    }

    /**
     * @return high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
         return mean() + 1.96 * stddev() / Math.sqrt(trials.length);
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        System.out.println("input grid size = " + N + " with times = " + T);
        PercolationStats stats = new PercolationStats(N, T);
        System.out.println("mean = " + stats.mean());
        System.out.println("stddev = " + stats.stddev());
        System.out.println("confidenceLo = " + stats.confidenceLo());
        System.out.println("confidenceHi = " + stats.confidenceHi());
    }
}

