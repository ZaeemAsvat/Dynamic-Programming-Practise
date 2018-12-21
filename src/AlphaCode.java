import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class AlphaCode {

    public static HashMap<String, Integer> decodingsFound = new HashMap<>();

    public static void main(String[] args) {
        // write your code here

        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {

            String input = in.readLine();
            while (!input.equals("0")) {
                decodingsFound.clear();
                System.out.println(getNumDecodings(input, 0));
                input = in.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getNumDecodings (String code, int startIndex) {

        if (!decodingsFound.containsKey(code)) {

            if (code.length() <= 1) {
                decodingsFound.put(code, code.equals("0") ? 0 : 1);
            } else {
                int count = 0;
                int i = startIndex;
                while (i < code.length() && Integer.parseInt(code.substring(0, i + 1)) > 0 && Integer.parseInt(code.substring(0, i + 1)) < 27) {
                    count += getNumDecodings(code.substring(i + 1), 0);
                    i++;
                }
                decodingsFound.put(code, count);
            }
        }

        return decodingsFound.get(code);
    }

    public static int naiveGetNumDecodings (String code, int startIndex) {

        if (code.length() <= 1) {
            return code.equals("0") ? 0 : 1;
        } else {
            int count = 0;
            int i = startIndex;
            while (i < code.length() && Integer.parseInt(code.substring(0, i + 1)) > 0 && Integer.parseInt(code.substring(0, i + 1)) < 27) {
                count += getNumDecodings(code.substring(i + 1), 0);
                i++;
            }
            return count;
        }
    }

}

/*
25114
BEAN
BEAAD
YAAD
YAN
YKD
BEKD

Total = 6

 */