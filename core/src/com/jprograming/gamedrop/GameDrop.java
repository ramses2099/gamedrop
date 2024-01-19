package com.jprograming.gamedrop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameDrop extends ApplicationAdapter {

    final float WIDTH = 800;
    final float HEIGHT = 480;
    final float BUCKET_WIDTH = 64;
    final float BUCKET_HEIGHT = 64;
    SpriteBatch batch;
    Texture dropImage;
    Texture bucketImage;
    Sound dropSound;
    Music rainMusic;
    OrthographicCamera camera;

    Rectangle bucket;

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
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(bucketImage, bucket.x, bucket.y);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();

    }

    private void inputBucket() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();
        //
        if (bucket.x < 0) bucket.x = 0;
        if (bucket.x > WIDTH - BUCKET_WIDTH) bucket.x = WIDTH - BUCKET_WIDTH;
    }

}
