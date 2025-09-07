package me.nao.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {

//	   // Texturas para las animaciones
//    private TextureRegion[] caminataTexturas = new TextureRegion[] {
//        new TextureRegion(new Texture("images/AnimacionPlayer/caminar_1.png")),
//        new TextureRegion(new Texture("images/AnimacionPlayer/caminar_2.png")),
//        new TextureRegion(new Texture("images/AnimacionPlayer/caminar_3.png")),
//        // Agrega o elimina fotos para la animación
//    };
//
//    private TextureRegion[] correrTexturas = new TextureRegion[] {
//        new TextureRegion(new Texture("images/AnimacionPlayer/correr_1.png")),
//        new TextureRegion(new Texture("images/AnimacionPlayer/correr_2.png")),
//        new TextureRegion(new Texture("images/AnimacionPlayer/correr_3.png")),
//        // Agrega o elimina fotos para la animación
//    };
//
//    private TextureRegion[] saltarTexturas = new TextureRegion[] {
//        new TextureRegion(new Texture("images/AnimacionPlayer/saltar_1.png")),
//        new TextureRegion(new Texture("images/AnimacionPlayer/saltar_2.png")),
//        new TextureRegion(new Texture("images/AnimacionPlayer/saltar_3.png")),
//        // Agrega o elimina fotos para la animación
//    };
//
//    private TextureRegion[] idleTexturas = new TextureRegion[] {
//        new TextureRegion(new Texture("images/AnimacionPlayer/idle_1.png")),
//        new TextureRegion(new Texture("images/AnimacionPlayer/idle_2.png")),
//        new TextureRegion(new Texture("images/AnimacionPlayer/idle_3.png")),
//        new TextureRegion(new Texture("images/AnimacionPlayer/idle_4.png")),
//        // Agrega o elimina fotos para la animación
//    };
    // Animaciones
//    private Animation caminataAnimacion = new Animation(0.1f, caminataTexturas);
//    private Animation correrAnimacion = new Animation(0.1f, correrTexturas);
//    private Animation saltarAnimacion = new Animation(0.1f, saltarTexturas);
//    private Animation idleAnimacion = new Animation(0.1f, idleTexturas);
//	
	
//


  
	private SpriteBatch spriteBatch;
    private Rectangle rect;
    private Sprite sprite;
    private Vector2 velocidad;
    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> jumpAnimation;
    private Animation<TextureRegion> crouchAnimation;
    // Estado actual de la animación
    private Animation<TextureRegion> currentAnimation;
    private float stateTime;
    private boolean isJumping;
    private boolean isCrouching;

    public Player(float x, float y, float width, float height) {
    	spriteBatch = new SpriteBatch();
        rect = new Rectangle(x, y, width, height);
        velocidad = new Vector2();
        stateTime = 0;
        isJumping = false;
        isCrouching = false;

        // Cargar las imágenes de las animaciones
        //Texture idleTexture = new Texture("idle_animation.png");
        //TextureRegion[][] idleRegions = TextureRegion.split(idleTexture, idleTexture.getWidth() / 4, idleTexture.getHeight());
        //TextureRegion[] idleFrames = new TextureRegion[4];
        //for (int i = 0; i < 4; i++) {
        //    idleFrames[i] = idleRegions[0][i];
        // }
        //  idleAnimation = new Animation<TextureRegion>(0.1f, idleFrames);
        TextureRegion[] idleFrames = cargarTexturas("idle_animation.png",null);//se debe pasar la ruta de una carpeta
        idleAnimation = new Animation<TextureRegion>(0.1f, idleFrames);
        currentAnimation = idleAnimation;
        
//        Texture walkTexture = new Texture("walk_animation.png");
//        TextureRegion[][] walkRegions = TextureRegion.split(walkTexture, walkTexture.getWidth() / 4, walkTexture.getHeight());
//        TextureRegion[] walkFrames = new TextureRegion[4];
//        for (int i = 0; i < 4; i++) {
//            walkFrames[i] = walkRegions[0][i];
//        }
        TextureRegion[] walkFrames = cargarTexturas("idle_animation.png",null);//se debe pasar la ruta de una carpeta
        walkAnimation = new Animation<TextureRegion>(0.1f, walkFrames);

//        Texture runTexture = new Texture("run_animation.png");
//        TextureRegion[][] runRegions = TextureRegion.split(runTexture, runTexture.getWidth() / 4, runTexture.getHeight());
//        TextureRegion[] runFrames = new TextureRegion[4];
//        for (int i = 0; i < 4; i++) {
//            runFrames[i] = runRegions[0][i];
//        }
        TextureRegion[] runFrames = cargarTexturas("idle_animation.png",null);//se debe pasar la ruta de una carpeta
        runAnimation = new Animation<TextureRegion>(0.1f, runFrames);

//        Texture jumpTexture = new Texture("jump_animation.png");
//        TextureRegion[][] jumpRegions = TextureRegion.split(jumpTexture, jumpTexture.getWidth() / 2, jumpTexture.getHeight());
//        TextureRegion[] jumpFrames = new TextureRegion[2];
//        for (int i = 0; i < 2; i++) {
//            jumpFrames[i] = jumpRegions[0][i];
//        }
        TextureRegion[] jumpFrames = cargarTexturas("idle_animation.png",null);//se debe pasar la ruta de una carpeta
        jumpAnimation = new Animation<TextureRegion>(0.1f, jumpFrames);

//        Texture crouchTexture = new Texture("crouch_animation.png");
//        TextureRegion[][] crouchRegions = TextureRegion.split(crouchTexture, crouchTexture.getWidth() / 2, crouchTexture.getHeight());
//        TextureRegion[] crouchFrames = new TextureRegion[2];
//        for (int i = 0; i < 2; i++) {
//            crouchFrames[i] = crouchRegions[0][i];
//        }
        TextureRegion[] crouchFrames = cargarTexturas("idle_animation.png",null);//se debe pasar la ruta de una carpeta
        crouchAnimation = new Animation<TextureRegion>(0.1f, crouchFrames);

        sprite = new Sprite(idleAnimation.getKeyFrame(0));
        sprite.setPosition(x, y);
    }
    //SE ENCARGA DE ACTUALIZAR LA LOGICA DEL JUEGO
    public void update(float deltaTime) {
    	
    	velocidad.y -= 9.8f * deltaTime;//GRAVEDAD ESTA EN 9.8
        stateTime += deltaTime;
        // Actualiza la animación actual
        if (isJumping) {
            currentAnimation = jumpAnimation;
        } else if (isCrouching) {
            currentAnimation = crouchAnimation;
        } else if (velocidad.x != 0) {
            if (velocidad.x > 200) {
                currentAnimation = runAnimation;
            } else {
                currentAnimation = walkAnimation;
            }
        } else {
            currentAnimation = idleAnimation;
        }
        rect.x += velocidad.x * deltaTime;
        rect.y += velocidad.y * deltaTime;
    }

    //SE ENCARGA DE RENDERIZAR LA IMAGEN EN PANTALLA
    public void render() {
        TextureRegion currentFrame = (TextureRegion) currentAnimation.getKeyFrame(stateTime, true);
        spriteBatch.begin();
        spriteBatch.draw(currentFrame, rect.x, rect.y);
        spriteBatch.end();
    }

    public void setAnimation(Animation<TextureRegion> animation) {
        currentAnimation = animation;
        stateTime = 0;
    }
    
    public void setVelocidad(float x, float y) {
        velocidad.x = x;
        velocidad.y = y;
    }

    public void jump() {
        isJumping = true;
        stateTime = 0;
    }

    public void crouch() {
        isCrouching = true;
        stateTime = 0;
    }

    public Rectangle getRect() {
        return rect;
    }

    public Sprite getSprite() {
        return sprite;
    }
	
    public Vector2 getVelocidad() {
        return velocidad;
    }
	
    
//    public TextureRegion[] cargarTextura(String ruta, int frames) {
//        Texture textura = new Texture(ruta);
//        TextureRegion[][] regiones = TextureRegion.split(textura, textura.getWidth() / frames, textura.getHeight());
//        TextureRegion[] framesTextura = new TextureRegion[frames];
//        for (int i = 0; i < frames; i++) {
//            framesTextura[i] = regiones[0][i];
//        }
//        return framesTextura;
//    }
//    
    
    public TextureRegion[] cargarTexturas(String rutaCarpeta, ArrayList<String> nombresArchivos) {
        FileHandle carpeta = Gdx.files.internal(rutaCarpeta);
        TextureRegion[] texturas;

        if (nombresArchivos == null || nombresArchivos.isEmpty()) {
            // Leer toda la carpeta
            FileHandle[] archivos = carpeta.list();
            Arrays.sort(archivos, new Comparator<FileHandle>() {
                @Override
                public int compare(FileHandle f1, FileHandle f2) {
                    return f1.name().compareTo(f2.name());
                }
            });
            texturas = new TextureRegion[archivos.length];
            for (int i = 0; i < archivos.length; i++) {
                Texture textura = new Texture(archivos[i]);
                texturas[i] = new TextureRegion(textura);
            }
        } else {
            // Leer archivos específicos
            texturas = new TextureRegion[nombresArchivos.size()];
            for (int i = 0; i < nombresArchivos.size(); i++) {
                FileHandle archivo = carpeta.child(nombresArchivos.get(i));
                if (archivo.exists()) {
                    Texture textura = new Texture(archivo);
                    texturas[i] = new TextureRegion(textura);
                } else {
                    // Manejar el caso en que el archivo no existe
                    System.out.println("Archivo no encontrado: " + nombresArchivos.get(i));
                }
            }
        }

        return texturas;
    }
    
}
