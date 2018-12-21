import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;

public class BinomialCoefficientz {

    private static BigInteger[][] pascalsTriangle;
    private static BigInteger defaultValue = new BigInteger("-1");
    private static final BigInteger mod = new BigInteger(Long.toString((long) (Math.pow(10, 9) + 7)));

    public static void main (String[] args) {

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {

            int numCases = Integer.parseInt(bufferedReader.readLine());
            while (numCases > 0) {

                String[] strArrInput = bufferedReader.readLine().split(" ");
                //  System.out.println("Enter n: ");
                int n = Integer.parseInt(strArrInput[0]);

                // System.out.println("Enter k: ");
                int k = Integer.parseInt(strArrInput[1]);

             //   while (n >= 0 && k >= 0) {

                    pascalsTriangle = new BigInteger[n + 1][];
                    for (int r = 0; r < n + 1; r++) {
                        pascalsTriangle[r] = new BigInteger[Math.min(r + 1, k + 1)];
                        Arrays.fill(pascalsTriangle[r], defaultValue);
                    }

                    //long start = System.nanoTime();
                    // System.out.println(naivrGetBinomialCoefficient(n, k) + " Naive time: " + (System.nanoTime() - start));

                //    start = System.nanoTime();
                  //  System.out.println(MemoizationGetBinomialCoefficient(n, k) + " DP Memoization time: " + (System.nanoTime() - start));

                    //start = System.nanoTime();
                    System.out.println(TabGetBinomialCofficient(n, k).mod(mod) /* + " DP Tab time: " + (System.nanoTime() - start) */);

                    //System.out.println();

                 //   System.out.println("Enter n: ");
                //    n = Integer.parseInt(bufferedReader.readLine());

               //     System.out.println("Enter k: ");
                  //  k = Integer.parseInt(bufferedReader.readLine());
             //   }

                numCases--;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int naivrGetBinomialCoefficient (int n, int k) {

        if (k == 0 || k == n)
            return 1;
        return naivrGetBinomialCoefficient(n - 1, k - 1) + naivrGetBinomialCoefficient(n - 1, k);
    }

    public static BigInteger MemoizationGetBinomialCoefficient (int n, int k) {

        if (pascalsTriangle[n][k].equals(defaultValue)) {

            if (k == 0 || k == n)
                pascalsTriangle[n][k] = new BigInteger("1");
            else
                pascalsTriangle[n][k] = MemoizationGetBinomialCoefficient(n - 1, k - 1)
                        .add(MemoizationGetBinomialCoefficient(n - 1, k));
        }

        return pascalsTriangle[n][k];
    }

    public static BigInteger TabGetBinomialCofficient (int n, int k) {

        for (int i = 0; i < n + 1; ++i)
            for (int j = 0; j < Math.min(i + 1, k + 1); ++j)
                pascalsTriangle[i][j] = (j == 0 || j == i) ? new BigInteger("1") : pascalsTriangle[i - 1][j].add(pascalsTriangle[i - 1][j - 1]);

        return pascalsTriangle[n][k];

    }

    /*
    1
    1 1
    1 2 1
    1 3 3 1
    1 4 6 4 1
     */

}
