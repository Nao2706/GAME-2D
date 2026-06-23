package me.nao.game.enums;

public enum Items {

	
    PISTOLA("Pistola", 12, 0.2f, 30, TipoArma.DISTANCIA),
    ESCOPETA("Escopeta", 6, 0.8f, 80, TipoArma.DISTANCIA),
    CUCHILLO("Cuchillo", -1, 0.3f, 25, TipoArma.MELEE),
    ESPADA("Espada", -1, 0.5f, 40, TipoArma.MELEE),
    LANZA("Lanza", -1, 0.7f, 55, TipoArma.MELEE);
    
    public final String nombre;
    public final int balasMax;
    public final float cadencia;
    public final int daño;
    public final TipoArma tipo;
    
    Items(String nombre, int balasMax, float cadencia, int daño, TipoArma tipo) {
        this.nombre = nombre;
        this.balasMax = balasMax;
        this.cadencia = cadencia;
        this.daño = daño;
        this.tipo = tipo;
    }
    
    public enum TipoArma { DISTANCIA, MELEE }
	
	
}
