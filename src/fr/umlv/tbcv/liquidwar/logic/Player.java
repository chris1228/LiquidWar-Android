package fr.umlv.tbcv.liquidwar.logic;

public class Player {
	
	private Position position ;

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	
	public Player () {
		position = new Position() ;
	}
	
	
	
}
