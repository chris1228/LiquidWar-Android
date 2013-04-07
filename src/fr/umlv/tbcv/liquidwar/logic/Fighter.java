package fr.umlv.tbcv.liquidwar.logic;

public class Fighter {
	private Coordinates position ;
	short health ;
	short act_counter ;
	char team ;
	char last_dir ;
	
	private Coordinates nextPosition ;
	
	
	public Coordinates getPosition() {
		return position;
	}
	public void setPosition(Coordinates position) {
		this.position = position;
	}
	
	public Fighter () {
		position = new Coordinates() ;
		health = 100 ;
		act_counter = 0 ;
		team = 'a' ;
		last_dir = 'n' ;
		
		nextPosition = new Coordinates() ;
	}
	
	public void move () {
		nextPosition.setX(42) ;
		nextPosition.setY(56) ;
		
		return ;
	}
	
	public void attack () {
		
		return ;
	}

}
