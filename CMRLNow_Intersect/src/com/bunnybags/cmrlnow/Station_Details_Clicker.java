/**
 * 
 */
package com.bunnybags.cmrlnow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author SURESH
 *
 */
public class Station_Details_Clicker implements OnClickListener {

	/**
	 * @param station_Name 
	 * @param context 
	 * 
	 */
	
	private Context parent_context;
	private String station_name;
	
	public Station_Details_Clicker(Context context, String station_Name) {
		this.parent_context = context;
		this.station_name = station_Name;
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View arg0) {
		Bundle station_details_bundle = new Bundle();
		station_details_bundle.putString("Station_Name", this.station_name);
		Intent send_station_info = new Intent(this.parent_context,Station_Details_Activity.class );
		
		//send_route_info.putExtra("Route_Details_Object", this.route_search_result);
		send_station_info.putExtras(station_details_bundle);
		
		this.parent_context.startActivity(send_station_info);
		return;
	}

}
