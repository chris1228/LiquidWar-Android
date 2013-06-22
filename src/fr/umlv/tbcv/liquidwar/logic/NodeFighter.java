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

import java.util.Deque;

import fr.umlv.tbcv.liquidwar.input.GameInput;
import fr.umlv.tbcv.liquidwar.logic.pathfinding.AStar;
import fr.umlv.tbcv.liquidwar.logic.pathfinding.JumpPointFinder;
import fr.umlv.tbcv.liquidwar.logic.pathfinding.Naive;
import fr.umlv.tbcv.liquidwar.logic.pathfinding.PathFinder;

/**
 * Implementation of Fighter using Nodes for pathfinding (interacting with LiquidNodeMap).
 */
public class NodeFighter extends Fighter {
    Deque<Coordinates> path ;       // Path of coordinates that the fighter has to follow to reach the cursor. Recalculated every time the cursor moves
    PathFinder pathFinder ;         // Path algorithm used to reach the cursor
    Coordinates nextPosition ;      // Next coordinate that the fighter has to reach
    Coordinates approximateCursor ; // Last good cursor position. Only updated when the real cursor has moved far away from that last position
    LiquidNodeMap nodeMap ;         // The grid
    boolean isLeader ;              // If node is leader
    NodeFighter leader ;            // Leader fighter to follow if current fighter isn't itself a leader

    public NodeFighter (LiquidMap lwmap, int team) {
        super(team) ;
        if(!(lwmap instanceof LiquidNodeMap)) {
            throw new RuntimeException() ;
        }
        nodeMap = (LiquidNodeMap) lwmap ;
        pathFinder = new JumpPointFinder(lwmap) ;

    }

    public void move(LiquidMap lwmap, Fighter[] fighters) {
        // Get a pathJumpPointFinder
        computePath();
        nodeMap.resetNodes();

        // If after computing, no available paths were found, we don't move
        if(path == null || path.isEmpty()) {
            return ;
        }

        if(nextPosition == null || (Coordinates.getSquareDistance(position,nextPosition) <= 3 )) {
            nextPosition = path.pop() ;
            Log.e("MOVE","Position popped :" + nextPosition);
        }

        Coordinates tempPosition = new Coordinates(position.getX(), position.getY()) ;
        Coordinates idealPosition = new Coordinates(position.getX(), position.getY()) ;
        Coordinates finalPosition = new Coordinates(position.getX(), position.getY()) ;

        for (int i = position.getX() - 1 ; i <= position.getX() + 1 ; i ++ ) {
            tempPosition.setX(i) ;
            for ( int j = position.getY() - 1 ; j <= position.getY() + 1  ; j++ ) {
                tempPosition.setY( j ) ;
                if ( (nodeMap.isEmpty(tempPosition)) &&
                        Coordinates.getSquareDistance( tempPosition, nextPosition ) <
                                Coordinates.getSquareDistance( finalPosition, nextPosition) ) {
                    finalPosition.copyCoordinates( tempPosition ); // Figure out the next best possible position
                }
                if ( Coordinates.getSquareDistance( tempPosition, nextPosition ) <
                        Coordinates.getSquareDistance( idealPosition, nextPosition) ) {
                    idealPosition.copyCoordinates( tempPosition ); // Figure out the next best position regardless of possibility
                }
            }
        }

//        Log.e("FIGHTER","CURRENT POSITION" + position) ;
//        Log.e("FIGHTER","WANTED POSITION " + nextPosition) ;
//        Log.e("FIGHTER","FINAL POSITION " + finalPosition );

        // If the fighter hasnt been able to move, there is an obstacle/a soldier
        if(finalPosition.equals(position)) {
            if(nodeMap.hasFighter(idealPosition)) {
                Fighter obstacle = nodeMap.getFighter(idealPosition) ;
                if (isFriend(obstacle)) {
                    heal(obstacle) ;
                }
                else {
                    attack(obstacle) ;
                }
            }
            return ;
        }
        // Fighter can move freely, its position is updated
        else {
            nodeMap.putSoldier(finalPosition, this) ;
            position.copyCoordinates(finalPosition);
        }
    }

    /**
     * move equivalent to the one in SimpleFighter for testing purposes
     * @param lwmap The grid
     * @param fighters The array of fighters (useless in here)
     */
    public void move2(LiquidMap lwmap, Fighter[] fighters) {
        if(!(lwmap instanceof LiquidNodeMap)) {
            throw new RuntimeException() ;
        }
        LiquidNodeMap nodeMap = (LiquidNodeMap) lwmap ;
        Coordinates cursor = GameInput.getPlayerCoordinate(team) ;
        Coordinates tempPosition = new Coordinates(position.getX(), position.getY()) ;
        Coordinates idealPosition = new Coordinates(position.getX(), position.getY()) ;
        Coordinates finalPosition = new Coordinates(position.getX(), position.getY()) ;

        for (int i = position.getX() - 1 ; i <= position.getX() + 1 ; i ++ ) {
            tempPosition.setX(i) ;
            for ( int j = position.getY() - 1 ; j <= position.getY() + 1  ; j++ ) {
                tempPosition.setY( j ) ;
                if ( (nodeMap.isEmpty(tempPosition)) &&
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

//        Log.e("FIGHTER","CURRENT POSITION" + position) ;
//        Log.e("FIGHTER","WANTED POSITION " + nextPosition) ;

        // If the fighter hasnt been able to move, there is an obstacle/a soldier
        if(finalPosition.equals(position)) {
            if(nodeMap.hasFighter(idealPosition)) {
                Fighter obstacle = nodeMap.getFighter(idealPosition) ;
                if (isFriend(obstacle)) {
                    heal(obstacle) ;
                }
                else {
                    attack(obstacle) ;
                }
            }
            return ;
        }
        // Fighter can move freely, its position is updated
        else {
            nodeMap.putSoldier(finalPosition, this) ;
            position.copyCoordinates(finalPosition);
        }
    }

    /**
     * Figure out if 2 coordinates are close to each other
     * @param a First coordinate
     * @param b Second coordinate
     * @return True if the two coordinates are close to each other, false otherwise
     */
    private boolean areClose (Coordinates a, Coordinates b) {
        return Coordinates.getSquareDistance(a,b) <= 2 ;
    }

    /**
     * Calculate a path between the soldier current position and the player's cursor
     *
     * @return 1 if a path has been found ;
     *         -1 if no path exists ;
     *         0 if the path doesn't need to be recalculated (follow previous path)
     */
    private int computePath () {
        Coordinates cursor = GameInput.getPlayerCoordinate(team) ;

        // Update approximate cursor
        if(approximateCursor == null) {
            approximateCursor = new Coordinates(cursor) ;
        }
        else {
            if( !approximateCursor.equals(cursor) || nodeMap.obstacleInPath(approximateCursor,cursor)) {
                approximateCursor.copyCoordinates(cursor);
            }
            //TODO Fix this. Supposed to avoid useless path recalculations but has weird side effets with JPS
//            else {
//                // No need to recalculate paths if the cursor hasnt moved since last time
//                return 0 ;
//            }
        }

        // Make sure we're not already at the destination
        if(position.equals(cursor)) {
            return 0 ;
        }
        path = pathFinder.finder(position,cursor) ;
        if(path != null && !path.isEmpty()) {
            path.pop(); // First node is useless (it's where we are right now)
            return 0 ;
        }
        return -1 ;
    }
}
