package com.bunnybags.cmrlnow;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.internal.IPolylineDelegate;

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
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView.CommaTokenizer;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

public class Nearby_Station_Search extends TabActivity implements LocationListener{

	private LocationManager location_manager;
	GoogleMap googleMap;
	public Context Application_Context;
	public boolean update_nearby_station;
	Marker current_position_marker;
	List<Marker> Nearest_Station_Marker;
	public Nearby_Station_Search nearby_activity = this;
	//private PolylineOptions Route_Current_Nearest;
	private List<Nearby_Search_Result> nearby_search_result_list;
	private Polyline Nearest_Station_Polyline;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nearby_station_search);
		
	
		GoogleMap Nearby_Search_Google_Map = null;
		Location Last_Known_Location;
		
		Application_Context = getApplicationContext();
		update_nearby_station = false;
		current_position_marker = null;
		Nearest_Station_Marker = new ArrayList<Marker>();


		ListView Nearby_Staion_Result_List = (ListView) findViewById(R.id.Nearby_Station_Search_Summary_ListView);
		
		Nearby_Staion_Result_List.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		
		TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
		TabSpec	spec = tabHost.newTabSpec("Station_List");
		spec.setIndicator("List");
		spec.setContent(R.id.Nearby_Stations_List_Tab);
		tabHost.addTab(spec);
		
		TabSpec	spec2 = tabHost.newTabSpec("Map_View");
		spec2.setIndicator("Map");
		spec2.setContent(R.id.Nearby_Stations_Map_Tab);
		tabHost.addTab(spec2);
		
		
				
		
		try 
		{
		    if (Nearby_Search_Google_Map == null) 
		    {
            	Nearby_Search_Google_Map = ((MapFragment) getFragmentManager().findFragmentById(R.id.nearby_search_google_map_view)).getMap();
            	googleMap = Nearby_Search_Google_Map;
            }
		    
		    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(13.0339542,80.2069541), 16));
		    
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
		
		boolean gps_provider_status = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		
				
		if(!gps_provider_status)
		{
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
 
			alertDialogBuilder.setTitle("Location Service");
			alertDialogBuilder.setCancelable(false);
			alertDialogBuilder.setMessage("Disabling GPS will give inaccurate location updates");
			alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
			
			alertDialogBuilder.setNegativeButton("Cancel", new OnClickListener() {
			
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
							arg0.cancel();
						}
					});
			
			alertDialogBuilder.setPositiveButton("Enable GPS", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
						
							Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
							nearby_activity.startActivity(callGPSSettingIntent);
							
							Toast.makeText(getApplicationContext(),"Settings done",Toast.LENGTH_SHORT).show();
							
						}
					});
			 
			AlertDialog alertdialog = alertDialogBuilder.create();
			alertdialog.setCanceledOnTouchOutside(false);
			alertdialog.show();
 
		}
		else
		{
			update_nearby_station  = true;
		}
		
		Last_Known_Location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if(Last_Known_Location != null)
		{

		}
		
		Last_Known_Location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(Last_Known_Location != null)
		{

		}
		
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
		
	}

	List<Nearby_Search_Result> Get_Nearest_Station_List_To_Location(
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
					
					CMRL_GMap_Direction md = new CMRL_GMap_Direction();

					Document doc = md.getDocument(new LatLng(test_location.getLatitude(),test_location.getLongitude()), new LatLng(station_location.getLatitude(),station_location.getLongitude()), CMRL_GMap_Direction.MODE_DRIVING);
					ArrayList<LatLng> directionPoint = md.getDirection(doc);
					station_distance = md.getDistanceValue(doc);
					String direction_summary = md.getDurationText(doc) +" via " +  md.getRouteSummary(doc);
					
					/*NodeList node1 = (NodeList) nodes.item(getNodeIndex(nodes, "route"));
					node1 = (NodeList) node1.item(getNodeIndex(nodes, "leg"));
			        NodeList nl2 = node1.getChildNodes();
			        Node node2 = nl2.item(getNodeIndex(nl2, "text"));*/
					
					//if(station_distance <= least_distance)
					{
						least_distance = station_distance;
						Nearby_Search_Result nearby_station = new Nearby_Search_Result();
						nearby_station.setDistance_from_current(station_distance/1000);
						nearby_station.setStation_Name(station_name);
						nearby_station.setRoute_id(current_route_id);
						nearby_station.setDirection_Point(directionPoint);
						nearby_station.setDirection_Summary(direction_summary);
						
						
						/*if(nearby_station_search_result_list.size()!=0)
						{
							if(current_route_id == nearby_station_search_result_list.get(nearby_station_search_result_list.size()-1).getRoute_id() || station_name.equalsIgnoreCase(nearby_station_search_result_list.get(nearby_station_search_result_list.size()-1).getStation_Name()))
							{
								nearby_station_search_result_list.remove(nearby_station_search_result_list.size()-1);
							}
						}*/
						
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

	private int getNodeIndex(NodeList nl, String string) {
		for(int i = 0 ; i < nl.getLength() ; i++) {
            if(nl.item(i).getNodeName().equals(string))
                return i;
        }
        return -1;
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
		
		this.location_manager.removeUpdates(this);
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

		if(!update_nearby_station)
		{
			return;
		}
		
		
		String station_name;
		DBHelper db_helper = new DBHelper(Application_Context);
		
		Toast.makeText(getApplicationContext(),"Location received from " + arg0.getProvider(),Toast.LENGTH_SHORT).show();
		
		
		//googleMap.clear();
		
		ListView Nearby_Staion_Result_List = (ListView) findViewById(R.id.Nearby_Station_Search_Summary_ListView);
		

		LatLng Current_Lat_Lng = new LatLng(arg0.getLatitude(), arg0.getLongitude());

		
		for (Marker nearest_station_marker : Nearest_Station_Marker) 
		{
			nearest_station_marker.remove();
		}
		
		Nearest_Station_Marker.clear();
		
		if(current_position_marker==null)
		{
			Marker current_location = googleMap.addMarker(new MarkerOptions().position(Current_Lat_Lng).title("Current Position"));
			current_position_marker = current_location;
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Current_Lat_Lng, 16));
		}
		else
		{
			current_position_marker.setPosition(Current_Lat_Lng);
		}
		
		
		Search_Nearby_Station_Task search_stations_async = new Search_Nearby_Station_Task(nearby_activity,arg0); 
		search_stations_async.execute();
		
		List<Nearby_Search_Result> nearby_search_result_list_from_task = null;
		
		try 
		{
			nearby_search_result_list_from_task = search_stations_async.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*nearby_search_result_list = Get_Nearest_Station_List_To_Location(arg0);
		
		this.setNearby_search_result_list(nearby_search_result_list);*/
		
		Nearby_Search_List_Adapter nearby_station_summary_adapter = new Nearby_Search_List_Adapter(this, R.layout.nearest_station_search_list_item_layout, nearby_search_result_list_from_task);
		
		nearby_station_summary_adapter.setDropDownViewResource(R.layout.nearest_station_search_list_item_layout);
		Nearby_Staion_Result_List.setAdapter(nearby_station_summary_adapter);
		
		ProgressBar Load_Nearest_Station_List_Progress = (ProgressBar) this.findViewById(R.id.Nearest_Station_List_Progress_Bar); 
		Load_Nearest_Station_List_Progress.setVisibility(View.GONE);
		
		Nearby_Staion_Result_List.setVisibility(View.VISIBLE);
		
		update_nearby_station = false;
		this.location_manager.removeUpdates(this);
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		
		if(arg0.equalsIgnoreCase("gps"))
		{
			Toast.makeText(getApplicationContext(),"GPS disabled. Enable for better location updates",Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onProviderEnabled(String arg0) {
		if(arg0.equalsIgnoreCase("gps"))
		{
			Toast.makeText(getApplicationContext(),"GPS enabled",Toast.LENGTH_SHORT).show();
			update_nearby_station  = true;
		}
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		Toast.makeText(getApplicationContext(),"Status changed",Toast.LENGTH_SHORT).show();
	}

	/**
	 * @return the nearest_Station_Polyline
	 */
	public Polyline getNearest_Station_Polyline() {
		return Nearest_Station_Polyline;
	}

	/**
	 * @param nearest_Station_Polyline the nearest_Station_Polyline to set
	 */
	public void setNearest_Station_Polyline(Polyline nearest_Station_Polyline) {
		Nearest_Station_Polyline = nearest_Station_Polyline;
	}

	/**
	 * @return the nearby_search_result_list
	 */
	public List<Nearby_Search_Result> getNearby_search_result_list() {
		return nearby_search_result_list;
	}

	/**
	 * @param nearby_search_result_list the nearby_search_result_list to set
	 */
	public void setNearby_search_result_list(
			List<Nearby_Search_Result> nearby_search_result_list) {
		this.nearby_search_result_list = nearby_search_result_list;
	}




	
}
