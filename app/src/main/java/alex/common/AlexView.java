package alex.common;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

final public class AlexView {
    public static void hideAndShow(View[] hide, View[] show) {
        for (View view : hide) view.setVisibility(View.GONE);
        for (View view : show) view.setVisibility(View.VISIBLE);
    }

    public static void toggleKeyboard(Context context, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInputFromWindow(view.getWindowToken(), InputMethodManager.SHOW_FORCED, 0);
        }
    }
}
