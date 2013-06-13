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

import java.util.ArrayList;
import java.util.List;

public class LiquidSimpleMap implements LiquidMap {
	private int w,h ;
	private int[][] map;
	public static final int EMPTY = 0 ;
	public static final int OBSTACLE = -1 ; // Map contains either 0(empty), -1(obstacle), or n > 0 : n being the fighter index in the Fighters[] array
	
	public LiquidSimpleMap (int w, int h) {
		this.w = w ;
		this.h = h ;
		
		// Every element initialized and equals 0 (EMPTY) at the creation
		map = new int[w][h] ;
	}

    public void loadMap() {
        // HARDCODED FOR NOW
        for (int i = 5 ; i <= 20 ; i++ ) {
            map[i][15] = OBSTACLE ;
        }
        for (int j = 15 ; j <= 40 ; j++) {
            map[15][j] = OBSTACLE ;
        }
    }
	
	private void putElement ( Coordinates coord, int element) {
        if( coord.getX() <  0 || coord.getX() >= w || coord.getY() < 0 || coord.getY() >= h ) {
            throw new RuntimeException(); // Out of bounds : Throw exception
        }
		map[ coord.getX() ][ coord.getY() ] = element ;
	}

    private int getElement ( Coordinates coord ) {
        if( coord.getX() <  0 || coord.getX() >= w || coord.getY() < 0 || coord.getY() >= h ) {
            return -1 ; // Out of bounds : We say it's s an obstacle
        }
        return map[ coord.getX() ][ coord.getY() ] ;
    }

    private Fighter getFighterWithIndex(Fighter[] fighters, int index) {
        if( index <= 0 || index > fighters.length) {
            throw new RuntimeException() ;
        }
        return fighters[index-1] ;
    }

    public Fighter getFighter(Fighter[] fighters, Coordinates c) {
        return getFighterWithIndex( fighters, getElement(c)) ;
    }

    @Override
	public void clear ( Coordinates coord ) {
		putElement( coord,  EMPTY) ;
	}
	
	@Override
	public void putObstacle (Coordinates coord) {
		putElement( coord,  OBSTACLE) ;
	}
	
	@Override
	public void putSoldier ( Coordinates coord,  Fighter f) {
		if (! (f instanceof SimpleFighter) )  {
			throw new RuntimeException() ;
		}
		
		SimpleFighter fighter = (SimpleFighter) f ;
		// Old fighter position gets cleared from the board
		clear( fighter.getPosition() ) ;
		// New position (in coord) is now occupied by the fighter
		putElement( coord, fighter.getIndex() ) ;
	}

    @Override
    public int getWidth() {
        return w ;
    }

    @Override
    public int getHeight() {
        return h ;
    }

    @Override
    public int countObstacles() {
        int obstacles = 0 ;
        for(int i = 0 ; i < w ; i++) {
            for(int j = 0 ; j < h ; j++) {
                if (map[i][j] == OBSTACLE) {
                    obstacles++ ;
                }
            }
        }
        return obstacles ;
    }


	public CellState checkPosition ( Coordinates pos ) {
		switch(getElement(pos)) {
            case -1 : return CellState.OBSTACLE ;
            case 0 : return CellState.EMPTY ;
            default : return CellState.FIGHTER ;
        }
	}

    @Override
    public boolean isEmpty (Coordinates pos) {
        if (checkPosition(pos) == CellState.EMPTY ) {
            return true ;
        }
        return false ;
    }

    @Override
    public boolean hasFighter(Coordinates pos) {
        if (checkPosition(pos) == CellState.FIGHTER ) {
            return true ;
        }
        return false ;
    }

    @Override
    public boolean hasObstacle (Coordinates pos) {
        if (checkPosition(pos) == CellState.OBSTACLE) {
            return true ;
        }
        return false ;
    }

    @Override
    public boolean hasObstacle (int x, int y) {
        if (checkPosition(new Coordinates(x,y)) == CellState.OBSTACLE) {
            return true ;
        }
        return false ;
    }

    @Override
    public List<Coordinates> getNeighbors(Coordinates cell) {
        List<Coordinates> neighborList = new ArrayList<Coordinates>() ;
        if(cell != null) {
            for(int i = cell.getX()-1 ; i <= cell.getX()+1 ; i++){
                if(i < 0 || i >= w) { continue ; }
                for(int j = cell.getY()-1 ; j <= cell.getY()+1 ; j++) {
                    if(j < 0 || j >= h) { continue ; }
                    if(map[i][j] != OBSTACLE && (i != cell.getX() || j != cell.getY()) ) {
                        neighborList.add(new Coordinates(i,j)) ;
                    }
                }
            }
        }
        return neighborList ;
    }


}
