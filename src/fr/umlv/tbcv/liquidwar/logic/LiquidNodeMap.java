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

import fr.umlv.tbcv.liquidwar.logic.pathfinding.Node;

/**
 * Implementation of LiquidMap that consists of interconnected Nodes.
 */
public class LiquidNodeMap implements LiquidMap{
    private int w,h ;
    private Node[][] map;
    public static final int EMPTY = 0 ;
    public static final int OBSTACLE = -1 ; // Map contains either 0(empty), -1(obstacle), or n > 0 : n being the fighter index in the Fighters[] array

    public LiquidNodeMap (int w, int h) {
        this.w = w ;
        this.h = h ;

        // Every element initialized and equals 0 (EMPTY) at the creation
        map = new Node[w][h] ;

        // Initialize every node with its coordinate
        for(int i = 0 ; i < w ; i++)  {
            for(int j = 0 ; j < h ; j++) {
                map[i][j] = new Node(i,j) ;
            }
        }
    }

    private boolean outOfBounds (Coordinates coord) {
        if( coord.getX() <  0 || coord.getX() >= w || coord.getY() < 0 || coord.getY() >= h ) {
            return true ;
        }
        return false ;
    }

    /**
     * Set a coordinate state on the grid as EMPTY or OBSTACLE
     * @param coord Coordinate to modify on the grid
     * @param newState New state of the node on the grid
     */
    private void putElement ( Coordinates coord, CellState newState) {
        if( outOfBounds(coord) ) {
            //TODO : Send to err log
            return; // Out of bounds : Nothing is done
        }
        map[ coord.getX() ][ coord.getY() ].setState(newState);
    }

    private CellState getElement ( Coordinates coord ) {
        if(outOfBounds(coord)) {
            return CellState.OBSTACLE ; // Out of bounds : We say it's s an obstacle
        }
        return map[ coord.getX() ][ coord.getY() ].getState() ;
    }

    /**
     * Load map information, which for now consists in obstacle coordinates
     */
    @Override
    public void loadMap() {
        loadMapTwo();
    }

    private void loadMapOne() {
        for (int i = 5 ; i <= 20 ; i++ ) {
            putObstacle(new Coordinates(i,15));
        }
        for (int j = 15 ; j <= 40 ; j++) {
            putObstacle(new Coordinates(15,j));
        }
    }

    private void loadMapTwo() {
        // HARDCODED FOR NOW
        int i = 5 ;
        for(int j = 0 ; j < h-5 ; j++ ) {
            putObstacle(new Coordinates(i,j));
        }
        i = 25 ;
        for(int j = 0 ; j < h-5 ; j++ ) {
            putObstacle(new Coordinates(i,j));
        }

        i = 15 ;
        for(int j = 5 ; j < h ; j++ ) {
            putObstacle(new Coordinates(i,j));
        }
        i= 35 ;
        for(int j = 5 ; j < h ; j++ ) {
            putObstacle(new Coordinates(i,j));
        }
    }

    @Override
    public void clear(Coordinates coord) {
        putElement(coord,CellState.EMPTY);
    }

    @Override
    public void putObstacle(Coordinates coord) {
        putElement(coord,CellState.OBSTACLE);
    }

    @Override
    public void putSoldier(Coordinates coord, Fighter f) {
        if(!(f instanceof NodeFighter)) {
            throw new RuntimeException() ;
        }
        if( outOfBounds(coord)) {
            return ; // Nothing is done if the coordinates are invalid
        }
        NodeFighter nf = (NodeFighter) f ;
        clear(f.getPosition()) ;
        putElement(coord, CellState.FIGHTER );
        map[ coord.getX() ][ coord.getY() ].setFighter(nf);
    }

    @Override
    public int getWidth() {
        return w;
    }

    @Override
    public int getHeight() {
        return h;
    }

    @Override
    public int countObstacles() {
        int obstacles = 0 ;
        for(int i = 0 ; i < w ; i++) {
            for(int j = 0 ; j < h ; j++) {
                if (map[i][j].getState() == CellState.OBSTACLE) {
                    obstacles++ ;
                }
            }
        }
        return obstacles ;
    }

    @Override
    public boolean isEmpty(Coordinates pos) {
        return getElement(pos) == CellState.EMPTY ;
    }

    @Override
    public boolean hasFighter(Coordinates pos) {
        return getElement(pos) == CellState.FIGHTER ;
    }

    @Override
    public boolean hasObstacle(Coordinates pos) {
        return  getElement(pos) == CellState.OBSTACLE ;
    }

    @Override
    public boolean hasObstacle(int x, int y) {
        return getElement( new Coordinates(x,y)) == CellState.OBSTACLE ;
    }

    /**
     * Reset every field used by path algorithm for every node on the grid
     */
    public void resetNodes() {
        for(int i = 0 ; i < w ; i++)  {
            for(int j = 0 ; j < h ; j++) {
                map[i][j].resetNode();
            }
        }
    }

    @Override
    public List<Coordinates> getNeighbors(Coordinates cell) {
        List<Coordinates> neighborList = new ArrayList<>() ;
        if(cell != null) {
            for(int i = cell.getX()-1 ; i <= cell.getX()+1 ; i++){
                if(i < 0 || i >= w) { continue ; }
                for(int j = cell.getY()-1 ; j <= cell.getY()+1 ; j++) {
                    if(j < 0 || j >= h) { continue ; }
                    if(map[i][j].getState() != CellState.OBSTACLE && (i != cell.getX() || j != cell.getY()) ) {
                        neighborList.add(new Coordinates(i,j)) ;
                    }
                }
            }
        }
        return neighborList ;
    }

    public Node getNode(Coordinates cell) {
        // TODO Treat out of bounds case
        return map[cell.getX()][cell.getY()] ;
    }

    public Node getNode(int x, int y) {
        // TODO Treat out of bounds case
        return map[x][y] ;
    }

    public Fighter getFighter(Coordinates cell) {
        if(outOfBounds(cell)) {
            return null ;
        }
        return getNode(cell).getFighter();
    }

    /**
     * Determine if, in a direct path between one point and another, there is an obstacle
     */
    public boolean obstacleInPath (Coordinates a, Coordinates b) {
        Node n = getNode(a) ;

        while(! n.getCoord().equals(b)) {
            int x = n.getCoord().getX() ;
            int y = n.getCoord().getY() ;
            for(int i = x-1 ; i <= x+1 ; i++) {
                if(i<0 || i >= w) { continue; }
                for(int j = y-1 ; j <= y+1 ; j++) {
                    if(j<0 || j >= h) { continue ; }
                    Node t = getNode(i,j) ;
                    if( Coordinates.getSquareDistance(t.getCoord(),b) < Coordinates.getSquareDistance(n.getCoord(),b)) {
                        n = getNode(i,j) ;
                        if(hasObstacle(n.getCoord())) {
                            return true ;
                        }
                    }
                }
            }
        }
        return false ;
    }
}
