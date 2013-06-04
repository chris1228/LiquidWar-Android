package fr.umlv.tbcv.liquidwar.activities;

import fr.umlv.tbcv.liquidwar.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas; 
import android.graphics.Color;
import android.view.View.OnTouchListener ;
import android.graphics.Paint;
 
public class GameActivity extends Activity implements OnTouchListener {
	
	OurView v ;
	Bitmap ball ;
	float x,y ;
	Paint p = new Paint();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState) ;
		
		v = new OurView(this) ;
		v.setOnTouchListener(this) ;
		
		// Dessiner l'intérieur d'une figure
		p.setStyle(Paint.Style.FILL);
		 
		// Dessiner ses contours
		p.setStyle(Paint.Style.STROKE);
		 
		// Dessiner les deux
		p.setStyle(Paint.Style.FILL_AND_STROKE);
		
		p.setColor(Color.parseColor("#000000"));
		
		
		ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball ) ;
		x = y = 0 ;
		setContentView(v) ;
		
	}
	
	@Override
	protected void onPause() {
		super.onPause() ;
		v.pause();
	}
	
	@Override
	protected void onResume() {
		super.onResume() ;
		v.resume() ;
	}
	
	@Override
	public boolean onTouch(View argView, MotionEvent event) {
		
		try {
			Thread.sleep(50) ;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		switch ( event.getAction() ) {
		
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_MOVE:
			x = event.getX() ;
			y = event.getY();
			break ;
		
			default : 
				break ;
		
		}
		
		return true;
	}
	
	
	
	
	
	
	public class OurView extends SurfaceView implements Runnable {
		
		Thread t = null ;
		SurfaceHolder holder ;
		boolean isOK = false ;

		public OurView(Context context) {
			super(context);
			holder = getHolder() ;
		}

		@Override
		public void run() {
			
			while ( isOK ) {
				// Draw stuff
				if (! holder.getSurface().isValid() ) { continue ; }
				
				Canvas c = holder.lockCanvas() ;
				
				
				c.drawARGB(255, 150, 150, 10) ;
				c.drawBitmap(ball, x - (ball.getWidth() / 2) , y - (ball.getHeight()/2) , null) ;
				c.drawPoint(x+150, y+150, p);
				
//				Makes it move
//				x += xStep ;
//				if ( x >= (v.getWidth() - ball.getWidth() )|| x <= 0 )
//				{
//					xStep = -xStep ;
//				}
//				
				
				holder.unlockCanvasAndPost(c) ;
			}
		}
		
		public void pause() {
			isOK = false ;
			for(;;) {
				try { t.join() ; }
				catch ( InterruptedException e ) {
					//TODO real error handling
					e.printStackTrace() ;
				}
				break ;
			}
			t = null ;
		}
		
		public void resume() {
			isOK = true ;
			t = new Thread(this) ;
			t.start();
		}
		
	}






	
	
}