package br.edu.infnet.persistencesample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Question> questions;
    private final int QUESTION_CODE = 77;
    RecyclerView recyclerView;
    public static final String QUESTIONS_FILE = "SavedQuestions.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        questions = new ArrayList<>();

        for (int i = 1; i < 4; i++){
            questions.add(new Question("O que vamos fazer quando o número " + i + " acabar?"));
        }

        //questions = readQuestions();


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
    protected void onStart() {
        super.onStart();

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Question> questions = readQuestions();
                final QuestionAdapter adapter = new QuestionAdapter(questions);
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    private List<Question> readQuestions(){
        List<Question> questions = new ArrayList<>();
        File inputFile = new File(getFilesDir(), QUESTIONS_FILE);

        try{
            InputStream inputStream = new FileInputStream(inputFile);
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            while (line != null){
                if (line.equals("#")){
                    String text = bufferedReader.readLine();
                    Date date = new Date();
                    String dateString = bufferedReader.readLine();
                    try{
                        date = SimpleDateFormat.getDateTimeInstance().parse(dateString);
                    } catch (Exception e){
                        //Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d("XABU", e.getMessage());
                    }
                    questions.add(new Question(text, date));
                }
                Log.d("ARQUIVO", line);
                line = bufferedReader.readLine();

            }

        } catch (IOException exception){
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
        }

        return questions;
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
