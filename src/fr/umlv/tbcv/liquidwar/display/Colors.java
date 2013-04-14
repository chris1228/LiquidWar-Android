package fr.umlv.tbcv.liquidwar.display;

public class Colors {
	// Various colors for the points
	private static final float[][] colors = { 
		{ 0.74215f, 0.83125f, 0.22265625f, 1.0f } ,
		{    0.42f,    0.42f,	    0.42f, 1.0f } 
		} ;
	
	public static float[] getColor (int number) {
		if ( number >= colors.length )  return colors[0] ;
		return colors[number] ;
	}
}
