package me.nao.game1;

//Player.java
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Player {
 private Rectangle hitbox;
 private float velY;
 private float vx = 0; // NUEVO: velocidad horizontal
 private float velocidadMax = 200f; // velocidad tope
 private float aceleracion = 1200f; // qué tan rápido llegas al tope
 private float friccionAire = 0.92f; // 0.92 = desliza, 0.8 = frena rápido
 private float friccionSuelo = 0.7f;
 @SuppressWarnings("unused")
private boolean miraDerecha = true;
 
 
 
 public Player(float x, float y) {
     hitbox = new Rectangle(x, y, 40, 40);
     velY = 0;
 }
 
 public Rectangle getHitbox() { return hitbox; }

 

 
 public void mover(float delta, float velocidad, int limiteIzq, int limiteDer,int ALTO_SUELO) {
     boolean enSuelo = hitbox.y == ALTO_SUELO; // ajusta según tu variable de suelo
     
     // 1. Input = aceleración, no movimiento directo
     if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
         vx -= aceleracion * delta;
         miraDerecha = false;
     }
     if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
         vx += aceleracion * delta;
         miraDerecha = true;
     }
     
     // 2. Limita velocidad máxima
     vx = MathUtils.clamp(vx, -velocidadMax, velocidadMax);
     
     // 3. Fricción: si no presionas nada, vx decae
     float friccion = enSuelo ? friccionSuelo : friccionAire;
     if(!Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
         vx *= friccion;
     }
     
     // 4. Para micro-deslizamiento
     if(Math.abs(vx) < 2f) vx = 0;
     
     // 5. Ahora sí mueve con vx
     hitbox.x += vx * delta;
     
     // Agachado
     boolean agachado = Gdx.input.isKeyPressed(Input.Keys.DOWN);
     hitbox.height = agachado ? 20 : 40;
     
     // Límites
     if(hitbox.x < limiteIzq) {
         hitbox.x = limiteIzq;
         vx = 0; // rebote contra pared
     }
     if(hitbox.x > limiteDer) {
         hitbox.x = limiteDer;
         vx = 0;
     }
     
     // Salto
     if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && enSuelo) {
         velY = 300;
     }
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