package me.nao.game1;

//Player.java
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Player {
 private Rectangle hitbox;
 private float velY;
 
 public Player(float x, float y) {
     hitbox = new Rectangle(x, y, 40, 40);
     velY = 0;
 }
 
 public Rectangle getHitbox() { return hitbox; }
 
 public void mover(float delta, float velocidad, int limiteIzq, int limiteDer) {
     if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) hitbox.x -= velocidad;
     if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) hitbox.x += velocidad;
     if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && hitbox.y == 50) velY = 300;
     
     boolean agachado = Gdx.input.isKeyPressed(Input.Keys.DOWN);
     hitbox.height = agachado ? 20 : 40;
     
     if(hitbox.x < limiteIzq) hitbox.x = limiteIzq;
     if(hitbox.x > limiteDer) hitbox.x = limiteDer;
 }
 
 public void aplicarGravedad(float delta, float GRAVEDAD, int ALTO_SUELO) {
     velY += GRAVEDAD * delta;
     hitbox.y += velY * delta;
     if(hitbox.y < ALTO_SUELO) {
         hitbox.y = ALTO_SUELO;
         velY = 0;
     }
 }
 
 public void dibujar(ShapeRenderer shape) {
     shape.setColor(Color.RED);
     shape.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
 }
}