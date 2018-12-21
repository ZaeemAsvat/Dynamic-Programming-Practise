import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class AssemblyLineScheduling {

    private static long[][] minTime;

    public static void main (String[] args) {

        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.print("Number of stations: ");
            int numStations = Integer.parseInt(in.readLine());

            minTime = new long[2][numStations];
            Arrays.fill(minTime[0], -1);
            Arrays.fill(minTime[1], -1);

            int[][] a = new int[2][numStations];
            System.out.println(numStations + " station times for Assembly line 1: ");
            String[] strArrStationTimes0 = in.readLine().split(" ");
            System.out.println(numStations + " station times for Assembly line 2: ");
            String[] strArrStationTimes1 = in.readLine().split(" ");
            for (int i = 0; i < numStations; i++) {
                a[0][i] = Integer.parseInt(strArrStationTimes0[i]);
                a[1][i] = Integer.parseInt(strArrStationTimes1[i]);
            }

            int[] entryCost = new int[2];
            System.out.println("\n\nEntry costs:");
            System.out.print("Assembly line 1: ");
            entryCost[0] = Integer.parseInt(in.readLine());
            System.out.print("\nAssembly line 2: ");
            entryCost[1] = Integer.parseInt(in.readLine());

            int[][] t = new int[2][numStations];
            System.out.println("\n\nSwitch Assembly line costs: ");
            System.out.println("Assembly line 1: ");
            String[] strArrSwitchCosts0 = in.readLine().split(" ");
            System.out.println("Assembly line 2: ");
            String[] strArrSwitchCosts1 = in.readLine().split(" ");
            for (int i = 1; i < numStations; i++) {
                t[0][i] = Integer.parseInt(strArrSwitchCosts0[i - 1]);
                t[1][i] = Integer.parseInt(strArrSwitchCosts1[i - 1]);
            }

            int[] exitCost = new int[2];
            System.out.println("\n\nExit costs:");
            System.out.print("Assembly line 1: ");
            exitCost[0] = Integer.parseInt(in.readLine());
            System.out.print("\nAssembly line 2: ");
            exitCost[1] = Integer.parseInt(in.readLine());


            System.out.println(tab(numStations, a, entryCost,exitCost, t));
            Arrays.fill(minTime[0], -1);
            Arrays.fill(minTime[1], -1);
            a[0][numStations - 1] -= exitCost[0];
            a[1][numStations - 1] -= exitCost[1];
            System.out.println(memmmmm(numStations, a, entryCost, exitCost, t));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long tab (int n, int[][] a, int[] entryCosts, int[] exitCosts, int[][] t) {

        minTime[0][0] = a[0][0] + entryCosts[0];
        minTime[1][0] = a[1][0] + entryCosts[1];

        a[0][n - 1] += exitCosts[0];
        a[1][n - 1] += exitCosts[1];

        for (int i = 1; i < n; i++) {
            minTime[0][i] = Math.min(minTime[0][i - 1] + a[0][i],
                    minTime[1][i - 1] + t[1][i] + a[0][i]);
            minTime[1][i] = Math.min(minTime[1][i - 1] + a[1][i],
                    minTime[0][i - 1] + t[0][i] + a[1][i]);
        }

        return Math.min(minTime[0][n - 1], minTime[1][n - 1]);
    }

    private static long memmmmm (int n, int[][] a, int[] entryCosts, int[] exitCosts, int[][] t) {

        a[0][0] += entryCosts[0];
        a[1][0] += entryCosts[1];

        a[0][n - 1] += exitCosts[0];
        a[1][n - 1] += exitCosts[1];

        return Math.min(mem(0, n - 1, a, t), mem(1, n - 1, a, t));
    }

    private static long mem (int currAssLine, int curr_station, int[][] a, int[][] t) {

        if (currAssLine == 0 && minTime[currAssLine][curr_station] == -1
                || currAssLine == 1 && minTime[currAssLine][curr_station] == -1) {

            int oppAssLine = currAssLine == 0 ? 1 : 0;

            minTime[currAssLine][curr_station] = curr_station == 0 ? a[currAssLine][0] :
                    Math.min(mem(currAssLine, curr_station - 1, a, t) + a[currAssLine][curr_station],
                            mem(oppAssLine, curr_station - 1, a, t) + t[oppAssLine][curr_station] + a[currAssLine][curr_station]);
        }

        return minTime[currAssLine][curr_station];
    }
}
