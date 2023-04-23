package com.example.europequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
    implements SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferences sharedPreferences;

    TextView bestScoreValueTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("bestScoreSharedPreferences", MODE_PRIVATE);

        bestScoreValueTextView = findViewById(R.id.bestScoreValueTextView);

        setValueToBestScoreValueTextView();

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }


    private void setValueToBestScoreValueTextView() {
        int bestScore = sharedPreferences.getInt("bestScore", 0);
        bestScoreValueTextView.setText(String.valueOf(bestScore));
    }

    public void startQuiz(View view) {
        Intent openQuizActivity = new Intent(
                MainActivity.this, QuizActivity.class);
        startActivity(openQuizActivity);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("bestScore")) {
            setValueToBestScoreValueTextView();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }
}