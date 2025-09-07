package me.nao.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ParallaxBackground {

	
	
	
	    private Texture backgroundLayer1;
	    private Texture backgroundLayer2;
	    private Texture backgroundLayer3;
	    private float layer1Speed = 0.1f;
	    private float layer2Speed = 0.05f;
	    private float layer3Speed = 0.01f;
	    private float layer1X = 0;
	    private float layer2X = 0;
	    private float layer3X = 0;

	    public ParallaxBackground() {
	        backgroundLayer1 = new Texture("layer1.png");
	        backgroundLayer2 = new Texture("layer2.png");
	        backgroundLayer3 = new Texture("layer3.png");
	    }

	    public void update(float deltaTime) {
	        layer1X += layer1Speed * deltaTime;
	        layer2X += layer2Speed * deltaTime;
	        layer3X += layer3Speed * deltaTime;

	        if (layer1X > backgroundLayer1.getWidth()) {
	            layer1X -= backgroundLayer1.getWidth();
	        }
	        if (layer2X > backgroundLayer2.getWidth()) {
	            layer2X -= backgroundLayer2.getWidth();
	        }
	        if (layer3X > backgroundLayer3.getWidth()) {
	            layer3X -= backgroundLayer3.getWidth();
	        }
	    }

	    public void render(SpriteBatch spriteBatch) {
	        spriteBatch.draw(backgroundLayer1, layer1X, 0);
	        spriteBatch.draw(backgroundLayer1, layer1X + backgroundLayer1.getWidth(), 0);
	        spriteBatch.draw(backgroundLayer2, layer2X, 0);
	        spriteBatch.draw(backgroundLayer2, layer2X + backgroundLayer2.getWidth(), 0);
	        spriteBatch.draw(backgroundLayer3, layer3X, 0);
	        spriteBatch.draw(backgroundLayer3, layer3X + backgroundLayer3.getWidth(), 0);
	    }
	
	
	
	
	
	
	
	
	
	
	
}
