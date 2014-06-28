/**
 * 
 */
package com.bunnybags.cmrlnow;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

/**
 * @author SURESH
 *
 */
public class Nearby_Search_List_Adapter extends ArrayAdapter<Nearby_Search_Result> implements SpinnerAdapter {

	private Context context;
	private int Resource_Id;
	private List<Nearby_Search_Result> Nearby_Search_List_Items;
	/**
	 * 
	 */
	/*public Nearby_Search_List_Adapter() {
		// TODO Auto-generated constructor stub
	}*/
	
	
	public Nearby_Search_List_Adapter(Context context, int resource,
			List<Nearby_Search_Result> objects) {
		super(context, resource, objects);
		this.context = context;
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
			LayoutInflater inflator = ((Activity) this.context)
					.getLayoutInflater();
			row = inflator.inflate(Resource_Id, parent, false);
		}

		Nearby_Search_Result nearby_search_current_item = (Nearby_Search_Result) this.Nearby_Search_List_Items.get(position);
		
		
		ImageButton station_details_button_view = (ImageButton) row.findViewById(R.id.Nearest_Station_Details_Button);
		TextView Station_Name = (TextView) row.findViewById(R.id.Nearest_Station_Name_Text);
		TextView Station_Direction_Summary = (TextView) row.findViewById(R.id.Nearest_Station_Direction_Summary_Text);
		
		
		Station_Name.setText(nearby_search_current_item.getStation_Name());
		Station_Direction_Summary.setText(nearby_search_current_item.getDirection_Summary());
		
		TextView Distance_from_here = (TextView) row.findViewById(R.id.Nearest_Station_Distance_Text);
		Distance_from_here.setText(Float.toString(nearby_search_current_item.getDistance_from_current()) + "km" );
		
		station_details_button_view.setOnClickListener(new Nearby_Result_Clicker(this.context,nearby_search_current_item.getStation_Name()));
		
		return row;
		//return super.getView(position, convertView, parent);
	}


}
