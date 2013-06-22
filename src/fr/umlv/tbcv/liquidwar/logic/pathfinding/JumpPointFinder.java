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

package fr.umlv.tbcv.liquidwar.logic.pathfinding;

import android.util.Log;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;

import fr.umlv.tbcv.liquidwar.logic.Coordinates;
import fr.umlv.tbcv.liquidwar.logic.LiquidMap;
import fr.umlv.tbcv.liquidwar.logic.LiquidNodeMap;

/**
 * Implementation of PathFinder with the Jump Point Search algorithm.
 */
public class JumpPointFinder extends PathFinder{
    PriorityQueue<Node> openSet ;
    Node startNode, endNode ;
    LiquidNodeMap nodemap ;

    public JumpPointFinder(LiquidMap lwmap) {
        if(!(lwmap instanceof LiquidNodeMap)) {
            throw new RuntimeException() ;
        }
        nodemap = (LiquidNodeMap) lwmap ;
    }

    @Override
    public Deque<Coordinates> finder(Coordinates start, Coordinates end) {
        if(nodemap == null) {
            // Constructor should be called before calling finder
            throw new RuntimeException() ;
        }
        openSet = new PriorityQueue<>() ;
        startNode = nodemap.getNode(start) ;
        endNode = nodemap.getNode(end) ;

        startNode.g = 0 ;
        startNode.heuristic = 0 ;

        // Push the start node in the open set

        startNode.opened = true ;
        openSet.add(startNode) ;

        while(!openSet.isEmpty()) {
            Node n = openSet.poll();
            n.closed = true ;
            if( n.equals(endNode)) {
                return n.backtrace() ;
            }
            identifySuccessors(n);
        }
        return null;
    }

    private void identifySuccessors (Node n) {
        List<Coordinates> neighbors = findNeighbors(n) ;

        for (Coordinates neighbor : neighbors) {
            Coordinates jumpPoint = jump(neighbor, n.getCoord()) ;
            if(jumpPoint != null) {
                Node jumpNode = nodemap.getNode(jumpPoint) ;
                if(jumpNode.closed) {
                    continue ;
                }

                // Include distance, as parent may not be immediately adjacent
                int dx = Math.abs(n.getCoord().getX() - jumpPoint.getX());
                int dy = Math.abs(n.getCoord().getY() - jumpPoint.getY());
                double d = Math.sqrt( dx*dx + dy*dy );
                double ng = n.g + d ;

                if(!jumpNode.opened || ng < jumpNode.g) {
                    if(jumpNode.opened) {
                        if(! openSet.remove(jumpNode)) {
                            throw new RuntimeException();
                        }
                        jumpNode.opened = false ;
                    }
                    jumpNode.g = ng ;
                    if(jumpNode.heuristic == 0) {
                        jumpNode.heuristic = hFunction.distance(jumpPoint , endNode.getCoord() ) ;
                    }
                    jumpNode.f = jumpNode.g + jumpNode.heuristic ;
                    jumpNode.setParent(n);
                    if(!openSet.add(jumpNode) ) {
                        throw  new RuntimeException() ;
                    }

                }

            }
        }
    }

    private Coordinates jump (Coordinates child, Coordinates parent) {
        int x = child.getX() ;
        int y = child.getY() ;
        int dx = child.getX() - parent.getX() ;
        int dy = child.getY() - parent.getY() ;

        if(dx > 0) { dx = 1 ; } else if (dx < 0 ) { dx = -1 ; } else { dx = 0 ; }
        if(dy > 0) { dy = 1 ; } else if (dy < 0 ) { dy = -1 ; } else { dy = 0 ; }


        if(nodemap.hasObstacle(child)) {
            return null ;
        }
        else if ( child.equals(endNode.getCoord())) {
            return child ;
        }

        // Check for forced neighbors
        // along the diagonal
        if( dx!=0 && dy !=0) {
            if ( (!nodemap.hasObstacle(x - dx, y + dy) && nodemap.hasObstacle(x - dx, y)) ||
                 (!nodemap.hasObstacle(x + dx, y - dy) && nodemap.hasObstacle(x, y - dy))) {
                return child ;
            }
        }
        else {
            if( dx != 0 ) { // moving along x
                if( (!nodemap.hasObstacle(x + dx, y + 1) && nodemap.hasObstacle(x, y + 1)) ||
                    (!nodemap.hasObstacle(x + dx, y - 1) && nodemap.hasObstacle(x, y - 1))) {
                    return child ;
                }
            }
            else {
                if( (!nodemap.hasObstacle(x + 1, y + dy) && nodemap.hasObstacle(x + 1, y)) ||
                    (!nodemap.hasObstacle(x - 1, y + dy) && nodemap.hasObstacle(x - 1, y))) {
                    return child ;
                }
            }
        }

        // when moving diagonally, must check for vertical/horizontal jump points
        if (dx != 0 && dy != 0) {
            Coordinates jx = jump(new Coordinates(x + dx, y) , child);
            Coordinates jy = jump(new Coordinates(x, y + dy), child);
            if (jx != null || jy != null) {
                return child ;
            }
        }

        // moving diagonally, must make sure one of the vertical/horizontal
        // neighbors is open to allow the path
        if (!nodemap.hasObstacle(x + dx, y) || !nodemap.hasObstacle(x, y + dy)) {
            return jump(new Coordinates(x + dx, y + dy) , child);
        } else {
            return null;
        }
    }

    private List<Coordinates> findNeighbors (Node n) {
        int x = n.getCoord().getX() ;
        int y = n.getCoord().getY() ;
        List<Coordinates> neighbors = new ArrayList<>() ;

        if(n.getParent() != null){
            int px = n.getParent().getCoord().getX() ;
            int py = n.getParent().getCoord().getY() ;

            // Get direction of travel
            int dx = (x-px) ;
            int dy = (y-py)  ;

            if(dx > 0) { dx = 1 ; } else if (dx < 0 ) { dx = -1 ; } else { dx = 0 ; }
            if(dy > 0) { dy = 1 ; } else if (dy < 0 ) { dy = -1 ; } else { dy = 0 ; }

            // Diagonal search
            if(dx != 0 && dy != 0) {
                if(!nodemap.hasObstacle(x , y+dy)) {
                    neighbors.add(new Coordinates(x , y+dy)) ;
                }

                if(!nodemap.hasObstacle(x+dx , y)) {
                    neighbors.add(new Coordinates(x+dx , y)) ;
                }

                if(!nodemap.hasObstacle(x , y+dy) || !nodemap.hasObstacle(x+dx , y)) {
                    neighbors.add(new Coordinates(x+dx , y+dy)) ;
                }

                if(nodemap.hasObstacle(x-dx , y) && !nodemap.hasObstacle(x , y+dy)) {
                    neighbors.add(new Coordinates(x-dx , y+dy)) ;
                }

                if(nodemap.hasObstacle(x , y-dy) && !nodemap.hasObstacle(x+dx , y)) {
                    neighbors.add(new Coordinates(x+dx , y-dy)) ;
                }
            }
            // Horizontal-Vertical search
            else {
                if (dx == 0) {
                    if(!nodemap.hasObstacle(x, y+dy)) {
                        neighbors.add(new Coordinates(x , y+dy));
                        if(nodemap.hasObstacle(x+1 , y)) {
                            neighbors.add(new Coordinates(x+1 , y+dy)) ;
                        }
                        if(nodemap.hasObstacle(x-1 , y)) {
                            neighbors.add(new Coordinates(x-1 , y+dy)) ;
                        }
                    }
                }
                else {
                    if(!nodemap.hasObstacle(x+dx, y)) {
                        neighbors.add(new Coordinates(x+dx , y));
                        if(nodemap.hasObstacle(x , y+1)) {
                            neighbors.add(new Coordinates(x+dx , y+1)) ;
                        }
                        if(nodemap.hasObstacle(x , y-1)) {
                            neighbors.add(new Coordinates(x+dx , y-1)) ;
                        }
                    }
                }
            }
        }
        // Return all neighbors
        else {
            neighbors = nodemap.getNeighbors(n.getCoord()) ;
        }
    return neighbors ;
    }
}
