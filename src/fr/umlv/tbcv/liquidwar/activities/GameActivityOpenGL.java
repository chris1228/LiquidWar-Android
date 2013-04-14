package fr.umlv.tbcv.liquidwar.activities;

import fr.umlv.tbcv.liquidwar.display.LiquidWarRenderer;
import fr.umlv.tbcv.liquidwar.input.GameInput;
import fr.umlv.tbcv.liquidwar.logic.LiquidWorld;
import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
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
			// MotionEvent reports input details from the touch screen
	        // and other input controls. In this case, you are only
	        // interested in events where the touch position changed.

	        float x = e.getX();
	        float y = e.getY();
	        
//	        Log.e("OriginalCursor", "("+ x + "," + y + ")") ;
	        
//	        GameInput.setxPlayer( (int) (x* LiquidWorld.getGamewidth() / this.getWidth())  ) ;
//	        GameInput.setyPlayer( (int) (y* LiquidWorld.getGameheight() / this.getHeight()) ) ;
	        
	        GameInput.setxPlayer( (int) (x* LiquidWorld.getGamewidth()  / this.getWidth()) ) ;
	        GameInput.setyPlayer( LiquidWorld.getGameheight() - ((int) (y* LiquidWorld.getGameheight() / this.getHeight()) ) ) ;
	        
//	        Log.e("CustomCursor", "("+ GameInput.getxPlayer() + "," + GameInput.getyPlayer() + ")") ;
	        

	        return true;
	    }
	}
	
	
	
}
