package com.jprograming.gamedrop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameDrop extends ApplicationAdapter {

    final float WIDTH = 800;
    final float HEIGHT = 480;
    final float BUCKET_WIDTH = 64;
    final float BUCKET_HEIGHT = 64;
    final float RAINDROP_WIDTH = 64;
    final float RAINDROP_HEIGHT = 64;
    SpriteBatch batch;
    Texture dropImage;
    Texture bucketImage;
    Sound dropSound;
    Music rainMusic;
    OrthographicCamera camera;
    Rectangle bucket;
    Array<Rectangle> raindrops;
    long lastDropTime;

    @Override
    public void create() {
        batch = new SpriteBatch();
        //
        dropImage = new Texture(Gdx.files.internal("drop.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        //
        rainMusic.setLooping(true);
        rainMusic.play();
        //
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);

		/*---------------------------------------------------------------------
		PLAYER
		----------------------------------------------------------------------*/
        bucket = new Rectangle();
        bucket.x = WIDTH / 2 - BUCKET_WIDTH / 2;
        bucket.y = 20;
        bucket.width = BUCKET_WIDTH;
        bucket.height = BUCKET_HEIGHT;

        raindrops = new Array<Rectangle>();
        spawnRaindrop();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        // DRAW
        batch.begin();
        batch.draw(bucketImage, bucket.x, bucket.y);
        for (Rectangle rainDrop : raindrops) {
            batch.draw(dropImage, rainDrop.x, rainDrop.y);
        }
        batch.end();
        // UPDATE
        inputBucket();
        //
        if (TimeUtils.nanoTime() - lastDropTime > 100000000) spawnRaindrop();
        //
        for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext();) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if(raindrop.y +  RAINDROP_HEIGHT < 0)iter.remove();
            if(raindrop.overlaps(bucket)){
                dropSound.play();
                iter.remove();
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();

    }

    private void inputBucket() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();
        //
        if (bucket.x < 0) bucket.x = 0;
        if (bucket.x > WIDTH - BUCKET_WIDTH) bucket.x = WIDTH - BUCKET_WIDTH;
    }

    //
    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, WIDTH - BUCKET_WIDTH);
        raindrop.y = 480;
        raindrop.width = RAINDROP_WIDTH;
        raindrop.height = RAINDROP_HEIGHT;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

}
