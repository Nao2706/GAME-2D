package me.nao.game1;

import com.badlogic.gdx.graphics.Color; // te falta este
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    
    public void caminarHacia(Player p, float delta, boolean qteActivo) {
        if(qteActivo) return; // congelado en QTE
        
        float dir = p.getHitbox().x - this.hitbox.x;
        if(Math.abs(dir) > 5) {
            if(dir > 0) hitbox.x += velZombie * delta;
            else hitbox.x -= velZombie * delta;
        }
        if(hitbox.x < GROSOR_PARED) hitbox.x = GROSOR_PARED;
        if(hitbox.x > ANCHO_PANTALLA - GROSOR_PARED - hitbox.width) 
            hitbox.x = ANCHO_PANTALLA - GROSOR_PARED - hitbox.width;
    }
    
    public void dibujar(ShapeRenderer shape) {
        shape.setColor(Color.GRAY);
        shape.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }
}