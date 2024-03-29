package com.kandclay.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import jdk.internal.net.http.common.Pair;

import java.util.Iterator;

import static com.kandclay.drop.Constants.*;

public class GameScreen implements Screen {
    final Drop game;
    Array<Texture> dropTextureArray;
    Texture bucketImage;
    Sound dropSound;
    Music rainMusic;
    OrthographicCamera camera;
    Rectangle bucket;
    Array<Rectangle> raindrops;

    // array of pair that stores a raindrop rentangle and its id
    Array<Pair<Rectangle, Integer>> raindropsWithId;
    long lastDropTime;
    int dropsGathered;

    public GameScreen(final Drop game) {
        this.game = game;

        dropTextureArray = new Array<>();
        for (int i = 0; i < 13; i++) {
            dropTextureArray.add(new Texture(Gdx.files.internal("sprites/droplet-" + i + ".png")));
        }

        bucketImage = new Texture(Gdx.files.internal(BUCKET_IMAGE_PATH));

        // load the drop sound effect and the rain background "music"
        dropSound = Gdx.audio.newSound(Gdx.files.internal(DROPLET_SOUND_PATH));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal(RAIN_MUSIC_PATH));
        rainMusic.setLooping(true);

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

        // create a Rectangle to logically represent the bucket
        bucket = new Rectangle();
        bucket.x = SCREEN_WIDTH / 2 - BUCKET_WIDTH / 2; // center the bucket horizontally
        bucket.y = BUCKET_Y; // bottom left corner of the bucket is 20 pixels above
        // the bottom screen edge
        bucket.width = BUCKET_WIDTH;
        bucket.height = BUCKET_HEIGHT;

        // create the raindrops array and spawn the first raindrop
        raindrops = new Array<>();
        spawnRaindrop();

    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, SCREEN_WIDTH - RAINDROP_WIDTH);
        raindrop.y = SCREEN_HEIGHT;
        raindrop.width = RAINDROP_WIDTH;
        raindrop.height = RAINDROP_HEIGHT;
        raindrops.add(raindrop);
        raindropsWithId.add(new Pair<>(raindrop, //raind));
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color.
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and all drops
        game.batch.begin();
        game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, SCREEN_HEIGHT);
        game.batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height);
        for (int i = 0; i < raindrops.size; i++) {
            Rectangle raindrop = raindrops.get(i);
            // Set the color of the raindrom to a color of the rainbow depending on its index
            setRainbowColor(i);
            game.batch.draw(dropImage, raindrop.x, raindrop.y, raindrop.width, raindrop.height);
        }
        game.batch.end();

        // process user input
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = touchPos.x - BUCKET_WIDTH / 2;
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT))
            bucket.x -= RAINDROP_SPEED * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            bucket.x += RAINDROP_SPEED * Gdx.graphics.getDeltaTime();

        // make sure the bucket stays within the screen bounds
        if (bucket.x < 0)
            bucket.x = 0;
        if (bucket.x > SCREEN_WIDTH - BUCKET_WIDTH)
            bucket.x = SCREEN_WIDTH - BUCKET_WIDTH;

        // check if we need to create a new raindrop
        if (TimeUtils.nanoTime() - lastDropTime > ONE_SECOND_NS)
            spawnRaindrop();

        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we increase the
        // value our drops counter and add a sound effect.
        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= RAINDROP_SPEED * Gdx.graphics.getDeltaTime();
            if (raindrop.y + RAINDROP_WIDTH < 0)
                iter.remove();
            if (raindrop.overlaps(bucket)) {
                dropsGathered++;
                dropSound.play();
                iter.remove();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        rainMusic.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }

}
