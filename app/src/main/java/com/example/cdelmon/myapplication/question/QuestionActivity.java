package com.example.cdelmon.myapplication.question;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cdelmon.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        int numberOfquestion = extras.getInt("NUM_OF_QUESTIONS");

        this.fetchQuestions(numberOfquestion);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) this.findViewById(R.id.question_list);
        recyclerView.setLayoutManager(layoutManager);

    }

    protected ArrayList<Question> parseResultsRequestQuestion(JSONArray results) throws JSONException {

        ArrayList<Question> questions = new ArrayList<>();

        for (int i = 0; i < results.length(); i++) {
            try {
                String title = (String) results.getJSONObject(i).get("question");
                String difficulty = (String) results.getJSONObject(i).get("difficulty");
                String answer = (String) results.getJSONObject(i).get("correct_answer");
                String category = (String) results.getJSONObject(i).get("category");
                JSONArray incorrect = (JSONArray) results.getJSONObject(i).get("incorrect_answers");

                final ArrayList<String> incorrectClean = new ArrayList<>();
                for (int j = 0; j < incorrect.length(); j++) {
                    Log.d("ici", convertCharacter(incorrect.getString(j)));
                    incorrectClean.add(convertCharacter(incorrect.getString(j)));
                }

                Question question = new Question(convertCharacter(title), convertCharacter(difficulty), convertCharacter(answer), convertCharacter(category), incorrectClean);
                questions.add(question);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return questions;
    }

    protected String convertCharacter(String string) {
        String result;
        result = string.replace("&quot;", "\"");
        return result.replace("&#039;", "'");
    }


    void fetchQuestions(int numberOfquestion) {
        final String url = "https://opentdb.com/api.php?amount=" + numberOfquestion;
        final RequestQueue queue = Volley.newRequestQueue(this);
        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray results = response.getJSONArray("results");
                    ArrayList<Question> questions = parseResultsRequestQuestion(results);

                    //Update ui
                    recyclerView.setAdapter(new QuestionAdapter(questions));

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonRequest);
    }
}
