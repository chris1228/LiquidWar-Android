package fr.umlv.tbcv.liquidwar.logic;

import android.util.Log;

public abstract class Fighter {
    private static final int FULL_HEALTH = 100 ;
	protected Coordinates position ;
	protected short health ;
    protected int team ;
	
	public abstract void move (LiquidMap lwmap, Fighter[] fighters) ;

    public boolean isFriend(Fighter f) {
        return team == f.team ;
    }
	
	public void attack (Fighter ennemy) {
        int damageAmount = 3 ;
        ennemy.health -= damageAmount ;
        if(ennemy.health <= 0) {
            ennemy.health = FULL_HEALTH ;
            ennemy.team = team ;
        }
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
	
	public short getHealth() {
		return health;
	}
	public void setHealth(short health) {
		this.health = health;
	}
	
	protected void removeHealth(int damage) {
		health -= damage ;
	}
}
