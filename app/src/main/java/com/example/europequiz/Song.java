package com.example.europequiz;

public class Song {

    private int albumArt;
    private int song;
    private String title;


    public Song(int albumArt, int song, String title) {
        this.albumArt = albumArt;
        this.song = song;
        this.title = title;
    }


    public int getAlbumArt() {
        return albumArt;
    }

    public int getSong() {
        return song;
    }

    public String getTitle() {
        return title;
    }


    public void setAlbumArt(int albumArt) {
        this.albumArt = albumArt;
    }

    public void setSong(int song) {
        this.song = song;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
