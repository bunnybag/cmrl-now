package com.bunnybags.cmrlnow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SlidingDrawer;

/**
 * 
 */

/**
 * @author SURESH
 *
 */
public class Nearby_Result_Clicker extends Activity implements View.OnClickListener{

	private String Station_Name;
	private Nearby_Station_Search activity;
	/**
	 * 
	 */
	

	public Nearby_Result_Clicker(Nearby_Station_Search parent_activity, String station_Name) {
		// TODO Auto-generated constructor stub
		this.activity = parent_activity;
		this.Station_Name = station_Name;
	}

	@Override
	public void onClick(View arg0) {
		
		
		Bundle station_details_bundle = new Bundle();
		Intent send_station_info = new Intent(this.activity,Station_Details_Activity.class );
		
		station_details_bundle.putString("Station_Name", this.Station_Name);
		send_station_info.putExtras(station_details_bundle);
		
		this.activity.startActivity(send_station_info);
		

	}

}
