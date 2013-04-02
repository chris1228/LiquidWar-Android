package fr.umlv.tbcv.liquidwar.logic;

public class Armies {
	
	private static final int fighterNumber = 500 ;
	private Fighter[] fighters ;
	
	public Armies () {
		for ( int i = 0 ; i < fighterNumber ; i++ ) {
			fighters[i] = new Fighter();
		}
	}

	public void move() {
		for ( Fighter f : fighters ) {
			f.move() ;
		}
	}
}
