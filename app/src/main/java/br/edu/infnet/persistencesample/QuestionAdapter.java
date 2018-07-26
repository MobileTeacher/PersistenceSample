package br.edu.infnet.persistencesample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter {

    List<Question> questions;

    public QuestionAdapter(List<Question> questions) {
        this.questions = questions;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.question_card,
                viewGroup,
                false);

        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        QuestionViewHolder vh = (QuestionViewHolder) viewHolder;
        Question question = questions.get(i);
        vh.text.setText(question.getText());
        DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
        vh.moment.setText(dateFormat.format(question.getMoment()));

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
    public void addItem(Question q){
        questions.add(q);
        notifyItemInserted(getItemCount());
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {

        public TextView text;
        public TextView moment;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            text =  itemView.findViewById(R.id.question_body);
            moment = itemView.findViewById(R.id.time);
        }
    }
}
