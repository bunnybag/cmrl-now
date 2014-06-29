/**
 * 
 */
package com.bunnybags.cmrlnow;

import java.util.List;

import android.location.Location;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

/**
 * @author SURESH
 *
 */
public class Search_Nearby_Station_Task extends AsyncTask<Void, Void, List<Nearby_Search_Result>>
{

	private Nearby_Station_Search Nearest_Search_Activity;
	private Location current_location;

	public Search_Nearby_Station_Task(Nearby_Station_Search Nearest_Search_Activity, Location current_location) 
	{
		this.setNearest_Search_Activity(Nearest_Search_Activity);
		this.setCurrent_location(current_location);
	}

	@Override
	protected List<Nearby_Search_Result> doInBackground(Void... params) 
	{
		ListView Nearby_Staion_Result_List = (ListView) this.Nearest_Search_Activity.findViewById(R.id.Nearby_Station_Search_Summary_ListView);
		List<Nearby_Search_Result> nearby_search_result_list = this.Nearest_Search_Activity.Get_Nearest_Station_List_To_Location(this.current_location);
		
		this.Nearest_Search_Activity.setNearby_search_result_list(nearby_search_result_list);
		/*Nearby_Search_List_Adapter nearby_station_summary_adapter = new Nearby_Search_List_Adapter(this.Nearest_Search_Activity, R.layout.nearest_station_search_list_item_layout, nearby_search_result_list);
		
		nearby_station_summary_adapter.setDropDownViewResource(R.layout.nearest_station_search_list_item_layout);
		Nearby_Staion_Result_List.setAdapter(nearby_station_summary_adapter);
		
		ProgressBar Load_Nearest_Station_List_Progress = (ProgressBar) this.Nearest_Search_Activity.findViewById(R.id.Nearest_Station_List_Progress_Bar); 
		Load_Nearest_Station_List_Progress.setVisibility(View.GONE);
		
		Nearby_Staion_Result_List.setVisibility(View.VISIBLE);
*/
		return nearby_search_result_list;
	}

	/**
	 * @return the nearest_Search_Activity
	 */
	public Nearby_Station_Search getNearest_Search_Activity() {
		return Nearest_Search_Activity;
	}

	/**
	 * @param nearest_Search_Activity the nearest_Search_Activity to set
	 */
	public void setNearest_Search_Activity(Nearby_Station_Search nearest_Search_Activity) {
		Nearest_Search_Activity = nearest_Search_Activity;
	}

	/**
	 * @return the current_location
	 */
	public Location getCurrent_location() {
		return current_location;
	}

	/**
	 * @param current_location the current_location to set
	 */
	public void setCurrent_location(Location current_location) {
		this.current_location = current_location;
	}

}
