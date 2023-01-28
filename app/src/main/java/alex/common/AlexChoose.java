package alex.common;

import java.util.ArrayList;
import java.util.Random;

public class AlexChoose {
    public static String getFromList(Random random, ArrayList<String> list) {
        if (list == null || list.isEmpty()) return "";
        return list.get(random.nextInt(list.size()));
    }
}
