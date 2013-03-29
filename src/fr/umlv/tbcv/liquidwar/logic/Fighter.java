package fr.umlv.tbcv.liquidwar.logic;

public class Fighter {
	private Position position ;
	short health ;
	short act_counter ;
	char team ;
	char last_dir ;
	
	private Position nextPosition ;
	
	
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	
	public Fighter () {
		position = new Position() ;
		health = 100 ;
		act_counter = 0 ;
		team = 'a' ;
		last_dir = 'n' ;
		
		nextPosition = new Position() ;
	}
	
	public void move () {
		int i , j ;
		
		
		return ;
	}
	
	public void attack () {
		
		return ;
	}

}
