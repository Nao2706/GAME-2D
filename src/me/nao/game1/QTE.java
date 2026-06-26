package me.nao.game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;

//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class QTE {
    private boolean activo = false;
    private float timer = 0;
    private float progresoPlayer = 0;
    private float anchoBarra = 120;
    private float altoBarra = 12;

    private boolean usarBarrasApiladas = false;
    @SuppressWarnings("unused")
	private boolean decaeProgreso = true;
	private float playerQteChargeSpeed = 0.20f;
	private float zombieQteChargeSpeed = 1f;
    @SuppressWarnings("unused")
	private float decaySpeed = 0.08f;
    @SuppressWarnings("unused")
	private float tiempoLimite = 4f;
    
    // Offset para mover la letra
//    private float offsetX = 0f;  // + derecha, - izquierda  
//    private float offsetY = 15f; // + arriba, - abajo
    
    private int tecla1; // tecla random 1
    private int tecla2; // tecla random 2
    private int teclaActual;
    private boolean alternar = false;
    
    private Array<Integer> secuencia = new Array<>();
   
    private BitmapFont font;
    private ShapeRenderer shapeQTE; // NUEVO
    
    // Pool de teclas disponibles
    private final int[] POOL_TECLAS = {
        Input.Keys.A, Input.Keys.D
//        , Input.Keys.W, Input.Keys.S
    };
    
    public QTE() {
        font = new BitmapFont(); // crear 1 vez
        font.getData().setScale(1.5f); // tamaño decente
        shapeQTE = new ShapeRenderer(); // NUEVO
    }
    
    public void setPlayerQteChargeSpeed(float speed) { playerQteChargeSpeed = speed; }
    public void setZombieQteChargeSpeed(float speed) { zombieQteChargeSpeed = speed; }
    public void setDecaySpeed(float speed) { decaySpeed = speed; }
    public void setDecaeProgreso(boolean decae) { decaeProgreso = decae; }
    public void setEstilo(boolean apiladas) { usarBarrasApiladas = apiladas; }
    public void setTiempoLimite(float segundos) { 
        tiempoLimite = segundos;
        zombieQteChargeSpeed = 1f / segundos;
    }
    
    
    
    public void dibujar(SpriteBatch batch, Player p, Zombie z, OrthographicCamera camera,int cantidad) {
        if(!activo) return;
 
        shapeQTE.setProjectionMatrix(camera.combined); // NUEVO: usa la cámara
        
        if(usarBarrasApiladas) dibujarApiladas(batch, p, z,cantidad);
        else dibujarSobrepuestas(batch, p, z,cantidad);
    }
    
    public void dispose() {
        shapeQTE.dispose(); // <- NO OLVIDES ESTO
        font.dispose();
    }
//    private void dibujarApiladas(ShapeRenderer shape, SpriteBatch batch, Player p, Zombie z) {
//        // Centro en coordenadas de MUNDO
//        float xCentro = (p.getHitbox().x + p.getHitbox().width/2 + z.getHitbox().x + z.getHitbox().width/2) / 2f;
//        float yBase = Math.max(p.getHitbox().y + p.getHitbox().height, z.getHitbox().y + z.getHitbox().height) + 65 - 30f;
//        
//        
//        // dibuja barras con barraX...
//        float espacio = 3;
//        float barraX = xCentro - anchoBarra/2;
//        
//        // Fondo negro
//        shape.setColor(Color.DARK_GRAY);
//        shape.rect(barraX - 2, yBase - 2, anchoBarra + 4, altoBarra * 2 + espacio + 4);
//        
//        // Barra player arriba
//        shape.setColor(Color.CYAN);
//        int pixelsPlayer = (int)(anchoBarra * progresoPlayer + 0.5f);
//        shape.rect(barraX, yBase + altoBarra + espacio, pixelsPlayer, altoBarra);
//        
//        // Barra zombie abajo  
//        shape.setColor(Color.RED);
//        int pixelsZombie = (int)(anchoBarra * timer + 0.5f);
//        shape.rect(barraX, yBase, pixelsZombie, altoBarra);
//        
//        
//        batch.begin();
//        String letra = Input.Keys.toString(teclaActual);
//        font.setColor(Color.BLACK);
//        GlyphLayout layout = new GlyphLayout(font, letra);
//        
//        float offsetX = 0f; // ya no necesitas -125
//        float offsetY = 15f;
//        
//        float xLetra = barraX + anchoBarra/2 - layout.width/2 + offsetX;
//        float yLetra = yBase + altoBarra * 2 + espacio + layout.height + offsetY;
//        
//        font.draw(batch, letra, xLetra, yLetra);
//        batch.end();
//    }
    
    private void dibujarSobrepuestas(SpriteBatch batch, Player p, Zombie z,int cantidad) {
        // 1. Calcula centro
//        float xCentro = (p.getHitbox().x + p.getHitbox().width/2 + z.getHitbox().x + z.getHitbox().width/2) / 2f;
//        float yBase = Math.max(p.getHitbox().y + p.getHitbox().height, z.getHitbox().y + z.getHitbox().height) + 35f;
    	float xCentro = p.getHitbox().x + p.getHitbox().width/2;
    	float yBase = p.getHitbox().y + p.getHitbox().height + 35f;
    	   
        float barraX = xCentro - anchoBarra/2;
        
        // 2. Dibuja barras con ShapeRenderer propio
        shapeQTE.begin(ShapeRenderer.ShapeType.Filled);
        
        // Fondo
        shapeQTE.setColor(Color.DARK_GRAY);
        shapeQTE.rect(barraX - 2, yBase - 2, anchoBarra + 4, altoBarra + 4);
        
        // Barra player = cyan
      
        shapeQTE.setColor(cantidad == 1 ? Color.CYAN : Color.YELLOW);
        int pixelsPlayer = (int)(anchoBarra * progresoPlayer + 0.5f);
        shapeQTE.rect(barraX, yBase, pixelsPlayer, altoBarra);
        
        // Barra zombie = roja, sobrepuesta
        shapeQTE.setColor(cantidad == 1 ? Color.YELLOW : Color.RED);
        int pixelsZombie = (int)(anchoBarra * timer + 0.5f);
        shapeQTE.rect(barraX, yBase, pixelsZombie, altoBarra);
        
        shapeQTE.end(); // <- CIERRA AQUÍ
        
        // 3. Dibuja letra con batch
//        batch.begin();
        String letra = Input.Keys.toString(teclaActual);
        font.setColor(Color.BLACK);
        GlyphLayout layout = new GlyphLayout(font, letra);
        
        float offsetX = -125f; // tu valor que funciona
        float offsetY = 15f;
        
        float xLetra = barraX + anchoBarra/2 - layout.width/2 + offsetX;
        float yLetra = yBase + altoBarra + layout.height + offsetY;
        
        font.draw(batch, letra, xLetra, yLetra);
//        batch.end();
    }
    
    private void dibujarApiladas(SpriteBatch batch, Player p, Zombie z,int cantidad) {
//        float xCentro = (p.getHitbox().x + p.getHitbox().width/2 + z.getHitbox().x + z.getHitbox().width/2) / 2f;
//        float yBase = Math.max(p.getHitbox().y + p.getHitbox().height, z.getHitbox().y + z.getHitbox().height) + 35f;
    	float xCentro = p.getHitbox().x + p.getHitbox().width/2;
    	float yBase = p.getHitbox().y + p.getHitbox().height + 35f;
        float barraX = xCentro - anchoBarra/2;
        float espacio = 3;
        
        shapeQTE.begin(ShapeRenderer.ShapeType.Filled);
        
        // Fondo
        shapeQTE.setColor(Color.DARK_GRAY);
        shapeQTE.rect(barraX - 2, yBase - 2, anchoBarra + 4, altoBarra * 2 + espacio + 4);
        
        // Player arriba
        shapeQTE.setColor(cantidad == 1 ? Color.CYAN : Color.YELLOW);
        int pixelsPlayer = (int)(anchoBarra * progresoPlayer + 0.5f);
        shapeQTE.rect(barraX, yBase + altoBarra + espacio, pixelsPlayer, altoBarra);
        
        // Zombie abajo
        shapeQTE.setColor(cantidad == 1 ? Color.YELLOW : Color.RED);
        int pixelsZombie = (int)(anchoBarra * timer + 0.5f);
        shapeQTE.rect(barraX, yBase, pixelsZombie, altoBarra);
        
        shapeQTE.end();
        
        // Letra
//        batch.begin();
        String letra = Input.Keys.toString(teclaActual);
        font.setColor(Color.BLACK);
        GlyphLayout layout = new GlyphLayout(font, letra);
        float xLetra = barraX + anchoBarra/2 - layout.width/2;
        float yLetra = yBase + altoBarra * 2 + espacio + layout.height + 15f;
        font.draw(batch, letra, xLetra, yLetra);
//        batch.end();
    }
    
//    public void actualizar(float delta) {
//        if(!activo) return;
//        
//        timer += delta * 0.25f; // zombie avanza
//        
//        if(Gdx.input.isKeyJustPressed(teclaActual)) {
//            progresoPlayer += 0.15f; // sube barra
//            
//            // 2. Cambia a la otra tecla del combo
//            alternar = !alternar;
//            teclaActual = alternar ? tecla2 : tecla1;
//        }
//        
//        // Penalización si presiona tecla incorrecta
//        if(Gdx.input.isKeyJustPressed(tecla1) && teclaActual != tecla1 ||
//           Gdx.input.isKeyJustPressed(tecla2) && teclaActual != tecla2) {
//            progresoPlayer -= 0.08f;
//        }
//        
//        progresoPlayer = MathUtils.clamp(progresoPlayer, 0f, 1f);
//        
//        if(timer >= 1f && progresoPlayer < 1f) activo = false;
//        if(progresoPlayer >= 1f) activo = false;
//    }
    
    public void actualizar(float delta) {
        if(!activo) return;
        
        timer += delta * zombieQteChargeSpeed; // <- usa la variable
        
        if(Gdx.input.isKeyJustPressed(teclaActual)) {
            progresoPlayer += playerQteChargeSpeed; // <- usa la variable
            
            alternar = !alternar;
            teclaActual = alternar ? tecla2 : tecla1;
        }
        
        // Penalización si presiona tecla incorrecta
        if(Gdx.input.isKeyJustPressed(tecla1) && teclaActual != tecla1 ||
           Gdx.input.isKeyJustPressed(tecla2) && teclaActual != tecla2) {
            progresoPlayer -= playerQteChargeSpeed * 0.5f; // penalidad = 50% de lo que sube
        }
        
        progresoPlayer = MathUtils.clamp(progresoPlayer, 0f, 1f);
        
        if(timer >= 1f && progresoPlayer < 1f) activo = false;
        if(progresoPlayer >= 1f) activo = false;
    }
    
    public void iniciar() { 
        activo = true; 
        timer = 0; 
        progresoPlayer = 0; 
//        generarSecuencia(8);
        
        
        IntArray pool = new IntArray(POOL_TECLAS);
        pool.shuffle(); // mezcla
        tecla1 = pool.get(0);
        tecla2 = pool.get(1);
        
        teclaActual = tecla1; // empieza con la primera
    }
    
    public void resetear() { 
        activo = false; 
        timer = 0; 
        progresoPlayer = 0; 
        secuencia.clear();
    }
    
    public boolean zombieGano() { return timer >= 1f && progresoPlayer < 1f; }
    public boolean jugadorGano() { return progresoPlayer >= 1f; }
    public boolean isActivo() { return activo; }
    
}   
//    private void dibujarSobrepuestas(ShapeRenderer shape, SpriteBatch batch, Player p, Zombie z) {
//        float xMedio = (p.getHitbox().x + z.getHitbox().x) / 2 + 30;
//        float yMedio = Math.max(p.getHitbox().y, z.getHitbox().y) + 65;
//        
//        shape.setColor(Color.DARK_GRAY);
//        shape.rect(xMedio - anchoBarra/2 - 2, yMedio - 2, anchoBarra + 4, altoBarra + 4);
//        
//        shape.setColor(Color.RED);
//        int pixelsZombie = (int)(anchoBarra * timer + 0.5f);
//        shape.rect(xMedio - anchoBarra/2, yMedio, pixelsZombie, altoBarra);
//        
//        shape.setColor(Color.CYAN);
//        int pixelsPlayer = (int)(anchoBarra * progresoPlayer + 0.5f);
//        shape.rect(xMedio - anchoBarra/2, yMedio, pixelsPlayer, altoBarra);
//        
//        // LETRA GUIA CENTRADA
//        shape.end();
//        batch.begin();
//        String letra = Input.Keys.toString(teclaActual);
//        font.setColor(Color.BLACK);
//        GlyphLayout layout = new GlyphLayout(font, letra);
//        
//        // Centrado: xMedio - anchoTexto/2. Luego sumas offsetX/offsetY
//        float x = xMedio - layout.width/2 + offsetX;
//        float y = yMedio + altoBarra + layout.height + offsetY;
//        
//        font.draw(batch, letra, x-100, y); // 3 args: batch, texto, x, y
//        System.out.println("2X: "+x+" Y: "+y);
//        batch.end();
////        shape.begin(); NO COLOCAR ESTO AQUI CUANDO YA ESTA EN EL MAIN
//    }
    
//    private void dibujarApiladas(ShapeRenderer shape, SpriteBatch batch, Player p, Zombie z) {
//        float xMedio = (p.getHitbox().x + z.getHitbox().x) / 2 + 30;
//        float yMedio = Math.max(p.getHitbox().y, z.getHitbox().y) + 65;
//        float espacio = 3;
//        
//        shape.setColor(Color.DARK_GRAY);
//        shape.rect(xMedio - anchoBarra/2 - 2, yMedio - 2, anchoBarra + 4, altoBarra * 2 + espacio + 4);
//        
//        shape.setColor(Color.CYAN);
//        int pixelsPlayer = (int)(anchoBarra * progresoPlayer + 0.5f);
//        shape.rect(xMedio - anchoBarra/2, yMedio + altoBarra + espacio, pixelsPlayer, altoBarra);
//        
//        shape.setColor(Color.RED);
//        int pixelsZombie = (int)(anchoBarra * timer + 0.5f);
//        shape.rect(xMedio - anchoBarra/2, yMedio, pixelsZombie, altoBarra);
//        
//        // LETRA GUIA CENTRADA - arriba de ambas barras
//        shape.end();
//        batch.begin();
//        String letra = Input.Keys.toString(teclaActual);
//        font.setColor(Color.BLACK);
//        GlyphLayout layout = new GlyphLayout(font, letra);
//        
//        float x = xMedio - layout.width/2 + offsetX;
//        float y = yMedio + altoBarra * 2 + espacio + layout.height + offsetY; // arriba de todo
//        
//        font.draw(batch, letra, x-125, y);
//        System.out.println("1X: "+x+" Y: "+y);
//        batch.end();
////        shape.begin();
//    }
    
    
    
//    1X: 551.85736 Y: 173.5
//    GAME OVER
//    1X: 546.9343 Y: 173.5
    
  
    
//    public void actualizar(float delta) {
//        if(!activo) return;
//        
//        timer += delta * zombieQteChargeSpeed;
//        if(timer > 1f) timer = 1f;
//        
//        if(Gdx.input.isKeyJustPressed(teclaActual)) {
//            progresoPlayer += playerQteChargeSpeed;
//            secuencia.removeIndex(0);
//            
//            if(secuencia.size > 0) {
//                teclaActual = secuencia.first();
//            } else {
//                generarSecuencia(8);
//            }
//        }
//        
//        for(int key : TECLAS_VALIDAS) {
//            if(key != teclaActual && Gdx.input.isKeyJustPressed(key)) {
//                progresoPlayer -= 0.08f;
//            }
//        }
//        
//        if(decaeProgreso) progresoPlayer -= delta * decaySpeed;
//        progresoPlayer = MathUtils.clamp(progresoPlayer, 0f, 1f);
//        if(progresoPlayer >= 0.995f) progresoPlayer = 1f;
//        
//        if(timer >= 1f && progresoPlayer < 1f) activo = false;
//        if(progresoPlayer >= 1f) activo = false;
//    }
    
//    private void generarSecuencia(int cantidad) {
//        secuencia.clear();
//        for(int i = 0; i < cantidad; i++) {
//            int randomKey = TECLAS_VALIDAS[MathUtils.random(TECLAS_VALIDAS.length - 1)];
//            secuencia.add(randomKey);
//        }
//        teclaActual = secuencia.first();
//    }
    
    
//    private void generarSecuencia(int cantidadPares) {
//        secuencia.clear();
//        for(int i = 0; i < cantidadPares; i++) {
//            // Elige un par random
//            int par = MathUtils.random(1); // 0 o 1
//            
//            if(par == 0) {
//                // Par horizontal A-D
//                if(MathUtils.randomBoolean()) {
//                    secuencia.add(Input.Keys.A);
//                    secuencia.add(Input.Keys.D);
//                } else {
//                    secuencia.add(Input.Keys.D);
//                    secuencia.add(Input.Keys.A);
//                }
//            } else {
//                // Par vertical W-S  
//                if(MathUtils.randomBoolean()) {
//                    secuencia.add(Input.Keys.W);
//                    secuencia.add(Input.Keys.S);
//                } else {
//                    secuencia.add(Input.Keys.S);
//                    secuencia.add(Input.Keys.W);
//                }
//            }
//        }
//        teclaActual = secuencia.first();
//    }
    
  

