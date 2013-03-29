package fr.umlv.tbcv.liquidwar.activities;

import fr.umlv.tbcv.liquidwar.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button b = (Button) findViewById(R.id.testButton) ;
		Button bOpenGL = (Button) findViewById(R.id.testopenGLButton) ;
		
		b.setOnClickListener(this) ;
		bOpenGL.setOnClickListener(this);

		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
		switch ( v.getId() ) {
		case R.id.testButton:
			Intent startTest = new Intent(MainActivity.this, GameActivity.class);
			MainActivity.this.startActivity(startTest);
			break ;
			
		case R.id.testopenGLButton:
			Intent startOpenGLTest = new Intent(MainActivity.this, GameActivityOpenGL.class);
			
			MainActivity.this.startActivity(startOpenGLTest);
			break ;
		}
		
		
	}


}
