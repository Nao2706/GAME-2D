package me.nao.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Juego extends ApplicationAdapter {
    private OrthographicCamera camara;
    private ShapeRenderer shapeRenderer;
    private Personaje personaje;

    @Override
    public void create() {
        camara = new OrthographicCamera();
        camara.setToOrtho(false, 800, 480);
        shapeRenderer = new ShapeRenderer();
        personaje = new Personaje(400, 240, 5);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camara.update();
        shapeRenderer.setProjectionMatrix(camara.combined);
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        personaje.update();
        shapeRenderer.rect(personaje.getX(), personaje.getY(), 50, 50);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
