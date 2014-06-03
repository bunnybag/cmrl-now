/**
 * 
 */
package com.bunnybags.cmrlnow;


import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;



/**
 * @author SURESH
 *
 */
public class Route_Search_Result implements Comparable<Route_Search_Result>,Parcelable {

	private String Source_Station;
	private String Destination_Station;
	private Double Fare;
	private Double Distance_in_kms;
	private Integer Number_of_Interconnects;
	private Integer Number_of_Stops;
	private List<String> Routes_Stations_List;
	private String Route_Via;
	private Double Travel_Time;
	private List<String> Route_List;
	
	
	public Route_Search_Result(Parcel parcel_to_fill)
	{
		readFromParcel(parcel_to_fill);
	}
	
	public Route_Search_Result() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the source_Station
	 */
	public String getSource_Station() {
		return Source_Station;
	}
	/**
	 * @param source_Station the source_Station to set
	 */
	public void setSource_Station(String source_Station) {
		Source_Station = source_Station;
	}
	/**
	 * @return the destination_Station
	 */
	public String getDestination_Station() {
		return Destination_Station;
	}
	/**
	 * @param destination_Station the destination_Station to set
	 */
	public void setDestination_Station(String destination_Station) {
		Destination_Station = destination_Station;
	}
	/**
	 * @return the fare
	 */
	public Double getFare() {
		return Fare;
	}
	/**
	 * @param fare the fare to set
	 */
	public void setFare(Double fare) {
		Fare = fare;
	}
	/**
	 * @return the distance_in_kms
	 */
	public Double getDistance_in_kms() {
		return Distance_in_kms;
	}
	/**
	 * @param distance_in_kms the distance_in_kms to set
	 */
	public void setDistance_in_kms(Double distance_in_kms) {
		Distance_in_kms = distance_in_kms;
	}
	/**
	 * @return the number_of_Interconnects
	 */
	public Integer getNumber_of_Interconnects() {
		return Number_of_Interconnects;
	}
	/**
	 * @param number_of_Interconnects the number_of_Interconnects to set
	 */
	public void setNumber_of_Interconnects(Integer number_of_Interconnects) {
		Number_of_Interconnects = number_of_Interconnects;
	}
	/**
	 * @return the number_of_Stops
	 */
	public Integer getNumber_of_Stops() {
		return Number_of_Stops;
	}
	/**
	 * @param number_of_Stops the number_of_Stops to set
	 */
	public void setNumber_of_Stops(Integer number_of_Stops) {
		Number_of_Stops = number_of_Stops;
	}
	/**
	 * @return the routes_Stations_List
	 */
	public List<String> getRoutes_Stations_List() {
		return Routes_Stations_List;
	}
	/**
	 * @param routes_Stations_List the routes_Stations_List to set
	 */
	public void setRoutes_Stations_List(List<String> routes_Stations_List) {
		Routes_Stations_List = routes_Stations_List;
		setNumber_of_Stops(routes_Stations_List.size() - 1);
	}
	
	@Override
	public int compareTo(Route_Search_Result another) {
		// TODO Auto-generated method stub
		//return 0;
		return this.Number_of_Stops > another.Number_of_Stops ? 1 : (this.Number_of_Stops < another.Number_of_Stops ? -1 : 0);
		
		
		/*Collections.sort(orders);
        Collections.sort(orders, Collections.reverseOrder());*/
		
	}
	/**
	 * @return the route_Via
	 */
	public String getRoute_Via() {
		return Route_Via;
	}
	/**
	 * @param route_Via the route_Via to set
	 */
	public void setRoute_Via(String route_Via) {
		Route_Via = route_Via;
	}
	/**
	 * @return the travel_Time
	 */
	public Double getTravel_Time() {
		return Travel_Time;
	}
	/**
	 * @param travel_Time the travel_Time to set
	 */
	public void setTravel_Time(Double travel_Time) {
		Travel_Time = travel_Time;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel parcel_to_send, int arg1) {
		parcel_to_send.writeList(this.Routes_Stations_List);
		//parcel_to_send.writeDouble(this.Distance_in_kms);
		//parcel_to_send.writeDouble(this.Fare);
 		parcel_to_send.writeInt(this.Number_of_Interconnects);
		parcel_to_send.writeInt(this.Number_of_Stops);
		parcel_to_send.writeString(this.Route_Via);
		//parcel_to_send.writeDouble(this.Travel_Time);
		parcel_to_send.writeString(this.Source_Station);
		parcel_to_send.writeString(this.Destination_Station);
		parcel_to_send.writeList(this.Route_List);
	}
	
	public void readFromParcel(Parcel parcel_to_fill) 
	{
		Routes_Stations_List = new ArrayList<String>(); 
		parcel_to_fill.readList(Routes_Stations_List, null);
		//Distance_in_kms = parcel_to_fill.readDouble();
		//Fare = parcel_to_fill.readDouble();
		Number_of_Interconnects = parcel_to_fill.readInt();
		Number_of_Stops = parcel_to_fill.readInt();
		Route_Via = parcel_to_fill.readString();
		//Travel_Time = parcel_to_fill.readDouble();
		Source_Station = parcel_to_fill.readString();
		Destination_Station = parcel_to_fill.readString();
		Route_List = new ArrayList<String>();
		parcel_to_fill.readList(Route_List , null);
	}
	
	
	/**
	 * @return the route_List
	 */
	public List<String> getRoute_List() {
		return Route_List;
	}

	/**
	 * @param route_List the route_List to set
	 */
	public void setRoute_List(List<String> route_List) {
		Route_List = route_List;
	}


	public static final Parcelable.Creator CREATOR = 
			new Creator() {

				@Override
				public Route_Search_Result createFromParcel(Parcel parcel_to_fill) {
					// TODO Auto-generated method stub
					return new Route_Search_Result(parcel_to_fill);
				}

				@Override
				public Route_Search_Result[] newArray(int arg0) {
					// TODO Auto-generated method stub
					return null;
				}
			};
	
	
}
