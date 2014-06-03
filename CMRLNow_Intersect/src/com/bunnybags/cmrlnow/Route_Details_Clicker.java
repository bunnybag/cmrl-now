/**
 * 
 */
package com.bunnybags.cmrlnow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;



/**
 * @author SURESH
 *
 */
public class Route_Details_Clicker extends Activity implements View.OnClickListener  {

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	
	private int position;
	private Route_Search_Result route_search_result;
	private Context parent_context;
	
	public Route_Details_Clicker(Context context, int selected_position, Route_Search_Result current_search_result)
	{
		this.position = selected_position;
		this.route_search_result = current_search_result;
		this.parent_context = context;
	}
	
	@Override	
	public void onClick(View arg0) {

		Bundle route_details_bundle = new Bundle();
		route_details_bundle.putParcelable("Route_Stations_List", this.route_search_result);
		Intent send_route_info = new Intent(this.parent_context,Route_Details_List_Activity.class );
		
		//send_route_info.putExtra("Route_Details_Object", this.route_search_result);
		send_route_info.putExtras(route_details_bundle);
		
		this.parent_context.startActivity(send_route_info);
		return;
	}

}
