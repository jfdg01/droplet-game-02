package com.kandclay.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class Drop extends ApplicationAdapter {

    // Cosntants
    private static final int WIDTH = 800;
    private static final int HEIGHT = 480;
    private static final int LEFT_WALL = 0;
    private static final int RIGHT_WALL = 800;
    private static final int ONE_SECOND_NS = 1_000_000_000;
    private static final int BUCKET_WIDTH = 64;
    private static final int BUCKET_HEIGHT = 20;
    private static final int RAINDROP_WIDTH = 64;
    private static final int RAINDROP_HEIGHT = 64;
    private static final int BUCKET_INITIAL_Y = 20;
    private static final int BUCKET_MOVE_SPEED = 300;
    private static final int RAINDROP_FALL_SPEED = 200;

    // Bucket
    private Rectangle bucket;
    private Texture bucketImg;

    // Raindrops
    private Array<Rectangle> raindrops;
    private Texture dropletImg;
    private Sound dropletSound;

    private long lastDropTime;

    // Extra
    private Music rainMusic;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Vector3 touchPos;

    @Override
    public void create() {

        // Assets

        dropletImg = new Texture(Gdx.files.internal("sprites/droplet.png"));
        bucketImg = new Texture(Gdx.files.internal("sprites/bucket.png"));

        dropletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/rain.mp3"));

        rainMusic.setLooping(true);
        rainMusic.play();

        // Camera

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);

        // Batch
        batch = new SpriteBatch();

        // Objects
        bucket = new Rectangle();
        bucket.x = (float) HEIGHT / 2 - (float) BUCKET_WIDTH / 2;
        bucket.y = BUCKET_INITIAL_Y;
        bucket.width = BUCKET_WIDTH;
        bucket.height = BUCKET_HEIGHT;

        raindrops = new Array<>();
        spawnRaindrop();

        touchPos = new Vector3();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();

        batch.begin();
        batch.draw(bucketImg, bucket.x, bucket.y);
        for (Rectangle raindrop : raindrops) {
            batch.draw(dropletImg, raindrop.x, raindrop.y);
        }
        batch.end();

        handleTouchInputBucket();

        float deltaTime = Gdx.graphics.getDeltaTime();
        float moveAmount = BUCKET_MOVE_SPEED * deltaTime;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (bucket.x - moveAmount < LEFT_WALL) {
                bucket.x = 0;
            } else {
                bucket.x -= moveAmount;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (bucket.x + moveAmount > RIGHT_WALL - BUCKET_WIDTH) {
                bucket.x = RIGHT_WALL - BUCKET_WIDTH;
            } else {
                bucket.x += moveAmount;
            }
        }

        if (TimeUtils.nanoTime() - lastDropTime > ONE_SECOND_NS) {
            spawnRaindrop();
        }

        for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext(); ) {
            Rectangle raindrop = iter.next();
            raindrop.y -= RAINDROP_FALL_SPEED * Gdx.graphics.getDeltaTime();
            if (raindrop.y + RAINDROP_WIDTH < 0) iter.remove();
            if (raindrop.overlaps(bucket)) {
                dropletSound.play();
                iter.remove();
            }
        }
    }

    private void handleTouchInputBucket() {
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = touchPos.x - (float) BUCKET_WIDTH / 2;
        }
<<<<<<< HEAD
    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, WIDTH - BUCKET_WIDTH);
        raindrop.y = HEIGHT;
        raindrop.width = RAINDROP_WIDTH;
        raindrop.height = RAINDROP_HEIGHT;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void dispose() {
        dropletImg.dispose();
        bucketImg.dispose();
        dropletSound.dispose();
        rainMusic.dispose();
        batch.dispose();
=======
>>>>>>> parent of d8c1f64 (Created MainMenu scene)
    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, WIDTH - BUCKET_WIDTH);
        raindrop.y = HEIGHT;
        raindrop.width = RAINDROP_WIDTH;
        raindrop.height = RAINDROP_HEIGHT;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void dispose() {
        dropletImg.dispose();
        bucketImg.dispose();
        dropletSound.dispose();
        rainMusic.dispose();
        batch.dispose();
    }

}
