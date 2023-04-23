package com.example.europequiz.utils;

import com.example.europequiz.R;
import com.example.europequiz.Song;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Utils {

    public static int[] europeImages = {
            R.drawable.europe1, R.drawable.europe2, R.drawable.europe3,
            R.drawable.europe4, R.drawable.europe5, R.drawable.europe6,
            R.drawable.europe7, R.drawable.europe8, R.drawable.europe9,
            R.drawable.europe10
    };

    public static ArrayList<Song> songs = new ArrayList<>(Arrays.asList(
            new Song(R.drawable.the_final_countdown , R.raw.carrie , "Carrie"),
            new Song(R.drawable.the_final_countdown , R.raw.cherokee , "Cherokee"),
            new Song(R.drawable.the_final_countdown , R.raw.danger_on_the_track , "Danger on the Track"),
            new Song(R.drawable.the_final_countdown , R.raw.heart_of_stone , "Heart of Stone"),
            new Song(R.drawable.the_final_countdown , R.raw.love_chaser , "Love Chaser"),
            new Song(R.drawable.the_final_countdown , R.raw.ninja , "Ninja"),
            new Song(R.drawable.the_final_countdown , R.raw.on_the_loose , "On the Loose"),
            new Song(R.drawable.the_final_countdown , R.raw.rock_the_night , "Rock the Night"),
            new Song(R.drawable.the_final_countdown , R.raw.the_final_countdown , "The Final Countdown"),
            new Song(R.drawable.the_final_countdown , R.raw.time_has_come , "Time Has Come")
    ));

    public static int getRandomEuropeImage() {
        int randomIndex =  getRandomNumberInRange(0, europeImages.length-1);
        return europeImages[randomIndex];
    }

    public static int getRandomNumberInRange(int min, int max) {
        // min and max are inclusive
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
