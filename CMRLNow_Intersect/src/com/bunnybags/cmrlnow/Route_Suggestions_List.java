package com.bunnybags.cmrlnow;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Route_Suggestions_List extends Search_Route implements OnCheckedChangeListener{

	//private LinkedList<List<String>> Route_details1 = new LinkedList<List<String>>();
	private ArrayList<List<String>> Route_details = new ArrayList<List<String>>();
	
	private ArrayList<Station_List> Route_Station_List = new ArrayList<Station_List>();
	private List<String> Intersection_Stations = Arrays.asList("Alandur","Central Metro");
	
	//private List<LinkedList<List<String>>> = null;
	//private ArrayList<ArrayList<List<String>>> =	new ArrayList<ArrayList<List<String>>>();

	private List<Route_Search_Result> Route_Search_List;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_search_result_list);
		
		setTitle("Route Summary");
		
		Bundle route_info = getIntent().getExtras();

		//Extract the data
		String selected_source = route_info.getString(getString(R.string.Src_Station_Bundle_Key_Str));
		String selected_dest = route_info.getString(getString(R.string.Dest_Station_Bundle_Key_Str));
		
		
		DBHelper db = new DBHelper(this);
		/*try {
			db.createDatabase();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		db.openDatabase();
		
		
		
		TextView Selected_Src_Stations = (TextView) findViewById(R.id.Selected_Src_Stations_Text);
		TextView Selected_Dest_Stations = (TextView) findViewById(R.id.Selected_Dest_Stations_Text);

		final ListView Route_Efficient_Result_Summary_List = (ListView) findViewById(R.id.Route_Efficient_Result_Summary_List);
		
		ToggleButton Route_Result_Filter = (ToggleButton) findViewById(R.id.Result_Filter_Toggle);
		
		
		
		/*String Source_Route = db.getRouteNamebyStationName(selected_source);
		String Dest_Route = db.getRouteNamebyStationName(selected_dest);*/
		
		db.closeDataBase();
		
		Selected_Src_Stations.setText("From : " + selected_source);
		Selected_Dest_Stations.setText("To : " + selected_dest);
		
		
		this.Route_Search_List = Search_Route_by_Src_Dest(selected_source, selected_dest);
		
		int Suggestions_Count = this.Route_Search_List.size();
		
		Route_Search_Result Efficient_Search_Result = this.Route_Search_List.get(0);
		List<String> station_list = Efficient_Search_Result.getRoutes_Stations_List();
		
		//Interchange_count.setText(Double.toString(Efficient_Search_Result.getNumber_of_Interconnects()));
		
		ArrayAdapter<String> station_details_adapter = new ArrayAdapter<String>(this,  
			    android.R.layout.simple_list_item_1, station_list);  

		station_details_adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
		
		//Route_Details_List.setAdapter(station_details_adapter);		
		
		//ArrayAdapter<Route_Search_Result> route_summary_adapter = new ArrayAdapter<Route_Search_Result>(this,
			//	R.layout.route_result_summary, Route_Search_List);
		
		Route_Summary_List_Adapter route_eficient_summary_adapter = new Route_Summary_List_Adapter(this, R.layout.route_result_summary, this.Route_Search_List.subList(0, 1));
		
		route_eficient_summary_adapter.setDropDownViewResource(R.layout.route_result_summary);
		Route_Efficient_Result_Summary_List.setAdapter(route_eficient_summary_adapter);

		Route_Result_Filter.setOnCheckedChangeListener(this);
		
		Button Map_View_Button = (Button) findViewById(R.id.MapViewButton);
		
		Map_View_Button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent MapViewIntent = new Intent(getApplicationContext(),Route_Suggestion_Map.class);
				// TODO Auto-generated method stub
				startActivity(MapViewIntent);
				return;
			}
		});
		
		// Show the Up button in the action bar.
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
		getMenuInflater().inflate(R.menu.train__timings, menu);
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

	/**
	 * @return the route_details
	 */
	public ArrayList<List<String>> getRoute_details_List() {
		return Route_details;
	}

	/**
	 * @param route_details the route_details to set
	 */
	public void setRoute_details_List(ArrayList<List<String>> route_details) {
		Route_details = route_details;
	}


	/**
	 * @return the route_Search_List
	 */
	public List<Route_Search_Result> getRoute_Search_List() {
		return Route_Search_List;
	}


	/**
	 * @param route_Search_List the route_Search_List to set
	 */
	public void setRoute_Search_List(List<Route_Search_Result> route_Search_List) {
		Route_Search_List = route_Search_List;
	}


	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean is_show_all) {
		// TODO Auto-generated method stub

		ListView Route_Efficient_Result_Summary_List = (ListView) findViewById(R.id.Route_Efficient_Result_Summary_List);
		
		Route_Summary_List_Adapter filter_route_eficient_summary_adapter = new Route_Summary_List_Adapter(this, R.layout.route_result_summary);//, this.Route_Search_List.subList(0, this.Route_Search_List.size()));		
		filter_route_eficient_summary_adapter.setDropDownViewResource(R.layout.route_result_summary);
		
		List<Route_Search_Result> Filter_Route_Search_List = getRoute_Search_List();
		if(is_show_all)
		{
			filter_route_eficient_summary_adapter.setObjectList(Filter_Route_Search_List);   // .addAll(Filter_Route_Search_List);
			
		//	Route_Efficient_Result_Summary_List.setDivider(getResources().getDrawable(android.R.drawable.divider_horizontal_bright));
		}
		else
		{
			filter_route_eficient_summary_adapter.setObjectList(Filter_Route_Search_List.subList(0, 1));
		//	Route_Efficient_Result_Summary_List.setDivider(null);
		}
		Route_Efficient_Result_Summary_List.setAdapter(filter_route_eficient_summary_adapter);
		
		
		
		return;
	}
	

}
