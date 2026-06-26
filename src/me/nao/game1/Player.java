package me.nao.game1;

//Player.java
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
//import com.badlogic.gdx.Input.Keys;
//import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    private Animation<TextureRegion> animIdle, animPreCorrer, animCorrer;
    private Texture spriteSheet; // <- FALTABA ESTE
    private Rectangle hitbox;
    private float velY;
    private float vx = 0;
    private float velocidadMax = 200f;
    private float aceleracion = 1200f;
    private float friccionAire = 0.92f;
    private float friccionSuelo = 0.7f;
    private boolean miraDerecha = true;
    private float anguloApuntado = 0;
    private float stateTime = 0; // <- FALTABA ESTE para la animación

    public Player(float x, float y) {
        hitbox = new Rectangle(x, y, 40, 40);
        velY = 0;

        spriteSheet = new Texture("C:\\Users\\Fernando\\Downloads\\stickman.png");

        // 32 ancho, 48 alto por frame
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet, 32, 48);

        // Idle = 1,2,3,4,5 → tmp[0][0] a tmp[0][4]
        animIdle = new Animation<>(0.15f, tmp[0][0], tmp[0][1], tmp[0][2], tmp[0][3], tmp[0][4]);

        // PreCorrer = 6,7,8,9,10 → tmp[0][5], tmp[0][6], tmp[0][7], tmp[1][0], tmp[1][1]
        animPreCorrer = new Animation<>(0.08f, tmp[0][5], tmp[0][6], tmp[0][7], tmp[1][0], tmp[1][1]);

        // Correr = 11,12 → tmp[1][2], tmp[1][3]
        animCorrer = new Animation<>(0.08f, tmp[1][2], tmp[1][3]);

        animIdle.setPlayMode(Animation.PlayMode.LOOP);
        animPreCorrer.setPlayMode(Animation.PlayMode.NORMAL); // NO loop, solo 1 vez
        animCorrer.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void actualizar(float delta, int limiteIzq, int limiteDer, int ALTO_SUELO) {
        stateTime += delta;
        mover(delta, limiteIzq, limiteDer, ALTO_SUELO);
        aplicarGravedad(delta, -600, ALTO_SUELO);
        actualizarApuntado();
    }


    public void dibujar(SpriteBatch batch, int ALTO_SUELO) {
        stateTime += Gdx.graphics.getDeltaTime(); // Suma SIEMPRE al inicio

        boolean moviendose = Math.abs(vx) > 5;
        Animation<TextureRegion> animActual;

        // Lógica de 3 estados: Idle -> PreCorrer -> Correr
        if (!moviendose) {
            animActual = animIdle;
            stateTime = 0; // Solo resetea cuando paras
        } else {
            // Si no ha terminado PreCorrer, quédate ahí
            if (stateTime <= animPreCorrer.getAnimationDuration()) {
                animActual = animPreCorrer;
            } else {
                animActual = animCorrer;
            }
        }

        // Loop solo para Idle y Correr. PreCorrer es NORMAL = no loop
        boolean loop = animActual == animIdle || animActual == animCorrer;
        TextureRegion frame = animActual.getKeyFrame(stateTime, loop);

        // Flip: hazlo en el frame que vas a dibujar
        if (!miraDerecha &&!frame.isFlipX()) frame.flip(true, false);
        if (miraDerecha && frame.isFlipX()) frame.flip(true, false);

        batch.draw(frame, hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    public void mover(float delta, int limiteIzq, int limiteDer, int ALTO_SUELO) {
        boolean enSuelo = hitbox.y == ALTO_SUELO;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            vx -= aceleracion * delta;
            miraDerecha = false;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            vx += aceleracion * delta;
            miraDerecha = true;
        }

        vx = MathUtils.clamp(vx, -velocidadMax, velocidadMax);

        float friccion = enSuelo? friccionSuelo : friccionAire;
        if(!Gdx.input.isKeyPressed(Input.Keys.LEFT) &&!Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            vx *= friccion;
        }

        if(Math.abs(vx) < 2f) vx = 0;
        hitbox.x += vx * delta;

        // FIX: agachado sin flotar
        float alturaAnterior = hitbox.height;
        boolean agachado = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        hitbox.height = agachado? 20 : 40;
        if(!agachado) hitbox.y += alturaAnterior - hitbox.height;

        if(hitbox.x < limiteIzq) {
            hitbox.x = limiteIzq;
            vx = 0;
        }
        if(hitbox.x > limiteDer) {
            hitbox.x = limiteDer;
            vx = 0;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && enSuelo &&!agachado) {
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

    
    
    public void actualizarApuntado() {
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) anguloApuntado = 90;
        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) anguloApuntado = 270;
        else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) anguloApuntado = 180;
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) anguloApuntado = 0;
    }
    // CAMBIO: ahora usa SpriteBatch en vez de ShapeRenderer
//    public void dibujar(SpriteBatch batch) {
//        Animation<TextureRegion> animActual = Math.abs(vx) > 5? animCorrer : animIdle;
//        TextureRegion frame = animActual.getKeyFrame(stateTime, true);
//
//        // Flip según dirección
//        if(!miraDerecha &&!frame.isFlipX()) frame.flip(true, false);
//        if(miraDerecha && frame.isFlipX()) frame.flip(true, false);
//
//        batch.draw(frame, hitbox.x, hitbox.y, 32, 48); // 32x48 = tamaño sprite
//    }

    public void dispose() {
        spriteSheet.dispose(); // <- importante para no fugar memoria
    }

    public Rectangle getHitbox() { return hitbox; }
    public float getAnguloApuntado() { return anguloApuntado; }
}