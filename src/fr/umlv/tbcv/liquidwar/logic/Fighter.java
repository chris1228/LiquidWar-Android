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

/**
 * A fighter, soldier in an army.
 * It can attack or heal other fighters depending on their team.
 * Its only objective is ultimately to follow the player controlling its team.
 */
public abstract class Fighter {
    protected static final int FULL_HEALTH = 100 ;
    protected static final int damageAmount = 3 ;
	protected Coordinates position ;
	protected int health ;
    protected int team ;

    public Fighter(int team) {
        position = new Coordinates() ;
        this.team = team ;
        health = FULL_HEALTH ;
    }

    /**
     * Make the fighter move on the grid or attack/heal another fighter
     * @param lwmap     The grid
     * @param fighters  Array of fighters on the battlefield
     * @return  0 if the fighter's position hasn't changed ; 1 if the fighter has moved on the map
     */
	public abstract int move (LiquidMap lwmap, Fighter[] fighters) ;

    public boolean isFriend(Fighter f) {
        return team == f.team ;
    }
	
	public Fighter attack (Fighter ennemy) {
        ennemy.health -= damageAmount ;
        if(ennemy.health <= 0) {
            ennemy.health = FULL_HEALTH ;
            ennemy.team = team ;
            return ennemy ;
        }
        return null ;
    }

    public void heal (Fighter friend) {
        int healAmount = 2 ;
        if (friend.health >= FULL_HEALTH){
            return ;
        }
        else if (friend.health+healAmount > FULL_HEALTH) {
            friend.health = FULL_HEALTH ;
        }
        else {
            friend.health += healAmount ;
        }
    }
	
	public Coordinates getPosition() {
		return position;
	}
	public void setPosition(Coordinates position) {
		this.position.copyCoordinates(position) ;
	}
	
	public int getHealth() {
		return health;
	}
	public void setHealth(short health) {
		this.health = health;
	}

    public int getTeam() {
        return team ;
    }
	
	protected void removeHealth(int damage) {
		health -= damage ;
	}
}
