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

import fr.umlv.tbcv.liquidwar.logic.Coordinates;
import fr.umlv.tbcv.liquidwar.logic.LiquidMap;
import fr.umlv.tbcv.liquidwar.logic.LiquidNodeMap;

/**
 * Implementation of PathFinder with the A* algorithm
 */
public class AStar extends PathFinder {
    TreeNodeSet<Node> openSet ;
    Node startNode, endNode ;
    LiquidNodeMap nodemap ;

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
        openSet = new TreeNodeSet<>() ;
        Log.e("FINDER", "Searching from " + start + " to " + end);
        startNode = nodemap.getNode(start) ;
        endNode = nodemap.getNode(end) ;

        openSet.add(startNode);
        startNode.opened = true ;

        while(!openSet.isEmpty()) {
            // Retrieve the node with the minimum 'f' value
            Node n = openSet.pollFirst() ;
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
                double ng = nodeNeighbor.g + ((neighbor.getX()-n.getCoord().getX() == 0 || neighbor.getY()-n.getCoord().getY() == 0 ) ? 1 : Math.sqrt(2)) ;

                if(!nodeNeighbor.opened || ng < nodeNeighbor.g) {
                    nodeNeighbor.g = ng ;
                    if(nodeNeighbor.heuristic == 0) {
                        nodeNeighbor.heuristic = hFunction.distance(nodeNeighbor.getCoord() , endNode.getCoord()) ;
                        nodeNeighbor.f = nodeNeighbor.g + nodeNeighbor.heuristic ;
                        nodeNeighbor.setParent(n);

                        if(!nodeNeighbor.opened) {
                            openSet.add(nodeNeighbor);
                            nodeNeighbor.opened = true ;
                        }
                        else {
                            // nodeNeighbor can be reached with a smaller cost
                            openSet.update(nodeNeighbor) ;
                        }
                    }
                }
            }
        }
        return null;
    }
}
