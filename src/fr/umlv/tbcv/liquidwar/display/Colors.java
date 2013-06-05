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

package fr.umlv.tbcv.liquidwar.display;

public class Colors {
	// Various colors for the points
	private static final float[][] colors = { 
		{ 0.74215f, 0.83125f, 0.22265625f, 1.0f } ,
		{    0.54f,    0.82f,	    0.42f, 1.0f } ,
        {       0f,      95f,         95f, 1.0f }
		} ;

    private static final float[][] teamColors = {
        {    0.42f,    0.72f,       0.92f, 1.0f } ,
        {    0.88f,    0.28f,	    0.18f, 1.0f } ,
        {    0.14f,    0.59f,       0.42f, 1.0f }
    } ;
	
	public static float[] getColor (int number) {
		if ( number >= colors.length )  return colors[0] ;
		return colors[number] ;
	}

    public static float[] getTeamColor (int number) {
        if ( number >= teamColors.length )  return teamColors[0] ;
        return teamColors[number] ;
    }
}
