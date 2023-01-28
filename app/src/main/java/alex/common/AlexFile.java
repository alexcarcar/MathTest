package alex.common;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class AlexFile {
    public static ArrayList<String> readAsList(Resources resources, int rawFile) {
        ArrayList<String> list = new ArrayList<>();
        InputStream inputStream = resources.openRawResource(rawFile);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            while (reader.ready()) {
                list.add(reader.readLine());
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<String> readAsList(Context context, String filename) {
        ArrayList<String> list = new ArrayList<>();
        try {
            InputStream in = context.openFileInput(filename);
            if (in != null) {
                InputStreamReader tmp = new InputStreamReader(in);
                BufferedReader reader = new BufferedReader(tmp);
                String str;

                while ((str = reader.readLine()) != null) {
                    list.add(str);
                }
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String readAsString(Context context, String filename) {
        return AlexList.listAsString(readAsList(context, filename));
    }

    public static void saveString(Context context, String filename, String toSave) {
        try {
            OutputStreamWriter out = new OutputStreamWriter(context.openFileOutput(filename, 0));
            out.write(toSave);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
