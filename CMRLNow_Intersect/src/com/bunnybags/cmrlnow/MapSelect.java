package com.bunnybags.cmrlnow;

import android.app.Activity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.Menu;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.view.MotionEvent;

public class MapSelect extends Activity {

	private ImageView img;
	private TextView txt;
	private Matrix matrix = new Matrix();
	private float scale = 1f;
	private ScaleGestureDetector SGD;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_select);
		img = (ImageView)findViewById(R.id.line_map);
		txt = (TextView)findViewById(R.id.test_text);
	
		
		img.setScaleType(ScaleType.MATRIX);
		SGD = new ScaleGestureDetector(this,new ScaleListener());
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		SGD.onTouchEvent(ev);
		return true;
	}

	private class ScaleListener extends ScaleGestureDetector.
	SimpleOnScaleGestureListener {
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			scale *= detector.getScaleFactor();
			scale = Math.max(0.1f, Math.min(scale, 5.0f));
			txt.setText("kjkjn");
			matrix.setScale(scale, scale);
			img.setImageMatrix(matrix);
			return true;
		}
	}


	   @Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	      // Inflate the menu; this adds items to the action bar if it is present.
	      getMenuInflater().inflate(R.menu.station__details_, menu);
	      return true;
	   }
}
