import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;

public class FriendsPairing {

    private static BigInteger[] numPairings;
    private static final BigInteger defaultValue = new BigInteger("-1");
    private static final String modOutputValue = Long.toString((long) (Math.pow(10, 9) + 7));

    public static void main (String[] args) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {

            int numTestCases = Integer.parseInt(in.readLine());
            while (numTestCases > 0) {
                int n = Integer.parseInt(in.readLine());
                numPairings = new BigInteger[n + 1];
                Arrays.fill(numPairings, defaultValue);

                long start = System.nanoTime();
                System.out.println(getNumPairingsMem(n).mod(new BigInteger(modOutputValue))
                                    + " DP Mem time: " + (System.nanoTime() - start));

                start = System.nanoTime();
                System.out.println(getNumPairingsTab(n).mod(new BigInteger(modOutputValue))
                                    + " DP Tab time: " + (System.nanoTime() - start));

                numTestCases--;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BigInteger getNumPairingsMem (int n) {

        if (numPairings[n].equals(defaultValue))
            numPairings[n] = n <= 2 ?
                    new BigInteger(Integer.toString(n)) : getNumPairingsMem(n - 1).
                                                            add(getNumPairingsMem(n - 2).
                                                                    multiply(new BigInteger(Integer.toString(n - 1))));

        return numPairings[n];
    }

    private static BigInteger getNumPairingsTab (int n) {

        for (int i = 0; i < 3; i++)
            numPairings[i] = new BigInteger(Integer.toString(i));

        for (int i = 3; i <= n; i++)
            numPairings[i] = numPairings[i - 1].
                    add(numPairings[i - 2].
                            multiply(new BigInteger(Integer.toString((i - 1)))));

        return numPairings[n];
    }

    private static int naive (int n) {
        if (n <= 2)
            return n;
        return naive(n - 1) + (naive(n-2) * (n-1));
    }
}


// 1 2 3 4