import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ChoiceOfArea {

    enum Area {X, Y, Z, numAreas}

    private static HashMap<String, Integer> mymap;

    public static void main (String[] args) {

        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {

            mymap = new HashMap<>();

            System.out.print("Enter initial Power A value: ");
            int initAValue = Integer.parseInt(in.readLine());

            System.out.print("Enter initial Power B value: ");
            int initBValue = Integer.parseInt(in.readLine());

            int[] areaAValues = new int[Area.numAreas.ordinal()];
            int[] areaBValues = new int[Area.numAreas.ordinal()];

            // get Area X input
            System.out.print("\nEnter Power A value change for Area X: ");
            areaAValues[Area.X.ordinal()] = Integer.parseInt(in.readLine());
            System.out.print("\nEnter Power B value change for Area X: ");
            areaBValues[Area.X.ordinal()] = Integer.parseInt(in.readLine());

            // get Area Y input
            System.out.print("\nEnter Power A value change for Area Y: ");
            areaAValues[Area.Y.ordinal()] = Integer.parseInt(in.readLine());
            System.out.print("\nEnter Power B value change for Area Y: ");
            areaBValues[Area.Y.ordinal()] = Integer.parseInt(in.readLine());

            // get Area Z input
            System.out.print("\nEnter Power A value change for Area Z: ");
            areaAValues[Area.Z.ordinal()] = Integer.parseInt(in.readLine());
            System.out.print("\nEnter Power B value change for Area Z: ");
            areaBValues[Area.Z.ordinal()] = Integer.parseInt(in.readLine());

            // calculate and display naive output
            long start = System.nanoTime();
            System.out.println(naiveSurvive(initAValue, initBValue, areaAValues, areaBValues) +
                    " Naive time: " + (System.nanoTime() - start));

            // calculate and display DP Memoization output
            start = System.nanoTime();
            System.out.println(MemoizationSurvive(initAValue, initBValue, areaAValues, areaBValues) +
                    " DP Mem time: " + (System.nanoTime() - start));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int naiveSurvive (int initAVal, int initBVal, int[] areaAValues, int[] areaBValues) {
        return Math.max(naiveSurviveRecur(Area.X, areaAValues,areaBValues, initAVal, initBVal),
                Math.max(naiveSurviveRecur(Area.Y, areaAValues,areaBValues, initAVal, initBVal),
                        naiveSurviveRecur(Area.Z, areaAValues,areaBValues, initAVal, initBVal)));
    }

    private static int naiveSurviveRecur (Area startArea, int[] areaAValues, int[] areaBValues, int currAVal, int currBVal) {

        currAVal += areaAValues[startArea.ordinal()];
        currBVal += areaBValues[startArea.ordinal()];

        if (currAVal < 0 || currBVal < 0)
            return 0;

        Area[] nextAreas = new Area[2];
        if (startArea == Area.X) {
            nextAreas[0] = Area.Y;
            nextAreas[1] = Area.Z;
        } else if (startArea == Area.Y) {
            nextAreas[0] = Area.X;
            nextAreas[1] = Area.Z;
        } else {
            nextAreas[0] = Area.Y;
            nextAreas[1] = Area.X;
        }

        return 1 + Math.max(naiveSurviveRecur(nextAreas[0], areaAValues, areaBValues, currAVal, currBVal),
                naiveSurviveRecur(nextAreas[1], areaAValues, areaBValues, currAVal, currBVal));

    }

    private static int MemoizationSurvive (int initAVal, int initBVal, int[] areaAValues, int[] areaBValues){

        return Math.max(MemoizationSurviveRecur(Area.X, areaAValues, areaBValues, initAVal, initBVal),
                Math.max(MemoizationSurviveRecur(Area.Y, areaAValues, areaBValues, initAVal, initBVal),
                        MemoizationSurviveRecur(Area.Z, areaAValues, areaBValues, initAVal, initBVal)));
    }

    private static int MemoizationSurviveRecur (Area startArea, int[] areaAValues, int[] areaBValues, int currAVal, int currBVal) {

        String thisSubProblemIdentifier = Integer.toString(currAVal) + " "
                + Integer.toString(currBVal) + " " + Integer.toString(startArea.ordinal());

        if (!mymap.containsKey(thisSubProblemIdentifier)) {

            currAVal += areaAValues[startArea.ordinal()];
            currBVal += areaBValues[startArea.ordinal()];

            if (currAVal < 0 || currBVal < 0)
                mymap.put(thisSubProblemIdentifier, 0);

            else {
                Area[] nextAreas = new Area[2];
                if (startArea == Area.X) {
                    nextAreas[0] = Area.Y;
                    nextAreas[1] = Area.Z;
                } else if (startArea == Area.Y) {
                    nextAreas[0] = Area.X;
                    nextAreas[1] = Area.Z;
                } else {
                    nextAreas[0] = Area.Y;
                    nextAreas[1] = Area.X;
                }

                mymap.put(thisSubProblemIdentifier, 1 + Math.max(MemoizationSurviveRecur(nextAreas[0], areaAValues, areaBValues, currAVal, currBVal),
                        MemoizationSurviveRecur(nextAreas[1], areaAValues, areaBValues, currAVal, currBVal)));
            }
        }

        return mymap.get(thisSubProblemIdentifier);
    }

}
