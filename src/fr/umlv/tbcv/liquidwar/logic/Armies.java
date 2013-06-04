package fr.umlv.tbcv.liquidwar.logic;

public interface Armies {

    /**
     * Make every fighter of an army move to an adjacent case if it can
     */
	public void move() ;

    /**
     * Get the number of fighters in a team, or the total number
     * @param team The team whose fighters we are counting
     * @return The number of fighters in the team
     */
	public int getFightersNumber(int team) ;

    /**
     * Get the number of active armies
     * @return The number of armies
     */
    public int getArmiesNumber() ;

    /**
     * Retrieve the position of every fighter
     * @param team The fighter number team (Starts from 0, and -1 retrieves every fighter regardless of their team)
     * @return An integer array of the fighters position, each fighter taking 2 slots in the array.
     */
	public int[] getFightersPosition (int team) ;


}
