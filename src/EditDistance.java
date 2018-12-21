import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class EditDistance {

    public static void main (String[] args) {

        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {

            int numCases = Integer.parseInt(in.readLine());
            while (numCases > 0) {

                HashMap<String, Long> mymap = new HashMap<>();

                String str1 = in.readLine();
                String str2 = in.readLine();

                long start = System.nanoTime();
                System.out.println(naive(str1, str2, 0)
                        + " naive time: " + (System.nanoTime() - start));

                start = System.nanoTime();
                System.out.println(memoization(str1, str2, mymap)
                        + " DP Memoization time: " + (System.nanoTime() - start));

                numCases--;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long naive(String str1, String str2, int currIndex) {

        if (currIndex >= str2.length() || currIndex >= str1.length())
            return Math.abs(str2.length() - str1.length());

        if (str1.charAt(currIndex) == str2.charAt(currIndex))
            return naive(str1, str2, currIndex + 1);

        String strReplace = str1.substring(0, currIndex) + str2.charAt(currIndex) + str1.substring(currIndex + 1);
        String strInsert = str1.substring(0, currIndex) + str2.charAt(currIndex) + str1.substring(currIndex);
        String strRemove = str1.substring(0, currIndex) + str1.substring(currIndex + 1);

        return 1 + min(naive(strReplace, str2, currIndex + 1),
                    naive(strInsert, str2, currIndex + 1),
                    naive(strRemove, str2, currIndex));
    }

    private static long memoization (String str1, String str2, HashMap<String, Long> mymap) {

        String key = str1 + "#" + str2;

        if (!mymap.containsKey(key)) {
            if (str1.isEmpty() || str2.isEmpty()) {
                mymap.put(key, (long) Math.abs(str2.length() - str1.length()));
            } else if (str1.charAt(0) == str2.charAt(0)) {
                mymap.put(key, memoization(str1.substring(1), str2.substring(1), mymap));
            } else {

                /*
                replace
                insert
                remove
                 */

                mymap.put(key,
                        1 + min(memoization(str1.substring(1), str2.substring(1), mymap),
                                memoization(str1, str2.substring(1), mymap),
                                memoization(str1.substring(1), str2, mymap)));
            }
        }

        return mymap.get(key);
    }

    private static long min (long a, long b, long c) {
        return Math.min(Math.min(a, b), c);
    }
}
