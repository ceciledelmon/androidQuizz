package com.example.cdelmon.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Button;

import com.example.cdelmon.myapplication.question.QuestionActivity;

public class MainActivity extends AppCompatActivity {

    TextView numberTextView;
    SeekBar actionSeekBar;
    EditText editAction;
    Button changeActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberTextView = (TextView) this.findViewById(R.id.numberView);
        actionSeekBar = (SeekBar) this.findViewById(R.id.numberAction);
        editAction = (EditText) this.findViewById(R.id.editNumAction);
        changeActivityButton = (Button) this.findViewById(R.id.changeActivity);

        String initStateValue = String.valueOf(actionSeekBar.getProgress());

        numberTextView.setText(initStateValue);
        editAction.setText(initStateValue);

        actionSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String currentValue = String.valueOf(progress);
                numberTextView.setText(currentValue);
                editAction.setText(currentValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        editAction.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String val = v.getText().toString();
                int currentValue = Integer.parseInt(val);
                if (currentValue > actionSeekBar.getMax()) {
                    currentValue = actionSeekBar.getMax();
                }
                actionSeekBar.setProgress(currentValue);
                numberTextView.setText(String.valueOf(currentValue));
                return false;
            }
        });


        changeActivityButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeView(v);
            }
        });

    }

    private void changeView(View view) {
        Intent intent = new Intent(this, QuestionActivity.class);
        int num_of_questions = this.actionSeekBar.getProgress();
        intent.putExtra("NUM_OF_QUESTIONS", num_of_questions);
        startActivity(intent);
    }
}
