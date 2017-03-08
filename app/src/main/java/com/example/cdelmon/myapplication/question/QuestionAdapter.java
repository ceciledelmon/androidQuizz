package com.example.cdelmon.myapplication.question;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cdelmon.myapplication.R;

import java.util.ArrayList;

/**
 * Created by cdelmon on 14/02/2017.
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private ArrayList<Question> questions;

    public QuestionAdapter(ArrayList<Question> questions){
        this.questions = questions;
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context ctx = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(ctx);
        final View questionItemView = inflater.inflate(R.layout.question_item, parent, false);
        return new QuestionViewHolder(questionItemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        final Question question = questions.get(position);
        holder.questionText.setText(question.text);
        holder.questionCat.setText(question.category);
        switch(question.difficulty) {
            case "easy":
                holder.questionText.setBackground(new ColorDrawable(Color.parseColor("#8EA1F0")));
                break;
            case "medium":
                holder.questionText.setBackground(new ColorDrawable(Color.parseColor("#A5F0E4")));
                break;
            case "hard":
                holder.questionText.setBackground(new ColorDrawable(Color.parseColor("#F5FAC8")));
                break;
        }
        holder.questionAnswer = question.answer;
        String allAnswers = question.answer;
        for (Object response : question.incorrect) {
            allAnswers += response;
        }
        holder.questionRep.setText(allAnswers);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        TextView questionText;
        TextView questionCat;
        TextView questionRep;
        String questionAnswer;
        public QuestionViewHolder(View itemView) {
            super(itemView);
            this.questionText = (TextView) itemView.findViewById(R.id.question_text);
            this.questionCat = (TextView) itemView.findViewById(R.id.question_cat);
            this.questionRep = (TextView) itemView.findViewById(R.id.question_rep);
            this.questionAnswer = "the answer";
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            AlertDialog.Builder myBuilder = new AlertDialog.Builder(itemView.getContext());
            myBuilder.setTitle("Answer");
            myBuilder.setMessage("The answer is " + questionAnswer);
            myBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            myBuilder.show();
            return true;
        }
    }
}
