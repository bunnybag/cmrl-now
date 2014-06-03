/**
 * 
 */
package com.bunnybags.cmrlnow;

/**
 * @author SURESH
 *
 */
public enum Route_Name_Icon_Id {
	Corridor_1(R.drawable.cmrl_logo_red),
	Corridor_2(R.drawable.cmrl_logo_green);
	
	private int resource_id;
	
	Route_Name_Icon_Id(int resource_id)
	{
		this.resource_id = resource_id;
	}
	
	public int get_route_drwable_id() 
	{
		return  this.resource_id;
	}
}
