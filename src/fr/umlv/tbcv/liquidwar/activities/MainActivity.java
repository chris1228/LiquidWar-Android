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
