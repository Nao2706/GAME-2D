package me.nao.game1;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ZombieGame extends ApplicationAdapter {
    ShapeRenderer shape;
    SpriteBatch batch;
    Player player;
    Zombie zombie;
    QTE qte;
    
    final int ANCHO_PANTALLA = 800;
    final int ALTO_PANTALLA = 400;
    final int GROSOR_PARED = 20;
    final int ALTO_SUELO = 50;
    final float GRAVEDAD = -500;

    OrthographicCamera camera;
    float zoomNormal = 1f;
    float zoomQTE = 0.75f; // 0.6f era mucho zoom, prueba 0.75f
    
    @Override
    public void create() {
        shape = new ShapeRenderer();
        player = new Player(100, ALTO_SUELO);
        zombie = new Zombie(700, ALTO_SUELO);
        zombie.setVelocidadZombie(100);
        qte = new QTE();
        batch =  new SpriteBatch();
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, ANCHO_PANTALLA, ALTO_PANTALLA);
        shape.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);
    }
    
    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        
        // ========== LÓGICA ==========
        if(!qte.isActivo()) {
            // Juego normal 200 es la velocidad
            player.mover(delta, 200 * delta, GROSOR_PARED, ANCHO_PANTALLA - GROSOR_PARED - 40);
            player.aplicarGravedad(delta, GRAVEDAD, ALTO_SUELO);
            zombie.caminarHacia(player, delta,qte.isActivo());
            
            // Si el zombie toca al player → inicia QTE
//            if(player.getHitbox().overlaps(zombie.getHitbox())) {
//                qte.iniciar();
//                qte.setDecaeProgreso(true);
//                qte.setEstilo(true); // true = apiladas, false = sobrepuestas
//            }
            if(player.getHitbox().overlaps(zombie.getHitbox())) {
                qte.iniciar();
                
//                if(zombie.tipo == TipoZombie.JEFE) {
//                    qte.setEstilo(false); // sobrepuestas
//                    qte.setDecaeProgreso(true);
//                    qte.setPlayerQteChargeSpeed(0.15f); // player sube lento
//                    qte.setZombieQteChargeSpeed(1.5f); // zombie va 50% más rápido
//                    qte.setDecaySpeed(0.12f); // decae rápido
//                } 
//                else if(zombie.tipo == TipoZombie.RAPIDO) {
//                    qte.setEstilo(true);
//                    qte.setDecaeProgreso(true);
//                    qte.setPlayerQteChargeSpeed(0.18f);
//                    qte.setZombieQteChargeSpeed(1.2f);
//                    qte.setDecaySpeed(0.10f);
//                }
//                else { // normal
//                  
//                }
                
                qte.setEstilo(true);
                qte.setDecaeProgreso(false); // no decae
                qte.setPlayerQteChargeSpeed(0.09f); // sube rápido 0.1 normal por debajo es 0.09 lento mas lento 0.08 7 6 etc
                qte.setZombieQteChargeSpeed(0.3f); // zombie lento
                qte.setDecaySpeed(0.06f);
            }
            
//            if(player.getHitbox().overlaps(zombie.getHitbox())) {
//                qte.iniciar();
//                
//                if(zombie.esJefe) { // zombies difíciles
//                    qte.setEstilo(false); // sobrepuestas = tensión
//                    qte.setDecaeProgreso(true); // SÍ decae = tienes que spamear
//                } else { // zombies normales
//                    qte.setEstilo(true); // apiladas = legible
//                    qte.setDecaeProgreso(false); // NO decae = spameas tranquilo
//                }
//            }
            
        } 
        
        else {
            // QTE activo: congela todo
            qte.actualizar(delta);
            
            if(qte.jugadorGano()) {
                zombie.getHitbox().x = player.getHitbox().x - 60; // empuja zombie atrás
                qte.resetear();
            }
            
            if(qte.zombieGano()) {
                System.out.println("GAME OVER");
                qte.resetear();
            }
        }
        
        // ========== CÁMARA + ZOOM ==========
//        if(qte.isActivo()) {
//            // Centra entre player y zombie, y baja Y para ver cubos completos
//            float xMedio = (player.getHitbox().x + zombie.getHitbox().x) / 2;
//            float yMedio = Math.max(player.getHitbox().y, zombie.getHitbox().y) + 45; // +45 prueba
//            
//            camera.position.x += (xMedio - camera.position.x) * delta * 8f;
//            camera.position.y += (yMedio - camera.position.y) * delta * 8f;
//            camera.zoom += (zoomQTE - camera.zoom) * delta * 8f;
//        } 
     // En vez de centrar Y en QTE, deja Y fija al player
//        if(qte.isActivo()) {
//            float xMedio = (player.getHitbox().x + zombie.getHitbox().x) / 2;
//            
//            camera.position.x += (xMedio - camera.position.x) * delta * 8f;
//            // camera.position.y ya no se mueve → suelo queda horizontal
//            camera.zoom += (zoomQTE - camera.zoom) * delta * 8f;
//        }
//        
//        else {
//            // Sigue al player normal
//            camera.position.x += (player.getHitbox().x - camera.position.x) * delta * 5f;
//            camera.position.y += (player.getHitbox().y - camera.position.y) * delta * 5f;
//            camera.zoom += (zoomNormal - camera.zoom) * delta * 5f;
//        }
//        camera.update();
//        shape.setProjectionMatrix(camera.combined);
        
        
        
     // ========== CÁMARA + ZOOM ==========
        float alturaFija = ALTO_PANTALLA / 2f; // 400/2 = 200. Centro vertical del mapa

        if(qte.isActivo()) {
            // QTE: centra X entre player y zombie, Y fijo
            float xMedio = (player.getHitbox().x + zombie.getHitbox().x) / 2;
            
            camera.position.x += (xMedio - camera.position.x) * delta * 8f;
            camera.position.y += (alturaFija - camera.position.y) * delta * 8f; // Y va al centro
            camera.zoom += (zoomQTE - camera.zoom) * delta * 8f;
        } else {
            // Normal: sigue X del player, Y fijo al centro
            camera.position.x += (player.getHitbox().x - camera.position.x) * delta * 5f;
            camera.position.y += (alturaFija - camera.position.y) * delta * 5f; // Y fijo, no sigue al player
            camera.zoom += (zoomNormal - camera.zoom) * delta * 5f;
        }
        camera.update();
        shape.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);

        
        // ========== DIBUJADO ==========
        Gdx.gl20.glClearColor(0.9f, 0.9f, 0.9f, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        shape.begin(ShapeRenderer.ShapeType.Filled);
        dibujarEscenario();
        player.dibujar(shape);
        zombie.dibujar(shape);
        if(qte.isActivo()) qte.dibujar(shape,batch,player,zombie); //ERROR
        shape.end();
    }
    
    private void dibujarEscenario() {
        shape.setColor(0.3f, 0.3f, 0.3f, 1);
        shape.rect(0, 0, ANCHO_PANTALLA, ALTO_SUELO);
        shape.rect(0, 0, GROSOR_PARED, ALTO_PANTALLA);
        shape.rect(ANCHO_PANTALLA - GROSOR_PARED, 0, GROSOR_PARED, ALTO_PANTALLA);
    }

    @Override
    public void dispose() {
        shape.dispose();
    }
    
    
    
    // <-- AQUÍ VA EL MAIN, al final de la clase
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Zombie Game");
        config.setWindowedMode(800, 400);
        config.useVsync(true);
        new Lwjgl3Application(new ZombieGame(), config);
    }
}