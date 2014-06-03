package com.bunnybags.cmrlnow;

import java.io.IOException;
import java.util.List;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NavUtils;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;
import android.widget.ZoomControls;

public class Route_Suggestion_Map extends Search_Route {
	
	private float memory_x,memory_y;
	private boolean action_move;
	private String selected_src_station;
	private String selected_dest_station;
	private int selected_src_point_X;
	private int selected_src_point_Y;
	private int selected_dest_point_X;
	private int selected_dest_point_Y;
	private Drawable CMRL_Drawable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result_map);

		this.action_move = false;
		this.selected_src_station = null;
		this.selected_dest_station = null;
		this.selected_src_point_X = 0;
		this.selected_src_point_Y = 0;
		this.selected_dest_point_X = 0;
		this.selected_dest_point_Y = 0;
		 
		
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		final ImageView CMRLMapView = (ImageView) findViewById(R.id.Route_Map);
		//String imagePath = Environment.getExternalStorageDirectory().toString() + "/CMRL.jpg";
		String hard_path = "/storage/sdcard1/CMRL.jpg";
		//String datapath = Environment.getDataDirectory().toString();
		//String dataitem = getApplicationContext().getFilesDir().getPath();
		setupActionBar();
		
	
		try {
			this.CMRL_Drawable = Drawable.createFromStream(getAssets().open("CMRL.jpg"), null);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		
//		this.CMRL_Drawable =  getResources().getDrawable( R.drawable.cmrl);
		//CMRLMapView.setImageResource(R.drawable.cmrl);
		
		
		CMRLMapView.setImageDrawable(this.CMRL_Drawable);
		CMRLMapView.setScaleType(ScaleType.FIT_XY);
		
		CMRLMapView.setScaleX(2);
		CMRLMapView.setScaleY(2);
		
//		this.CMRL_Drawable.setBounds(CMRLMapView.getLeft()+100, CMRLMapView.getTop()+100, CMRLMapView.getRight()+100, CMRLMapView.getBottom()+100);

		Toast.makeText(getApplicationContext(),"Use two fingers to drag map and one finger to select station",Toast.LENGTH_LONG).show();
		
		CMRLMapView.setLongClickable(true);
		
		CMRLMapView.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View arg0) {
				Toast.makeText(getApplicationContext(),"Long press" ,Toast.LENGTH_SHORT).show();
				return true;
			}
		});
		
		

		CMRLMapView.setOnTouchListener(new OnTouchListener() {
			
			private boolean pointer_down_count;

			@Override
			public boolean onTouch(View parent, MotionEvent event) {
				// TODO Auto-generated method stub
		
				float X_coord = 0;
				float Y_coord = 0;
				float Curr_X_coord=0;
				float Curr_Y_coord=0;
				
				switch (event.getAction()) 
				{
				case MotionEvent.ACTION_DOWN:
				
					
					
					X_coord = event.getX();
					Y_coord = event.getY();
				
					//Toast.makeText(getApplicationContext(), Float.toString(X_coord) + ":" +Float.toString(Y_coord), Toast.LENGTH_SHORT).show();
				
					memory_x = X_coord;
					memory_y = Y_coord;
					break;

				case MotionEvent.ACTION_MOVE:
					
					Curr_X_coord = event.getX();
					Curr_Y_coord = event.getY();
					
					int historySize = event.getHistorySize();
					//Toast.makeText(getApplicationContext(),Integer.toString(historySize) ,Toast.LENGTH_SHORT).show();
					
					//if(historySize>=3)
					if(pointer_down_count)
					{
						if(!action_move)
						{
							
						}
						
						action_move = true;
						
						CMRLMapView.scrollBy((int)(memory_x-Curr_X_coord),(int)(memory_y-Curr_Y_coord));
						
						int scroll_X = CMRLMapView.getScrollX();
						int scroll_Y = CMRLMapView.getScrollY();
						
						memory_x = Curr_X_coord;
						memory_y = Curr_Y_coord;
						
						
						if(CMRLMapView.getScaleX() == 1 || CMRLMapView.getScaleY() == 1 )
						{
								memory_x = 0;
								memory_y = 0;
								CMRLMapView.scrollTo((int)0,(int)0);
						}
						//CMRLMapView.scrollTo((int)(Curr_X_coord),(int)(Curr_Y_coord));
						
					}
					
					break;
				case MotionEvent.ACTION_UP:
					/*Curr_X_coord = event.getX();
					Curr_Y_coord = event.getY();*/
					
					/*Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
					vibrator.vibrate(100);
					*/
					
					if(!action_move)
					{
						
						DBHelper db = new DBHelper(getApplicationContext());
						/*try {
							db.createDatabase();
						} catch (IOException e) {
							e.printStackTrace();
						}*/
						
						db.openDatabase();
						
						
						double windowWidth = getWindow().getDecorView().getWidth();
						double windowHeight = getWindow().getDecorView().getHeight();
						float xDensity = event.getX();
						double x = (xDensity / windowWidth) * 100;
						float yDensity = event.getY();
						double y = (yDensity / windowHeight) * 100;
						
						DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
						x = (int) (( event.getX()/displayMetrics.density)+0.5);
						y = (int) (( event.getY()/displayMetrics.density)+0.5);
						
						Paint paint = new Paint();
						
						paint.setColor(Color.BLACK);
						DisplayMetrics temp = new DisplayMetrics();
						
						Drawable drawable = CMRLMapView.getDrawable();
						
						float scale = getResources().getDisplayMetrics().density;
						
						getWindowManager().getDefaultDisplay().getMetrics(temp);
						int width = temp.widthPixels;
						int height = temp.heightPixels;
						
						float screen_dim_X = (float) width/720;
						//float screen_dim_Y = (float) height/1184;
						float screen_dim_Y = (float) height/1280;

						//original height and width of the bitmap
						int intrinsicHeight = drawable.getIntrinsicHeight();
						int intrinsicWidth = drawable.getIntrinsicWidth();
						
						
						Config config = Config.ARGB_8888;
						//Bitmap bitmap = Bitmap.createBitmap((int)((float)parent.getWidth()/screen_dim_X),(int)((float)parent.getHeight()/screen_dim_Y),config );
						//Bitmap bitmap = Bitmap.createBitmap((int)((float)intrinsicWidth/screen_dim_X),(int)((float)intrinsicHeight/screen_dim_Y),config );
						
						int parent_width = parent.getWidth();
						int parent_height = parent.getHeight();
						
						
						//Bitmap bitmap = Bitmap.createBitmap(displayMetrics,parent_width,parent_height,config );
						Bitmap bitmap = Bitmap.createBitmap(parent_width,parent_height,config );
						
//						Bitmap bitmap_immutable = BitmapFactory.decodeResource(getResources(), R.drawable.cmrl);
						//Bitmap bitmap = Bitmap.createBitmap(displayMetrics,bitmap_immutable.getWidth(),bitmap_immutable.getHeight(),config );
//						Bitmap bitmap1 = ((BitmapDrawable)CMRL_Drawable).getBitmap();
						
						//Bitmap bitmap = bitmap1.copy(Bitmap.Config.ARGB_8888, true);
						//Bitmap bitmap = Bitmap.createBitmap((int)((float)bitmap_immutable.getWidth()),(int)((float)bitmap_immutable.getHeight()),config );
						Canvas canvas = new Canvas(bitmap);
						
						
						android.graphics.PorterDuff.Mode mode = Mode.CLEAR;
						canvas.drawColor(0,mode);
						float radius = 5;
						
						
						
						
						
						
						Rect imageBounds = drawable.getBounds();
						
						//screen_dim_X = 1;
						//screen_dim_Y = 1;
						


						//height and width of the visible (scaled) image
						int scaledHeight = imageBounds.height();
						int scaledWidth = imageBounds.width();

						
						int Actual_scaledHeight	= 876;	
						int Actual_scaledWidth =656;
						
						float scale_ratio_X = (float)scaledWidth/(float)Actual_scaledWidth;
						float scale_ratio_Y = (float)scaledHeight/(float)Actual_scaledHeight;
						
						float scaled_X = event.getX()/scale_ratio_X;
						float scaled_Y = event.getY()/scale_ratio_Y;
						

						
						//Find the ratio of the original image to the scaled image
						//Should normally be equal unless a disproportionate scaling
						//(e.g. fitXY) is used.
						float heightRatio = (float)intrinsicHeight / (float)scaledHeight;
						float widthRatio = (float)intrinsicWidth / (float)scaledWidth;

						//do whatever magic to get your touch point
						//MotionEvent event;

						float X_Scroll = CMRLMapView.getScrollX();
						float Y_Scroll = CMRLMapView.getScrollY();
						
						//X_Scroll = X_Scroll / screen_dim_X;
						//Y_Scroll = Y_Scroll / screen_dim_Y;
						float Scaled_X_Scroll = X_Scroll/scale_ratio_X; 
						float Scaled_Y_Scroll = Y_Scroll/scale_ratio_Y;
						
						float Scaled_Touch_point_X = scaled_X + Scaled_X_Scroll;
						float Scaled_Touch_point_Y = scaled_Y + Scaled_Y_Scroll;
						
						//Toast.makeText(getApplicationContext(),"X :" + Float.toString(Scaled_Touch_point_X) + " Y : " + Float.toString(Scaled_Touch_point_Y) ,Toast.LENGTH_SHORT).show();
						
						double X_Scroll_dip = (int) (( X_Scroll/displayMetrics.density)+0.5);
						double Y_Scroll_dip = (int) (( Y_Scroll/displayMetrics.density)+0.5);
						
						//get the distance from the left and top of the image bounds
						float scaledImageOffsetX = (event.getX() / screen_dim_X)- imageBounds.left +( X_Scroll/screen_dim_X);
						float scaledImageOffsetY = (event.getY() / screen_dim_Y)- imageBounds.top + (Y_Scroll/screen_dim_Y);
						
						
						
						
						
					///	Toast.makeText(getApplicationContext(),Integer.toString((int) scaledImageOffsetX) + " : " + Integer.toString((int) scaledImageOffsetY) ,Toast.LENGTH_SHORT).show();
						
						scaledImageOffsetX = (float) (x- imageBounds.left + X_Scroll_dip);
						scaledImageOffsetY = (float) (y- imageBounds.top + Y_Scroll_dip);
						
						scaledImageOffsetX = (float)Scaled_Touch_point_X;
						scaledImageOffsetY = (float)Scaled_Touch_point_Y;

					///	Toast.makeText(getApplicationContext(),Integer.toString((int) scaledImageOffsetX) + " : " + Integer.toString((int) scaledImageOffsetY) ,Toast.LENGTH_SHORT).show();
						List<String> Nearest_Station_Position = db.get_Station_List_near_points((int)scaledImageOffsetX, (int)scaledImageOffsetY, 50);
						
						x = (float) (( scaledImageOffsetX*displayMetrics.density)+0.5);
						y = (float) (( scaledImageOffsetY*displayMetrics.density)+0.5);

						/*x = (float) event.getX() - imageBounds.left + X_Scroll;
						y = (float) event.getY() - imageBounds.top + Y_Scroll;*/
						
						int h1 = CMRL_Drawable.getIntrinsicHeight();
						int w1 = CMRL_Drawable.getIntrinsicWidth();
						
						int h2 = bitmap.getHeight();
						int w2 = bitmap.getWidth();
						
						//canvas.drawCircle(scaledImageOffsetX * screen_dim_X, scaledImageOffsetY*screen_dim_Y, radius, paint);
						
						//canvas.drawCircle((float)x, (float)y, radius, paint);
						
						//canvas.drawCircle((float)event.getX(), (float)event.getY(), radius, paint);
						//Nearest_Station_Position.clear();
						
						if(Nearest_Station_Position.isEmpty())
						{
							Toast.makeText(getApplicationContext(),"No stations found near" ,Toast.LENGTH_SHORT).show();
		
							/*Drawable[] layers = new Drawable[2];
//							canvas.drawCircle((int) scaledImageOffsetX, scaledImageOffsetY, radius, paint);
							layers[0] = CMRL_Drawable;
							
							BitmapDrawable EditDrwable = new BitmapDrawable(getResources(), bitmap);
							layers[1] = EditDrwable;
							//EditDrwable.setBounds(CMRLMapView.getLeft()+100, CMRLMapView.getTop()+100, CMRLMapView.getRight()+100, CMRLMapView.getBottom()+100);
							
							//EditDrwable.setBounds(imageBounds.left,imageBounds.top,imageBounds.right, imageBounds.bottom);
							LayerDrawable layerDrawable = new LayerDrawable(layers);
							CMRLMapView.setImageDrawable(layerDrawable);*/
							//CMRLMapView.setImageDrawable(EditDrwable);

						}
						else
						{
							//scale these distances according to the ratio of your scaling
							//For example, if the original image is 1.5x the size of the scaled
							//image, and your offset is (10, 20), your original image offset
							//values should be (15, 30). 
							float NearestImageOffsetX = Float.parseFloat(Nearest_Station_Position.get(0));
							float NearestImageOffsetY = Float.parseFloat(Nearest_Station_Position.get(1));
							String NearestStation = Nearest_Station_Position.get(2);
							
							Toast.makeText(getApplicationContext(),"Nearest Station : "+ NearestStation ,Toast.LENGTH_SHORT).show();
							
							/*float originalImageOffsetX = NearestImageOffsetX * widthRatio;
							float originalImageOffsetY = NearestImageOffsetY * heightRatio;*/
							
							float originalImageOffsetX = NearestImageOffsetX * scale_ratio_X;
							float originalImageOffsetY = NearestImageOffsetY * scale_ratio_Y;
							
							Drawable[] layers = new Drawable[2];
							canvas.drawCircle(originalImageOffsetX , originalImageOffsetY, radius*scale_ratio_X, paint);
							layers[0] = CMRL_Drawable;
							
							if(selected_src_station == null || ((selected_src_station !=null) && (selected_dest_station!=null)))
							{
								selected_src_station = NearestStation;
								selected_src_point_X = (int) NearestImageOffsetX;
								selected_src_point_Y = (int) NearestImageOffsetY;
								
								selected_dest_station = null;
								selected_dest_point_X = 0;
								selected_dest_point_Y = 0;

								//canvas.drawLine(NearestImageOffsetX, NearestImageOffsetY, NearestImageOffsetX-40, NearestImageOffsetY+40, paint);
								//canvas.drawCircle(memory_x, memory_y, radius, paint);
								BitmapDrawable EditDrwable = new BitmapDrawable(getResources(), bitmap);
								

								//layers[0] = CMRLMapdrawable;
								layers[1] = EditDrwable;

							}
							else if(selected_dest_station == null)
							{
								if(selected_src_station.equalsIgnoreCase(NearestStation))
									break;
								selected_dest_station = NearestStation;
								selected_dest_point_X = (int) NearestImageOffsetX;
								selected_dest_point_Y = (int) NearestImageOffsetY;
								//canvas.drawLine(NearestImageOffsetX, NearestImageOffsetY, selected_src_point_X, selected_src_point_Y, paint);
								BitmapDrawable EditDrwable = new BitmapDrawable(getResources(), bitmap);
								List<Route_Search_Result> Route_Search_List = Search_Route_by_Src_Dest(selected_src_station, selected_dest_station);
								
								List<String> station_list = Route_Search_List.get(0).getRoutes_Stations_List();
								for (String current_station : station_list) {
									List<Integer> station_point = db.get_Station_Points_In_Map(current_station); 
									canvas.drawCircle(station_point.get(0) * scale_ratio_X,station_point.get(1) * scale_ratio_Y, radius*scale_ratio_X, paint);
								}
								
								paint.setColor(Color.RED);
								
								//layers[0] = drawable;
								layers[1] = EditDrwable;	
							}

							LayerDrawable layerDrawable = new LayerDrawable(layers);
							CMRLMapView.setImageDrawable(layerDrawable);
							db.closeDataBase();
						}
						
					
						
					}
					
					action_move = false;
					
					/*CMRLMapView.scrollBy((int)(my-Curr_X_coord),(int)(my-Curr_Y_coord));
					//CMRLMapView.scrollTo((int)(Curr_X_coord),(int)(Curr_Y_coord));
					
					my = Curr_X_coord;
					my = Curr_Y_coord;*/
					
					
					
					break;
				case MotionEvent.ACTION_CANCEL:
					//Toast.makeText(getApplicationContext(),"Cancel",Toast.LENGTH_SHORT).show();
					break;
				case MotionEvent.ACTION_SCROLL:
					//Toast.makeText(getApplicationContext(),"scroll",Toast.LENGTH_SHORT).show();
					break;
				case MotionEvent.ACTION_POINTER_1_DOWN:
					pointer_down_count = false;
					//Toast.makeText(getApplicationContext(),"1 down",Toast.LENGTH_SHORT).show();
					break;
				case MotionEvent.ACTION_POINTER_1_UP:
					pointer_down_count = false;
					//Toast.makeText(getApplicationContext(),"1 up",Toast.LENGTH_SHORT).show();
					break;
				case MotionEvent.ACTION_POINTER_2_DOWN:
					pointer_down_count = true;
					//Toast.makeText(getApplicationContext(),"2 down",Toast.LENGTH_SHORT).show();
					break;
				case MotionEvent.ACTION_POINTER_2_UP:
					pointer_down_count = false;
					//Toast.makeText(getApplicationContext(),"2 up",Toast.LENGTH_SHORT).show();
					break;
				default:
					//Toast.makeText(getApplicationContext(),"Default",Toast.LENGTH_SHORT).show();
					//MotionEvent.actionToString(event.getAction());
					break;
				}
				return true;
			}
		});
		
		ZoomControls MapZoomCntrl = (ZoomControls) findViewById(R.id.MapViewZoom);

		MapZoomCntrl.setOnZoomInClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				/*float[] f = new float[9];
		        CMRLMapView.getImageMatrix().getValues(f);

		        // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)
		        final float scaleX = f[Matrix.MSCALE_X];
		        final float scaleY = f[Matrix.MSCALE_Y];
*/				
				
				float x_scale = CMRLMapView.getScaleX();
				float y_scale = CMRLMapView.getScaleY();
				
				CMRLMapView.setScaleX(x_scale+1);
				CMRLMapView.setScaleY(y_scale+1);
				
			}
		});
		
		
		MapZoomCntrl.setOnZoomOutClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				float x_scale = CMRLMapView.getScaleX();
				float y_scale = CMRLMapView.getScaleY();
				
				
				
				if(x_scale > 1 && y_scale >1)
				{
					CMRLMapView.setScaleX(x_scale-1);
					CMRLMapView.setScaleY(y_scale-1);
				}
				
				if(x_scale > 1 && y_scale >1)
				{
					CMRLMapView.scrollTo((int)0,(int)0);
				}
				
			}
		});
		
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
