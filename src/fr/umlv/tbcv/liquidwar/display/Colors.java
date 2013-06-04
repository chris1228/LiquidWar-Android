package fr.umlv.tbcv.liquidwar.display;

public class Colors {
	// Various colors for the points
	private static final float[][] colors = { 
		{ 0.74215f, 0.83125f, 0.22265625f, 1.0f } ,
		{    0.54f,    0.82f,	    0.42f, 1.0f } ,
        {       0f,      95f,         95f, 1.0f }
		} ;

    private static final float[][] teamColors = {
        {    0.42f,    0.72f,       0.92f, 1.0f } ,
        {    0.88f,    0.28f,	    0.18f, 1.0f } ,
        {    0.14f,    0.59f,       0.42f, 1.0f }
    } ;
	
	public static float[] getColor (int number) {
		if ( number >= colors.length )  return colors[0] ;
		return colors[number] ;
	}

    public static float[] getTeamColor (int number) {
        if ( number >= teamColors.length )  return teamColors[0] ;
        return teamColors[number] ;
    }
}
