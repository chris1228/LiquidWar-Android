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

import java.util.ArrayDeque;
import java.util.Deque;

import fr.umlv.tbcv.liquidwar.logic.CellState;
import fr.umlv.tbcv.liquidwar.logic.Coordinates;

/**
 * Nodes for pathfinding
 */
public class Node {
    private Coordinates coord ;
    protected int goal ;
    protected int heuristic ;
    protected int f ;
    protected boolean opened, closed ;
    private Node parent = null ;
    private CellState state = CellState.EMPTY ;
    private int fighterNumber ;

    public Node(Coordinates c) {
        goal = heuristic = f = 0 ;
        opened = closed = false ;
        coord.copyCoordinates(c);
    }

    public Coordinates getCoord() {
        return coord;
    }

    public void setCoord(Coordinates coord) {
        this.coord.copyCoordinates(coord);
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public int getFighterNumber() {
        return fighterNumber;
    }

    public void setFighterNumber(int fighterNumber) {
        this.fighterNumber = fighterNumber;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Node) {
            Node n = (Node) o ;
            return coord.equals(n.coord) ;
        }
        return false ;
    }

    /**
     * Build a list of node by adding parents successively
     * @return The list of parents of the node
     */
    public Deque<Coordinates> backtrace () {
        Deque<Coordinates> path = new ArrayDeque<>() ;
        Node nParent = null ;

        path.push(this.coord) ;
        nParent = this.parent ;
        this.parent = null ; // Remove the parent link for the next algorithm iteration
        while(nParent != null) {
            path.push(nParent.coord) ;
            Node temp = nParent ;
            nParent = nParent.parent ;
            temp.parent = null ; // Remove the parent link for the next algorithm iteration
        }

        return path ;
    }

}
