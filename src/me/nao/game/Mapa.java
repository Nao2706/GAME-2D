package me.nao.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Mapa {

	
    private TiledMap tiledMap;
    private ParallaxBackground parallaxBackground;

    public Mapa(String rutaMapa) {
        tiledMap = new TmxMapLoader().load(rutaMapa);
        parallaxBackground = new ParallaxBackground();
    }

    public void render(SpriteBatch spriteBatch, OrthographicCamera camera) {
        parallaxBackground.render(spriteBatch);
        OrthogonalTiledMapRenderer orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        orthogonalTiledMapRenderer.setView(camera);
        orthogonalTiledMapRenderer.render();
    }

    public void update(float deltaTime) {
        parallaxBackground.update(deltaTime);
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }
    
  //HACE REFEENCIA A LA CAPA DE COLISIONES DE LOS TILED EL ARCHIVO PUEDE SER RENOMBRADO
    public boolean isCollision(Rectangle rectangle) {
        for (MapLayer layer : tiledMap.getLayers()) {
            if (layer.getName().startsWith("colisiones") && layer instanceof TiledMapTileLayer) {
                TiledMapTileLayer tileLayer = (TiledMapTileLayer) layer;
                for (int x = (int) rectangle.x; x < rectangle.x + rectangle.width; x++) {
                    for (int y = (int) rectangle.y; y < rectangle.y + rectangle.height; y++) {
                        TiledMapTileLayer.Cell cell = tileLayer.getCell(x / tileLayer.getTileWidth(), y / tileLayer.getTileHeight());
                        if (cell != null) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
	
}
