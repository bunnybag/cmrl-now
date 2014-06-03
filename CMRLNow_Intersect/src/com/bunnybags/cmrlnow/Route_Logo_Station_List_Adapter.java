/**
 * 
 */
package com.bunnybags.cmrlnow;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author SURESH
 *
 */
public class Route_Logo_Station_List_Adapter extends
		ArrayAdapter<String> {

			
	private Context context;
	private int Resource_Id;
	private Route_Search_Result Route_Summary_Object;
	private int Interconnect_Position;
	private List<String> Station_Name_List;
	private boolean is_Summary;

	
	public Route_Logo_Station_List_Adapter(Context context, int resource,Route_Search_Result object) {
		super(context, resource);
		this.context = context;
		this.Resource_Id = resource;
		this.Route_Summary_Object = object;
		this.Interconnect_Position = -1;
		this.Station_Name_List = new ArrayList<String>();
		this.Station_Name_List.addAll(this.Route_Summary_Object.getRoutes_Stations_List());
		this.is_Summary = false;
		super.addAll(this.Route_Summary_Object.getRoutes_Stations_List());
	}
	
	public Route_Logo_Station_List_Adapter(Context context, int resource,Route_Search_Result object,List<String>Station_Summary_List) {
		super(context, resource);
		this.context = context;
		this.Resource_Id = resource;
		this.Route_Summary_Object = object;
		this.Interconnect_Position = -1;
		this.Station_Name_List = new ArrayList<String>();
		this.Station_Name_List.addAll(Station_Summary_List);
		this.is_Summary = true;
		super.addAll(this.Station_Name_List);
	}

	
	
	/**
	 * @param context
	 * @param resource
	 */
	public Route_Logo_Station_List_Adapter(Context context, int resource) {
		super(context, resource);
		this.context = context;
		this.Resource_Id = resource;
		this.Interconnect_Position = -1;
		this.Station_Name_List = new ArrayList<String>();
	}

/*	*//**
	 * @param context
	 * @param resource
	 * @param textViewResourceId
	 *//*
	public Route_Logo_Station_List_Adapter(Context context, int resource,
			int textViewResourceId) {
		super(context, resource, textViewResourceId);
		this.context = context;
		this.Resource_Id = resource;
	}

	*//**
	 * @param context
	 * @param resource
	 * @param objects
	 *//*
	public Route_Logo_Station_List_Adapter(Context context, int resource,
			Route_Search_Result[] objects) {
		super(context, resource, objects);
		this.context = context;
		this.Resource_Id = resource;
	}

	*//**
	 * @param context
	 * @param resource
	 * @param objects
	 *//*
	public Route_Logo_Station_List_Adapter(Context context, int resource,
			List<Route_Search_Result> objects) {
		super(context, resource, objects);
		this.context = context;
		this.Resource_Id = resource;
	}

	*//**
	 * @param context
	 * @param resource
	 * @param textViewResourceId
	 * @param objects
	 *//*
	public Route_Logo_Station_List_Adapter(Context context, int resource,
			int textViewResourceId, Route_Search_Result[] objects) {
		super(context, resource, textViewResourceId, objects);
		this.context = context;
		this.Resource_Id = resource;
	}

	*//**
	 * @param context
	 * @param resource
	 * @param textViewResourceId
	 * @param objects
	 *//*
	public Route_Logo_Station_List_Adapter(Context context, int resource,
			int textViewResourceId, List<Route_Search_Result> objects) {
		super(context, resource, textViewResourceId, objects);
		this.context = context;
		this.Resource_Id = resource;
	}
*/
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		View row = convertView;
		
		if (row == null) 
		{
			LayoutInflater inflator = ((Activity) this.context)
					.getLayoutInflater();
			row = inflator.inflate(Resource_Id, parent, false);
		}
		
		TextView station_name_text_view = (TextView) row.findViewById(R.id.route_station_name_text_view);
		
		ImageView route_image1 = (ImageView) row.findViewById(R.id.route_image1);
		ImageView route_image2 = (ImageView) row.findViewById(R.id.route_image2);
		ImageView route_image3 = (ImageView) row.findViewById(R.id.route_image3);
		
		
		//String Station_Name = this.Route_Summary_Object.getRoutes_Stations_List().get(position);
		String Station_Name = this.Station_Name_List.get(position);
		route_image1.setImageResource(0);
		route_image2.setImageResource(0);
		route_image3.setImageResource(0);
		
		if(!this.is_Summary)
		{
			route_image1.setVisibility(View.GONE);
			route_image2.setVisibility(View.GONE);
			route_image3.setVisibility(View.GONE);
		}
		
		
		List<String> Route_List = this.Route_Summary_Object.getRoute_List();
		
		int Src_Drawable_Id;
		int Dest_Drawable_Id;
		
		if(Route_List.get(0).equalsIgnoreCase("CMRL Corridor 1"))
		{
			Src_Drawable_Id = Route_Name_Icon_Id.Corridor_1.get_route_drwable_id();
		}
		else
		{
			Src_Drawable_Id = Route_Name_Icon_Id.Corridor_2.get_route_drwable_id();
		}
		
		if(Route_List.get(1).equalsIgnoreCase("CMRL Corridor 1"))
		{
			Dest_Drawable_Id = Route_Name_Icon_Id.Corridor_1.get_route_drwable_id();
		}
		else
		{
			Dest_Drawable_Id = Route_Name_Icon_Id.Corridor_2.get_route_drwable_id();
		}
	
		
		if(this.Interconnect_Position < 0)
		{
			if(Station_Name.equalsIgnoreCase(this.Route_Summary_Object.getRoute_Via()))
			{
				this.Interconnect_Position = position;
			}
			else
			{
				route_image3.setVisibility(View.VISIBLE);
				route_image3.setImageResource(Src_Drawable_Id);
			}
		}
		
		if(this.Interconnect_Position >= 0)
		{
			if(position < this.Interconnect_Position)
			{
				route_image3.setVisibility(View.VISIBLE);
				route_image3.setImageResource(Src_Drawable_Id);
			}
			else if(position > this.Interconnect_Position)
			{
				route_image3.setVisibility(View.VISIBLE);
				route_image3.setImageResource(Dest_Drawable_Id);
			}
			else if(position == this.Interconnect_Position)
			{
				route_image1.setVisibility(View.VISIBLE);
				route_image2.setVisibility(View.VISIBLE);
				route_image3.setVisibility(View.VISIBLE);
				
				route_image1.setImageResource(Src_Drawable_Id);
				route_image2.setImageResource(R.drawable.transition_logo);
				route_image3.setImageResource(Dest_Drawable_Id);
			}
				
		}
		
		if(position == 0)
		{
			route_image1.setVisibility(View.VISIBLE);
			route_image3.setVisibility(View.VISIBLE);

			route_image1.setImageResource(R.drawable.flag_green);
			route_image3.setImageResource(Src_Drawable_Id);
		}
		else if(position == this.Station_Name_List.size()-1)
		{
			route_image1.setVisibility(View.VISIBLE);
			route_image3.setVisibility(View.VISIBLE);

			route_image1.setImageResource(Dest_Drawable_Id);
			route_image3.setImageResource(R.drawable.flag_red);
		}
		
		station_name_text_view.setText(Station_Name);
		station_name_text_view.setOnClickListener(new Station_Details_Clicker(this.context,Station_Name));
		
		return row;
		//return super.getView(position, convertView, parent);
	}
}
