package me.nao.game1;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.nao.game.enums.Items;

public class Arma {
    private Items tipo;
    private int balasActuales;
    private float cooldownDisparo = 0;
    private float angulo = 0;
    private boolean atacando = false; // para melee
    private float tiempoAtaque = 0;   // duración animación melee
    
    public Arma(Items tipo) {
        this.tipo = tipo;
        this.balasActuales = tipo.balasMax;
    }
    
    public void actualizar(float delta, float anguloApuntado) {
        this.angulo = anguloApuntado;
        if(cooldownDisparo > 0) cooldownDisparo -= delta;
        
        // Para melee: termina ataque después de X seg
        if(atacando) {
            tiempoAtaque -= delta;
            if(tiempoAtaque <= 0) atacando = false;
        }
    }
    
    public void dibujar(SpriteBatch batch, float xPlayer, float yPlayer) {
//        float originX = 5; // pivote hombro
//        float originY = 8;

        if(tipo.tipo == Items.TipoArma.DISTANCIA) {
//            batch.draw(spritePistola, xPlayer + 20, yPlayer + 10, originX, originY, 20, 10, 1, 1, angulo);
        } else {
            if(estaAtacando()) {
//                batch.draw(spriteEspada, xPlayer + 15, yPlayer + 10, originX, originY, 30, 8, 1, 1, angulo);
            }
        }
    }
    
    public boolean atacar() {
        if(cooldownDisparo > 0) return false;
        
        if(tipo.tipo == Items.TipoArma.DISTANCIA) {
            // Lógica de balas
            if(balasActuales == 0) return false;
            balasActuales--;
            cooldownDisparo = tipo.cadencia;
            return true;
            
        } else {
            // Lógica melee: no gasta balas, pero tiene duración de ataque
            atacando = true;
            tiempoAtaque = 0.2f; // 200ms dura el golpe
            cooldownDisparo = tipo.cadencia;
            return true;
        }
    }
    
    public boolean estaAtacando() { return atacando; } // para dibujar espada girando
    public float getAngulo() { return angulo; }
    public Items getTipo() { return tipo; }
}
