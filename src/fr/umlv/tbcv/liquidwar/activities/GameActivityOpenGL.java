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
//	private LiquidWorld gameWorld = new LiquidWorld() ;
	
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
		private float mPreviousX;
		private float mPreviousY;
		private final float TOUCH_SCALE_FACTOR = 180.0f / 320;

		public MyGLSurfaceView(Context context) {
			super(context);
			
			setEGLContextClientVersion(2);
			
			// Set the Renderer for drawing on the GLSurfaceView
			myRenderer = new LiquidWarRenderer() ;
	        setRenderer(myRenderer);	
	        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
//	        gameWorld.run() ;
		}
		
		@Override
	    public boolean onTouchEvent(MotionEvent e) {
			// MotionEvent reports input details from the touch screen
	        // and other input controls. In this case, you are only
	        // interested in events where the touch position changed.

	        float x = e.getX();
	        float y = e.getY();
	        
	        GameInput.setxPlayer( (int) (x* LiquidWorld.getGamewidth() / this.getWidth())  ) ;
	        GameInput.setyPlayer( (int) (y* LiquidWorld.getGameheight()/ this.getHeight()) ) ;


	        //TODO remove this part (rotating the triangle)
	        switch (e.getAction()) {
	            case MotionEvent.ACTION_MOVE:

	                float dx = x - mPreviousX;
	                float dy = y - mPreviousY;

	                // reverse direction of rotation above the mid-line
	                if (y < getHeight() / 2) {
	                  dx = dx * -1 ;
	                }

	                // reverse direction of rotation to left of the mid-line
	                if (x > getWidth() / 2) {
	                  dy = dy * -1 ;
	                }

	                myRenderer.mAngle += (dx + dy) * TOUCH_SCALE_FACTOR;  // = 180.0f / 320
	                requestRender();
	        }

	        mPreviousX = x;
	        mPreviousY = y;
	        return true;
	    }
	}
	
	
	
}
