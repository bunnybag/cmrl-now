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
import android.widget.Toast;

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
		List<Nearby_Search_Result> nearby_search_result_list = this.Nearest_Search_Activity.Get_Nearest_Station_List_To_Location(this.current_location);
		
		this.Nearest_Search_Activity.setNearby_search_result_list(nearby_search_result_list);
		
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
