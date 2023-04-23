package com.example.europequiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.europequiz.utils.Utils;
import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    TextView secondsLeftTextView, scoreTextView;
    ImageView albumImageView;
    Button variant1, variant2, variant3, variant4, rightButton;

    MediaPlayer mediaPlayer;
    Song currentSong;

    ArrayList<Song> leftSongs = (ArrayList<Song>) Utils.songs.clone();

    int currentLevel = 0;
    int numberOfLevels = 7;
    int currentScore = 0;
    int secondsLeft = 0;
    int randomValue = 21;
    int randomValue2 = 22;
    int randomValue3 = 23;
    int randomValue4 = 24;
    int randomValue5 = 25;

    CountDownTimer secondsLeftCountdownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        enableSetDisplayHomeAsUpToActionBar();

        initViewFields();

        setOnClickListenersToVariantButtons();

        startNewLevel();
    }


    private void enableSetDisplayHomeAsUpToActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initViewFields() {
        secondsLeftTextView = findViewById(R.id.secondsLeftTextView);
        scoreTextView = findViewById(R.id.scoreTextView);

        albumImageView = findViewById(R.id.albumImageView);

        variant1 = findViewById(R.id.variant1Button);
        variant2 = findViewById(R.id.variant2Button);
        variant3 = findViewById(R.id.variant3Button);
        variant4 = findViewById(R.id.variant4Button);
    }

    private void setOnClickListenersToVariantButtons() {
        variant1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endLevel(variant1);
            }
        });
        variant2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endLevel(variant2);
            }
        });
        variant3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endLevel(variant3);
            }
        });
        variant4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endLevel(variant4);
            }
        });
    }


    private void startNewLevel() {
        currentLevel++;
        setRandomEuropeImageToAlbumImageView();
        currentScore += secondsLeft;
        scoreTextView.setText(String.valueOf(currentScore));
        secondsLeft = 20;
        makeAllButtonsClickable();
        makeAllButtonsGrey();
        initNewCurrentSong();
        leftSongs.remove(currentSong);
        initMediaPlayer();
        setSongNamesToVariantButtons();
        mediaPlayer.start();
        startSecondsLeftCountdownTimer();
    }

    public void skip(View view) {
        if (currentLevel < numberOfLevels) {
            secondsLeftCountdownTimer.cancel();
            mediaPlayer.stop();
            secondsLeft = 0;
            startNewLevel();
        } else {
            endGame();
        }
    }

    private void endLevel(Button chosenButton) {
        secondsLeftCountdownTimer.cancel();
        mediaPlayer.stop();
        albumImageView.setImageResource(currentSong.getAlbumArt());
        makeAllButtonsNotClickable();
        if (chosenButton == rightButton) {
            chosenButton.setBackgroundResource(R.color.colorButtonSelectedRight);
        } else {
            rightButton.setBackgroundResource(R.color.colorButtonSelectedRight);
            if (chosenButton != null) {
                chosenButton.setBackgroundResource(R.color.colorButtonSelectedWrong);
            }
            secondsLeft = 0;
        }
        makeOneSecondPauseAndStartViewLevelOrEndGame();
    }

    private void endGame() {
        secondsLeftCountdownTimer.cancel();
        mediaPlayer.stop();
        SharedPreferences bestScoreSharedPreferences =
                getSharedPreferences("bestScoreSharedPreferences", MODE_PRIVATE);
        int bestScore = bestScoreSharedPreferences.getInt("bestScore", 0);

        makeAllViewsInvisibleAndResultTextViewVisible();

        TextView resultTextView = findViewById(R.id.resultTextView);

        if (currentScore > bestScore) {
            resultTextView.setText("Congrats! It's a new record!\nYour score is " + currentScore);
            updateBestScoreSharedPreference();
        } else {
            resultTextView.setText(
                    "Your score is " + currentScore + ".\nBest Score is " + bestScore + ".");
        }
    }


    // startNewLevel methods
    private void setRandomEuropeImageToAlbumImageView() {
        albumImageView.setImageResource(Utils.getRandomEuropeImage());
    }

    private void makeAllButtonsClickable() {
        variant1.setClickable(true);
        variant2.setClickable(true);
        variant3.setClickable(true);
        variant4.setClickable(true);
        findViewById(R.id.skipButton).setClickable(true);
    }

    private void makeAllButtonsGrey() {
        variant1.setBackgroundResource(R.color.colorDefaultButton);
        variant2.setBackgroundResource(R.color.colorDefaultButton);
        variant3.setBackgroundResource(R.color.colorDefaultButton);
        variant4.setBackgroundResource(R.color.colorDefaultButton);
    }

    private void initNewCurrentSong() {
        currentSong = leftSongs.get(Utils.getRandomNumberInRange(0, leftSongs.size()-1));
    }

    private void initMediaPlayer() {
        mediaPlayer =
                MediaPlayer.create(getApplicationContext(), currentSong.getSong());
    }

    private void setSongNamesToVariantButtons() {
        ArrayList<Song> leftSongsToGetTitleInCurrentLevel = (ArrayList<Song>) Utils.songs.clone();
        leftSongsToGetTitleInCurrentLevel.remove(currentSong);
        int rightButtonIdentifier = Utils.getRandomNumberInRange(1, 4);

        switch (rightButtonIdentifier) {
            case 1:
                rightButton = variant1;
            case 2:
                rightButton = variant2;
            case 3:
                rightButton = variant3;
            case 4:
                rightButton = variant4;
        }

        for (Button variantButton: new Button[] {variant1, variant2, variant3, variant4}) {
            if (variantButton == rightButton) {
                variantButton.setText(currentSong.getTitle());
            } else {
                Song randomSongToGetTitle = leftSongsToGetTitleInCurrentLevel.get(
                        Utils.getRandomNumberInRange(
                                0, leftSongsToGetTitleInCurrentLevel.size()-1));
                leftSongsToGetTitleInCurrentLevel.remove(randomSongToGetTitle);
                variantButton.setText(randomSongToGetTitle.getTitle());
            }
        }
    }

    private void startSecondsLeftCountdownTimer() {
        secondsLeftCountdownTimer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                secondsLeft--;
                secondsLeftTextView.setText(String.valueOf(secondsLeft));
            }

            @Override
            public void onFinish() {
                endLevel(null);
            }
        };
        secondsLeftCountdownTimer.start();
    }

    // endLevel methods
    private void makeAllButtonsNotClickable() {
        variant1.setClickable(false);
        variant2.setClickable(false);
        variant3.setClickable(false);
        variant4.setClickable(false);
        findViewById(R.id.skipButton).setClickable(false);
    }

    private void makeOneSecondPauseAndStartViewLevelOrEndGame() {
        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (currentLevel < numberOfLevels) {
                    secondsLeftCountdownTimer.cancel();
                    Log.i("!@#208", "here");
                    startNewLevel();
                } else {
                    endGame();
                }
            }
        }.start();
    }


    // endGame methods
    private void makeAllViewsInvisibleAndResultTextViewVisible() {
        secondsLeftTextView.setAlpha(0);
        albumImageView.setAlpha(0);
        variant1.setAlpha(0);
        variant2.setAlpha(0);
        variant3.setAlpha(0);
        variant4.setAlpha(0);
        findViewById(R.id.scoreTextTextView).setAlpha(0);
        scoreTextView.setAlpha(0);
        findViewById(R.id.skipButton).setAlpha(0);

        findViewById(R.id.resultTextView).setAlpha(1);
    }

    private void updateBestScoreSharedPreference() {
        SharedPreferences bestScoreSharedPreferences =
                getSharedPreferences("bestScoreSharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = bestScoreSharedPreferences.edit();
        editor.putInt("bestScore", currentScore);
        editor.apply();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            secondsLeftCountdownTimer.cancel();
            mediaPlayer.stop();
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
