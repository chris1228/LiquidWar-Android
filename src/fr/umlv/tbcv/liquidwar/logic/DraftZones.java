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

public class DraftZones {
	private Coordinates shape ;
	private int max_zone_size ; 
	private DraftZone[] zones ;
	
	public class DraftZone {
		Coordinates pos ;
		boolean used ;
		int size ;
		int[] link ; 
		int corres ;
		
		public DraftZone(int x, int y) {
			pos = new Coordinates(x,y) ;
			used = false ;
			size = 1 ;
			link = new int[ LiquidDirections.numberOfDir ] ;
			corres = 0 ;
		}
	}
	
	
	public void draft_zones_new (MapLevel level) {
		
	}
	
	
	
	
}
