/**
 * 
 */
package com.bunnybags.cmrlnow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import com.google.android.gms.maps.model.LatLng;


/**
 * @author SURESH
 *
 */
public class Nearby_Search_Result implements Comparable<Nearby_Search_Result>{

	
	private String Station_Name;
	private int Route_id;
	private Float Distance_from_current;
	private ArrayList<LatLng> Direction_Point;
	private String Direction_Summary;
	
	
	/**
	 * 
	 */
	public Nearby_Search_Result() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the distance_from_current
	 */
	public Float getDistance_from_current() {
		return Distance_from_current;
	}


	/**
	 * @param distance_from_current the distance_from_current to set
	 */
	public void setDistance_from_current(Float distance_from_current) {
		Distance_from_current = distance_from_current;
	}


	/**
	 * @return the station_Name
	 */
	public String getStation_Name() {
		return Station_Name;
	}


	/**
	 * @param station_Name the station_Name to set
	 */
	public void setStation_Name(String station_Name) {
		Station_Name = station_Name;
	}


	/**
	 * @return the route_id
	 */
	public int getRoute_id() {
		return Route_id;
	}


	/**
	 * @param route_id the route_id to set
	 */
	public void setRoute_id(int route_id) {
		Route_id = route_id;
	}


	@Override
	public int compareTo(Nearby_Search_Result another) {
		// TODO Auto-generated method stub
		return this.Distance_from_current > another.Distance_from_current ? 1 : (this.Distance_from_current < another.Distance_from_current ? -1 : 1);
	}

	
	@Override
	public boolean equals(Object o) 
	{
		if(o != null)
		{
			Nearby_Search_Result another = (Nearby_Search_Result) o;
			return this.getStation_Name().equalsIgnoreCase(another.getStation_Name());
		}
		else
		{
			return false;
		}
	}


	/**
	 * @return the direction_Point
	 */
	public ArrayList<LatLng> getDirection_Point() {
		return Direction_Point;
	}


	/**
	 * @param direction_Point the direction_Point to set
	 */
	public void setDirection_Point(ArrayList<LatLng> direction_Point) {
		Direction_Point = direction_Point;
	}


	/**
	 * @return the direction_Summary
	 */
	public String getDirection_Summary() {
		return Direction_Summary;
	}


	/**
	 * @param direction_Summary the direction_Summary to set
	 */
	public void setDirection_Summary(String direction_Summary) {
		Direction_Summary = direction_Summary;
	}
}
