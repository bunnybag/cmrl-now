package com.bunnybags.cmrlnow;


import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.*;

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

public class Nearby_Station_Search extends Activity {

	private LocationManager location_manager;
	private LocationListener location_listener_gps;
	private LocationListener location_listener_network;
	GoogleMap googleMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nearby_station_search);
		
	
		final TextView Network_Provider_Location_Text_View = (TextView) findViewById(R.id.Network_Provider_Location_Text);
		final TextView GPS_Location_Text_View = (TextView) findViewById(R.id.GPS_Location_Text);
		final ListView Nearby_Staion_Result_List = (ListView) findViewById(R.id.Nearby_Station_Search_Summary_ListView);
		GoogleMap Nearby_Search_Google_Map = null;
		
		
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		this.location_manager = locationManager;
		
		
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
			//	Toast.makeText(getApplicationContext(),"Location received from NETWORK Provider",Toast.LENGTH_SHORT).show();
				Network_Provider_Location_Text_View.setText(arg0.toString());
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
			public void onLocationChanged(Location arg0) {
			//	Toast.makeText(getApplicationContext(),"Location received from GPS",Toast.LENGTH_SHORT).show();
				GPS_Location_Text_View.setText(arg0.toString());
			}
		};
		
		this.location_listener_network = location_listener;
		this.location_listener_gps = location_listener_gps;
		
		Location Last_Known_Location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if(Last_Known_Location != null)
		{
			Network_Provider_Location_Text_View.setText(Last_Known_Location.toString());
		}
		Last_Known_Location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(Last_Known_Location != null)
		{
			GPS_Location_Text_View.setText(Last_Known_Location.toString());
		}
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, location_listener );
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, location_listener_gps );
		
		
		
		
		
		List<String> station_list = new ArrayList<String>();
		
		station_list.clear();
		station_list.add("Station1");
		station_list.add("Station2");

		List<Nearby_Search_Result> neaby_search_result_list = new ArrayList<Nearby_Search_Result>();
		
		Nearby_Search_Result s1 = new Nearby_Search_Result();
		s1.setStation_Name("Station 1");
		s1.setDistance_from_current((float) 8);
		
		Nearby_Search_Result s11 = new Nearby_Search_Result();
		s11.setStation_Name("Station 2");
		s11.setDistance_from_current((float) 10);

		neaby_search_result_list.add(s1);
		neaby_search_result_list.add(s11);
		
		
		Nearby_Search_List_Adapter nearby_station_summary_adapter = new Nearby_Search_List_Adapter(this, R.layout.nearest_station_search_list_item_layout, neaby_search_result_list);
		
		nearby_station_summary_adapter.setDropDownViewResource(R.layout.nearest_station_search_list_item_layout);
		Nearby_Staion_Result_List.setAdapter(nearby_station_summary_adapter);

		try 
		{
		    if (Nearby_Search_Google_Map == null) 
		    {
            	Nearby_Search_Google_Map = ((MapFragment) getFragmentManager().findFragmentById(R.id.nearby_search_google_map_view)).getMap();
            }
		    Nearby_Search_Google_Map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		    Nearby_Search_Google_Map.setMyLocationEnabled(true);
		    Nearby_Search_Google_Map.setTrafficEnabled(true);
		    
		    //Marker TP = Nearby_Search_Google_Map.addMarker(new MarkerOptions().position(TutorialsPoint).title("TutorialsPoint"));

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	
		
		Toast.makeText(getApplicationContext(),"Map View shown",Toast.LENGTH_SHORT).show();
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

	
}
