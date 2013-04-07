package fr.umlv.tbcv.liquidwar.logic;

public class Fighter {
	private Coordinates position ;
	int index ;
	short health ;
	short act_counter ;
	char team ;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}

	char last_dir ;
	
	private Coordinates nextPosition ;
	
	public Coordinates getPosition() {
		return position;
	}
	public void setPosition(Coordinates position) {
		this.position = position;
	}
	
	
	public short getHealth() {
		return health;
	}
	public void setHealth(short health) {
		this.health = health;
	}
	public void removeHealth(int damage) {
		health -= damage ;
	}
	
	
	
	public Fighter (int index) {
		position = new Coordinates() ;
		health = 100 ;
		act_counter = 0 ;
		team = 'a' ;
		last_dir = 'n' ;
		this.index = index ;
		
		nextPosition = new Coordinates() ;
	}
	
	public void move () {
		nextPosition.setX(42) ;
		nextPosition.setY(56) ;
		
		return ;
	}
	
	public void attack (Fighter ennemy) {
		ennemy.removeHealth(5) ;
		return ;
	}

}
