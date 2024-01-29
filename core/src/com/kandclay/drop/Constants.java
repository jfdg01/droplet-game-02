package com.kandclay.drop;

public class Constants {

    // Screen dimensions
    public static final float SCREEN_WIDTH = 800;
    public static final float SCREEN_HEIGHT = 480;

    // Units
    public static final int ONE_SECOND_NS = 1_000_000_000;

    // Button dimensions
    public static final float BUTTON_WIDTH = 70f;
    public static final float BUTTON_HEIGHT = 70f;

    // Button position
    public static final float BUTTON_X = (SCREEN_WIDTH - BUTTON_WIDTH) / 2;
    public static final float BUTTON_Y = (SCREEN_HEIGHT - BUTTON_HEIGHT) / 2;

    // Bucket dimensions
    public static final float BUCKET_WIDTH = 64;
    public static final float BUCKET_HEIGHT = 64;
    public static final float BUCKET_Y = 20;

    // Raindrop dimensions
    public static final float RAINDROP_WIDTH = 64;
    public static final float RAINDROP_HEIGHT= 64;
    public static final float RAINDROP_SPEED = 200;

    // Paths
    public static final String BUTTON_IMAGE_PATH = "sprites/button.png";
    public static final String DROPLET_IMAGE_PATH = "sprites/droplet.png";
    public static final String BUCKET_IMAGE_PATH = "sprites/bucket.png";
    public static final String DROPLET_SOUND_PATH = "sounds/drop.wav";
    public static final String RAIN_MUSIC_PATH = "sounds/rain.mp3";


    // Other constants can be added here as needed

}
