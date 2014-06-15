package com.bunnybags.cmrlnow;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.maps.MapActivity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView.CommaTokenizer;
import android.widget.TextView;
import android.widget.Toast;

public class Nearby_Station_Search extends Activity implements LocationListener{

	private LocationManager location_manager;
	private LocationListener location_listener_gps;
	private LocationListener location_listener_network;
	GoogleMap googleMap;
	public Context Application_Context;
	public boolean update_nearby_station;
	Marker current_position_marker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nearby_station_search);
		
	
		final TextView Network_Provider_Location_Text_View = (TextView) findViewById(R.id.Network_Provider_Location_Text);
		final TextView GPS_Location_Text_View = (TextView) findViewById(R.id.GPS_Location_Text);
		final ListView Nearby_Staion_Result_List = (ListView) findViewById(R.id.Nearby_Station_Search_Summary_ListView);
		GoogleMap Nearby_Search_Google_Map = null;
		Location Last_Known_Location;
		
		Application_Context = getApplicationContext();
		update_nearby_station = true;
		current_position_marker = null;
		
		
		
		try 
		{
		    if (Nearby_Search_Google_Map == null) 
		    {
            	Nearby_Search_Google_Map = ((MapFragment) getFragmentManager().findFragmentById(R.id.nearby_search_google_map_view)).getMap();
            	googleMap = Nearby_Search_Google_Map;
            }
		    Nearby_Search_Google_Map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		    Nearby_Search_Google_Map.setMyLocationEnabled(true);
		    Nearby_Search_Google_Map.setTrafficEnabled(true);
		    
		    

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		
		
		
		
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		this.location_manager = locationManager;
		
/*		
		LocationListener location_listener = new LocationListener() {
			
			@Override
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderDisabled(String arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLocationChanged(Location arg0) {
				Toast.makeText(getApplicationContext(),"Location received from NETWORK Provider",Toast.LENGTH_SHORT).show();
				Network_Provider_Location_Text_View.setText(arg0.toString());
				
				if(update_nearby_station)
				{
					List<Nearby_Search_Result> neaby_search_result_list;
					
					neaby_search_result_list = Get_Nearest_Station_List_To_Location(arg0);
					Nearby_Search_List_Adapter nearby_station_summary_adapter = new Nearby_Search_List_Adapter(Application_Context, R.layout.nearest_station_search_list_item_layout, neaby_search_result_list);
					
					nearby_station_summary_adapter.setDropDownViewResource(R.layout.nearest_station_search_list_item_layout);
					Nearby_Staion_Result_List.setAdapter(nearby_station_summary_adapter);
	
					update_nearby_station = false;
				}
				
				
				//if(update_nearby_station)
				{

//					ListView Nearby_Staion_Result_List_View = (ListView) findViewById(R.id.Nearby_Station_Search_Summary_ListView);
					
//					List<Nearby_Search_Result> neaby_search_result_list;
//					
//					neaby_search_result_list = Get_Nearest_Station_List_To_Location(arg0);
//					Nearby_Search_List_Adapter nearby_station_summary_adapter = new Nearby_Search_List_Adapter(Application_Context, R.layout.nearest_station_search_list_item_layout, neaby_search_result_list);
//					nearby_station_summary_adapter.setDropDownViewResource(R.layout.nearest_station_search_list_item_layout);
//					Nearby_Staion_Result_List_View.setAdapter(nearby_station_summary_adapter);
					
//					Toast.makeText(getApplicationContext(),"List View updated",Toast.LENGTH_SHORT).show();
					
					//Nearby_Staion_Result_List_View.setAdapter(nearby_station_summary_adapter);
	
	//				update_nearby_station = false;
				}
	
			}
		};
		
		LocationListener location_listener_gps = new LocationListener() {
			
			@Override
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderDisabled(String arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLocationChanged(Location arg0) 
			{

				Toast.makeText(getApplicationContext(),"Location received from GPS",Toast.LENGTH_SHORT).show();
				GPS_Location_Text_View.setText(arg0.toString());
				
				
				
				if(update_nearby_station)
				{

					ListView Nearby_Staion_Result_List_View = (ListView) findViewById(R.id.Nearby_Station_Search_Summary_ListView);
					
					List<Nearby_Search_Result> neaby_search_result_list;
					
					neaby_search_result_list = Get_Nearest_Station_List_To_Location(arg0);
					//Nearby_Search_List_Adapter nearby_station_summary_adapter = new Nearby_Search_List_Adapter(Application_Context, R.layout.nearest_station_search_list_item_layout, neaby_search_result_list);
					//nearby_station_summary_adapter.setDropDownViewResource(R.layout.nearest_station_search_list_item_layout);
					//Nearby_Staion_Result_List_View.setAdapter(nearby_station_summary_adapter);
					
					nearby_station_summary_adapter.clear();
					nearby_station_summary_adapter.addAll(neaby_search_result_list);
					
					nearby_station_summary_adapter.notifyDataSetChanged();
					
					Nearby_Staion_Result_List_View.setAdapter(nearby_station_summary_adapter);
	
					update_nearby_station = false;
				}
	
				
				LatLng Current_Lat_Lng = new LatLng(arg0.getLatitude(), arg0.getLongitude());
				Marker current_location = googleMap.addMarker(new MarkerOptions().position(Current_Lat_Lng).title("Current Position"));
				
			}
		};
		
		this.location_listener_network = location_listener;
		this.location_listener_gps = location_listener_gps;*/
		
		Last_Known_Location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if(Last_Known_Location != null)
		{
			Network_Provider_Location_Text_View.setText(Last_Known_Location.toString());
		}
		
		Last_Known_Location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(Last_Known_Location != null)
		{
			GPS_Location_Text_View.setText(Last_Known_Location.toString());
		}
		
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
	
	}

	private List<Nearby_Search_Result> Get_Nearest_Station_List_To_Location(
			Location test_location) {

		Float least_distance = Float.valueOf(Float.MAX_VALUE);
		float station_distance;
		int current_route_id;
		
		List<Nearby_Search_Result> nearby_station_search_result_list = new ArrayList<Nearby_Search_Result>();
		
		
		DBHelper db_helper = new DBHelper(Application_Context);
		List<String> Route_List = db_helper.getRouteList();
		
		for (String route : Route_List) 
		{
			current_route_id = db_helper.getRouteIdbyRouteName(route);
			List<String> Station_List_Route = db_helper.get_Station_List(current_route_id);
			
			least_distance = Float.MAX_VALUE;
			
			for (String station_name : Station_List_Route) 
			{
				Location station_location = db_helper.get_Station_Location_by_Station_Name(station_name);
				
				if(station_location!=null)
				{
					station_distance = test_location.distanceTo(station_location);
					
					if(station_distance <= least_distance)
					{
						least_distance = station_distance;
						Nearby_Search_Result nearby_station = new Nearby_Search_Result();
						nearby_station.setDistance_from_current(station_distance/1000);
						nearby_station.setStation_Name(station_name);
						nearby_station.setRoute_id(current_route_id);
						
						
						if(nearby_station_search_result_list.size()!=0)
						{
							if(current_route_id == nearby_station_search_result_list.get(nearby_station_search_result_list.size()-1).getRoute_id() || station_name.equalsIgnoreCase(nearby_station_search_result_list.get(nearby_station_search_result_list.size()-1).getStation_Name()))
							{
								nearby_station_search_result_list.remove(nearby_station_search_result_list.size()-1);
							}
						}
						
						if(!nearby_station_search_result_list.contains(nearby_station))
						{
							nearby_station_search_result_list.add(nearby_station);
						}
						
					}
				}
			}
			
		}
		
		Collections.sort(nearby_station_search_result_list);
		return nearby_station_search_result_list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nearby__station__search, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(),"Back",Toast.LENGTH_SHORT).show();
		
		this.location_manager.removeUpdates(location_listener_network);
		this.location_manager.removeUpdates(location_listener_gps);
		super.onBackPressed();
	}

	@Override
	public void onAttachedToWindow() {
		// TODO Auto-generated method stub
 		super.onAttachedToWindow();
		Window window = this.getWindow();
//		window.setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
//		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_HOME)
		{
			Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

		Toast.makeText(getApplicationContext(),"Location received from " + arg0.getProvider(),Toast.LENGTH_SHORT).show();
		
		ListView Nearby_Staion_Result_List = (ListView) findViewById(R.id.Nearby_Station_Search_Summary_ListView);
		List<Nearby_Search_Result> neaby_search_result_list;
		neaby_search_result_list = Get_Nearest_Station_List_To_Location(arg0);
		
		Nearby_Search_List_Adapter nearby_station_summary_adapter = new Nearby_Search_List_Adapter(this, R.layout.nearest_station_search_list_item_layout, neaby_search_result_list);
		
		nearby_station_summary_adapter.setDropDownViewResource(R.layout.nearest_station_search_list_item_layout);
		Nearby_Staion_Result_List.setAdapter(nearby_station_summary_adapter);

		LatLng Current_Lat_Lng = new LatLng(arg0.getLatitude(), arg0.getLongitude());

		if(current_position_marker==null)
		{
			Marker current_location = googleMap.addMarker(new MarkerOptions().position(Current_Lat_Lng).title("Current Position"));
			current_position_marker = current_location;
		}
		else
		{
			current_position_marker.setPosition(Current_Lat_Lng);
		}
		
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

	
}
