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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * A group of fighters with one leader. Squads are a subset of Armies, and an army consist of 1 or more squads.
 * Squads were created to group up Fighters who followed the same path.
 * This class interact with NodeFighter, instead of Fighter.
 */
public class Squad {
    private List<NodeFighter> fighterList ;         // List of fighters who belong to the squad
    private Fighter leader ;                        // Leader of the squad


    public Squad () {
        fighterList = new ArrayList<>() ;
    }

    public Squad (NodeFighter leader) {
        this() ;
        this.leader = leader ;
        addFighter(leader);
        leader.setSquad(this);
    }

    public void addFighter(NodeFighter f) {
        f.setLeader((NodeFighter)leader);
        f.setSquad(this);
        f.turnIntoGrunt();
        fighterList.add(f) ;
    }

    public void removeFighter(NodeFighter f) {
        fighterList.remove(f);
        f.setSquad(null);
    }

    public void addLeader(NodeFighter f) {
        // Every fighter in the squad is now looking up to another fighter
        for(NodeFighter inSquad : fighterList) {
            inSquad.turnIntoGrunt();
            inSquad.setLeader(f);
        }
        // Leader gets added to the team
        leader = f ;
        f.setSquad(this);
        f.turnIntoLeader();
        fighterList.add(f) ;
    }

    public void changeLeader(NodeFighter f) {
        NodeFighter lead = (NodeFighter) getLeader() ;

        if(!fighterList.contains(f)) {
            addLeader(f);
        }
        else {
            for(NodeFighter inSquad : fighterList) {
                inSquad.turnIntoGrunt();
                inSquad.setLeader(f);
                leader = f ;
                f.turnIntoLeader();
            }
        }
    }

    /**
     * Change leader of the squad to another fighter ( the next in the list )
     */
    public void changeLeader() {
        boolean leaderMet = false ;

        if(fighterList.isEmpty()) {
            return ;
        }
        // Go through every fighter
        for(NodeFighter inSquad : fighterList) {

            if(leader != null && inSquad.equals(leader)) {
                leaderMet = true ;
            }
            // Get the fighter right after the leader in the list and make it the new leader
            else if(leaderMet) {
                changeLeader(inSquad) ;
                return ;
            }
        }
        // If we get here, the leader was at the end of the list / There was no leader. The next leader should be the first in the list then.
        changeLeader(fighterList.get(0));
    }

    public void move (LiquidMap lwmap, Fighter[] fighters) {
        int movements = 0 ;
        Iterator<NodeFighter> iter = fighterList.iterator() ;
        while(iter.hasNext()) {
            NodeFighter f = iter.next() ;
            // Spot the dead fighters and switch their team
            if(f.health <= 0) {
                if(f.isLeader) {
                    changeLeader();
                }
                // remove fighter from current squad
                iter.remove();

                f.team = -f.health ;                // Change fighter's team
                f.health = f.FULL_HEALTH ;          // Restore its health

                // Add fighter to new squad
                Squad newSquad = f.getNewSquad() ;
                newSquad.addFighter(f);
            }
            movements += f.move(lwmap,fighters);
        }
        // If no fighters in this squad moved, we try another leader
        if( movements == 0 ) {
            changeLeader();
        }
    }

    /**
     * Splits this current squad in two squads, based on soldiers proximity.
     * Groups of soldiers who are in the same squad but yet are far away ( not touching each other )
     * Squads who need to be splitted in more than two are only splitted in two. (The splitted parties will be resplitted by calling this function)
     *
     * @return The splitted squad if splitting has been done
     *         null if the squad hasn't been splitted
     */
    public Squad splitSquad(LiquidMap lwmap) {
        if(!(lwmap instanceof LiquidNodeMap)) {
            throw new RuntimeException();
        }
        //TODO Reduce search complexity
        HashSet<NodeFighter> firstList = new HashSet<>() ;      // List of adjacent fighters
        Squad splitList = new Squad() ;                         // The new squad (fighters far from the group)

        if(!fighterList.isEmpty()) {
            // Get first fighter
            firstList.add(fighterList.get(0)) ;
            Queue<NodeFighter> neighbors = new LinkedList<>() ;
            neighbors.add(fighterList.get(0)) ;

            while(!neighbors.isEmpty()) {
                NodeFighter f = neighbors.poll() ;

                // Get all his fighter neighbors
                List<Coordinates> surroundingFighters = lwmap.getNeighbors(f.getPosition()) ;

                // Put those fighters in the firstList
                for(Coordinates c : surroundingFighters) {
                    if( !lwmap.hasFighter(c)) {
                        continue ;
                    }
                    NodeFighter neighborFighter = (NodeFighter) ((LiquidNodeMap) lwmap).getFighter(c) ;
                    if( neighborFighter.getSquad().equals(this) && !firstList.contains(neighborFighter)) {
                        firstList.add(neighborFighter) ;
                    }
                }
            }
        }

        // For every fighter of the squad that isn't in firstList
        Iterator<NodeFighter> iter = fighterList.iterator() ;
        while(iter.hasNext()) {
            NodeFighter f = iter.next() ;
            if(!firstList.contains(f)) {
                iter.remove() ;         // Remove then from the squad
                splitList.addFighter(f); ;      // Put them in splitlist
            }
        }

        // Return the new split squad
        if(!splitList.isEmpty()) {
            splitList.changeLeader(); // Designate a leader
            return splitList ;
        }
        return null ;
    }

    /**
     * Merge soldiers of other squads in this squad
     *
     * @return true if the squad has new members ;
     *         false if the squad is untouched.
     */
    public boolean mergeSquad(LiquidMap lwmap) {
        return false ;
    }

    public boolean isEmpty() {
        return fighterList.isEmpty() ;
    }

    @Override
    public String toString() {
        return fighterList.toString() ;
    }


    /* GETTERS / SETTERS */
    public Fighter getLeader() {
        return leader;
    }

    public void setLeader(Fighter leader) {
        this.leader = leader;
    }

}
