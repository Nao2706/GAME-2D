package me.nao.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Platform {

    private Rectangle rect;
    private Sprite sprite;
    private Vector2 velocidad;
    private boolean esDañina;
    private Vector2 puntoA;
    private Vector2 puntoB;

    public Platform(float x, float y, float width, float height) {
        rect = new Rectangle(x, y, width, height);
        velocidad = new Vector2(0, 0);
        esDañina = false;
        puntoA = new Vector2(x, y);
        puntoB = new Vector2(x + 100, y); // ejemplo de movimiento horizontal

        Pixmap pixmap = new Pixmap((int) width, (int) height, Pixmap.Format.RGB565);
        pixmap.setColor(Color.GRAY);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        sprite = new Sprite(texture);
        sprite.setPosition(x, y);
    }

    public void update(float deltaTime) {
        rect.x += velocidad.x * deltaTime;
        rect.y += velocidad.y * deltaTime;
        sprite.setPosition(rect.x, rect.y);

        // ejemplo de movimiento entre dos puntos
        if (rect.x >= puntoB.x) {
            velocidad.x = -velocidad.x;
        } else if (rect.x <= puntoA.x) {
            velocidad.x = velocidad.x;
        }
    }

    public void setVelocidad(Vector2 velocidad) {
        this.velocidad = velocidad;
    }

    public void setEsDañina(boolean esDañina) {
        this.esDañina = esDañina;
    }

    public boolean esDañina() {
        return esDañina;
    }

    public Rectangle getRect() {
        return rect;
    }
    
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
