/**
 * 
 */
package com.bunnybags.cmrlnow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.app.Activity;

/**
 * @author SURESH
 *
 */
public abstract class Search_Route extends Activity{
	
	protected String selected_src_station;
	protected String selected_dest_station;
	private List<String> Intersection_Stations = Arrays.asList("Alandur","Central Metro");
	
	protected List<Route_Search_Result> Search_Route_by_Src_Dest(String Src_Station,String Dest_Station) 
	{
		
		DBHelper db = new DBHelper(this);
		/*try {
			db.createDatabase();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		db.openDatabase();
		
		/*String Source_Route = db.getRouteNamebyStationName(Src_Station);
		String Dest_Route = db.getRouteNamebyStationName(Dest_Station);*/
		
		String Source_Route = new String();
		String Dest_Route = new String();
		
		List<String> Source_Route_List = db.getRouteNameListbyStationName(Src_Station);
		List<String> Dest_Route_List = db.getRouteNameListbyStationName(Dest_Station);
		
		List<Route_Search_Result> Route_Search_Result_List = new ArrayList<Route_Search_Result>();
		
		Route_Search_Result_List.clear();
		
		for (int route_index = 0; route_index < Math.min(Source_Route_List.size(),Dest_Route_List.size()); route_index++) 
		{
			
			if(Source_Route_List.size() == Dest_Route_List.size())
			{
				Source_Route = Source_Route_List.get(route_index);
				Dest_Route = Dest_Route_List.get(route_index);
			}
			else
			{
				
				Source_Route = (Source_Route_List.size() < Dest_Route_List.size()) ? Source_Route_List.get(route_index): Dest_Route_List.get(route_index);
				Dest_Route = Source_Route;
				
				/*Source_Route = (Source_Route_List.size() == 1) ? Source_Route_List.get(0): Source_Route_List.get(route_index);
				Dest_Route = (Dest_Route_List.size() == 1) ? Dest_Route_List.get(0): Dest_Route_List.get(route_index);*/
			}
			
				
			
			
			if (Source_Route.compareTo(Dest_Route) == 0) 
			{
				//Route_Search_Result_List.clear();
				Route_Search_Result Route_Search_Object = new Route_Search_Result();
				List<String> Routes_List = new ArrayList<String>();
				
				Route_Search_Object.setSource_Station(Src_Station);
				Route_Search_Object.setDestination_Station(Dest_Station);
				Route_Search_Object.setNumber_of_Interconnects(0);
				Route_Search_Object.setRoute_Via(null);

				int Src_Station_Id = db.getStationIdbyStationName(Source_Route,
						Src_Station);
				int Dest_Station_Id = db.getStationIdbyStationName(
						Source_Route, Dest_Station);
				Route_Search_Object.setRoutes_Stations_List(db
						.getStationBetweenId(Source_Route, Src_Station_Id,
								Dest_Station_Id));
				
				Routes_List.add(Source_Route);
				Routes_List.add(Source_Route);
				
				Route_Search_Object.setRoute_List(Routes_List);
				
				//Route_Search_Object.setRoute_Via(Route_Search_Object.getRoutes_Stations_List().get((Route_Search_Object.getRoutes_Stations_List().size())/2));

				Route_Search_Result_List.add(Route_Search_Object);
			} 
			else 
			{

				for (String current_Interconnect_Station : this.Intersection_Stations) {
					List<String> current_route_stations = new ArrayList<String>();
					Route_Search_Result Route_Search_Object = new Route_Search_Result();

					List<String> Routes_List = new ArrayList<String>();
					
					Route_Search_Object.setSource_Station(Src_Station);
					Route_Search_Object.setDestination_Station(Dest_Station);
					Route_Search_Object.setNumber_of_Interconnects(1);
					Route_Search_Object.setRoute_Via(current_Interconnect_Station);

					current_route_stations.clear();

					int Src_Station_Id = db.getStationIdbyStationName(
							Source_Route, Src_Station);
					int Dest_Station_Id = db.getStationIdbyStationName(
							Source_Route, current_Interconnect_Station);
					current_route_stations.addAll(db.getStationBetweenId(
							Source_Route, Src_Station_Id, Dest_Station_Id));

					Src_Station_Id = db.getStationIdbyStationName(Dest_Route,
							current_Interconnect_Station);
					Dest_Station_Id = db.getStationIdbyStationName(Dest_Route,
							Dest_Station);
					List<String> Dest_Route_Stations_List = db.getStationBetweenId(Dest_Route, Src_Station_Id, Dest_Station_Id);
					current_route_stations.addAll(Dest_Route_Stations_List.subList(1, Dest_Route_Stations_List.size()));

					Route_Search_Object
							.setRoutes_Stations_List(current_route_stations);
					
					Routes_List.add(Source_Route);
					Routes_List.add(Dest_Route);
					Route_Search_Object.setRoute_List(Routes_List);
					
					Route_Search_Result_List.add(Route_Search_Object);
				}
			}
		}
		
		Collections.sort(Route_Search_Result_List);
		db.closeDataBase();
				
		return Route_Search_Result_List;
	}
	
}
