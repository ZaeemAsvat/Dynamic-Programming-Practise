import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.LinkOption;
import java.util.Arrays;

public class GoldMine {

    private static long[][] maxTable;

    public static void main (String[] args) {

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.print("No of rows: ");
            int rows = Integer.parseInt(bufferedReader.readLine());

            System.out.print("No of cols: ");
            int cols = Integer.parseInt(bufferedReader.readLine());

            System.out.println();

            while (rows >= 0 && cols >= 0) {

                int[][] mine = new int[rows][cols];
                maxTable = new long[rows][cols];

                System.out.println("Enter mine:");

                for (int i = 0; i < rows; i++) {
                    String[] strArrayRow = bufferedReader.readLine().split(" ");
                    for (int j = 0; j < cols; j++)
                        mine[i][j] = Integer.parseInt(strArrayRow[j]);
                }

               /* System.out.print("Enter start row: ");
                int startRow = Integer.parseInt(bufferedReader.readLine());
                System.out.print("Enter start col: ");
                int startCol = Integer.parseInt(bufferedReader.readLine());

                while (startRow >= 0 && startRow < rows && startCol >= 0 && startCol < cols) { */

                    for (long[] row : maxTable)
                        Arrays.fill(row, -1);

                    long start = System.nanoTime();
                    System.out.println(naive(mine) + " Naive time: " + (System.nanoTime() - start));

                    start = System.nanoTime();
                    System.out.println(Memoization(mine) + " DP Memoization time: " + (System.nanoTime() - start));

                    start = System.nanoTime();
                    System.out.println(Tab(mine) + " DP Tab time: " + (System.nanoTime() - start));

                    System.out.println();

                    // get next start point input
                   /* System.out.print("Enter start row: ");
                    startRow = Integer.parseInt(bufferedReader.readLine());
                    System.out.print("Enter start col: ");
                    startCol = Integer.parseInt(bufferedReader.readLine()); */

                    System.out.println();
                //}

                // get next people
                System.out.print("No of rows: ");
                rows = Integer.parseInt(bufferedReader.readLine());

                System.out.print("No of cols: ");
                cols = Integer.parseInt(bufferedReader.readLine());

                System.out.println();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long naive (int[][] mine) {

        long max = 0;
        for (int i = 0; i < mine.length; i++) {
            long curr = naiveGetMaxGold(mine, i, 0);
            if (curr > max)
                max = curr;
        }
        return max;
    }

    private static long Memoization (int[][] mine) {

        long max = 0;
        for (int i = 0; i < mine.length; i++)
            max = Math.max(max, MemoizationGetMaxGold(mine, i, 0));

        return max;
    }

    private static long Tab (int[][] mine) {

        long max = 0;
        fillTab(mine);
        for (int i = 0; i < maxTable.length; i++)
            max = Math.max(max, maxTable[i][0]);

        return max;
    }

    private static void fillTab (int[][] mine) {

        // set initial values
        for (int i = 0; i < maxTable.length; i++)
            maxTable[i][maxTable[i].length - 1] = mine[i][mine[i].length - 1];

        // DP Tabulation method
        for (int j = maxTable.length - 2; j >= 0; j--) {
            for (int i = 0; i < maxTable.length; i++) {

                long rightUpMax = i - 1 < 0 ? 0 : maxTable[i - 1][j + 1];
                long rightMax = maxTable[i][j + 1];
                long rightDownMax = i + 1 >= maxTable.length ? 0 : maxTable[i + 1][j + 1];

                maxTable[i][j] = mine[i][j] + max(rightUpMax, rightMax, rightDownMax);
            }
        }
    }

    private static long naiveGetMaxGold (int[][] mine, int startRow, int startCol) {

        if (startRow < 0 || startRow > mine.length - 1 || startCol > mine[0].length - 1 || startCol < 0)
            return 0;

        return mine[startRow][startCol] + max(naiveGetMaxGold(mine, startRow - 1, startCol + 1),
                                                naiveGetMaxGold(mine, startRow, startCol + 1),
                                                    naiveGetMaxGold(mine, startRow + 1, startCol + 1));
    }

    private static long MemoizationGetMaxGold (int[][] mine, int startRow, int startCol) {

        if (startRow < 0 || startRow > mine.length - 1 || startCol > mine[0].length - 1 || startCol < 0)
            return 0;

        if (maxTable[startRow][startCol] == -1) {
            maxTable[startRow][startCol] =
                    mine[startRow][startCol] +
                            max(naiveGetMaxGold(mine, startRow - 1, startCol + 1),
                                naiveGetMaxGold(mine, startRow, startCol + 1),
                                naiveGetMaxGold(mine, startRow + 1, startCol + 1));
        }

        return maxTable[startRow][startCol];
    }

    private static long TabGetMaxGold (int[][] mine, int startRow, int startCol) {

        // set initial values
        for (int i = 0; i < maxTable.length; i++)
            maxTable[i][maxTable[i].length - 1] = mine[i][mine[i].length - 1];

        // DP Tabulation method
        for (int j = maxTable.length - 2; j >= 0; j--) {
            for (int i = 0; i < maxTable.length; i++) {

                long rightUpMax = i - 1 < 0 ? 0 : maxTable[i - 1][j + 1];
                long rightMax = maxTable[i][j + 1];
                long rightDownMax = i + 1 >= maxTable.length ? 0 : maxTable[i + 1][j + 1];

                maxTable[i][j] = mine[i][j] + max(rightUpMax, rightMax, rightDownMax);

                // not very elegant but im lazy :p
                if (i == startRow && j == startCol)
                    break;
            }
        }

        return maxTable[startRow][startCol];
    }

    private static long max (long x, long y, long z) {
        return Math.max(x, Math.max(y, z));
    }
}
