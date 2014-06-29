/**
 * 
 */
package com.bunnybags.cmrlnow;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * @author SURESH
 *
 */
public class Nearby_Search_List_Adapter extends ArrayAdapter<Nearby_Search_Result> {

	private Nearby_Station_Search activity;
	private int Resource_Id;
	private List<Nearby_Search_Result> Nearby_Search_List_Items;
	/**
	 * 
	 */
	/*public Nearby_Search_List_Adapter() {
		// TODO Auto-generated constructor stub
	}*/
	
	
	public Nearby_Search_List_Adapter(Nearby_Station_Search activity, int resource,
			List<Nearby_Search_Result> objects) {
		super(activity, resource, objects);
		this.activity = activity;
		this.Resource_Id = resource;
		this.Nearby_Search_List_Items = new ArrayList<Nearby_Search_Result>();
		if(objects != null)
		{
			Nearby_Search_List_Items.addAll(objects);
		}
	}

	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		View row = convertView;
		
		if (row == null) 
		{
			LayoutInflater inflator = ((Activity) this.activity)
					.getLayoutInflater();
			row = inflator.inflate(Resource_Id, parent, false);
		}

		Nearby_Search_Result nearby_search_current_item = (Nearby_Search_Result) this.Nearby_Search_List_Items.get(position);
		
		
		ImageButton station_details_button_view = (ImageButton) row.findViewById(R.id.Nearest_Station_Details_Button);
		TextView Station_Name = (TextView) row.findViewById(R.id.Nearest_Station_Name_Text);
		TextView Station_Direction_Summary = (TextView) row.findViewById(R.id.Nearest_Station_Direction_Summary_Text);
		
		RelativeLayout Items_Layout = (RelativeLayout) row.findViewById(R.id.Nearest_Station_Search_List_Item_Rel_Layout);
		
		Station_Name.setText(nearby_search_current_item.getStation_Name());
		Station_Direction_Summary.setText(nearby_search_current_item.getDirection_Summary());
		
		TextView Distance_from_here = (TextView) row.findViewById(R.id.Nearest_Station_Distance_Text);
		Distance_from_here.setText(Float.toString(nearby_search_current_item.getDistance_from_current()) + "km" );
		
		
		Items_Layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ListView Nearby_Staion_Result_List = (ListView) activity.findViewById(R.id.Nearby_Station_Search_Summary_ListView);
				
				Polyline Direction_Polyline = activity.getNearest_Station_Polyline();
				if(Direction_Polyline != null)
				{
					Direction_Polyline.remove();
				}
				
				List<Marker> Nearest_Station_Marker_List = activity.Nearest_Station_Marker;
				
				if(Nearest_Station_Marker_List != null)
				{
					if(Nearest_Station_Marker_List.size() > 0)
					{
						Nearest_Station_Marker_List.get(0).remove();
					}
					activity.Nearest_Station_Marker.clear();
				}
				
				
				
				TextView Station_Name = (TextView) v.findViewById(R.id.Nearest_Station_Name_Text);
				String station_name = Station_Name.getText().toString();
				
				Nearby_Search_Result nearby_station = getItembyStationName(Nearby_Search_List_Items,station_name);
				
				DBHelper db_helper = new DBHelper(activity.getApplicationContext());
				
				Location station_location = db_helper.get_Station_Location_by_Station_Name(station_name);
				Marker nearest_station_marker = activity.googleMap.addMarker(new MarkerOptions().position(new LatLng(station_location.getLatitude(), station_location.getLongitude())).title(station_name));
				activity.Nearest_Station_Marker.add(nearest_station_marker);
				PolylineOptions Route_Current_Nearest = new PolylineOptions(); 
				Route_Current_Nearest.addAll(nearby_station.getDirection_Point());
				Route_Current_Nearest.width(8);
				Route_Current_Nearest.color(Color.BLUE);
				activity.setNearest_Station_Polyline(activity.googleMap.addPolyline(Route_Current_Nearest));
				
				TabHost tabHost = (TabHost) activity.findViewById(android.R.id.tabhost);
				tabHost.setCurrentTabByTag("Map_View");
				
			}

			private Nearby_Search_Result getItembyStationName(
					List<Nearby_Search_Result> nearby_Search_List_Items,
					String station_name) 
			{
				Nearby_Search_Result nearby_found_item = null;
				for (Nearby_Search_Result nearby_Search_Result : nearby_Search_List_Items) {
					if(station_name.equalsIgnoreCase(nearby_Search_Result.getStation_Name()))
					{
						nearby_found_item = nearby_Search_Result;
						break;
					}
				}
				return nearby_found_item;
			}
		});
		
		station_details_button_view.setOnClickListener(new Nearby_Result_Clicker(this.activity,nearby_search_current_item.getStation_Name()));
		
		return row;
		//return super.getView(position, convertView, parent);
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		
		if (row == null) 
		{
			LayoutInflater inflator = ((Activity) this.activity)
					.getLayoutInflater();
			row = inflator.inflate(Resource_Id, parent, false);
		}

		Nearby_Search_Result nearby_search_current_item = (Nearby_Search_Result) this.Nearby_Search_List_Items.get(position);
		
		
		
		ImageButton station_details_button_view = (ImageButton) row.findViewById(R.id.Nearest_Station_Details_Button);
		TextView Station_Name = (TextView) row.findViewById(R.id.Nearest_Station_Name_Text);
		TextView Station_Direction_Summary = (TextView) row.findViewById(R.id.Nearest_Station_Direction_Summary_Text);
		
/*		Spinner Nearby_Station_Spinner = (Spinner)  parent.findViewById(R.id.Nearby_Station_Search_Summary_Spinner);
		
		Nearby_Station_Spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});		*/
		
		
		
		Station_Name.setText(nearby_search_current_item.getStation_Name());
		Station_Direction_Summary.setText(nearby_search_current_item.getDirection_Summary());
		
		TextView Distance_from_here = (TextView) row.findViewById(R.id.Nearest_Station_Distance_Text);
		Distance_from_here.setText(Float.toString(nearby_search_current_item.getDistance_from_current()) + "km" );
		
		//station_details_button_view.setOnClickListener(new Nearby_Result_Clicker(this.context,nearby_search_current_item.getStation_Name()));
		
		return row;
		//return super.getView(position, convertView, parent);
	}

}
