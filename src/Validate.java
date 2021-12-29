import java.util.*;

public class Validate {
    public static boolean res = true;
    public static List<StringBuilder> matrix;
    private static Set<Integer> vertical;
    private static Set<Integer> horizontal;
    private static Set<Integer> posDiagonal;
    private static Set<Integer> negDiagonal;

//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        int n = in.nextInt();
//        matrix = new ArrayList<>();
//        in.nextLine();
//        for (int i = 0; i < n; i++)
//            matrix.add(in.nextLine());
//        new Validate().validateNQueens(n);
//        System.out.println(res);
//    }

    public void validateNQueens(int n) {
        res = true;
        vertical = Collections.synchronizedSet(new HashSet<>());
        horizontal = Collections.synchronizedSet(new HashSet<>());
        posDiagonal = Collections.synchronizedSet(new HashSet<>());
        negDiagonal = Collections.synchronizedSet(new HashSet<>());
        List<Thread> threads = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            threads.add(new NQueensTask(i, n));
        }
        for (Thread t : threads) { // run threads in parallel
            t.start();
        }
        for (Thread t : threads) { // block until all threads done
            try {
                t.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void isValid(int n, int row) {
        if (!Validate.res) return;
        for (int col = 0; col < n; col++) {
            if (matrix.get(row).charAt(col) == 'Q') {
                if (horizontal.contains(row) || vertical.contains(col) || negDiagonal.contains(col - row) || posDiagonal.contains(col + row)) {
                    Validate.res = false;
                } else {
                    // Add
                    vertical.add(col);
                    horizontal.add(row);
                    negDiagonal.add(col - row);
                    posDiagonal.add(col + row);
                }
            }
        }
    }

    class NQueensTask extends Thread {
        int row;
        int n;

        NQueensTask(int row, int n) {
            this.row = row;
            this.n = n;
        }

        public void run() {
            try {
                isValid(n, row);
            } catch (Exception e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        }
    }

}
