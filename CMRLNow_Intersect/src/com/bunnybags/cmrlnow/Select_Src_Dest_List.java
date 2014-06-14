package com.bunnybags.cmrlnow;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Select_Src_Dest_List extends Activity{

	private EditText current_station_focus;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_select_src_dest);
			
		final EditText Source_Station_Input = (EditText) findViewById(R.id.Source_Station_Input);
		final EditText Dest_Station_Input = (EditText) findViewById(R.id.Dest_Station_Input);
		final Button Nearby_Stations_Button = (Button) findViewById(R.id.Nearby_Station_Button);
		
		setCurrent_station_focus(Source_Station_Input);
		Source_Station_Input.setSelection(0);
		Source_Station_Input.requestFocus();

		DBHelper db = new DBHelper(getApplicationContext());
		
		db.openDatabase();
		
		
		List<String> initial_station_name_list = db.filter_station_by_name("");
		
		db.closeDataBase();
		
		final ArrayAdapter<String> initial_station_adapter = new ArrayAdapter<String>(this,  
				    android.R.layout.simple_list_item_1, initial_station_name_list);  
	
		initial_station_adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
		
		final ListView Filtered_Station_List = (ListView) findViewById(R.id.Filtered_Station_List);
		Filtered_Station_List.setAdapter(initial_station_adapter);

		Filtered_Station_List.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1, int position,
					long arg3) {
				//Source_Station_Input.setText(parent.getItemAtPosition(position).toString());
				EditText current_text_view = getCurrent_station_focus(); 
				current_text_view.setText(parent.getItemAtPosition(position).toString());
				Filtered_Station_List.setAdapter(initial_station_adapter);
				toggleStationEditFocus();
			}
		});

		Source_Station_Input.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				setCurrent_station_focus(Source_Station_Input);
				filterStationListView(arg0);
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				
			}
		});
		
		Source_Station_Input.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if(arg1)
				{
					setCurrent_station_focus(Source_Station_Input);
				}
			}
		});
		
		Dest_Station_Input.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				setCurrent_station_focus(Dest_Station_Input);
				filterStationListView(arg0);
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});

		Dest_Station_Input.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if(arg1)
				{
					setCurrent_station_focus(Dest_Station_Input);
				}
				
			}
		});
		
		Filtered_Station_List.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView parent, int arg1) {
				// TODO Auto-generated method stub

			}
			
			@Override
			public void onScroll(AbsListView parent, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}
		});
		
		Button Get_Route_Button = (Button) findViewById(R.id.Get_Route_Button);
		
		Get_Route_Button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Show_Route_Information();
			}
		});
		
		Nearby_Stations_Button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//Bundle nearby_station = new Bundle();
				
				Intent nearby_station_search = new Intent();
				nearby_station_search.setClass(getApplicationContext(), Nearby_Station_Search.class);
				startActivity(nearby_station_search);
			}
		});
		
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

		
		
}

	protected void Show_Route_Information() {
		Bundle route_info = new Bundle();
		
		EditText src_station_view = (EditText) findViewById(R.id.Source_Station_Input);
		EditText dest_station_view = (EditText) findViewById(R.id.Dest_Station_Input);
		
		String selected_source = src_station_view.getText().toString();
		String selected_dest = dest_station_view.getText().toString();
		
		route_info.putString(getString(R.string.Src_Station_Bundle_Key_Str), selected_source);
		route_info.putString(getString(R.string.Dest_Station_Bundle_Key_Str), selected_dest);
		
		Intent send_route_info = new Intent(this, Route_Suggestions_List.class);
		send_route_info.putExtras(route_info);
		
		startActivity(send_route_info);
		return;
	}

	protected void toggleStationEditFocus() {
		EditText current_station_edit = getCurrent_station_focus();

		EditText Source_Station_Input = (EditText) findViewById(R.id.Source_Station_Input);
		EditText Dest_Station_Input = (EditText) findViewById(R.id.Dest_Station_Input);
		EditText alternate_station_edit = null;
		if(current_station_edit == Source_Station_Input)
		{
			alternate_station_edit =  Dest_Station_Input;
		}
		else if(current_station_edit == Dest_Station_Input)
		{
			alternate_station_edit =  Source_Station_Input;
		}
		
		setCurrent_station_focus(alternate_station_edit);
		alternate_station_edit.setSelection(0);
		alternate_station_edit.requestFocus();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.route__explorer, menu);
		return true;
	}
	
	public CMRL_Status filterStationListView(CharSequence changed_text) {
	
		DBHelper db = new DBHelper(this);
		/*try {
			db.createDatabase();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		db.openDatabase();
		
		List<String> station_name_list = db.filter_station_by_name(changed_text);
		
		db.closeDataBase();
		
		ArrayAdapter<String> station_adapter = new ArrayAdapter<String>(this,  
				    android.R.layout.simple_list_item_1, station_name_list);  
	
		station_adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
		
		ListView filter_station_list = (ListView) findViewById(R.id.Filtered_Station_List);
		filter_station_list.setAdapter(station_adapter);
		
		return CMRL_Status.CMRL_Success;
	}

	/**
	 * @return the current_station_focus
	 */
	public EditText getCurrent_station_focus() {
		return current_station_focus;
	}

	/**
	 * @param current_station_focus the current_station_focus to set
	 */
	public void setCurrent_station_focus(EditText current_station_focus) {
		this.current_station_focus = current_station_focus;
	}

}
