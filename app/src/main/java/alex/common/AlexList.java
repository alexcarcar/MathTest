package alex.common;

import java.util.ArrayList;
import java.util.Arrays;

public class AlexList {
    public static String listAsString(ArrayList<String> list) {
        StringBuilder buf = new StringBuilder();
        int n = list.size();
        for (int i = 0; i < n; i++) {
            buf.append(list.get(i));
            if (i < n - 1) buf.append("\n");
        }
        return buf.toString();
    }

    public static ArrayList<String> stringAsList(String string) {
        return new ArrayList<>(Arrays.asList(string.split("\n")));
    }
}