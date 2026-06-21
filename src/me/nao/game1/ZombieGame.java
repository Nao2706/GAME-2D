package me.nao.game1;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class ZombieGame extends ApplicationAdapter {
    ShapeRenderer shape;
    SpriteBatch batch;
    Player player;
//    Zombie zombie;
    Array<Zombie> zombies; // CAMBIO
    QTE qte;
    
    final int ANCHO_PANTALLA = 800;
    final int ALTO_PANTALLA = 400;
    final int GROSOR_PARED = 20;
    final int ALTO_SUELO = 50;
    final float GRAVEDAD = -500;
    
    // NUEVO: Tamaño real del mundo/mapa. Si tu mapa mide más que la pantalla, ponlo más grande
    final float mapWidth = 1600f;   // ej: 3000px de ancho para poder moverte
    final float mapHeight = 400f;   // si solo es horizontal, deja 400
    
    OrthographicCamera camera;
    float zoomNormal = 1f;
    float zoomQTE = 0.75f; // 0.6f era mucho zoom, prueba 0.75f
    
    @Override
    public void create() {
        shape = new ShapeRenderer();
        player = new Player(100, ALTO_SUELO);
//        zombie = new Zombie(700, ALTO_SUELO);
//        zombie.setVelocidadZombie(200);
        zombies = new Array<>();
        zombies.add(new Zombie(700, ALTO_SUELO));
        zombies.add(new Zombie(950, ALTO_SUELO)); // zombi 2 para probar
        zombies.get(0).setVelocidadZombie(20);
        zombies.get(1).setVelocidadZombie(20);
        
        
        qte = new QTE();
        batch =  new SpriteBatch();
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, ANCHO_PANTALLA, ALTO_PANTALLA);
        shape.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);
    }
    
//    @Override
//    public void render() {
//        float delta = Gdx.graphics.getDeltaTime();
//        float camHalfWidth = camera.viewportWidth * camera.zoom / 2f; // importante: multiplica por zoom
//        // ========== LÓGICA ==========
//        if(!qte.isActivo()) {
//            // Juego normal 200 es la velocidad
//            player.mover(delta, 200 * delta, GROSOR_PARED, ANCHO_PANTALLA - GROSOR_PARED - 40);
//            player.aplicarGravedad(delta, GRAVEDAD, ALTO_SUELO);
//            zombie.caminarHacia(player, delta,qte.isActivo());
//            
//            // Si el zombie toca al player → inicia QTE
////            if(player.getHitbox().overlaps(zombie.getHitbox())) {
////                qte.iniciar();
////                qte.setDecaeProgreso(true);
////                qte.setEstilo(true); // true = apiladas, false = sobrepuestas
////            }
//            if(player.getHitbox().overlaps(zombie.getHitbox())) {
//                qte.iniciar();
//                
////                if(zombie.tipo == TipoZombie.JEFE) {
////                    qte.setEstilo(false); // sobrepuestas
////                    qte.setDecaeProgreso(true);
////                    qte.setPlayerQteChargeSpeed(0.15f); // player sube lento
////                    qte.setZombieQteChargeSpeed(1.5f); // zombie va 50% más rápido
////                    qte.setDecaySpeed(0.12f); // decae rápido
////                } 
////                else if(zombie.tipo == TipoZombie.RAPIDO) {
////                    qte.setEstilo(true);
////                    qte.setDecaeProgreso(true);
////                    qte.setPlayerQteChargeSpeed(0.18f);
////                    qte.setZombieQteChargeSpeed(1.2f);
////                    qte.setDecaySpeed(0.10f);
////                }
////                else { // normal
////                  
////                }
//                
//                qte.setEstilo(true);
//                qte.setDecaeProgreso(false); // no decae
//                qte.setPlayerQteChargeSpeed(0.09f); // sube rápido 0.1 normal por debajo es 0.09 lento mas lento 0.08 7 6 etc
//                qte.setZombieQteChargeSpeed(0.3f); // zombie lento
//                qte.setDecaySpeed(0.06f);
//            }
//            
////            if(player.getHitbox().overlaps(zombie.getHitbox())) {
////                qte.iniciar();
////                
////                if(zombie.esJefe) { // zombies difíciles
////                    qte.setEstilo(false); // sobrepuestas = tensión
////                    qte.setDecaeProgreso(true); // SÍ decae = tienes que spamear
////                } else { // zombies normales
////                    qte.setEstilo(true); // apiladas = legible
////                    qte.setDecaeProgreso(false); // NO decae = spameas tranquilo
////                }
////            }
//            
//        } 
//        
//        else {
//            // QTE activo: congela todo
//            qte.actualizar(delta);
//            
//            if(qte.jugadorGano()) {
//                zombie.getHitbox().x = player.getHitbox().x - 60; // empuja zombie atrás
//                qte.resetear();
//            }
//            
//            if(qte.zombieGano()) {
//                System.out.println("GAME OVER");
//                qte.resetear();
//            }
//        }
//        
//        // ========== CÁMARA + ZOOM ==========
////        if(qte.isActivo()) {
////            // Centra entre player y zombie, y baja Y para ver cubos completos
////            float xMedio = (player.getHitbox().x + zombie.getHitbox().x) / 2;
////            float yMedio = Math.max(player.getHitbox().y, zombie.getHitbox().y) + 45; // +45 prueba
////            
////            camera.position.x += (xMedio - camera.position.x) * delta * 8f;
////            camera.position.y += (yMedio - camera.position.y) * delta * 8f;
////            camera.zoom += (zoomQTE - camera.zoom) * delta * 8f;
////        } 
//     // En vez de centrar Y en QTE, deja Y fija al player
////        if(qte.isActivo()) {
////            float xMedio = (player.getHitbox().x + zombie.getHitbox().x) / 2;
////            
////            camera.position.x += (xMedio - camera.position.x) * delta * 8f;
////            // camera.position.y ya no se mueve → suelo queda horizontal
////            camera.zoom += (zoomQTE - camera.zoom) * delta * 8f;
////        }
////        
////        else {
////            // Sigue al player normal
////            camera.position.x += (player.getHitbox().x - camera.position.x) * delta * 5f;
////            camera.position.y += (player.getHitbox().y - camera.position.y) * delta * 5f;
////            camera.zoom += (zoomNormal - camera.zoom) * delta * 5f;
////        }
////        camera.update();
////        shape.setProjectionMatrix(camera.combined);
//        
//        
//        
//     // ========== CÁMARA + ZOOM ==========
//        float alturaFija = ALTO_PANTALLA / 2f; // 400/2 = 200. Centro vertical del mapa
//
//        if(qte.isActivo()) {
//            // QTE: centra X entre player y zombie, Y fijo
//            float xMedio = (player.getHitbox().x + zombie.getHitbox().x) / 2;
//            
//            camera.position.x += (xMedio - camera.position.x) * delta * 8f;
//            camera.position.y += (alturaFija - camera.position.y) * delta * 8f; // Y va al centro
//            camera.zoom += (zoomQTE - camera.zoom) * delta * 8f;
//        } else {
//            // Normal: sigue X del player, Y fijo al centro
//            camera.position.x += (player.getHitbox().x - camera.position.x) * delta * 5f;
//            camera.position.y += (alturaFija - camera.position.y) * delta * 5f; // Y fijo, no sigue al player
//            camera.zoom += (zoomNormal - camera.zoom) * delta * 5f;
//        }
//        
//        
//     // NUEVO: Clamp para que no se salga del mapa
//        camera.position.x = MathUtils.clamp(camera.position.x, camHalfWidth, mapWidth - camHalfWidth);
//        camera.position.y = MathUtils.clamp(camera.position.y, camHalfHeight, mapHeight - camHalfHeight);
//        
//        
//        camera.update();
//        shape.setProjectionMatrix(camera.combined);
//        batch.setProjectionMatrix(camera.combined);
//
//        
//        // ========== DIBUJADO ==========
//        Gdx.gl20.glClearColor(0.9f, 0.9f, 0.9f, 1);
//        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        
//        shape.begin(ShapeRenderer.ShapeType.Filled);
//        dibujarEscenario();
//        player.dibujar(shape);
//        zombie.dibujar(shape);
//        if(qte.isActivo()) qte.dibujar(shape,batch,player,zombie); //ERROR
//        shape.end();
//    }
    
    
//    @Override
//    public void render() {
//        float delta = Gdx.graphics.getDeltaTime();
//        float camHalfWidth = camera.viewportWidth * camera.zoom / 2f;
//        float camHalfHeight = camera.viewportHeight * camera.zoom / 2f; // TE FALTABA ESTA
//        
//        // ========== LÓGICA ==========
//        if(!qte.isActivo()) {
//            // CAMBIO: límites ahora son del mapa, no de pantalla
//        	// En ZombieGame.render(), cambia esta línea:
//        	player.mover(delta, 400 * delta, GROSOR_PARED, (int) (mapWidth - GROSOR_PARED - player.getHitbox().width));
//            player.aplicarGravedad(delta, GRAVEDAD, ALTO_SUELO);
//            zombie.caminarHacia(player, delta, qte.isActivo(), mapWidth - GROSOR_PARED);
//            
//            if(player.getHitbox().overlaps(zombie.getHitbox())) {
//                qte.iniciar();
//                qte.setEstilo(true);
//                qte.setDecaeProgreso(false);
//                qte.setPlayerQteChargeSpeed(0.09f);
//                qte.setZombieQteChargeSpeed(0.3f);
//                qte.setDecaySpeed(0.06f);
//            }
//        } else {
//            qte.actualizar(delta);
//            if(qte.jugadorGano()) {
//                zombie.getHitbox().x = player.getHitbox().x - 60;
//                qte.resetear();
//            }
//            if(qte.zombieGano()) {
//                System.out.println("GAME OVER");
//                qte.resetear();
//            }
//        }
//        
//        // ========== CÁMARA + ZOOM ==========
//        float offsetY = -50f;
//        float alturaFija = mapHeight / 2f + offsetY; // usa mapHeight, no ALTO_PANTALLA
//
//        if(qte.isActivo()) {
//            // CAMBIO: centra usando centro de hitboxes, no esquina
//            float xMedio = (player.getHitbox().x + player.getHitbox().width/2 + zombie.getHitbox().x + zombie.getHitbox().width/2) / 2f;
//            
//            camera.position.x += (xMedio - camera.position.x) * delta * 8f;
//            camera.position.y += (alturaFija - camera.position.y) * delta * 8f;
//            camera.zoom += (zoomQTE - camera.zoom) * delta * 8f;
//        } else {
//            // CAMBIO: sigue al centro del player
//            camera.position.x += (player.getHitbox().x + player.getHitbox().width/2 - camera.position.x) * delta * 5f;
//            camera.position.y += (alturaFija - camera.position.y) * delta * 5f;
//            camera.zoom += (zoomNormal - camera.zoom) * delta * 5f;
//        }
//        
//        // CLAMP con mapWidth/mapHeight reales
//        camera.position.x = MathUtils.clamp(camera.position.x, camHalfWidth, mapWidth - camHalfWidth);
//        camera.position.y = MathUtils.clamp(camera.position.y, camHalfHeight, mapHeight - camHalfHeight);
//        
//        camera.update();
//        shape.setProjectionMatrix(camera.combined);
//        batch.setProjectionMatrix(camera.combined);
//
//        // ========== DIBUJADO ==========
//        Gdx.gl20.glClearColor(0.9f, 0.9f, 0.9f, 1);
//        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        
//        shape.begin(ShapeRenderer.ShapeType.Filled);
//        dibujarEscenario();
//        player.dibujar(shape);
//        zombie.dibujar(shape);
//        // CAMBIO: QTE fuera del shape.begin/end
//        if(qte.isActivo()) qte.dibujar(shape, batch, player, zombie);
//        
//        shape.end();
//        
//     
//    }

    @Override
    public void render() {
    	int zcantidad = 0;
        float delta = Gdx.graphics.getDeltaTime();
        float camHalfWidth = camera.viewportWidth * camera.zoom / 2f;
        float camHalfHeight = camera.viewportHeight * camera.zoom / 2f;
        float alturaFija = mapHeight / 2f - 50f;
        
        // 1. Actualiza zombis SIEMPRE
        for(Zombie z : zombies) {
            boolean meEstaTocando = player.getHitbox().overlaps(z.getHitbox()) && qte.isActivo();
            z.caminarHacia(player, delta, meEstaTocando, mapWidth - GROSOR_PARED);
        }
        
        // 2. Lógica player + QTE
        if(!qte.isActivo()) {
            player.mover(delta, 400 * delta, GROSOR_PARED, (int)(mapWidth - GROSOR_PARED - player.getHitbox().width),ALTO_SUELO);
            player.aplicarGravedad(delta, GRAVEDAD, ALTO_SUELO);
            
            Array<Zombie> tocando = new Array<>();
            for(Zombie z : zombies) if(player.getHitbox().overlaps(z.getHitbox())) tocando.add(z);
            
            if(!tocando.isEmpty()) {
                qte.iniciar();
                qte.setEstilo(true);
                qte.setDecaeProgreso(false);
                int cantidad = tocando.size;
                float playerSpeed = Math.max(0.09f - 0.05f * (cantidad - 1), 0.04f);
                float zombieSpeed = Math.min(0.3f + 0.09f * (cantidad - 1), 0.55f);
                qte.setPlayerQteChargeSpeed(playerSpeed);
                qte.setZombieQteChargeSpeed(zombieSpeed);
//                System.out.println("START: Player: "+playerSpeed+" ZombieSpeed: "+zombieSpeed);
                
                
                qte.setDecaySpeed(0.06f);
            }
        } else {
            qte.actualizar(delta);
            
            // CAMBIO: actualiza dificultad dinámicamente si entra/sale un zombi
            Array<Zombie> tocando = new Array<>();
          
            for(Zombie z : zombies) if(player.getHitbox().overlaps(z.getHitbox())) tocando.add(z);
            zcantidad =  tocando.size;
            
          
            int cantidad = tocando.size;
            float playerSpeed = Math.max(0.09f - 0.05f * (cantidad - 1), 0.04f);
            float zombieSpeed = Math.min(0.3f + 0.09f * (cantidad - 1), 0.55f);
            qte.setPlayerQteChargeSpeed(playerSpeed);
            qte.setZombieQteChargeSpeed(zombieSpeed);
//            System.out.println("PROGRESS: Player: "+playerSpeed+" ZombieSpeed: "+zombieSpeed);
            
            if(qte.jugadorGano()) {
                for(Zombie z : tocando) z.getHitbox().x = player.getHitbox().x - 60;
                qte.resetear();
            }
            if(qte.zombieGano()) {
                System.out.println("GAME OVER");
                qte.resetear();
            }
        }
        
        // 3. Cámara
        if(qte.isActivo()) {
            float xSuma = player.getHitbox().x + player.getHitbox().width/2;
            int contador = 1;
            for(Zombie z : zombies) if(player.getHitbox().overlaps(z.getHitbox())) {
                xSuma += z.getHitbox().x + z.getHitbox().width/2;
                contador++;
            }
            float xMedio = xSuma / contador;
            camera.position.x += (xMedio - camera.position.x) * delta * 8f;
            camera.position.y += (alturaFija - camera.position.y) * delta * 8f;
            camera.zoom += (zoomQTE - camera.zoom) * delta * 8f;
        } else {
            camera.position.x += (player.getHitbox().x + player.getHitbox().width/2 - camera.position.x) * delta * 5f;
            camera.position.y += (alturaFija - camera.position.y) * delta * 5f;
            camera.zoom += (zoomNormal - camera.zoom) * delta * 5f;
        }
        
        camera.position.x = MathUtils.clamp(camera.position.x, camHalfWidth, mapWidth - camHalfWidth);
        camera.position.y = MathUtils.clamp(camera.position.y, camHalfHeight, mapHeight - camHalfHeight);
        camera.update();
        shape.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);
        
        // 4. Dibujado
        Gdx.gl20.glClearColor(0.9f, 0.9f, 0.9f, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        shape.begin(ShapeRenderer.ShapeType.Filled);
        dibujarEscenario();
        player.dibujar(shape);
        for(Zombie z : zombies) z.dibujar(shape);
        if(qte.isActivo()) qte.dibujar(batch, player, zombies.first(), camera,zcantidad); // <- DIBUJA FUERA
        shape.end(); // <- CIERRA AQUÍ
        
       
    }
    
    
    
    private void dibujarEscenario() {
        shape.setColor(0.3f, 0.3f, 0.3f, 1);
        shape.rect(0, 0, mapWidth, ALTO_SUELO);
        shape.rect(0, 0, GROSOR_PARED, mapHeight);
        shape.rect(mapWidth - GROSOR_PARED, 0, GROSOR_PARED, mapHeight); // <- usa mapWidth
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