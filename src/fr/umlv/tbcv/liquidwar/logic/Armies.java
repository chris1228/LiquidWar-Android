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

public interface Armies {

    /**
     * Make every fighter of an army move to an adjacent case if it can
     */
	public void move() ;

    /**
     * Get the number of fighters in a team, or the total number
     * @param team The team whose fighters we are counting
     * @return The number of fighters in the team
     */
	public int getFightersNumber(int team) ;

    /**
     * Get the total number of fighters
     * @return The number of active fighters
     */
    public int getFightersNumber() ;

    /**
     * Get the number of active armies
     * @return The number of armies
     */
    public int getArmiesNumber() ;

    /**
     * Retrieve the position of every fighter
     * @param team The fighter number team (Starts from 0, and -1 retrieves every fighter regardless of their team)
     * @return An integer array of the fighters position, each fighter taking 2 slots in the array.
     */
	public int[] getFightersPosition (int team) ;


}
