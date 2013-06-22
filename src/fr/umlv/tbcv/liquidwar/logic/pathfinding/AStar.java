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

import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import fr.umlv.tbcv.liquidwar.logic.Coordinates;
import fr.umlv.tbcv.liquidwar.logic.LiquidMap;
import fr.umlv.tbcv.liquidwar.logic.LiquidNodeMap;

/**
 * Implementation of PathFinder with the A* algorithm
 */
public class AStar extends PathFinder {
    private LiquidNodeMap nodemap ;
    public static final double sqrt2 = Math.sqrt(2);

    public AStar(LiquidMap lwmap){
        if(!(lwmap instanceof LiquidNodeMap)) {
            throw new RuntimeException() ;
        }
        nodemap = (LiquidNodeMap) lwmap ;
    }

    /**
     * Get a path between a start position and an ending position
     *
     * @param start Coordinate of the point of departure
     * @param end   Coordinate of the point of arrival
     * @return A deque of coordinates leading to the arrival if it can be reached, null otherwise
     */
    @Override
    public Deque<Coordinates> finder(Coordinates start, Coordinates end) {
        if(nodemap == null) {
            // Constructor should be called before calling finder
            throw new RuntimeException() ;
        }
        PriorityQueue<Node>openSet = new PriorityQueue<>() ;
        Node startNode = nodemap.getNode(start) ;
        Node endNode = nodemap.getNode(end) ;

        startNode.g = 0 ;
        startNode.f = 0 ;

        startNode.opened = true ;
        openSet.add(startNode);

        while(!openSet.isEmpty()) {
            // Retrieve the node with the minimum 'f' value
            Node n = openSet.poll() ;
            n.closed = true ;

            // If n is the final node it's over
            if(n.equals(endNode)) {
                return n.backtrace() ;
            }

            // get the neighbors of n
            List<Coordinates> neighbors = nodemap.getNeighbors (n.getCoord()) ;
            for(Coordinates neighbor : neighbors) {
                Node nodeNeighbor = nodemap.getNode(neighbor) ;
                if(nodeNeighbor.closed) {
                    continue ;
                }

                // Get the distance between current node and neighbor
                double ng = n.g + ((neighbor.getX() - n.getCoord().getX() == 0 || neighbor.getY() - n.getCoord().getY() == 0 ) ? 1 : sqrt2 );

                if(!nodeNeighbor.opened || ng < nodeNeighbor.g) {

                    // nodeNeighbor wasn't opened, so we add it in the open set
                    if(!nodeNeighbor.opened) {
                        nodeNeighbor.g = ng ;
                        if(nodeNeighbor.heuristic == 0) {
                            nodeNeighbor.heuristic = hFunction.distance(nodeNeighbor.getCoord() , endNode.getCoord()) ;
                        }

                        nodeNeighbor.f = nodeNeighbor.g + nodeNeighbor.heuristic ;
                        nodeNeighbor.setParent(n);
                        nodeNeighbor.opened = true ;
                        openSet.add(nodeNeighbor);
                    }
                    else {
                        // nodeNeighbor was already opened and can be reached with a smaller cost
                        if ( !openSet.remove(nodeNeighbor)) {
                            throw new RuntimeException() ;
                        }
                        nodeNeighbor.g = ng ;
                        if(nodeNeighbor.heuristic == 0) {
                            nodeNeighbor.heuristic = hFunction.distance(nodeNeighbor.getCoord() , endNode.getCoord()) ;
                        }

                        nodeNeighbor.f = nodeNeighbor.g + nodeNeighbor.heuristic ;
                        nodeNeighbor.setParent(n);
                        if ( !openSet.add(nodeNeighbor)) {
                            throw new RuntimeException() ;
                        }
                    }
                }
            }
        }
        return null;
    }
}
