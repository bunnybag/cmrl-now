package com.bunnybags.cmrlnow;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class Route_Details_List_Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.route_details_list);
		// Show the Up button in the action bar.
		
		
		Bundle route_details_bundle = getIntent().getExtras();
		
		
		Route_Search_Result route_search_result_from_parcel = route_details_bundle.getParcelable("Route_Stations_List");
		
		ListView Route_Stations_List = (ListView) findViewById(R.id.Route_Detail_Station_List_View);
		
		ArrayAdapter<String> route_detail_station_adapter = new ArrayAdapter<String>(this, 
			    android.R.layout.simple_list_item_1, route_search_result_from_parcel.getRoutes_Stations_List());  

		route_detail_station_adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
		
		//Route_Stations_List.setAdapter(route_detail_station_adapter);
		
		Route_Logo_Station_List_Adapter route_detail_route_logo_station_adapter = new Route_Logo_Station_List_Adapter(this, R.layout.route_stations_route_icon_name_list, route_search_result_from_parcel); 
		route_detail_route_logo_station_adapter.setDropDownViewResource(R.layout.route_stations_route_icon_name_list);
		Route_Stations_List.setAdapter(route_detail_route_logo_station_adapter);
		
		
		TextView Details_Time = (TextView) findViewById(R.id.Detail_Running_Time);
		TextView Details_Src_Text = (TextView) findViewById(R.id.Detail_Src_Text_View);
		TextView Details_Dest_Text = (TextView) findViewById(R.id.Detail_Dest_Text_View);
		TextView Details_Distance = (TextView) findViewById(R.id.Detail_Distance_Text);
		TextView Details_Fare = (TextView) findViewById(R.id.Detail_Fare_Text);
		TextView Details_Interchange = (TextView) findViewById(R.id.Detail_Interchange_Text);
		TextView Details_Stops = (TextView) findViewById(R.id.Detail_Stops_Text);
		
		Details_Src_Text.setText("From : " + route_search_result_from_parcel.getSource_Station());
		Details_Dest_Text.setText("To : " + route_search_result_from_parcel.getDestination_Station());
		
		Details_Interchange.setText(Integer.toString(route_search_result_from_parcel.getNumber_of_Interconnects()));
		Details_Stops.setText(Integer.toString(route_search_result_from_parcel.getNumber_of_Stops()));
		
		setupActionBar();
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
		getMenuInflater().inflate(R.menu.route__details__list_, menu);
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
