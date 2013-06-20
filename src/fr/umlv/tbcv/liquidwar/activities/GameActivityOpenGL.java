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

package fr.umlv.tbcv.liquidwar.activities;

import fr.umlv.tbcv.liquidwar.display.LiquidWarRenderer;
import fr.umlv.tbcv.liquidwar.input.GameInput;
import fr.umlv.tbcv.liquidwar.logic.Coordinates;
import fr.umlv.tbcv.liquidwar.logic.LiquidWorld;
import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class GameActivityOpenGL extends Activity {
	
	private GLSurfaceView myGLView ;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Create a GLSurfaceView instance and set it
		// as the ContentView for this Activity.
		myGLView = new MyGLSurfaceView(this);
		
		
		setContentView(myGLView);
	}
	
	
	
	public class MyGLSurfaceView extends GLSurfaceView {
		
	    private final LiquidWarRenderer myRenderer ;


		public MyGLSurfaceView(Context context) {
			super(context);
			
			setEGLContextClientVersion(2);
			
			// Set the Renderer for drawing on the GLSurfaceView
			myRenderer = new LiquidWarRenderer() ;
	        setRenderer(myRenderer);	
//	        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

		}
		
		@Override
	    public boolean onTouchEvent(MotionEvent e) {
//	        Log.e("OriginalCursor", "("+ x + "," + y + ")") ;

            // Every touch on the screen can move up to one player cursor
            for(int touches = 0 ; touches < e.getPointerCount() ; touches++ ) {
                int x = (int) e.getX( e.getPointerId(touches)) ;
                int y = (int) e.getY( e.getPointerId(touches)) ;
                Coordinates touchCoordinate = new Coordinates( x* LiquidWorld.getGamewidth()  / this.getWidth() , LiquidWorld.getGameheight() - ( (y* LiquidWorld.getGameheight() / this.getHeight()) ) ) ;

                for(int i = 0 ; i < GameInput.getNbPlayers() ; i++) {
                    Coordinates playerCoordinate = GameInput.getPlayerCoordinate(i) ;

//                    Log.e("DIST", "Distance entre toucher et player "+i+" = "+ Coordinates.getSquareDistance(touchCoordinate, playerCoordinate )) ;
                    if( Coordinates.getSquareDistance(touchCoordinate, playerCoordinate ) < 20 ) {
                        // Safeguard so that player cursor never gets out of bounds
                        if(touchCoordinate.getX() >= LiquidWorld.getGamewidth()) {
                            touchCoordinate.setX(LiquidWorld.getGamewidth()-1);
                        }
                        else if (touchCoordinate.getX() <= 0) {
                            touchCoordinate.setX(0);
                        }

                        if(touchCoordinate.getY() >= LiquidWorld.getGameheight()) {
                            touchCoordinate.setY(LiquidWorld.getGameheight()-1);
                        }
                        else if (touchCoordinate.getY() <= 0) {
                            touchCoordinate.setY(0);
                        }
                        GameInput.setPlayersCoordinates(i,touchCoordinate);
                        // Have to break here so that the touch coordinate only modifies one player cursor
                        break ;
                    }
                }
            }

//            int x = (int) e.getX( e.getPointerId(0) );
//            int y = (int) e.getY( e.getPointerId(0) );
//            Coordinates nextCoordinates = new Coordinates( x* LiquidWorld.getGamewidth()  / this.getWidth() , LiquidWorld.getGameheight() - ( (y* LiquidWorld.getGameheight() / this.getHeight()) ) );
//
//            Log.e("DIST", "Distance entre toucher et player 0 : " + Coordinates.getSquareDistance(GameInput.getPlayerCoordinate(0), nextCoordinates)) ;
//            GameInput.setPlayersCoordinates(0,nextCoordinates);
//	        Log.e("CustomCursor", "PLAYER @ "+ GameInput.getPlayerCoordinate(0) ) ;
	        

	        return true;
	    }
	}
	
	
	
}
