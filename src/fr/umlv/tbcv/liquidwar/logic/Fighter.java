package fr.umlv.tbcv.liquidwar.logic;

public abstract class Fighter {
	protected Coordinates position ;
	protected short health ;
    protected int team ;
	
	public abstract void move (LiquidMap lwmap) ;
	
	public abstract void attack (Fighter ennemy) ;
	
	public Coordinates getPosition() {
		return position;
	}
	public void setPosition(Coordinates position) {
		this.position.copyCoordinates(position) ;
	}
	
	public short getHealth() {
		return health;
	}
	public void setHealth(short health) {
		this.health = health;
	}
	
	protected void removeHealth(int damage) {
		health -= damage ;
	}
}
