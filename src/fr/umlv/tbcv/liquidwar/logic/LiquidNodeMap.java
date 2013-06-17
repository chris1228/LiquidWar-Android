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
    }

    private void putElement ( Coordinates coord, int element) {
        if( coord.getX() <  0 || coord.getX() >= w || coord.getY() < 0 || coord.getY() >= h ) {
            throw new RuntimeException(); // Out of bounds : Throw exception
        }
        switch(element) {
            case EMPTY :
                map[ coord.getX() ][ coord.getY() ].setState(CellState.EMPTY);
                break ;

            case OBSTACLE :
                map[ coord.getX() ][ coord.getY() ].setState(CellState.OBSTACLE) ;
                break ;

            default :
                map[ coord.getX() ][ coord.getY() ].setState(CellState.FIGHTER) ;
                map[ coord.getX() ][ coord.getY() ].setFighterNumber(element);
                break ;
        }
    }

    private int getElement ( Coordinates coord ) {
        if( coord.getX() <  0 || coord.getX() >= w || coord.getY() < 0 || coord.getY() >= h ) {
            return OBSTACLE ; // Out of bounds : We say it's s an obstacle
        }
        switch(map[ coord.getX() ][ coord.getY() ].getState()) {
            case EMPTY : return EMPTY ;
            case OBSTACLE : return OBSTACLE ;
            default : return map[ coord.getX() ][ coord.getY() ].getFighterNumber() ;
        }
    }

    @Override
    public void loadMap() {

    }

    @Override
    public void clear(Coordinates coord) {
        putElement(coord,EMPTY);
    }

    @Override
    public void putObstacle(Coordinates coord) {
        putElement(coord,OBSTACLE);
    }

    @Override
    public void putSoldier(Coordinates coord, Fighter f) {
        //TODO : Make a NodeFighter
        SimpleFighter sf = (SimpleFighter) f ;
        putElement(coord, sf.getIndex() );
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
        return getElement(pos) == EMPTY ;
    }

    @Override
    public boolean hasFighter(Coordinates pos) {
        return getElement(pos) > 0 ;
    }

    @Override
    public boolean hasObstacle(Coordinates pos) {
        return  getElement(pos) == OBSTACLE ;
    }

    @Override
    public boolean hasObstacle(int x, int y) {
        return getElement( new Coordinates(x,y)) == OBSTACLE ;
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
        return map[cell.getX()][cell.getY()] ;
    }

    public Node getNode(int x, int y) {
        return map[x][y];
    }
}
