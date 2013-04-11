package fr.umlv.tbcv.liquidwar.logic;

//import fr.umlv.tbcv.liquidwar.input.GameInput;

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
	
//	private Coordinates nextPosition ;
	
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
		
//		nextPosition = new Coordinates() ;
	}
	
	public void move (LiquidMap lwmap) {
//		Coordinates cursor = GameInput.getPosition() ;
//		Coordinates finalPosition = new Coordinates( position.getX() , position.getY() ) ;
//		Coordinates tempPosition = new Coordinates( position.getX() , position.getY() ) ;
//		
//		for (int i = tempPosition.getX() - 1 ; i <= tempPosition.getX() + 1 ; i ++ ) {
//			tempPosition.setX(i) ;
//			for ( int j = tempPosition.getY() - 1 ; j <= tempPosition.getY() + 1  ; j++ ) {
//				tempPosition.setY( j ) ;
//				if ( lwmap.checkPosition(tempPosition) == 0 &&
//					 Coordinates.getSquareDistance( tempPosition, cursor ) <
//					 Coordinates.getSquareDistance( finalPosition, cursor) ) {
//					finalPosition.copyCoordinates( tempPosition );
//				} 
//			}
//		}
//		
//		lwmap.putSoldier(finalPosition, this) ;
//		setPosition( finalPosition ) ;
	}
	
	public void attack (Fighter ennemy) {
		ennemy.removeHealth(5) ;
	}

}
