package me.nao.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {

	
	
	
    public static void main(String[] args) {
    	
       // System.setProperty("java.library.path", System.getProperty("user.dir") + "\\target\\natives");
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "RedCube";
        new LwjglApplication(new Juego(), config);
    }
	
	
	
	
	
}
