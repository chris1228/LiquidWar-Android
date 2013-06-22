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

/**
 * Implementation of Army using Nodes (interacting with LiquidNodMap).
 */
public class NodeArmies implements Armies{

    public int fighterNumber = 2;      // Number of fighters in total
    private Fighter[] fighters ;                    // Array of every fighter on the battlefield
    private int[] fightersPosition ;                // Array of every fighter position. First int is f1(x), second int is f1(y), third int is f2(x)...
    private int nbArmies = 1 ;                      // Number of armies/players
    LiquidNodeMap lwmap ;                           // The grid

    public NodeArmies (LiquidMap map, int nbArmies) {
        if(!(map instanceof LiquidNodeMap)) {
            throw new RuntimeException() ;
        }

        this.nbArmies = nbArmies ;
        this.lwmap = (LiquidNodeMap)map ;

        fighters = new NodeFighter[ fighterNumber ] ;
        initArmy() ;

        // 2 slots ( one for X, the other for Y ) for each fighter
        fightersPosition = new int[fighterNumber * 2] ;
    }

    public NodeArmies (LiquidMap map, int nbArmies, int fighterNumber) {
        this(map,nbArmies);
        this.fighterNumber = fighterNumber ;
    }

    /**
     * Initializes the position of every fighter
     * Every fighter should be regrouped with its friends
     * from the same Army in a corner of the map.
     */
    private void initArmy() {
        int j  = 3 ;
        int fakeWidth = 20 ;
        Coordinates tmp = new Coordinates() ;
        int fightersPerTeam = fighterNumber / nbArmies ;

        for(int t = 0 ; t < nbArmies ; t++) {
            switch(t) {
                case 1 : case 3 : j = lwmap.getHeight()-3 ; break ;
                default : j = 3 ; break ;
            }
            for ( int i = t*(fightersPerTeam) ; i < (t+1)*fightersPerTeam ; i++ ) {
                tmp.setCoordinates(i%(fakeWidth+1) , j);
                if(lwmap.hasObstacle(tmp)) { continue ; }
                fighters[i] = new NodeFighter(lwmap,t);
                fighters[i].getPosition().setX( i% (fakeWidth + 1 )) ;
                fighters[i].getPosition().setY(  j ) ;

//			Log.e("FighterPos", "Fighter init at" + fighters[i].getPosition() ) ;

                if ( i >= fakeWidth && i % fakeWidth == 0 ) {
                    switch(t) {
                        case 1 :case 3 : j-- ; break ;
                        default : j++ ; break ;
                    }
                }
            }
        }
//        for ( int i = 0 ; i < fighterNumber/2 ; i++ ) {
//            tmp.setCoordinates(i%(fakeWidth+1) , j);
//            if(lwmap.hasObstacle(tmp)) { continue ; }
//            fighters[i] = new NodeFighter(lwmap,0);
//            fighters[i].getPosition().setX( i% (fakeWidth + 1 )) ;
//            fighters[i].getPosition().setY(  j ) ;
//
////			Log.e("FighterPos", "Fighter init at" + fighters[i].getPosition() ) ;
//
//            if ( i >= fakeWidth && i % fakeWidth == 0 ) {
//                j++ ;
//            }
//        }
//
//        j = lwmap.getHeight() - 3 ;
//        for ( int i = fighterNumber/2 ; i < fighterNumber ; i++ ) {
//            tmp.setCoordinates(i%(fakeWidth+1) , j);
//            if(lwmap.hasObstacle(tmp)) { continue ; }
//            fighters[i] = new NodeFighter(lwmap,1);
//            fighters[i].getPosition().setX( i% (fakeWidth + 1 )) ;
//            fighters[i].getPosition().setY( j ) ;
//
////			Log.e("FighterPos", "Fighter init at" + fighters[i].getPosition() ) ;
//
//            if ( i >= fakeWidth && i % fakeWidth == 0 ) {
//                j-- ;
//            }
//        }
    }

    /**
     * Make every fighter of an army move to an adjacent case if it can
     */
    @Override
    public void move() {
        for ( Fighter f : fighters ) {
            if(f != null) {
                f.move( lwmap, fighters ) ;
            }
        }
    }

    /**
     * Get the number of fighters in a team, or the total number
     *
     * @param team The team whose fighters we are counting
     * @return The number of fighters in the team
     */
    @Override
    public int getFightersNumber(int team) {
        int i = 0 ;
        for ( Fighter f : fighters)
        {
            if(f != null && f.team == team) {
                i++;
            }
        }
        return i ;
    }

    /**
     * Get the total number of fighters
     *
     * @return The number of active fighters
     */
    @Override
    public int getFightersNumber() {
        return fighterNumber ;
    }

    /**
     * Get the number of active armies
     *
     * @return The number of armies
     */
    @Override
    public int getArmiesNumber() {
        return nbArmies;
    }

    /**
     * Retrieve the position of every fighter
     *
     * @param team The fighter number team (Starts from 0, and -1 retrieves every fighter regardless of their team)
     * @return An integer array of the fighters position, each fighter taking 2 slots in the array.
     */
    @Override
    public int[] getFightersPosition(int team) {
        int i = 0 ;

        for ( Fighter f : fighters)
        {
            if(f != null && f.team == team || team == -1) {
                fightersPosition[i++] = f.getPosition().getX() ;
                fightersPosition[i++] = f.getPosition().getY() ;
            }
        }
        return fightersPosition ;
    }
}
