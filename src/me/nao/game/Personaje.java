package me.nao.game;

public class Personaje {
	
	
    private float x, y;
    private float velocidad;

    public Personaje(float x, float y, float velocidad) {
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
    }

    public void moverArriba() {
        y += velocidad;
    }

    public void moverAbajo() {
        y -= velocidad;
    }

    public void moverIzquierda() {
        x -= velocidad;
    }

    public void moverDerecha() {
        x += velocidad;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
	
	
	
	
	
	
	
	
	
	
	
	

}
