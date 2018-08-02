package br.edu.infnet.persistencesample;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class QuestionComposerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_composer);
    }


    public void sendQuestion(View view){
        Intent intent = new Intent();
        EditText questionField = findViewById(R.id.question_field);
        final Question question = new Question(questionField.getText().toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                saveQuestion(question);
            }
        }).start();

        intent.putExtra("question", question);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void saveQuestion(Question question){
        File outputFile = new File(getFilesDir(), MainActivity.QUESTIONS_FILE);
        try {
            if (!outputFile.exists()){
                outputFile.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(outputFile, true);
            //OutputStream outputStream = openFileOutput(MainActivity.QUESTIONS_FILE, Context.MODE_PRIVATE | MODE_APPEND);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            writer.write("#\n");
            writer.write(question.toString());
            writer.close();
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(QuestionComposerActivity.this, "Salvo com sucesso", Toast.LENGTH_LONG).show();
                }
            });
            //
        }
        catch (IOException e){
            //Toast.makeText(this, "Falha ao gravar questão", Toast.LENGTH_LONG).show();
        }
    }
}
