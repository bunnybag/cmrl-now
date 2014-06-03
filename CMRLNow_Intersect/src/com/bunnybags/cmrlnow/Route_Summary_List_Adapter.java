/**
 * 
 */
package com.bunnybags.cmrlnow;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author SURESH
 *
 */
public class Route_Summary_List_Adapter extends ArrayAdapter<Route_Search_Result> {

	private Context context;
	private int Resource_Id;
	private List<Route_Search_Result> Route_Summary_Objects;
	
	
	public Route_Summary_List_Adapter(Context context, int resource,
			List<Route_Search_Result> objects) {
		super(context, resource, objects);
		this.context = context;
		this.Resource_Id = resource;
		//Collections.copy(this.Route_Summary_Objects, objects);
		//this.Route_Summary_Objects.clear();
		//this.Route_Summary_Objects.addAll(objects);
		this.Route_Summary_Objects = new ArrayList<Route_Search_Result>();
		Route_Summary_Objects.addAll(objects);
	}

	public Route_Summary_List_Adapter(Context context,int resource) 
	{
		super(context, resource);
		this.context = context;
		this.Resource_Id = resource;
		this.Route_Summary_Objects = new ArrayList<Route_Search_Result>();
	}

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
		
		Route_Search_Result current_search_result = this.Route_Summary_Objects.get(position);
		
		//Button route_details_button_view = (Button) row.findViewById(R.id.Route_Detail_Button);
		TextView distance_text_view = (TextView) row.findViewById(R.id.Distance_Text);
		TextView fare_text_view = (TextView) row.findViewById(R.id.Fare_Text);
		TextView interchange_text_view = (TextView) row.findViewById(R.id.Interchange_Text);
		TextView stops_text_view = (TextView) row.findViewById(R.id.Stops_Text);
		ListView route_summary_list_view = (ListView)row.findViewById(R.id.Route_Summary_List);
		
		
		/*route_details_button_view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				return;
			}
		});*/
		

		
		LinearLayout route_summary_parameter_linear_layout = (LinearLayout)row.findViewById(R.id.Route_Summary_Parameter_Linear_Layout);
		route_summary_parameter_linear_layout.setOnClickListener(new Route_Details_Clicker(this.context,position,current_search_result));
		
		//route_summary_parameter_linear_layout.setBackgroundColor(row.getResources().getColor(android.R.color.holo_blue_light));
		
		//distance_text_view.setText(Double.toString(current_search_result.getDistance_in_kms()));
		distance_text_view.setText("kms");
		//fare_text_view.setText(Double.toString(current_search_result.getFare()));
		fare_text_view.setText("Rs.");
		interchange_text_view.setText(Integer.toString(current_search_result.getNumber_of_Interconnects()));
		stops_text_view.setText(Integer.toString(current_search_result.getNumber_of_Stops()));

		
		List<String> route_summary = new ArrayList<String>();
		
		route_summary.add(current_search_result.getSource_Station());
		if(current_search_result.getRoute_Via() != null)
		{
			route_summary.add(current_search_result.getRoute_Via());
		}
		route_summary.add(current_search_result.getDestination_Station());
		
		
		ArrayAdapter<String> route_summary_adapter = new ArrayAdapter<String>(getContext(),  
			    android.R.layout.simple_list_item_1, route_summary);  

		route_summary_adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
		
		//route_summary_list_view.setAdapter(route_summary_adapter);
		
		
		Route_Logo_Station_List_Adapter route_summary_logo_adapter = new Route_Logo_Station_List_Adapter(getContext(), R.layout.route_stations_route_icon_name_list, current_search_result, route_summary);
		route_summary_logo_adapter.setDropDownViewResource(R.layout.route_stations_route_icon_name_list);
		route_summary_list_view.setAdapter(route_summary_logo_adapter);
		
		
		return row;
		//return super.getView(position, convertView, parent);
	}

	public void setObjectList(List<Route_Search_Result> route_summar_objects) 
	{
		this.Route_Summary_Objects.clear();
		this.Route_Summary_Objects.addAll(route_summar_objects);
		super.clear();
		super.addAll(route_summar_objects);
		return;
	}
	

}
