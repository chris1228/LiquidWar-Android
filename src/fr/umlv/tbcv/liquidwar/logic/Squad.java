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

/**
 * A group of fighters with one leader. Squads are a subset of Armies, and an army consist of 1 or more squads.
 * Squads were created to group up Fighters who followed the same path.
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

    // Change leader of the squad to another fighter ( the next in the list )
    public void changeLeader() {
        boolean leaderMet = false ;

        if(fighterList.isEmpty()) {
            return ;
        }
        // Go through every fighter
        for(NodeFighter inSquad : fighterList) {

            if(inSquad.equals(leader)) {
                leaderMet = true ;
            }
            // Get the fighter right after the leader in the list and make it the new leader
            else if(leaderMet) {
                changeLeader(inSquad) ;
                return ;
            }
        }
        // If we get here, the leader was at the end of the list. The next leader should be the first in the list then.
        changeLeader(fighterList.get(0));
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
