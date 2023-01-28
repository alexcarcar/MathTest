package alex.carcar.multiplicationtest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import alex.common.AlexView;
import alex.common.voice.AlexVoice;
import alex.common.voice.PositivePhrases;

public class MainActivity extends AppCompatActivity {
    TextView question, stats;
    String answerText, mode;
    EditText answer;
    Random random;
    Button modeButton, difficultySquare;
    int totalCorrect;
    long individualTime;
    long startTime;
    Level difficulty;
    LinearLayout flashLayout;
    View[] flashScreen, mainScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        question = findViewById(R.id.question);
        answer = findViewById(R.id.answer);
        modeButton = findViewById(R.id.mode);
        stats = findViewById(R.id.statistics);
        difficultySquare = findViewById(R.id.difficultySquare);
        mainScreen = new View[]{question, answer, modeButton, stats, difficultySquare};

        random = new Random();
        mode = "x";
        totalCorrect = 0;
        startTime = System.currentTimeMillis();
        individualTime = startTime;
        difficulty = Level.REGULAR;
        setDifficultyColor();

        answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals(answerText)) {
                    totalCorrect++;
                    createQuestion();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // FLASH SCREEN
        flashLayout = findViewById(R.id.flashLayout);
        flashScreen = new View[]{flashLayout};
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(this::showFlashScreen, 1000);
    }

    private void showFlashScreen() {
        String toSpeak = getResources().getString(R.string.flash_screen_tag);
        AlexVoice.say(toSpeak);
        flashLayout.setVisibility(View.VISIBLE);
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(this::hideFlashScreen, 3500);
    }

    private void hideFlashScreen() {
        AlexView.hideAndShow(flashScreen, mainScreen);
        createQuestion();
    }

    private void createQuestion() {
        StringBuilder questionString = new StringBuilder();
        String operand;
        String modeString;
        int a, b;
        String toSpeak;
        if (mode.equals("x")) {
            operand = getResources().getString(R.string.multiply);
            modeString = getResources().getString(R.string.mode_multiply);
            a = random.nextInt(difficulty.multiplyRange) + difficulty.multiplyStart;
            b = random.nextInt(difficulty.multiplyRange) + difficulty.multiplyStart;
            answerText = Integer.toString(a * b);
            toSpeak = a + " times " + b;
            answer.setText("");
        } else {
            operand = getResources().getString(R.string.add);
            modeString = getResources().getString(R.string.mode_add);
            a = random.nextInt(difficulty.addRange) + difficulty.addStart;
            b = random.nextInt(difficulty.addRange) + difficulty.addStart;
            answerText = Integer.toString(a + b);
            toSpeak = a + " plus " + b;
            answer.setText("");
        }
        modeButton.setText(modeString);
        questionString.append(a);
        questionString.append(" ");
        questionString.append(operand);
        questionString.append(" ");
        questionString.append(b);
        question.setText(questionString.toString());
        if (totalCorrect > 0) {
            stats.setText(createReport());
            toSpeak = PositivePhrases.getRandom(random) + ". " + toSpeak;
        }
        AlexVoice.say(toSpeak);
    }

    private String createReport() {
        StringBuilder report = new StringBuilder();
        report.append("Correct: ");
        report.append(totalCorrect);
        report.append("\n");

        long individualDuration = System.currentTimeMillis() - individualTime;
        String s1 = String.format("%.2f s\n", individualDuration / 1000.0);
        report.append(s1);
        individualTime = System.currentTimeMillis();
        return report.toString();
    }

    public void modeClick(View view) {
        mode = mode.equals("x") ? "+" : "x";
        createQuestion();
    }

    public void difficultyClick(View view) {
        if (difficulty == Level.REGULAR) {
            difficulty = Level.EASY;
        } else if (difficulty == Level.EASY) {
            difficulty = Level.HARD;
        } else {
            difficulty = Level.REGULAR;
        }
        setDifficultyColor();
        createQuestion();
    }

    private void setDifficultyColor() {
        int color;
        String label;
        switch (difficulty) {
            case EASY:
                color = R.color.easyLevel;
                label = getResources().getString(R.string.easy);
                break;
            case HARD:
                color = R.color.hardLevel;
                label = getResources().getString(R.string.hard);
                break;
            case REGULAR:
            default:
                color = R.color.regularLevel;
                label = getResources().getString(R.string.normal);
                break;
        }
        difficultySquare.setBackgroundColor(getResources().getColor(color));
        difficultySquare.setText(label);
        modeButton.setBackgroundColor(getResources().getColor(color));
    }

    @Override
    protected void onPause() {
        AlexVoice.stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        AlexVoice.start(getApplicationContext());
        super.onResume();
    }

    enum Level {
        EASY(1, 13, 10, 90), REGULAR(1, 20, 100, 900), HARD(1, 100, 1000, 9000);

        final int multiplyStart, multiplyRange, addStart, addRange;

        Level(int _multiplyStart, int _multiplyRange, int _addStart, int _addRange) {
            multiplyStart = _multiplyStart;
            multiplyRange = _multiplyRange;
            addStart = _addStart;
            addRange = _addRange;
        }
    }
}