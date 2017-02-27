package edu.missouriwestern.awahlert1.csc445.mathquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    private static final String EXTRA_ANSWER_IS_TRUE =
            "edu.missouriwestern.awahlert1.csc445.mathquiz.answer_is_true";
    private static final String EXTRA_ANSWER_IS_SHOWN =
            "edu.missouriwestern.awahlert1.csc445.mathquiz.answer_is_shown";
    private static final String TAG = "CheatActivity";

    private TextView mAnswerTextView;
    private Button mShowAnswer;
    private TextView mApiLevelTextView;
    private boolean mAnswerIsTrue;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue)
    {
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    public static boolean wasAnswerShown(Intent result)
    {
     return result.getBooleanExtra(EXTRA_ANSWER_IS_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Cheat has been activated!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = getIntent().getBooleanExtra((EXTRA_ANSWER_IS_TRUE), false);
        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mApiLevelTextView = (TextView) findViewById(R.id.api_text_view);
        mApiLevelTextView.setText("API Level is " + Build.VERSION.SDK_INT);
        mShowAnswer = (Button) findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                if (mAnswerIsTrue)
                {
                    mAnswerTextView.setText(R.string.true_button);
                }
                else
                {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });

    }

    private void setAnswerShownResult(boolean isAnswerShown)
    {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_IS_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }
}
