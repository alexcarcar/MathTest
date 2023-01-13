package alex.carcar.multiplicationtest;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        question = findViewById(R.id.question);
        answer = findViewById(R.id.answer);
        modeButton = findViewById(R.id.mode);
        stats = findViewById(R.id.statistics);
        difficultySquare = findViewById(R.id.difficultySquare);

        random = new Random();
        mode = "x";
        totalCorrect = 0;
        startTime = System.currentTimeMillis();
        individualTime = startTime;
        difficulty = Level.REGULAR;
        setDifficultyColor();
        createQuestion();

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
    }

    private void createQuestion() {
        StringBuilder questionString = new StringBuilder();
        String operand;
        String modeString;
        int a, b;
        if (mode.equals("x")) {
            operand = getResources().getString(R.string.multiply);
            modeString = getResources().getString(R.string.mode_multiply);
            a = random.nextInt(difficulty.multiplyRange) + difficulty.multiplyStart;
            b = random.nextInt(difficulty.multiplyRange) + difficulty.multiplyStart;
            answerText = Integer.toString(a * b);
            answer.setText("");
        } else {
            operand = getResources().getString(R.string.add);
            modeString = getResources().getString(R.string.mode_add);
            a = random.nextInt(difficulty.addRange) + difficulty.addStart;
            b = random.nextInt(difficulty.addRange) + difficulty.addStart;
            answerText = Integer.toString(a + b);
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
        }
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

    enum Level {
        EASY(0, 13, 10, 90), REGULAR(0, 20, 100, 900), HARD(0, 100, 1000, 9000);

        final int multiplyStart, multiplyRange, addStart, addRange;

        Level(int _multiplyStart, int _multiplyRange, int _addStart, int _addRange) {
            multiplyStart = _multiplyStart;
            multiplyRange = _multiplyRange;
            addStart = _addStart;
            addRange = _addRange;
        }
    }
}