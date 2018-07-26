package br.edu.infnet.persistencesample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class QuestionComposerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_composer);
    }


    public void sendQuestion(View view){
        Intent intent = new Intent();
        EditText questionField = findViewById(R.id.question_field);
        Question question = new Question(questionField.getText().toString());
        intent.putExtra("question", question);
        setResult(RESULT_OK, intent);
        finish();
    }
}
