/**
 * 
 */
package com.bunnybags.cmrlnow;

import android.R.string;

/**
 * @author SURESH
 *
 */
public class Nearby_Search_Result {

	
	private String Station_Name;
	private Float Distance_from_current;
	
	
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

}
