package com.nexes.manager;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//import java.net.URI;
//import java.net.URISyntaxException;
import java.util.ArrayList;

import android.app.Activity;
//import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
//import android.content.BroadcastReceiver;
//import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
//import android.view.Window;
//import android.widget.TextView;
//import android.widget.Toast;
import com.simp.simplock.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class ShareActivity extends Activity {
	//private EventHandler mHandler;
	//MyTask task ;
	Uri imageuri;
	ArrayList<Uri> imageuris;
	SharedPreferences password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_share);
		
		 Intent intent = getIntent();
		    String action = intent.getAction();
		    String type = intent.getType();
		    Dialog dialog = new Dialog(this);
		    password=this.getSharedPreferences("simplock",Context.MODE_WORLD_READABLE);
		    loadadvert();
		   
		    if (Intent.ACTION_SEND.equals(action) && type != null) {
		        if (type.startsWith("image/")) {
		        	
		        	//ProgressDialog progress = new ProgressDialog(this);
		        	//progress.setMessage("Hiding...");
		        	
		        	super.onCreate(savedInstanceState);
		        	setContentView(R.layout.activity_share);
		        	loadadvert();
		        						
		        	imageuri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
		        	new MyTask(1).execute();
		        	/*File from=new File(getRealPathFromURIimage(getApplicationContext(), imageuri).replace("legacy","0"));
		        	
		        	File dir=new File(Environment.getExternalStorageDirectory()+File.separator+".simplock/.Image/");
		        	if(movefile(dir,from))
		        	{
		        	if(!imageuri.toString().startsWith("file"))
		        		getContentResolver().delete(imageuri, null, null);
		        	else
		        	{
		        		MediaScannerConnection.scanFile(getApplicationContext(),
					            new String[] { from.toString() }, null,
					            new MediaScannerConnection.OnScanCompletedListener() {
					        public void onScanCompleted(String path, Uri uri) {
					            Log.i("ExternalStorage", "Scanned " + path + ":");
					            Log.i("ExternalStorage", "-> uri=" + uri);
					            getContentResolver().delete(uri, null, null);
					        }
					    });
		        	}
		        	}
		        	else
		        	{
		        		dir=new File(Environment.getExternalStorageDirectory()+File.separator+".simplock/.Image/"+from.getName());
		        		try {
							copy(from,dir);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        		from.delete();
		        		if(!imageuri.toString().startsWith("file"))
			        		getContentResolver().delete(imageuri, null, null);
			        	else
			        	{
			        		MediaScannerConnection.scanFile(getApplicationContext(),
						            new String[] { from.toString() }, null,
						            new MediaScannerConnection.OnScanCompletedListener() {
						        public void onScanCompleted(String path, Uri uri) {
						            Log.i("ExternalStorage", "Scanned " + path + ":");
						            Log.i("ExternalStorage", "-> uri=" + uri);
						            getContentResolver().delete(uri, null, null);
						        }
						    });
			        	}
		        		
		        	}*/
		        	
		        	//finish();
		        }
		        else if (type.startsWith("video/")) {
		        	
		        	super.onCreate(savedInstanceState);
		        	setContentView(R.layout.activity_share);
		        	loadadvert();
		        	imageuri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
		        	new MyTask(2).execute();
		        	/*File from=new File(getRealPathFromURIvideo(getApplicationContext(),imageuri).replace("legacy","0"));
		        	File dir=new File(Environment.getExternalStorageDirectory()+File.separator+".simplock/.Video/");
		        	if(movefile(dir,from)){
		        	if(!imageuri.toString().startsWith("file"))
		        	getContentResolver().delete(imageuri, null, null);
		        	else
		        	{
		        		MediaScannerConnection.scanFile(getApplicationContext(),
					            new String[] { from.toString() }, null,
					            new MediaScannerConnection.OnScanCompletedListener() {
					        public void onScanCompleted(String path, Uri uri) {
					            Log.i("ExternalStorage", "Scanned " + path + ":");
					            Log.i("ExternalStorage", "-> uri=" + uri);
					            getContentResolver().delete(uri, null, null);
					        }
					    });
		        	}}
		        	else
		        	{
		        		dir=new File(Environment.getExternalStorageDirectory()+File.separator+".simplock/.Video/"+from.getName());
		        		try {
							copy(from,dir);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        		//new MyTask(true).execute(from,dir);
		        		from.delete();
		        		if(!imageuri.toString().startsWith("file"))
			        		getContentResolver().delete(imageuri, null, null);
			        	else
			        	{
			        		MediaScannerConnection.scanFile(getApplicationContext(),
						            new String[] { from.toString() }, null,
						            new MediaScannerConnection.OnScanCompletedListener() {
						        public void onScanCompleted(String path, Uri uri) {
						            Log.i("ExternalStorage", "Scanned " + path + ":");
						            Log.i("ExternalStorage", "-> uri=" + uri);
						            getContentResolver().delete(uri, null, null);
						        }
						    });
			        	}
		        		
		        	}*/
		        	//finish();
		        }
		        else if(type.startsWith("audio/")) 
		        {
		        	
		        	super.onCreate(savedInstanceState);
		        	setContentView(R.layout.activity_share);
		        	loadadvert();
		        	imageuri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
		        	new MyTask(3).execute();
		        	/*File dir=new File(Environment.getExternalStorageDirectory()+File.separator+".simplock/.Audio/");
		        	File from=new File(getRealPathFromURIaudeo(getApplicationContext(), imageuri).replace("legacy","0"));
		        	if(movefile(dir,from))
		        	{
		        	if(!imageuri.toString().startsWith("file"))
		        	getContentResolver().delete(imageuri, null, null);
		        	else
		        	{
		        		MediaScannerConnection.scanFile(getApplicationContext(),
					            new String[] { from.toString() }, null,
					            new MediaScannerConnection.OnScanCompletedListener() {
					        public void onScanCompleted(String path, Uri uri) {
					            Log.i("ExternalStorage", "Scanned " + path + ":");
					            Log.i("ExternalStorage", "-> uri=" + uri);
					            getContentResolver().delete(uri, null, null);
					        }
					    });
		        	}
		        	}
		        	else
		        	{
		        		dir=new File(Environment.getExternalStorageDirectory()+File.separator+".simplock/.Audio/"+from.getName());
		        		try {
							copy(from,dir);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        		//new MyTask(true).execute(from,dir);
		        		from.delete();
		        		if(!imageuri.toString().startsWith("file"))
			        		getContentResolver().delete(imageuri, null, null);
			        	else
			        	{
			        		MediaScannerConnection.scanFile(getApplicationContext(),
						            new String[] { from.toString() }, null,
						            new MediaScannerConnection.OnScanCompletedListener() {
						        public void onScanCompleted(String path, Uri uri) {
						            Log.i("ExternalStorage", "Scanned " + path + ":");
						            Log.i("ExternalStorage", "-> uri=" + uri);
						            getContentResolver().delete(uri, null, null);
						        }
						    });
			        	}
		        		
		        	}
		        	finish();*/
		        }
		        else
		        {
		        	//super.onCreate(savedInstanceState);
		        	//setContentView(R.layout.activity_share);
		        	//Uri imageuri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
		        	//String path = imageuri.getEncodedPath() ;// "file:///mnt/sdcard/FileName.mp3"
		        	super.onCreate(savedInstanceState);
		        	setContentView(R.layout.activity_share);
		        	loadadvert();
		    		imageuri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
		    		new MyTask(4).execute();
		    		//Toast.makeText(getApplicationContext(),getRealPathFromURIvideo(getApplicationContext(), imageuri),Toast.LENGTH_SHORT).show();
		        	/*File dir=new File(Environment.getExternalStorageDirectory()+File.separator+".simplock/.Other/");
		        	File from=new File(getRealPathFromURIaudeo(getApplicationContext(), imageuri).replace("legacy","0"));
		        	if(movefile(dir,from))
		        	{
		        	if(!imageuri.toString().startsWith("file"))
		        	getContentResolver().delete(imageuri, null, null);
		        	else
		        	{
		        		MediaScannerConnection.scanFile(getApplicationContext(),
					            new String[] { from.toString() }, null,
					            new MediaScannerConnection.OnScanCompletedListener() {
					        public void onScanCompleted(String path, Uri uri) {
					            Log.i("ExternalStorage", "Scanned " + path + ":");
					            Log.i("ExternalStorage", "-> uri=" + uri);
					            getContentResolver().delete(uri, null, null);
					        }
					    });
		        	}
		        	}
		        	else
		        	{
		        		dir=new File(Environment.getExternalStorageDirectory()+File.separator+".simplock/.Other/"+from.getName());
		        		try {
							copy(from,dir);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        		//new MyTask(true).execute(from,dir);
		        		from.delete();
		        		if(!imageuri.toString().startsWith("file"))
			        		getContentResolver().delete(imageuri, null, null);
			        	else
			        	{
			        		MediaScannerConnection.scanFile(getApplicationContext(),
						            new String[] { from.toString() }, null,
						            new MediaScannerConnection.OnScanCompletedListener() {
						        public void onScanCompleted(String path, Uri uri) {
						            Log.i("ExternalStorage", "Scanned " + path + ":");
						            Log.i("ExternalStorage", "-> uri=" + uri);
						            getContentResolver().delete(uri, null, null);
						        }
						    });
			        	}
		        		
		        	}
		        	finish();*/
		        	
		        }
		    } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
		        if (type.startsWith("image/")) {
		        	super.onCreate(savedInstanceState);
		        	setContentView(R.layout.activity_share);
		        	loadadvert();
		        	imageuris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
		        	new MyTask(5).execute();
		        	/*File dir=new File(Environment.getExternalStorageDirectory()+File.separator+".simplock/.Image/");
		        	int s=imageuris.size();
		        	for(int i=0;i<imageuris.size();i++)
		        	{
		        		int k=i+1;
		        		//text.setText("HIDING "+k+"/"+s);
		        		//Toast.makeText(getApplicationContext(),getRealPathFromURIimage(getApplicationContext(), imageuris.get(i)),Toast.LENGTH_SHORT).show();
		        		File from=new File(getRealPathFromURIimage(getApplicationContext(), imageuris.get(i)).replace("legacy","0"));
		        		if(movefile(dir,from)){
		        		if(!imageuris.get(i).toString().startsWith("file"))
		        		getContentResolver().delete(imageuris.get(i), null, null);
		        		else
		        		{
		        			MediaScannerConnection.scanFile(getApplicationContext(),
						            new String[] { from.toString() }, null,
						            new MediaScannerConnection.OnScanCompletedListener() {
						        public void onScanCompleted(String path, Uri uri) {
						            Log.i("ExternalStorage", "Scanned " + path + ":");
						            Log.i("ExternalStorage", "-> uri=" + uri);
						            getContentResolver().delete(uri, null, null);
						        }
						    });
		        		}
		        		}
		        		else
			        	{
			        		dir=new File(Environment.getExternalStorageDirectory()+File.separator+".simplock/.Image/"+from.getName());
			        		try {
								copy(from,dir);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			            	
			        		//new MyTask(false).execute(from,dir);
			        		from.delete();
			        		if(!imageuris.get(i).toString().startsWith("file"))
				        		getContentResolver().delete(imageuris.get(i), null, null);
				        	else
				        	{
				        		MediaScannerConnection.scanFile(getApplicationContext(),
							            new String[] { from.toString() }, null,
							            new MediaScannerConnection.OnScanCompletedListener() {
							        public void onScanCompleted(String path, Uri uri) {
							            Log.i("ExternalStorage", "Scanned " + path + ":");
							            Log.i("ExternalStorage", "-> uri=" + uri);
							            getContentResolver().delete(uri, null, null);
							        }
							    });
				        	}
			        		
			        	}
		        	}
		        	finish();*/
		        }
		        else if (type.startsWith("video/")) {
		        	super.onCreate(savedInstanceState);
		        	setContentView(R.layout.activity_share);
		        	loadadvert();
		        	imageuris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
		        	new MyTask(6).execute();
		        	/*File dir=new File(Environment.getExternalStorageDirectory()+File.separator+".simplock/.Video/");
		        	int s=imageuris.size();
		        	for(int i=0;i<imageuris.size();i++)
		     
		        	{
		        		int k=i+1;
		        		//text.setText("HIDING "+k+"/"+s);
		        		//Toast.makeText(getApplicationContext(),getRealPathFromURIvideo(getApplicationContext(), imageuris.get(i)),Toast.LENGTH_SHORT).show();
		        		File from=new File(getRealPathFromURIvideo(getApplicationContext(), imageuris.get(i)).replace("legacy","0"));
		        		if(movefile(dir,from)){
		        		if(!imageuris.get(i).toString().startsWith("file"))
		        		getContentResolver().delete(imageuris.get(i), null, null);
		        		else
		        		{
		        			MediaScannerConnection.scanFile(getApplicationContext(),
						            new String[] { from.toString() }, null,
						            new MediaScannerConnection.OnScanCompletedListener() {
						        public void onScanCompleted(String path, Uri uri) {
						            Log.i("ExternalStorage", "Scanned " + path + ":");
						            Log.i("ExternalStorage", "-> uri=" + uri);
						            getContentResolver().delete(uri, null, null);
						        }
						    });
		        		}
		        		}
		        		else
			        	{
			        		dir=new File(Environment.getExternalStorageDirectory()+File.separator+".simplock/.Video/"+from.getName());
			        		try {
								copy(from,dir);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			        		//new MyTask(false).execute(from,dir);
			        		from.delete();
			        		if(!imageuris.get(i).toString().startsWith("file"))
				        		getContentResolver().delete(imageuris.get(i), null, null);
				        	else
				        	{
				        		MediaScannerConnection.scanFile(getApplicationContext(),
							            new String[] { from.toString() }, null,
							            new MediaScannerConnection.OnScanCompletedListener() {
							        public void onScanCompleted(String path, Uri uri) {
							            Log.i("ExternalStorage", "Scanned " + path + ":");
							            Log.i("ExternalStorage", "-> uri=" + uri);
							            getContentResolver().delete(uri, null, null);
							        }
							    });
				        	}
			        		
			        	}
		        	}
		        	finish();*/
		        }
		        else if (type.startsWith("audio/")) {
		        	super.onCreate(savedInstanceState);
		        	setContentView(R.layout.activity_share);
		        	loadadvert();
		        	imageuris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
		        	new MyTask(7).execute();
		        	/*File dir=new File(Environment.getExternalStorageDirectory()+File.separator+".simplock/.Audio/");
		        	int s=imageuris.size();
		        	for(int i=0;i<imageuris.size();i++)
		        	{
		        		int k=i+1;
		        		//text.setText("HIDING "+k+"/"+s);
		        		//Toast.makeText(getApplicationContext(),getRealPathFromURIvideo(getApplicationContext(), imageuris.get(i)),Toast.LENGTH_SHORT).show();
		        		File from=new File(getRealPathFromURIaudeo(getApplicationContext(), imageuris.get(i)).replace("legacy","0"));
		        		if(movefile(dir,from)){
		        		if(!imageuris.get(i).toString().startsWith("file"))
		        		getContentResolver().delete(imageuris.get(i), null, null);
		        		else
		        		{
		        			MediaScannerConnection.scanFile(getApplicationContext(),
						            new String[] { from.toString() }, null,
						            new MediaScannerConnection.OnScanCompletedListener() {
						        public void onScanCompleted(String path, Uri uri) {
						            Log.i("ExternalStorage", "Scanned " + path + ":");
						            Log.i("ExternalStorage", "-> uri=" + uri);
						            getContentResolver().delete(uri, null, null);
						        }
						    });
		        		}
		        	}
		        		else
			        	{
			        		dir=new File(Environment.getExternalStorageDirectory()+File.separator+".simplock/.Audio/"+from.getName());
			        		try {
								copy(from,dir);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			        		//new MyTask(false).execute(from,dir);
			        		from.delete();
			        		if(!imageuris.get(i).toString().startsWith("file"))
				        		getContentResolver().delete(imageuris.get(i), null, null);
				        	else
				        	{
				        		MediaScannerConnection.scanFile(getApplicationContext(),
							            new String[] { from.toString() }, null,
							            new MediaScannerConnection.OnScanCompletedListener() {
							        public void onScanCompleted(String path, Uri uri) {
							            Log.i("ExternalStorage", "Scanned " + path + ":");
							            Log.i("ExternalStorage", "-> uri=" + uri);
							            getContentResolver().delete(uri, null, null);
							        }
							    });
				        	}
			        		
			        	}
		        	
		        	}
		        	finish();*/
		        }
		        else
		        {
		        	super.onCreate(savedInstanceState);
		        	setContentView(R.layout.activity_share);
		        	loadadvert();
		        	imageuris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
		        	new MyTask(8).execute();
		        	/*File dir=new File(Environment.getExternalStorageDirectory()+File.separator+".simplock/.Other/");
		        	int s=imageuris.size();
		        	for(int i=0;i<imageuris.size();i++)
		        	{
		        		int k=i+1;
		        		//text.setText("HIDING "+k+"/"+s);
		        		//Toast.makeText(getApplicationContext(),getRealPathFromURIvideo(getApplicationContext(), imageuris.get(i)),Toast.LENGTH_SHORT).show();
		        		File from=new File(getRealPathFromURIaudeo(getApplicationContext(), imageuris.get(i)).replace("legacy","0"));
		        		if(movefile(dir,from)){
		        		if(!imageuris.get(i).toString().startsWith("file"))
		        		getContentResolver().delete(imageuris.get(i), null, null);
		        		else
		        		{
		        			MediaScannerConnection.scanFile(getApplicationContext(),
						            new String[] { from.toString() }, null,
						            new MediaScannerConnection.OnScanCompletedListener() {
						        public void onScanCompleted(String path, Uri uri) {
						            Log.i("ExternalStorage", "Scanned " + path + ":");
						            Log.i("ExternalStorage", "-> uri=" + uri);
						            getContentResolver().delete(uri, null, null);
						        }
						    });
		        		}
		        	}
		        		else
			        	{
			        		dir=new File(Environment.getExternalStorageDirectory()+File.separator+".simplock/.Other/"+from.getName());
			        		try {
								copy(from,dir);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			        		//new MyTask(false).execute(from,dir);
			        		from.delete();
			        		if(!imageuris.get(i).toString().startsWith("file"))
				        		getContentResolver().delete(imageuris.get(i), null, null);
				        	else
				        	{
				        		MediaScannerConnection.scanFile(getApplicationContext(),
							            new String[] { from.toString() }, null,
							            new MediaScannerConnection.OnScanCompletedListener() {
							        public void onScanCompleted(String path, Uri uri) {
							            Log.i("ExternalStorage", "Scanned " + path + ":");
							            Log.i("ExternalStorage", "-> uri=" + uri);
							            getContentResolver().delete(uri, null, null);
							        }
							    });
				        	}
			        		
			        	}
		        	
		        	}
		        	finish();*/
		        }
		        
		    } 
		    
		    	else {
		    
		    	super.onCreate(savedInstanceState);
	        	setContentView(R.layout.activity_share);
	        	//Uri imageuri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
	    		
	        	
	        	//Toast.makeText(getApplicationContext(),"share",Toast.LENGTH_SHORT).show();
	

		        // Handle other intents, such as being started from the home screen
		    }
	}
	public void loadadvert()
	{
		//begin ad  ca-app-pub-9164569835667595/7969967069
      //  interstitial = new InterstitialAd(Main.this);
       // Toast.makeText(getApplicationContext(),"ads",Toast.LENGTH_SHORT).show();
    	// Insert the Ad Unit ID
    	//interstitial.setAdUnitId("ca-app-pub-9164569835667595/7969967069");

    	//Locate the Banner Ad in activity_main.xml
    	AdView adView = (AdView) this.findViewById(R.id.adView);
    	//AdView adView2= (AdView) this.findViewById(R.id.adView2);
    	// Request for Ads
    	AdRequest adRequest = new AdRequest.Builder()

    	// Add a test device to show Test Ads
    	//.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
    	//.addTestDevice("A86D0DC3D97A32BB11230CFFCAE0CF86")
    			.build();

    	// Load ads into Banner Ads
    	adView.loadAd(adRequest);
    	//adView2.loadAd(adRequest);
    	// Load ads into Interstitial Ads
    	//interstitial.loadAd(adRequest);
	}
	public void copy(File src, File dst) throws IOException {
	    InputStream in = new FileInputStream(src);
	    OutputStream out = new FileOutputStream(dst);

	    // Transfer bytes from in to out
	    byte[] buf = new byte[1024];
	    int len;
	    while ((len = in.read(buf)) > 0) {
	        out.write(buf, 0, len);
	    }
	    in.close();
	    out.close();
	}
	public boolean movefile(File hiddir,File from)
	{
		hiddir.mkdirs();
		 File to= new File(hiddir.getAbsolutePath()+File.separator+from.getName());
	     if(from.renameTo(to))
	     {
	    	 //Toast.makeText(getApplicationContext(),"Move Success",Toast.LENGTH_SHORT).show();
	    	 return true;
	     }
	     else
	    	 return false;
	}
	public String getRealPathFromURIimage(Context context, Uri contentUri) {
		  Cursor cursor = null;
		  if(contentUri.toString().startsWith("file"))
			  return contentUri.toString().replace("file://","").replace("%20"," ");
		  try { 
		    String[] proj = { MediaStore.Images.Media.DATA };
		    cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
		    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		    cursor.moveToFirst();
		    return cursor.getString(column_index);
		  } finally {
		    if (cursor != null) {
		      cursor.close();
		    }
		  }
		}
	public String getRealPathFromURIaudeo(Context context, Uri contentUri) {
		  Cursor cursor = null;
		  if(contentUri.toString().startsWith("file"))
			  return contentUri.toString().replace("file://","").replace("%20"," ");
		  try { 
		    String[] proj = { MediaStore.Audio.Media.DATA };
		    cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
		    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
		    cursor.moveToFirst();
		    return cursor.getString(column_index);
		  } finally {
		    if (cursor != null) {
		      cursor.close();
		    }
		  }
		}
	
	public String getRealPathFromURIvideo(Context context, Uri contentUri) {
		  Cursor cursor = null;
		  if(contentUri.toString().startsWith("file"))
			  return contentUri.toString().replace("file://","").replace("%20"," ");
		  try { 
		    String[] proj = { MediaStore.Video.Media.DATA };
		    cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
		    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
		    cursor.moveToFirst();
		    return cursor.getString(column_index);
		  } finally {
		    if (cursor != null) {
		      cursor.close();
		    }
		  }
		}
	public static String getFileNameByUri(Context context, Uri uri)
	{
	    String fileName="unknown";//default fileName
	    Uri filePathUri = uri;
	    if (uri.getScheme().toString().compareTo("content")==0)
	    {      
	        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
	        if (cursor.moveToFirst())
	        {
	            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);//Instead of "MediaStore.Images.Media.DATA" can be used "_data"
	            filePathUri = Uri.parse(cursor.getString(column_index));
	            fileName = filePathUri.getLastPathSegment().toString();
	        }
	    }
	    else if (uri.getScheme().compareTo("file")==0)
	    {
	        fileName = filePathUri.getLastPathSegment().toString();
	    }
	    else
	    {
	        fileName = fileName+"_"+filePathUri.getLastPathSegment();
	    }
	    return fileName;
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.share, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public class MyTask extends AsyncTask<Void,Void,Boolean> {
		   ProgressDialog progress; 
		   int type;
		 public MyTask(int data) {
			   this.type=data;
			  }
		  @Override
		  protected void onPreExecute() {
			 // progress=new ProgressDialog(getApplicationContext());
			 // progress.setMessage("Hiding..");
			  progress = ProgressDialog.show(ShareActivity.this, "Hiding", 
						"Hiding current file",
						true, true);
		  }

		  @Override
		  protected Boolean doInBackground(Void...voids) {
		    switch(type)
		    {
		    case 1:
		    	File from=new File(getRealPathFromURIimage(ShareActivity.this, imageuri).replace("legacy","0"));
	        	
	        	File dir=new File(password.getString("location","/sdcard/.simplock")+"/.Image/");
	        	if(movefile(dir,from))
	        	{
	        	if(!imageuri.toString().startsWith("file"))
	        		getContentResolver().delete(imageuri, null, null);
	        	else
	        	{
	        		MediaScannerConnection.scanFile(ShareActivity.this,
				            new String[] { from.toString() }, null,
				            new MediaScannerConnection.OnScanCompletedListener() {
				        public void onScanCompleted(String path, Uri uri) {
				            Log.i("ExternalStorage", "Scanned " + path + ":");
				            Log.i("ExternalStorage", "-> uri=" + uri);
				            getContentResolver().delete(uri, null, null);
				        }
				    });
	        	}
	        	}
	        	else
	        	{
	        		dir=new File(password.getString("location","/sdcard/.simplock")+"/.Image/"+from.getName());
	        		try {
						copy(from,dir);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		from.delete();
	        		if(!imageuri.toString().startsWith("file"))
		        		getContentResolver().delete(imageuri, null, null);
		        	else
		        	{
		        		MediaScannerConnection.scanFile(ShareActivity.this,
					            new String[] { from.toString() }, null,
					            new MediaScannerConnection.OnScanCompletedListener() {
					        public void onScanCompleted(String path, Uri uri) {
					            Log.i("ExternalStorage", "Scanned " + path + ":");
					            Log.i("ExternalStorage", "-> uri=" + uri);
					            getContentResolver().delete(uri, null, null);
					        }
					    });
		        	}
	        		
	        	}
		    	break;
		    case 2:
		    	from=new File(getRealPathFromURIvideo(ShareActivity.this,imageuri).replace("legacy","0"));
	        	dir=new File(password.getString("location","/sdcard/.simplock")+"/.Video/");
	        	if(movefile(dir,from)){
	        	if(!imageuri.toString().startsWith("file"))
	        	getContentResolver().delete(imageuri, null, null);
	        	else
	        	{
	        		MediaScannerConnection.scanFile(ShareActivity.this,
				            new String[] { from.toString() }, null,
				            new MediaScannerConnection.OnScanCompletedListener() {
				        public void onScanCompleted(String path, Uri uri) {
				            Log.i("ExternalStorage", "Scanned " + path + ":");
				            Log.i("ExternalStorage", "-> uri=" + uri);
				            getContentResolver().delete(uri, null, null);
				        }
				    });
	        	}}
	        	else
	        	{
	        		dir=new File(password.getString("location","/sdcard/.simplock")+"/.Video/"+from.getName());
	        		try {
						copy(from,dir);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		//new MyTask(true).execute(from,dir);
	        		from.delete();
	        		if(!imageuri.toString().startsWith("file"))
		        		getContentResolver().delete(imageuri, null, null);
		        	else
		        	{
		        		MediaScannerConnection.scanFile(ShareActivity.this,
					            new String[] { from.toString() }, null,
					            new MediaScannerConnection.OnScanCompletedListener() {
					        public void onScanCompleted(String path, Uri uri) {
					            Log.i("ExternalStorage", "Scanned " + path + ":");
					            Log.i("ExternalStorage", "-> uri=" + uri);
					            getContentResolver().delete(uri, null, null);
					        }
					    });
		        	}
	        		
	        	}
	        
		    	break;
		    case 3:
		    	dir=new File(password.getString("location","/sdcard/.simplock")+"/.Audio/");
	        	from=new File(getRealPathFromURIaudeo(ShareActivity.this, imageuri).replace("legacy","0"));
	        	if(movefile(dir,from))
	        	{
	        	if(!imageuri.toString().startsWith("file"))
	        	getContentResolver().delete(imageuri, null, null);
	        	else
	        	{
	        		MediaScannerConnection.scanFile(ShareActivity.this,
				            new String[] { from.toString() }, null,
				            new MediaScannerConnection.OnScanCompletedListener() {
				        public void onScanCompleted(String path, Uri uri) {
				            Log.i("ExternalStorage", "Scanned " + path + ":");
				            Log.i("ExternalStorage", "-> uri=" + uri);
				            getContentResolver().delete(uri, null, null);
				        }
				    });
	        	}
	        	}
	        	else
	        	{
	        		dir=new File(password.getString("location","/sdcard/.simplock")+"/.Audio/"+from.getName());
	        		try {
						copy(from,dir);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		//new MyTask(true).execute(from,dir);
	        		from.delete();
	        		if(!imageuri.toString().startsWith("file"))
		        		getContentResolver().delete(imageuri, null, null);
		        	else
		        	{
		        		MediaScannerConnection.scanFile(ShareActivity.this,
					            new String[] { from.toString() }, null,
					            new MediaScannerConnection.OnScanCompletedListener() {
					        public void onScanCompleted(String path, Uri uri) {
					            Log.i("ExternalStorage", "Scanned " + path + ":");
					            Log.i("ExternalStorage", "-> uri=" + uri);
					            getContentResolver().delete(uri, null, null);
					        }
					    });
		        	}
	        		
	        	}
		    	break;
		    case 4:
		    	dir=new File(password.getString("location","/sdcard/.simplock")+"/.Other/");
	        	from=new File(getRealPathFromURIaudeo(ShareActivity.this, imageuri).replace("legacy","0"));
	        	if(movefile(dir,from))
	        	{
	        	if(!imageuri.toString().startsWith("file"))
	        	getContentResolver().delete(imageuri, null, null);
	        	else
	        	{
	        		MediaScannerConnection.scanFile(ShareActivity.this,
				            new String[] { from.toString() }, null,
				            new MediaScannerConnection.OnScanCompletedListener() {
				        public void onScanCompleted(String path, Uri uri) {
				            Log.i("ExternalStorage", "Scanned " + path + ":");
				            Log.i("ExternalStorage", "-> uri=" + uri);
				            getContentResolver().delete(uri, null, null);
				        }
				    });
	        	}
	        	}
	        	else
	        	{
	        		dir=new File(password.getString("location","/sdcard/.simplock")+"/.Other/"+from.getName());
	        		try {
						copy(from,dir);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		//new MyTask(true).execute(from,dir);
	        		from.delete();
	        		if(!imageuri.toString().startsWith("file"))
		        		getContentResolver().delete(imageuri, null, null);
		        	else
		        	{
		        		MediaScannerConnection.scanFile(ShareActivity.this,
					            new String[] { from.toString() }, null,
					            new MediaScannerConnection.OnScanCompletedListener() {
					        public void onScanCompleted(String path, Uri uri) {
					            Log.i("ExternalStorage", "Scanned " + path + ":");
					            Log.i("ExternalStorage", "-> uri=" + uri);
					            getContentResolver().delete(uri, null, null);
					        }
					    });
		        	}
	        		
	        	}
	        	
		    	break;
		    case 5:
		    	dir=new File(password.getString("location","/sdcard/.simplock")+"/.Image/");
	        	int s=imageuris.size();
	        	for(int i=0;i<imageuris.size();i++)
	        	{
	        		int k=i+1;
	        		//text.setText("HIDING "+k+"/"+s);
	        		//Toast.makeText(getApplicationContext(),getRealPathFromURIimage(getApplicationContext(), imageuris.get(i)),Toast.LENGTH_SHORT).show();
	        		from=new File(getRealPathFromURIimage(ShareActivity.this, imageuris.get(i)).replace("legacy","0"));
	        		if(movefile(dir,from)){
	        		if(!imageuris.get(i).toString().startsWith("file"))
	        		getContentResolver().delete(imageuris.get(i), null, null);
	        		else
	        		{
	        			MediaScannerConnection.scanFile(ShareActivity.this,
					            new String[] { from.toString() }, null,
					            new MediaScannerConnection.OnScanCompletedListener() {
					        public void onScanCompleted(String path, Uri uri) {
					            Log.i("ExternalStorage", "Scanned " + path + ":");
					            Log.i("ExternalStorage", "-> uri=" + uri);
					            getContentResolver().delete(uri, null, null);
					        }
					    });
	        		}
	        		}
	        		else
		        	{
		        		dir=new File(password.getString("location","/sdcard/.simplock")+"/.Image/"+from.getName());
		        		try {
							copy(from,dir);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            	
		        		//new MyTask(false).execute(from,dir);
		        		from.delete();
		        		if(!imageuris.get(i).toString().startsWith("file"))
			        		getContentResolver().delete(imageuris.get(i), null, null);
			        	else
			        	{
			        		MediaScannerConnection.scanFile(ShareActivity.this,
						            new String[] { from.toString() }, null,
						            new MediaScannerConnection.OnScanCompletedListener() {
						        public void onScanCompleted(String path, Uri uri) {
						            Log.i("ExternalStorage", "Scanned " + path + ":");
						            Log.i("ExternalStorage", "-> uri=" + uri);
						            getContentResolver().delete(uri, null, null);
						        }
						    });
			        	}
		        		
		        	}
	        	}
	        
		    	break;
		    case 6:
		    	dir=new File(password.getString("location","/sdcard/.simplock")+"/.Video/");
	        	s=imageuris.size();
	        	for(int i=0;i<imageuris.size();i++)
	     
	        	{
	        		int k=i+1;
	        		//text.setText("HIDING "+k+"/"+s);
	        		//Toast.makeText(getApplicationContext(),getRealPathFromURIvideo(getApplicationContext(), imageuris.get(i)),Toast.LENGTH_SHORT).show();
	        		from=new File(getRealPathFromURIvideo(ShareActivity.this, imageuris.get(i)).replace("legacy","0"));
	        		if(movefile(dir,from)){
	        		if(!imageuris.get(i).toString().startsWith("file"))
	        		getContentResolver().delete(imageuris.get(i), null, null);
	        		else
	        		{
	        			MediaScannerConnection.scanFile(ShareActivity.this,
					            new String[] { from.toString() }, null,
					            new MediaScannerConnection.OnScanCompletedListener() {
					        public void onScanCompleted(String path, Uri uri) {
					            Log.i("ExternalStorage", "Scanned " + path + ":");
					            Log.i("ExternalStorage", "-> uri=" + uri);
					            getContentResolver().delete(uri, null, null);
					        }
					    });
	        		}
	        		}
	        		else
		        	{
		        		dir=new File(password.getString("location","/sdcard/.simplock")+"/.Video/"+from.getName());
		        		try {
							copy(from,dir);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        		//new MyTask(false).execute(from,dir);
		        		from.delete();
		        		if(!imageuris.get(i).toString().startsWith("file"))
			        		getContentResolver().delete(imageuris.get(i), null, null);
			        	else
			        	{
			        		MediaScannerConnection.scanFile(ShareActivity.this,
						            new String[] { from.toString() }, null,
						            new MediaScannerConnection.OnScanCompletedListener() {
						        public void onScanCompleted(String path, Uri uri) {
						            Log.i("ExternalStorage", "Scanned " + path + ":");
						            Log.i("ExternalStorage", "-> uri=" + uri);
						            getContentResolver().delete(uri, null, null);
						        }
						    });
			        	}
		        		
		        	}
	        	}
	    
		    	break;
		    case 7:
		    	dir=new File(password.getString("location","/sdcard/.simplock")+"/.Audio/");
	        	s=imageuris.size();
	        	for(int i=0;i<imageuris.size();i++)
	        	{
	        		int k=i+1;
	        		//text.setText("HIDING "+k+"/"+s);
	        		//Toast.makeText(getApplicationContext(),getRealPathFromURIvideo(getApplicationContext(), imageuris.get(i)),Toast.LENGTH_SHORT).show();
	        		from=new File(getRealPathFromURIaudeo(ShareActivity.this, imageuris.get(i)).replace("legacy","0"));
	        		if(movefile(dir,from)){
	        		if(!imageuris.get(i).toString().startsWith("file"))
	        		getContentResolver().delete(imageuris.get(i), null, null);
	        		else
	        		{
	        			MediaScannerConnection.scanFile(ShareActivity.this,
					            new String[] { from.toString() }, null,
					            new MediaScannerConnection.OnScanCompletedListener() {
					        public void onScanCompleted(String path, Uri uri) {
					            Log.i("ExternalStorage", "Scanned " + path + ":");
					            Log.i("ExternalStorage", "-> uri=" + uri);
					            getContentResolver().delete(uri, null, null);
					        }
					    });
	        		}
	        	}
	        		else
		        	{
		        		dir=new File(password.getString("location","/sdcard/.simplock")+"/.Audio/"+from.getName());
		        		try {
							copy(from,dir);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        		//new MyTask(false).execute(from,dir);
		        		from.delete();
		        		if(!imageuris.get(i).toString().startsWith("file"))
			        		getContentResolver().delete(imageuris.get(i), null, null);
			        	else
			        	{
			        		MediaScannerConnection.scanFile(ShareActivity.this,
						            new String[] { from.toString() }, null,
						            new MediaScannerConnection.OnScanCompletedListener() {
						        public void onScanCompleted(String path, Uri uri) {
						            Log.i("ExternalStorage", "Scanned " + path + ":");
						            Log.i("ExternalStorage", "-> uri=" + uri);
						            getContentResolver().delete(uri, null, null);
						        }
						    });
			        	}
		        		
		        	}
	        	
	        	}
	        
		    	break;
		    case 8:
		    	dir=new File(password.getString("location","/sdcard/.simplock")+"/.Other/");
	        	s=imageuris.size();
	        	for(int i=0;i<imageuris.size();i++)
	        	{
	        		int k=i+1;
	        		//text.setText("HIDING "+k+"/"+s);
	        		//Toast.makeText(getApplicationContext(),getRealPathFromURIvideo(getApplicationContext(), imageuris.get(i)),Toast.LENGTH_SHORT).show();
	        		from=new File(getRealPathFromURIaudeo(ShareActivity.this, imageuris.get(i)).replace("legacy","0"));
	        		if(movefile(dir,from)){
	        		if(!imageuris.get(i).toString().startsWith("file"))
	        		getContentResolver().delete(imageuris.get(i), null, null);
	        		else
	        		{
	        			MediaScannerConnection.scanFile(ShareActivity.this,
					            new String[] { from.toString() }, null,
					            new MediaScannerConnection.OnScanCompletedListener() {
					        public void onScanCompleted(String path, Uri uri) {
					            Log.i("ExternalStorage", "Scanned " + path + ":");
					            Log.i("ExternalStorage", "-> uri=" + uri);
					            getContentResolver().delete(uri, null, null);
					        }
					    });
	        		}
	        	}
	        		else
		        	{
		        		dir=new File(password.getString("location","/sdcard/.simplock")+"/.Other/"+from.getName());
		        		try {
							copy(from,dir);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        		//new MyTask(false).execute(from,dir);
		        		from.delete();
		        		if(!imageuris.get(i).toString().startsWith("file"))
			        		getContentResolver().delete(imageuris.get(i), null, null);
			        	else
			        	{
			        		MediaScannerConnection.scanFile(ShareActivity.this,
						            new String[] { from.toString() }, null,
						            new MediaScannerConnection.OnScanCompletedListener() {
						        public void onScanCompleted(String path, Uri uri) {
						            Log.i("ExternalStorage", "Scanned " + path + ":");
						            Log.i("ExternalStorage", "-> uri=" + uri);
						            getContentResolver().delete(uri, null, null);
						        }
						    });
			        	}
		        		
		        	}
	        	
	        	}
	    
		    	break;
		    }
		    return true;
		  }

		  @Override
		  protected void onPostExecute(Boolean success) {
		    progress.dismiss();
		    finish();
		    //Intent mon = new Intent(getApplicationContext(),ShareActivity.class);
        	
        	//startActivity(mon);
		    //finish();
		    // Show dialog with result
		  }
/*
		  @Override
		  protected void onProgressUpdate(Long... values) {
		    progress.setMessage("Transferred " + values[0] + " bytes");
		  }*/
		}

}
