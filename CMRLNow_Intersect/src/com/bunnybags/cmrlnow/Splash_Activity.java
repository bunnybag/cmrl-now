package com.bunnybags.cmrlnow;

import java.io.IOException;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;


public class Splash_Activity extends Activity {

	private Drawable CMRL_Welcome_Drawable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		// Show the Up button in the action bar.
		
/*		this.requestWindowFeature(Window.FEATURE_NO_TITLE);



	    //Remove notification bar
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
/*		ImageView splash_image_view = (ImageView) findViewById(R.id.Splash_Image_View);
		
		try {
//			CMRL_Welcome_Drawable = Drawable.createFromStream(getAssets().open("splash.jpg"), null);
			CMRL_Welcome_Drawable = Drawable.createFromStream(getAssets().open("Splash.png"), null);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		splash_image_view.setImageDrawable(this.CMRL_Welcome_Drawable);
*/
		
		/*TextView splash_image_view = (TextView) findViewById(R.id.textView1);
		splash_image_view.setText("This is a test message");*/
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent start_main = new Intent(Splash_Activity.this, Select_Src_Dest_List.class);
				startActivity(start_main);
				
				finish();
			}
		}, 1000);
		
		DBHelper db = new DBHelper(getApplicationContext());
		try {
			db.createDatabase();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		db.openDatabase();
		db.closeDataBase();
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
