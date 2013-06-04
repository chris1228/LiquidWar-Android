package fr.umlv.tbcv.liquidwar.logic;

public class Player {
	
	private Coordinates position ;

	public Coordinates getPosition() {
		return position;
	}

	public void setPosition(Coordinates newPosition) {
		this.position.copyCoordinates(newPosition);
	}
	
	public Player () {
		position = new Coordinates() ;
	}

    public Player(int x, int y) {
        position = new Coordinates(x,y);
    }
	
	
}
