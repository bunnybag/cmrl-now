package com.bunnybags.cmrlnow;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class Station_Details_Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_station_details);
		
		setTitle("Station Details");
		
		Bundle station_info = getIntent().getExtras();

		//Extract the data
		String selected_station = station_info.getString("Station_Name");
		
		TextView Selected_Station = (TextView) findViewById(R.id.Selected_Station_Text);
		Selected_Station.setText(selected_station);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.station__details_, menu);
		return true;
	}

}
