package br.edu.infnet.persistencesample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Question> questions;
    private final int QUESTION_CODE = 77;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        questions = new ArrayList<>();

        for (int i = 0; i < 4; i++){
            questions.add(new Question("O que vamos fazer quando o número " + i + " acabar?"));
        }


        recyclerView = findViewById(R.id.questions_list);
        LinearLayoutManager llm = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        QuestionAdapter adapter = new QuestionAdapter(questions);

        recyclerView.setAdapter(adapter);
    }


    public void askQuestion(View view){
        Intent intent = new Intent(this, QuestionComposerActivity.class);

        startActivityForResult(intent, QUESTION_CODE);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("DEBUG","VOLTANDO");
        if (resultCode == RESULT_OK && requestCode == QUESTION_CODE){
            Question question = (Question) data.getSerializableExtra("question");
            Log.d(question.getMoment().toString(), question.getText().toString());
            QuestionAdapter adapter = (QuestionAdapter) recyclerView.getAdapter();
            adapter.addItem(question);
        }
        Log.d("DEBUG", "SAINDO");
    }
}
