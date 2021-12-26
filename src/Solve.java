import java.util.*;

class Solve {
    List<List<String>> res;

    public static void main(String[] args) {
        List<List<String>> sols = new Solve().solveNQueens(8);
        for (List<String> list : sols) {
            for (String sol : list)
                System.out.println(sol);
            System.out.println();
        }
        System.out.println(sols.size());
    }

    private static void waitForThreads(List<Thread> threads) throws InterruptedException {
        for (Thread thread : threads)
            thread.join();
        threads.clear();
    }

    public List<List<String>> solveNQueens(int n) {
        res = Collections.synchronizedList(new ArrayList<>());
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Thread thread = new Thread(new NQueensTask(i, n));
            thread.start();
            threads.add(thread);
            if (threads.size() == 8) {
                try {
                    waitForThreads(threads);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            waitForThreads(threads);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }

    private void solveNQueens(int n, Set<Integer> vertical, Set<Integer> posDiagonal, Set<Integer> negDiagonal, int row, List<List<String>> res, List<Integer> currSol) {
        if (row == n) {
            res.add(formatRes(currSol, n));
        } else {
            for (int col = 0; col < n; col++) {
                if (!(vertical.contains(col) || negDiagonal.contains(col - row) || posDiagonal.contains(col + row))) {
                    // Add
                    vertical.add(col);
                    negDiagonal.add(col - row);
                    posDiagonal.add(col + row);
                    currSol.add(col);

                    solveNQueens(n, vertical, posDiagonal, negDiagonal, row + 1, res, currSol); // call

                    // Remove
                    vertical.remove(col);
                    negDiagonal.remove(col - row);
                    posDiagonal.remove(col + row);
                    currSol.remove(currSol.size() - 1);
                }
            }
        }
    }

    private List<String> formatRes(List<Integer> currSol, int n) {
        List<String> res = new LinkedList<>();
        for (int solCol : currSol) {
            StringBuilder sb = new StringBuilder();
            for (int index = 0; index < n; index++) {
                if (index == solCol) {
                    sb.append('Q');
                } else {
                    sb.append('.');
                }

            }
            res.add(sb.toString());
        }
        return res;
    }

    class NQueensTask extends Thread {
        Set<Integer> vertical;
        Set<Integer> posDiagonal;
        Set<Integer> negDiagonal;
        List<Integer> currSol;
        int col;
        int n;

        NQueensTask(int col, int n) {
            vertical = new HashSet<>();
            posDiagonal = new HashSet<>();
            negDiagonal = new HashSet<>();
            currSol = new LinkedList<>();
            this.col = col;
            this.n = n;
        }

        public void run() {
            try {
                vertical.add(col);
                negDiagonal.add(col);
                posDiagonal.add(col);
                currSol.add(col);
                solveNQueens(n, vertical, posDiagonal, negDiagonal, 1, res, currSol);
            } catch (Exception e) {
                System.out.println("Exception is caught");
            }
        }
    }
}
