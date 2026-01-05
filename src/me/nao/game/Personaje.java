package me.nao.game;

import com.badlogic.gdx.Gdx;

import me.nao.game.enums.EstadoAnimacion;

public class Personaje {
	
    private float x, y;
    private float velocidad;
    private Animacion animacionIdle;
    private Animacion animacionCaminando;
    private Animacion animacionCorriendo;
    private EstadoAnimacion estadoActual;

    public Personaje(float x, float y, float velocidad) {
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.animacionIdle = new Animacion(12, 8, EstadoAnimacion.IDLE);
        this.animacionCaminando = new Animacion(12, 16, EstadoAnimacion.CAMINANDO);
        this.animacionCorriendo = new Animacion(24, 16, EstadoAnimacion.CORRIENDO);
        this.estadoActual = EstadoAnimacion.IDLE;
    }

    public void moverArriba() {
        y += velocidad;
        estadoActual = EstadoAnimacion.CAMINANDO;
    }

    public void moverAbajo() {
        y -= velocidad;
        estadoActual = EstadoAnimacion.CAMINANDO;
    }

    public void moverIzquierda() {
        x -= velocidad;
        estadoActual = EstadoAnimacion.CAMINANDO;
    }

    public void moverDerecha() {
        x += velocidad;
        estadoActual = EstadoAnimacion.CAMINANDO;
    }

    public void update() {
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.UP) ||
                Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.DOWN) ||
                Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.LEFT) ||
                Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.RIGHT)) {
            if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.SHIFT_LEFT)) {
                estadoActual = EstadoAnimacion.CORRIENDO;
            } else {
                estadoActual = EstadoAnimacion.CAMINANDO;
            }
        } else {
            estadoActual = EstadoAnimacion.IDLE;
        }

        switch (estadoActual) {
            case IDLE:
                animacionIdle.update();
                break;
            case CAMINANDO:
                animacionCaminando.update();
                break;
            case CORRIENDO:
                animacionCorriendo.update();
                break;
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getFrameActual() {
        switch (estadoActual) {
            case IDLE:
                return animacionIdle.getFrameActual();
            case CAMINANDO:
                return animacionCaminando.getFrameActual();
            case CORRIENDO:
                return animacionCorriendo.getFrameActual();
            default:
                return 0;
        }
    }
	
	
	

}
