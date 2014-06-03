package com.bunnybags.cmrlnow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 
 */

/**
 * @author SURESH
 *
 */
public class Nearby_Result_Clicker extends Activity implements View.OnClickListener{

	private String Station_Name;
	private Context context;
	/**
	 * 
	 */
	

	public Nearby_Result_Clicker(Context parent_context, String station_Name) {
		// TODO Auto-generated constructor stub
		this.context = parent_context;
		this.Station_Name = station_Name;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		Bundle station_details_bundle = new Bundle();
		Intent send_station_info = new Intent(this.context,Station_Details_Activity.class );
		
		station_details_bundle.putString("Station_Name", this.Station_Name);
		send_station_info.putExtras(station_details_bundle);
		
		this.context.startActivity(send_station_info);

	}

}
