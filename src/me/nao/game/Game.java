package me.nao.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Player player;
    private Platform[] plataformas;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        player = new Player(100, 100, 32, 32);
        plataformas = new Platform[3];
        plataformas[0] = new Platform(0, 400, 800, 20);
        plataformas[1] = new Platform(300, 300, 200, 20);
        plataformas[2] = new Platform(100, 200, 200, 20);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Platform plataforma : plataformas) {
            plataforma.draw(batch);
        }
        player.getSprite().draw(batch);
        batch.end();

        // Movimiento del jugador
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.LEFT)) {
            player.setVelocidad(-200, 0);
        } else if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.RIGHT)) {
            player.setVelocidad(200, 0);
        } else {
            player.setVelocidad(0, 0);
        }

        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.UP)) {
            player.setVelocidad(player.getVelocidad().x, 200);
        } else if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.DOWN)) {
            player.setVelocidad(player.getVelocidad().x, -200);
        }

        player.update(Gdx.graphics.getDeltaTime());

        // Detección de colisiones
        for (Platform plataforma : plataformas) {
            if (player.getRect().overlaps(plataforma.getRect())) {
                // Ajustar la posición del jugador para que no se superponga con la plataforma
                if (player.getVelocidad().y > 0) {
                    player.getRect().y = plataforma.getRect().y - player.getRect().height;
                    player.setVelocidad(player.getVelocidad().x, 0);
                } else if (player.getVelocidad().y < 0) {
                    player.getRect().y = plataforma.getRect().y + plataforma.getRect().height;
                    player.setVelocidad(player.getVelocidad().x, 0);
                }
            }
        }
    }
}
