package fr.umlv.tbcv.liquidwar.logic;

public class Player {
	
	private Coordinates position ;

	public Coordinates getPosition() {
		return position;
	}

	public void setPosition(Coordinates position) {
		this.position = position;
	}
	
	public Player () {
		position = new Coordinates() ;
	}
	
	
	
}
