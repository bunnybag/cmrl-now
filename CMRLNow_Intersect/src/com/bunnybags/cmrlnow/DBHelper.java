/**
 * 
 */
package com.bunnybags.cmrlnow;

/**
 * @author SURESH
 *
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.os.Build;
import android.util.Log;




public class DBHelper extends SQLiteOpenHelper {
	
	 private SQLiteDatabase myDataBase;
     private Context myContext;
     private static String DATABASE_NAME = "CMRL_Database";
     public String DATABASE_PATH = null;
     public static int DATABASE_VERSION = 7;
     

     //Constructor
     public DBHelper(Context context)
     {
           super(context, DATABASE_NAME, null, DATABASE_VERSION);
           this.myContext = context;
           
           this.DATABASE_PATH = context.getDatabasePath(DATABASE_NAME).toString();//+ "/data/com.bunnybags.cmrlnow/databases/";
     }
         
     
     /*
      * 
      */
     
     //Create a empty database on the system
     public void createDatabase() throws IOException
     {

           boolean dbExist = checkDataBase();

           if(dbExist)
           {
        	   Log.v("DB Exists", "db exists");
                 // By calling this method here onUpgrade will be called on a
                 // writeable database, but only if the version number has been
                 // bumped
                 //onUpgrade(myDataBase, DATABASE_VERSION_old, DATABASE_VERSION);
        	   db_delete();
           
           }
          
           boolean dbExist1 = checkDataBase();
           if(!dbExist1)
           {
                 this.getReadableDatabase();
                 try
                 {
                       this.close();    
                       copyDataBase();
                 }
                 catch (IOException e)
                 {
                       throw new Error("Error copying database");
                 }
           }

     }

     private void db_delete() {
		// TODO Auto-generated method stub
    	 File file = new File(DATABASE_PATH/* + DATABASE_NAME*/);
         if(file.exists())
         {
        	 //if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN)
        		// SQLiteDatabase.deleteDatabase(file);
        	 //else
        	 file.setWritable(true);
        	 file.delete();
               //System.out.println("delete database file.");
         }
	}


	//Check database already exist or not
     private boolean checkDataBase()
     {
           boolean checkDB = false;
           try
           {
                 String myPath = DATABASE_PATH;// + DATABASE_NAME;
                 File dbfile = new File(myPath);
                 checkDB = dbfile.exists();
           }
           catch(SQLiteException e)
           {
           }
           return checkDB;
     }

     //Copies your database from your local assets-folder to the just created empty database in the system folder
     private void copyDataBase() throws IOException
     {

           String outFileName = DATABASE_PATH ;//+ DATABASE_NAME;


           OutputStream myOutput = new FileOutputStream(outFileName);
           InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

           byte[] buffer = new byte[1024];
           int length;
           while ((length = myInput.read(buffer)) > 0)
           {
                 myOutput.write(buffer, 0, length);
           }
           myInput.close();
           myOutput.flush();
           myOutput.close();
     }

     //delete database
     @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void db_delete(SQLiteDatabase db)
     {
         File file = new File(DATABASE_PATH/* + DATABASE_NAME*/);
         if(file.exists())
         {
        	 //if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN)
        		// SQLiteDatabase.deleteDatabase(file);
        	 //else
        	 file.setWritable(true);
        	 file.delete();
               //System.out.println("delete database file.");
         }
     }

     //Open database
     public void openDatabase() throws SQLException
     {
           String myPath = DATABASE_PATH ;//+ DATABASE_NAME;
           myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
     }

     public synchronized void closeDataBase()throws SQLException
     {
           if(myDataBase != null)
                 myDataBase.close();
           super.close();
     }

     public void onCreate(SQLiteDatabase db)
     {
     }

     public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
     {    
           if (newVersion > oldVersion)
           {
                 Log.v("Database Upgrade", "Database version higher than old.");
         //        db_delete(db);
           }
     }
     
     public List<String> getDbRecord(int id)
     {
    	 //List<String> query_result = new ArrayList<String>();
    	 SQLiteDatabase db = this.getReadableDatabase();
    	 String sql = "SELECT * FROM Station_Index";
    	 Cursor query_cursor = db.rawQuery(sql, null);
    	 List<String> data = new ArrayList<String>();
    	 
    	 if(query_cursor.moveToFirst())
    	 {
    	 	do
    	 	{
    	 		data.add(query_cursor.getString(id));
    	 	}while(query_cursor.moveToNext());	
    	 }
    	 
    	 query_cursor.close();
    	 db.close();
    	 
    	 return data;
     }
    
     public List<String> getRouteList()
     {
    	 SQLiteDatabase db = this.getReadableDatabase();
    	 String sql = "SELECT Name FROM Route_Index";
    	 Cursor query_cursor = db.rawQuery(sql, null);
    	 List<String> data = new ArrayList<String>();
    	 
    	 if(query_cursor.moveToFirst())
    	 {
    	 	do
    	 	{
    	 		data.add(query_cursor.getString(0));
    	 	}while(query_cursor.moveToNext());	
    	 }
    	 
    	 query_cursor.close();
    	 db.close();
    	 
    	 return data;
     }
     
     public int getRouteIdbyRouteName(String route_name)
     {
    	 SQLiteDatabase db = this.getReadableDatabase();
    	 String sql = "SELECT Route_id FROM Route_Index WHERE Name LIKE '";
    	 sql = sql.concat(route_name);
    	 sql = sql.concat("'");
    	 Cursor query_cursor = db.rawQuery(sql, null);
    	 int selected_route_id=0;
    	 
    	 if(query_cursor.moveToFirst())
    	 {
    		 selected_route_id = query_cursor.getInt(0);
    	 }
    	 
    	 query_cursor.close();
    	 db.close();
    	 
    	 return selected_route_id;
     }
     
     public int getStationIdbyStationName(String Route_name,String station_name)
     {
    	 SQLiteDatabase db = this.getReadableDatabase();
    	 String Route_id = Route_name.substring(Route_name.length()-1);
    	 String sql = "SELECT _id FROM Station_Index WHERE Station_Name LIKE '";
    	 if (station_name.contains("'")) 
 		 {
 			station_name= station_name.replaceAll("'", "''");
 		 }
    	 sql = sql.concat(station_name);
    	 sql = sql.concat("'");
    	 sql = sql.concat("AND Route_id = ");
    	 sql = sql.concat(Route_id);
    	 
    	 Cursor query_cursor = db.rawQuery(sql, null);
    	 int selected_route_id=0;
    	 
    	 if(query_cursor.moveToFirst())
    	 {
    		 selected_route_id = query_cursor.getInt(0);
    	 }
    	 
    	 query_cursor.close();
    	 db.close();
    	 
    	 return selected_route_id;
     }
 
     
     public List<String> get_Station_List(int route_id)
     {
    	 SQLiteDatabase db = this.getReadableDatabase();
    	 String route_id_string = Integer.toString(route_id);
    	 
    	 String sql = "SELECT DISTINCT Station_Name FROM Station_Index  WHERE Route_id LIKE '%";
    	 sql = sql.concat(route_id_string);
    	 sql = sql.concat("%'");
    	 Cursor query_cursor = db.rawQuery(sql, null);
    	 List<String> data = new ArrayList<String>();
    	 
    	 if(query_cursor.moveToFirst())
    	 {
    	 	do
    	 	{
    	 		data.add(query_cursor.getString(0));
    	 	}while(query_cursor.moveToNext());	
    	 }
    	 
    	 query_cursor.close();
    	 db.close();
    	 
    	 return data;
     }
     
     public List<String> get_Train_Time_List(int route_id,int src_station_id,int dest_station_id,String time_string)
     {
    	 SQLiteDatabase db = this.getReadableDatabase();
    	 String table_name = "R".concat(Integer.toString(route_id));
    	 
    	 if(dest_station_id > src_station_id)
    	 {
    		 table_name = table_name.concat("_Up");
    	 }
    	 else
    	 {
    		 table_name = table_name.concat("_Down");
    	 }
    	 
    	 if (time_string == null) 
    	 {
    		 time_string = "00:00";
    	 }
    	 String src_station_string = "S".concat(Integer.toString(src_station_id));
    	 String dest_station_string = "S".concat(Integer.toString(dest_station_id));
     	 String sql = "SELECT "+ src_station_string + "," + dest_station_string + " FROM "+ table_name +" WHERE "+ src_station_string +" >= '"+ time_string +"'";
    	 Cursor query_cursor = db.rawQuery(sql, null);
    	 List<String> data = new ArrayList<String>();
    	 
    	 
		if (query_cursor.moveToFirst()) {
			do {
				data.add(query_cursor.getString(0) +"\t\t\t\t\t\t\t\t\t" +query_cursor.getString(1));
			} while (query_cursor.moveToNext());
		}

		query_cursor.close();
    	 db.close();
    	 
    	 return data;
     }
//add your public methods for insert, get, delete and update data in database.


	public List<String> filter_station_by_name(CharSequence filter_text) {

		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT DISTINCT Station_Name FROM Station_Index  WHERE Station_Name LIKE '%";
		if (filter_text.toString().contains("'")) 
		{
			filter_text= (CharSequence)filter_text.toString().replaceAll("'", "''");
		}
		sql = sql.concat((String)filter_text.toString());
		sql = sql.concat("%'");
		Cursor query_cursor = db.rawQuery(sql, null);
		List<String> data = new ArrayList<String>();
		 
		if(query_cursor.moveToFirst())
		{
			do
			{
				data.add(query_cursor.getString(0));
			}while(query_cursor.moveToNext());	
		}
		
		query_cursor.close();
		db.close();
		
		return data;
	}


	public String getRouteNamebyStationName(String Station_Name) {

		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT Name FROM Station_Index INNER JOIN Route_Index ON Station_Index.Route_id = Route_index.Route_id where Station_Index.Station_Name LIKE '%";
		
		if (Station_Name.contains("'")) 
		{
			Station_Name= Station_Name.replaceAll("'", "''");
		}
		
		sql = sql.concat((String)Station_Name.toString());
		sql = sql.concat("%'");
		Cursor query_cursor = db.rawQuery(sql, null);
		String Route_Name = null; 
		
		if(query_cursor.moveToFirst())
		{
			Route_Name = query_cursor.getString(0);
		}
		
		query_cursor.close();
		db.close();
		return Route_Name;
	}
	
	public List<String> getRouteNameListbyStationName(String Station_Name) {

		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT Name FROM Station_Index INNER JOIN Route_Index ON Station_Index.Route_id = Route_index.Route_id where Station_Index.Station_Name LIKE '%";
		
		if (Station_Name.contains("'")) 
		{
			Station_Name= Station_Name.replaceAll("'", "''");
		}
		
		sql = sql.concat((String)Station_Name.toString());
		sql = sql.concat("%'");
		Cursor query_cursor = db.rawQuery(sql, null);
		List<String> Route_Name_List = new ArrayList<String>();
		
		if(query_cursor.moveToFirst())
		{
			do
			{
				Route_Name_List.add(query_cursor.getString(0));
			}while(query_cursor.moveToNext());	
		}
		
		query_cursor.close();
		db.close();
		return Route_Name_List;
	}


	public List<String> getStationBetweenId(String route_name, Integer src_Station_Id,
			Integer Dest_Station_Id) {
		
		Boolean reverse_list=false;
		SQLiteDatabase db = this.getReadableDatabase();
		
		if(src_Station_Id > Dest_Station_Id)
		{
			reverse_list = true; 
		}
		String sql = "SELECT Station_Name from Station_Index where _id between " ;
		
		sql = (reverse_list) ? sql.concat(Integer.toString(Dest_Station_Id)) : sql.concat(Integer.toString(src_Station_Id));
		sql = sql.concat(" and ");
		sql = (reverse_list) ? sql.concat(Integer.toString(src_Station_Id)) : sql.concat(Integer.toString(Dest_Station_Id));
		Cursor query_cursor = db.rawQuery(sql, null);
		List<String> data = new ArrayList<String>();
		 
		if(query_cursor.moveToFirst())
		{
			do
			{
				data.add(query_cursor.getString(0));
			}while(query_cursor.moveToNext());	
		}
		
		query_cursor.close();
		db.close();

		if(reverse_list)
		{
			Collections.reverse(data);
		}
		return data;
	}
	
	public List<String> get_Station_List_near_points(int position_x,int position_y,int radius)
    {
		
	   	 SQLiteDatabase db = this.getReadableDatabase();
	   	 String pos_X_min = Integer.toString(position_x-radius);
	   	 String pos_X_max = Integer.toString(position_x+radius);
	   	 String pos_Y_min = Integer.toString(position_y-radius);
	   	 String pos_Y_max = Integer.toString(position_y+radius);
	   	 String sql = "Select Position_X, Position_Y,Station_Name from Station_Index where Position_X >= " + pos_X_min +
	   	 		" and Position_X <= " + pos_X_max +
	   	 		" and Position_Y <= " + pos_Y_max +
	   	 		" and Position_Y >= " + pos_Y_min;
	   	 Cursor query_cursor = db.rawQuery(sql, null);
	   	 
	   	 //LinkedList<List<String>> Station_Position_List = new LinkedList<List<String>>();
	   	 List<String> Nearest_Station_Position = new ArrayList<String>();
	   	 double min_distance_bw_point_station = Double.MAX_VALUE; 
	   	 
	   	 
	   	 if(query_cursor.moveToFirst())
	   	 {
	   		 int station_count = query_cursor.getCount();
	   		 if(station_count > 1)
	   		 {
	   			 do
	   			 {
	   				 double current_distance;
	   				 /*//List<String> data = new ArrayList<String>();	 
	      	   		 //data.add(query_cursor.getString(0));
	      	   		 //data.add(query_cursor.getString(1));
	      	   		 //data.add(query_cursor.getString(2));
	   				 */
	   				 int current_position_x = Integer.parseInt(query_cursor.getString(0));
	   				 int current_position_y = Integer.parseInt(query_cursor.getString(1));
	   				 String current_station_name = query_cursor.getString(2);
	   				 
	      	   		 current_distance = Math.sqrt((Math.pow((position_x-current_position_x), 2))+(Math.pow((position_y-current_position_y), 2)));
	      	   		 
	      	   		 if(current_distance < min_distance_bw_point_station)
	      	   		 {
	      	   			 min_distance_bw_point_station = current_distance;
	      	   			 Nearest_Station_Position.clear();
	      	   			 Nearest_Station_Position.add(Integer.toString(current_position_x));
	      	   			 Nearest_Station_Position.add(Integer.toString(current_position_y));
	      	   			 Nearest_Station_Position.add(current_station_name);		 
	      	   		 }
	   			 }while(query_cursor.moveToNext());	
	   			 
	   			 
	   			  
	   		 }
	   		 else
	   		 {
	   			 Nearest_Station_Position.add(query_cursor.getString(0));
	   			 Nearest_Station_Position.add(query_cursor.getString(1));
	   			 Nearest_Station_Position.add(query_cursor.getString(2));
	   		 }
	   	 }   
	   	 else
	   	 {
	   		Nearest_Station_Position.clear();
	   	 }
	
	   	 
	   	 query_cursor.close();
	   	 db.close();
	   	 
	   	 return Nearest_Station_Position;
    }
   
	public List<Integer> get_Station_Points_In_Map(String Station_Name)
    {
		
	   	 SQLiteDatabase db = this.getReadableDatabase();
	   	 if (Station_Name.contains("'")) 
	   	 {
	   		 Station_Name= Station_Name.replaceAll("'", "''");
	   	 }
	   	 String sql = "Select Distinct Station_Name, Position_X, Position_Y from Station_Index where Station_Name LIKE '%" + Station_Name + "%'";
	   	 Cursor query_cursor = db.rawQuery(sql, null);
	   	 
	   	 List<Integer> Station_Position = new ArrayList<Integer>();
	   	 
	   	 if(query_cursor.moveToFirst())
	   	 {
   			 Station_Position.add(Integer.parseInt(query_cursor.getString(1)));
   			 Station_Position.add(Integer.parseInt(query_cursor.getString(2)));
	   	 }   
	   	 else
	   	 {
	   		 Station_Position.clear();
	   	 }
	
	   	 query_cursor.close();
	   	 db.close();
	   	 
	   	 return Station_Position;
    }

	public Location get_Station_Location_by_Station_Name(String Station_Name)
    {
		
	   	 SQLiteDatabase db = this.getReadableDatabase();
	   	 
	   	 Float Latitude= new Float(0);
	   	 Float Longitude = new Float(0);
	   	 Boolean valid_location = new Boolean(false);
	   	 
	   	 if (Station_Name.contains("'")) 
	   	 {
	   		 Station_Name= Station_Name.replaceAll("'", "''");
	   	 }
	   	 	   	 
	   	 String sql = "Select Latitude, Longitude,Service_Status from Station_Index where Station_Name LIKE '%" + Station_Name + "%'";
	   	 
	   	 Cursor query_cursor = db.rawQuery(sql, null);
	   	 
	   	 Location station_location = new Location("Database");
	   	 
	   	 if(query_cursor.moveToFirst())
	   	 {
	   		 if(query_cursor.getString(2).equalsIgnoreCase("ACTIVE"))
	   		 {
		   		 if(query_cursor.getString(0) != null && query_cursor.getString(1)!=null)
		   		 {
		   			 valid_location = true;
		   			 
		   			 try
		   			 {
			   			 Latitude = Float.parseFloat(query_cursor.getString(0));
				   		 Longitude = Float.parseFloat(query_cursor.getString(1));
		   			 }
		   			 catch (NumberFormatException excep)
		   			 {
		   				 valid_location = false;
		   			 }
			   		 
		   			 if(valid_location)
		   			 {
				   		 if(Float.isNaN(Latitude) || Float.isNaN(Longitude))
				   		 {
					   	   	 valid_location = false;
				   		 }
		   			 }
			   		 
		   			 if(valid_location)
		   			 {
			   			 station_location.setLatitude(Latitude);
			   			 station_location.setLongitude(Longitude);
		   			 }
		   			 
		   		 }
	   		 }
	   		 else
	   		 {
	   			valid_location = false;
	   		 }
	   	 }
	   	 
	
	   	 query_cursor.close();
	   	 db.close();
	   	 
	   	 Location retval = (valid_location)? station_location: null;
	   	 return retval;
    }

	
}
