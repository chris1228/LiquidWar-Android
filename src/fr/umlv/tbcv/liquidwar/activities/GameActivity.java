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