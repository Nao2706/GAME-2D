package me.nao.game1;

import com.badlogic.gdx.graphics.Color; // te falta este
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Zombie {
    private Rectangle hitbox;
   
	private float velZombie;
    final int ANCHO_PANTALLA = 800;
    final int ALTO_PANTALLA = 400;
    final int GROSOR_PARED = 20;
    final int ALTO_SUELO = 50;
    
    public Zombie(float x, float y) {
        hitbox = new Rectangle(x, y, 40, 40);
        velZombie = 50;
    }
    
    public void setVelocidadZombie(float velZombie) {
    	 this.velZombie = velZombie;
    }
    
    public Rectangle getHitbox() { return hitbox; }
    
    public void caminarHacia(Player player, float delta, boolean congelado, float limiteDerecho) {
        if(congelado) return; // solo se congela el que te está tocando
        
        float velocidad = velZombie * delta;
        if(hitbox.x < player.getHitbox().x) {
            hitbox.x += velocidad;
        } else {
            hitbox.x -= velocidad;
        }
        hitbox.x = MathUtils.clamp(hitbox.x, GROSOR_PARED, limiteDerecho - hitbox.width);
    }
    
    public void dibujar(ShapeRenderer shape) {
        shape.setColor(Color.GRAY);
        shape.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }
}