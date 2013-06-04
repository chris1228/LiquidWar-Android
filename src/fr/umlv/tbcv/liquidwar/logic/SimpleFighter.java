package fr.umlv.tbcv.liquidwar.logic;

import fr.umlv.tbcv.liquidwar.input.GameInput;

//import fr.umlv.tbcv.liquidwar.input.GameInput;

public class SimpleFighter extends Fighter {
	
	int index ;
	short act_counter ;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}




	public SimpleFighter (int index, int team) {
		position = new Coordinates() ;
		health = 100 ;
		act_counter = 0 ;
		this.team = team ;
		this.index = index ;
	}
	
	@Override
	public void move (LiquidMap lwmap) {
		Coordinates cursor = GameInput.getPlayerCoordinate(team) ;
		Coordinates finalPosition = new Coordinates( position.getX() , position.getY() ) ;
		Coordinates tempPosition = new Coordinates( position.getX() , position.getY() ) ;
		
//		Log.e("Cursor", cursor.toString() ) ;
		
		
		for (int i = position.getX() - 1 ; i <= position.getX() + 1 ; i ++ ) {
			tempPosition.setX(i) ;
			for ( int j = position.getY() - 1 ; j <= position.getY() + 1  ; j++ ) {
				tempPosition.setY( j ) ;
				if ( lwmap.checkPosition(tempPosition) == CellState.EMPTY &&
					 Coordinates.getSquareDistance( tempPosition, cursor ) <
					 Coordinates.getSquareDistance( finalPosition, cursor) ) {
					finalPosition.copyCoordinates( tempPosition );
				} 
			}
		}
		
		lwmap.putSoldier(finalPosition, this) ;
		position.setX( finalPosition.getX() ) ;
		position.setY( finalPosition.getY() ) ;
		
	}
	
	@Override
	public void attack (Fighter ennemy) {
		ennemy.removeHealth(5) ;
	}

}
