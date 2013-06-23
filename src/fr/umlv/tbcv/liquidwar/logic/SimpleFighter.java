/*
 Copyright (C) 2013 Thomas Bardoux, Christophe Venevongsos

 This file is part of Liquid Wars Android

 Liquid Wars Android is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Liquid Wars Android is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.

 */

package fr.umlv.tbcv.liquidwar.logic;

import android.util.Log;

import fr.umlv.tbcv.liquidwar.input.GameInput;

//import fr.umlv.tbcv.liquidwar.input.GameInput;

public class SimpleFighter extends Fighter {
	
	int index ;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}


	public SimpleFighter (int index, int team) {
        super(team) ;
		position = new Coordinates() ;
		health = 100 ;
		this.index = index ;
	}
	
	@Override
	public int move (LiquidMap lwmap, Fighter[] fighters) {
        if(! (lwmap instanceof LiquidSimpleMap) ) {
            throw new RuntimeException() ;
        }
        LiquidSimpleMap simpleMap = (LiquidSimpleMap) lwmap ;
		Coordinates cursor = GameInput.getPlayerCoordinate(team) ;
		Coordinates finalPosition = new Coordinates( position.getX() , position.getY() ) ;
		Coordinates tempPosition = new Coordinates( position.getX() , position.getY() ) ;
        Coordinates idealPosition = new Coordinates( position.getX() , position.getY() ) ;
		
//		Log.e("Cursor", cursor.toString() ) ;
		
		
		for (int i = position.getX() - 1 ; i <= position.getX() + 1 ; i ++ ) {
			tempPosition.setX(i) ;
			for ( int j = position.getY() - 1 ; j <= position.getY() + 1  ; j++ ) {
				tempPosition.setY( j ) ;
				if ( (simpleMap.isEmpty(tempPosition)) &&
					 Coordinates.getSquareDistance( tempPosition, cursor ) <
					 Coordinates.getSquareDistance( finalPosition, cursor) ) {
					finalPosition.copyCoordinates( tempPosition ); // Figure out the next best possible position
				}
                if ( Coordinates.getSquareDistance( tempPosition, cursor ) <
                        Coordinates.getSquareDistance( idealPosition, cursor) ) {
                    idealPosition.copyCoordinates( tempPosition ); // Figure out the next best position regardless of possibility
                }
            }
		}

        // If the fighter hasnt been able to move, there is an obstacle/a soldier
        if(finalPosition.equals(position)) {
            if(simpleMap.hasFighter(idealPosition)) {
                Fighter obstacle = simpleMap.getFighter(fighters,idealPosition) ;
                if (isFriend(obstacle)) {
                    heal(obstacle) ;
                }
                else {
                    attack(obstacle) ;
                }
            }
            return 0 ;
        }
        // Fighter can move freely, its position is updated
        else {
            simpleMap.putSoldier(finalPosition, this) ;
            position.setX( finalPosition.getX() ) ;
            position.setY( finalPosition.getY() ) ;
        }

	    return 1 ;
	}

}
