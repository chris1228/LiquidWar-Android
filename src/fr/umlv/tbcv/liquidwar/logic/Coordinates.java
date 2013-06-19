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

/**
 * Coordinates in a 2 dimensional Cartesian space
 */
public class Coordinates {
	private int x,y ;
	
	public Coordinates () {
		x = 0 ;
		y = 0 ;
	}
	
	public Coordinates (int x, int y) {
		this.x = x ;
		this.y = y ;
	}

    public Coordinates (Coordinates c) {
        x = c.x ;
        y = c.y ;
    }

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

    public void setCoordinates(int x, int y) {
        this.x = x ;
        this.y = y ;
    }

	
	/**
	 * Calculate the square distance between 2 positions
	 * @param a	The first position
	 * @param b The second position
	 * @return	The square distance between the 2 positions
	 */
	public static int getSquareDistance (Coordinates a, Coordinates b) {
		if ( a == null || b == null ) {
			return -1 ;
		}
		
		return ( (b.getX() - a.getX()) * (b.getX() - a.getX()) ) +
				( (b.getY() - a.getY()) * (b.getY() - a.getY()) ) ;

	}
	
	/**
	 * Translate an (x,y) coordinate in a sizeX*sizeY board
	 */
	public static int calculateIndex (int sizeX, int sizeY, int x, int y) {
		return  sizeX*y + x  ;
	}
	
	public void copyCoordinates( Coordinates source ) {
		x = source.getX() ;
		y = source.getY() ;
	}
	
	@Override
	public String toString () {
		return "("+x+","+y+")" ;
	}
	
	@Override
	public boolean equals (Object o) {
		if ( o instanceof Coordinates ) {
			Coordinates c = (Coordinates) o ;
			if ( c.getX() == x && c.getY() == y ) {
				return true ;
			}
		}
		return false ;
	}
}
