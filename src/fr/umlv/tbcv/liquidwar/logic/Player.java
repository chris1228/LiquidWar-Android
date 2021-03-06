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
 * A player in the Liquid Wars game.
 * The player gets to control a group of soldiers by changing his position on the map.
 */
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
