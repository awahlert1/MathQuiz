package edu.missouriwestern.awahlert1.csc445.mathquiz;

        import android.app.Activity;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.util.Log;

public class QuizActivity extends AppCompatActivity {
    private TextView questionTextView;
    private int currentIndex = 0;
    private boolean mIsCheater;
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;

    final Question[] questionBank = new Question[]{
            new Question(R.string.question1, false),
            new Question(R.string.question2, false),
            new Question(R.string.question3, true),
            new Question(R.string.question4, true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) was called!");
        setContentView(R.layout.activity_quiz);

        Button mTrueButton;
        Button mFalseButton;
        ImageButton mNextButton;
        ImageButton mPrevButton;
        Button mCheatButton;

        questionTextView = (TextView)findViewById(R.id.question_text_view);
        updateQuestion();
        //int question = questionBank[currentIndex].getTextResId();
        //questionTextView.setText(question);


        mTrueButton = (Button)findViewById(R.id.TrueButton);
        mFalseButton = (Button)findViewById(R.id.FalseButton);
        mCheatButton = (Button)findViewById(R.id.cheat_button);

        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
         /*       Toast.makeText(MainActivity.this,
                        R.string.TrueButton,
                        Toast.LENGTH_SHORT).show(); */
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
         /*       Toast.makeText(MainActivity.this,
                        R.string.FalseButton,
                        Toast.LENGTH_SHORT).show(); */
                checkAnswer(false);
            }
        });

        mCheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //start CheatActivity
                //Intent i = new Intent(QuizActivity.this, CheatActivity.class);
                boolean answerIsTrue = questionBank[currentIndex].isAnswerTrue();
                Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                //startActivity(i);
                startActivityForResult(i, REQUEST_CODE_CHEAT);
                Log.i(TAG,"Cheat Button Was Clicked");

            }

        });

        updateQuestion();

        mNextButton = (ImageButton)findViewById(R.id.NextButton);
        mNextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentIndex = (currentIndex + 1) % questionBank.length;
                //int question = questionBank[currentIndex].getTextResId();
                //questionTextView.setText(question);
                mIsCheater = false;
                updateQuestion();
            }
        });

        mPrevButton = (ImageButton)findViewById(R.id.PrevButton);
        mPrevButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(currentIndex >= 1)
                {
                    currentIndex = (currentIndex - 1) % questionBank.length;
                }
                else
                {
                    currentIndex = questionBank.length - 1;
                }
                //int question = questionBank[currentIndex].getTextResId();
                //questionTextView.setText(question);
                updateQuestion();
            }
        });
        if(savedInstanceState != null)
        {
            currentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }
        updateQuestion();

    }//end of onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode != Activity.RESULT_OK)
        {
            return;
        }
        if(resultCode == REQUEST_CODE_CHEAT)
        {
            if(data == null)
            {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, currentIndex);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "OnStart() was Called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "OnPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "OnResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "OnStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "I AM THE DESTROYER!!!!");
    }


    public void onClick(View v)
    {
        currentIndex = (currentIndex + 1) % questionBank.length;
        updateQuestion();
    }

    private void updateQuestion()
    {
        int question = questionBank[currentIndex].getTextResId();
        questionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue)
    {
        boolean answerIsTrue = questionBank[currentIndex].isAnswerTrue();
        int messageResId = 0;

        if(mIsCheater)
        {
            messageResId = R.string.judgment_toast;
        }
        else
        {


            if (userPressedTrue == answerIsTrue)
            {
                messageResId = R.string.correct_toast;
            }
            else
            {
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }


}//end of MainActivity
