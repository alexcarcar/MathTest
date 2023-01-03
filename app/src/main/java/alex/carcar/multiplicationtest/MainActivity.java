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
    public static final int OPERAND_MAX = 20;
    TextView question;
    EditText answer;
    String answerText;
    Random random;
    Button modeButton;
    String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        question = findViewById(R.id.question);
        answer = findViewById(R.id.answer);
        modeButton = findViewById(R.id.mode);

        random = new Random();
        mode = "x";
        createQuestion();

        answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals(answerText)) {
                    createQuestion();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void createQuestion() {
        if (mode.equals("x")) {
            modeButton.setText(getResources().getString(R.string.mode_multiply));
            int a = random.nextInt(OPERAND_MAX) + 1;
            int b = random.nextInt(OPERAND_MAX) + 1;
            question.setText(a + " x " + b);
            answerText = Integer.toString(a * b);
            answer.setText("");
        } else {
            modeButton.setText(getResources().getString(R.string.mode_add));
            int a = random.nextInt(900) + 100;
            int b = random.nextInt(900) + 100;
            question.setText(a + " + " + b);
            answerText = Integer.toString(a + b);
            answer.setText("");
        }
    }

    public void modeClick(View view) {
        mode = mode.equals("x") ? "+" : "x";
        createQuestion();
    }
}