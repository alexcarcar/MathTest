package alex.common.voice;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class AlexVoice {
    static TextToSpeech tts;

    public static void start(Context context) {
        if (tts != null) {
            stop();
        }
        tts = new TextToSpeech(context, status -> {
            if (status != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.US);
            }
        });
    }

    public static void say(String toSpeak) {
        tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    public static void stop() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}
