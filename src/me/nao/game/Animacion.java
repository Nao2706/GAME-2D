package me.nao.game;

import me.nao.game.enums.EstadoAnimacion;

public class Animacion {

	 private int fps;
	    private int frames;
	    private int frameActual;
	    private long tiempoUltimoFrame;
	    private EstadoAnimacion estado;

	    public Animacion(int fps, int frames, EstadoAnimacion estado) {
	        this.fps = fps;
	        this.frames = frames;
	        this.estado = estado;
	        this.frameActual = 0;
	        this.tiempoUltimoFrame = System.currentTimeMillis();
	    }

	    public void update() {
	        long tiempoActual = System.currentTimeMillis();
	        if (tiempoActual - tiempoUltimoFrame >= (1000 / fps)) {
	            frameActual = (frameActual + 1) % frames;
	            tiempoUltimoFrame = tiempoActual;
	        }
	    }

	    public int getFrameActual() {
	        return frameActual;
	    }

	    public EstadoAnimacion getEstado() {
	        return estado;
	    }
	
	
	
	
}
