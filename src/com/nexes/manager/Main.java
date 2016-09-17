/*
    Open Manager, an open source file manager for the Android system
    Copyright (C) 2009, 2010, 2011  Joe Berria <nexesdevelopment@gmail.com>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.nexes.manager;

import java.io.File;
import java.io.IOException;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.simp.simplock.R;
 

import android.app.Dialog;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
//import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaScannerConnection;
//import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.StatFs;
import android.os.Environment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;

/**
 * This is the main activity. The activity that is presented to the user
 * as the application launches. This class is, and expected not to be, instantiated.
 * <br>
 * <p>
 * This class handles creating the buttons and
 * text views. This class relies on the class EventHandler to handle all button
 * press logic and to control the data displayed on its ListView. This class
 * also relies on the FileManager class to handle all file operations such as
 * copy/paste zip/unzip etc. However most interaction with the FileManager class
 * is done via the EventHandler class. Also the SettingsMangager class to load
 * and save user settings. 
 * <br>
 * <p>
 * The design objective with this class is to control only the look of the
 * GUI (option menu, context menu, ListView, buttons and so on) and rely on other
 * supporting classes to do the heavy lifting. 
 *
 * @author Joe Berria
 *
 */
public final class Main extends ListActivity {
	public static final String ACTION_WIDGET = "com.nexes.manager.Main.ACTION_WIDGET";
	
	private static final String PREFS_NAME = "ManagerPrefsFile";	//user preference file name
	private static final String PREFS_HIDDEN = "hidden";
	private static final String PREFS_COLOR = "color";
	private static final String PREFS_THUMBNAIL = "thumbnail";
	private static final String PREFS_SORT = "sort";
	private static final String PREFS_STORAGE = "sdcard space";
	
	private static final int MENU_MKDIR =   0x00;			//option menu id
	private static final int MENU_SETTING = 0x01;			//option menu id
	private static final int MENU_SEARCH =  0x02;			//option menu id
	private static final int MENU_SPACE =   0x03;			//option menu id
	private static final int MENU_QUIT = 	0x04;			//option menu id
	private static final int SEARCH_B = 	0x09;
	
	private static final int D_MENU_DELETE = 0x05;			//context menu id
	private static final int D_MENU_RENAME = 0x06;			//context menu id
	private static final int D_MENU_COPY =   0x07;			//context menu id
	private static final int D_MENU_PASTE =  0x08;			//context menu id
	private static final int D_MENU_ZIP = 	 0x0e;			//context menu id
	private static final int D_MENU_UNZIP =  0x0f;			//context menu id
	private static final int D_MENU_MOVE = 	 0x30;
	private static final int D_MENU_UNHIDE = 	 0x33;
	private static final int F_MENU_UNHIDE = 	 0x34;
	private static final int F_MENU_LOCK = 	 0x31;//context menu id
	private static final int F_MENU_UNLOCK = 0x32;
	private static final int F_MENU_MOVE = 	 0x20;			//context menu id
	private static final int F_MENU_DELETE = 0x0a;			//context menu id
	private static final int F_MENU_RENAME = 0x0b;			//context menu id
	private static final int F_MENU_ATTACH = 0x0c;			//context menu id
	private static final int F_MENU_COPY =   0x0d;			//context menu id
	private static final int SETTING_REQ = 	 0x10;			//request code for intent

	private FileManager mFileMag;
	private EventHandler mHandler;
	private EventHandler.TableRow mTable;
	
	private SharedPreferences mSettings,password;
	private boolean mReturnIntent = false;
	private boolean mHoldingFile = false;
	private boolean mHoldingZip = false;
	private boolean mUseBackKey = true;
	private String mCopiedTarget;
	private String mZippedTarget;
	private String mSelectedListItem;			//item from context menu
	private TextView  mPathLabel, mDetailLabel, mStorageLabel;
	private ImageView selectall,unhideall,option;
	private Button pastebut;
	private LinearLayout pastelayout;
	private EditText t;
	private InterstitialAd interstitial;

	@Override
    public void onCreate(Bundle savedInstanceState) {
		//PackageManager p = getPackageManager();
		//ComponentName componentName = new ComponentName(this, com.nexes.manager.Main.class);
		//p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        super.onCreate(savedInstanceState);
        
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        password=this.getSharedPreferences("simplock",Context.MODE_WORLD_READABLE);
       File maindir= new File(password.getString("location","/sdcard/.simplock"));
        maindir.mkdirs();
        createnomedia(maindir);
        File imagedir= new File(password.getString("location","/sdcard/.simplock")+File.separator+".Image/");
        imagedir.mkdirs();
        File viddir= new File(password.getString("location","/sdcard/.simplock")+File.separator+".Video/");
        viddir.mkdirs();
        File auddir= new File(password.getString("location","/sdcard/.simplock")+File.separator+".Audio/");
        auddir.mkdirs();
        File othdir= new File(password.getString("location","/sdcard/.simplock")+File.separator+".Other/");
        othdir.mkdirs();
        //t=(EditText)findViewById(R.id.locktext);
       /* 
        //endad*/
       /* Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_lock);
		dialog.setTitle("SIMPLOCK");
		dialog.show();*/
      // Intent mon = new Intent(getApplicationContext(),Lock.class);
       // startActivityForResult(mon,9);
       // setContentView(R.layout.main);
       // PackageManager p = getPackageManager();
        //ComponentName componentName = new ComponentName("com.nexes","com.nexes.manager");
        //p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        //t.requestFocus();
        selectall=(ImageView)findViewById(R.id.hidden_selectall);
       option=(ImageView)findViewById(R.id.option);
        unhideall=(ImageView)findViewById(R.id.hidden_unhideall);
        pastebut=(Button)findViewById(R.id.pasteherebut);
        pastelayout = (LinearLayout)findViewById(R.id.pastehere);
		pastelayout.setVisibility(LinearLayout.GONE);
		
        /*read settings*/
        mSettings = getSharedPreferences(PREFS_NAME, 0);
        boolean hide = mSettings.getBoolean(PREFS_HIDDEN,false);
        boolean thumb = mSettings.getBoolean(PREFS_THUMBNAIL, true);
        int space = mSettings.getInt(PREFS_STORAGE, View.VISIBLE);
        int color = mSettings.getInt(PREFS_COLOR,Color.BLACK);
        int sort = mSettings.getInt(PREFS_SORT, 3);
        
        mFileMag = new FileManager();
        mFileMag.setShowHiddenFiles(true);
        mFileMag.setSortType(sort);
        
        if (savedInstanceState != null)
        {
        	mHandler = new EventHandler(Main.this, mFileMag, savedInstanceState.getString("location"));
       // loadadvert();
        }
        else
        {
        	mHandler = new EventHandler(Main.this, mFileMag);
        	//loadadvert();
        }
        mHandler.setTextColor(color);
        mHandler.setShowThumbnails(thumb);
        mTable = mHandler.new TableRow();
        
        /*sets the ListAdapter for our ListActivity and
         *gives our EventHandler class the same adapter
         */
        mHandler.setListAdapter(mTable);
        setListAdapter(mTable);
        
        /* register context menu for our list view */
        registerForContextMenu(getListView());
        
        mStorageLabel = (TextView)findViewById(R.id.storage_label);
        mDetailLabel = (TextView)findViewById(R.id.detail_label);
        mPathLabel = (TextView)findViewById(R.id.path_label);
        mPathLabel.setText(mFileMag.getCurrentDir());
        mPathLabel.setVisibility(TextView.GONE);
        mFileMag.setHomeDir(password.getString("location","/sdcard/.simplock"));
        updateStorageLabel();
        mStorageLabel.setVisibility(space);
        
        mHandler.setUpdateLabels(mPathLabel, mDetailLabel);
       // mHandler.stopThumbnailThread();
        option.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Main.this.openOptionsMenu();
			}
		});
        selectall.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int i;
				
				//mHandler.updateDirectory(mFileMag.getNextDir(mFileMag.getCurrentDir(),true));
				for(i=0;i<mHandler.mDataSource.size();i++)
				{
					mTable.addMultiPosition(i,mFileMag.getCurrentDir()+"/"+mHandler.getData(i));
					
				}
				//Toast.makeText(getApplicationContext(),i,Toast.LENGTH_SHORT).show();
				mTable.notifyDataSetChanged();
				loadadvert();
			//	Main.this.openOptionsMenu();
			}
			
		});
        unhideall.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getApplicationContext(),"Unhide Success",Toast.LENGTH_SHORT).show();
				// TODO Auto-generated method stub
				//Intent jo=new Intent(getApplicationContext(),Main.class);
				//startActivity(jo);
				mHandler.stopThumbnailThread();
				File[] files={};
				File too=new File("");
				if(mHandler.mMultiSelectData!=null)
				{
					if(!mHandler.mMultiSelectData.isEmpty())
					{
					mCopiedTarget = password.getString("unlocation","/sdcard/simplock");
    				Toast.makeText(getApplicationContext(),"Location : "+mCopiedTarget,Toast.LENGTH_LONG).show();
    				File to=new File(mCopiedTarget);
        			to.mkdirs();
        			int m=0;
        			for(m=0;m<mHandler.mMultiSelectData.size();m++)
					{
        				File from=new File(mHandler.mMultiSelectData.get(m));
        				too=new File(password.getString("unlocation","/sdcard/simplock")+"/"+from.getName());
        				if(from.isDirectory())
        					break;
        				if(!from.renameTo(too))
        				{
        					break;
        				}
					}
        			if((m!=mHandler.mMultiSelectData.size()))
        			{
    				mHandler.setDeleteAfterCopy(true);
    				mHandler.copyFileMultiSelect(password.getString("unlocation","/sdcard/simplock"));
        			}
    				for(int i=0;i<mHandler.mMultiSelectData.size();i++)
					{
						//Toast.makeText(getApplicationContext(),i,Toast.LENGTH_SHORT).show();
						File from=new File(mHandler.mMultiSelectData.get(i));
						//File to=new File(password.getString("unlocation","/sdcard/simplock")+"/"+from.getName());
						//files[i]=to.getAbsoluteFile();
						too=new File(password.getString("unlocation","/sdcard/simplock")+"/"+from.getName());
						MediaScannerConnection.scanFile(getApplicationContext(),new String[]{too.getAbsolutePath()}, null,
					            new MediaScannerConnection.OnScanCompletedListener() {
					        public void onScanCompleted(String path, Uri uri) {
					            Log.i("ExternalStorage", "Scanned " + path + ":");
					            Log.i("ExternalStorage", "-> uri=" + uri);
					        }
					    });
					}
    			/*for(int k=0;k<mHandler.mMultiSelectData.size();k++)
    			{
    			File from=new File(mHandler.mMultiSelectData.get(k));
    			//Toast.makeText(getApplicationContext(),from.getName(),Toast.LENGTH_SHORT).show();
    			to=new File(mCopiedTarget);
    			to.mkdirs();
    			if(from.isDirectory())
    			{
    				//to=new File(mCopiedTarget+"/"+from.getName().replace(".","")+"/");
    				//int i=1;
    				//while(to.exists())
    				//{
    					//to=new File(to.getAbsolutePath()+i+"/");
    					//i++;
    				//}
    				//Toast.makeText(getApplicationContext(),"Folder not moved",Toast.LENGTH_SHORT).show();
    			}
    			else
    			{
    				to=new File(mCopiedTarget+"/"+from.getName());
    				if(from.renameTo(to))
    					Toast.makeText(getApplicationContext(),"Unhide Success",Toast.LENGTH_SHORT).show();
    				else
    				{
    					mHandler.setDeleteAfterCopy(true);
    					mHandler.copyFile(from.getAbsolutePath(),password.getString("unlocation","/sdcard/simplock")+"/");
    				//Toast.makeText(getApplicationContext(),"Unhide Success",Toast.LENGTH_SHORT).show();
    				}
    			}
   				}*/
    			/*//mHandler.setDeleteAfterCopy(true);
    			/*for(int i=0;i<mHandler.mMultiSelectData.size();i++)
    				mHandler.copyFile(data[i],mCopiedTarget);
    			mHandler.copyFileMultiSelect(f.getAbsolutePath());*/
    			}
				}
				
    			
    			mTable.killMultiSelect(true);
    			mHandler.updateDirectory(mFileMag.getNextDir(mFileMag.getCurrentDir(),true));
    			//Toast.makeText(getApplicationContext(),"Unhide Success",Toast.LENGTH_SHORT).show();
    			//loadadvert();
				
			}
		});
        pastebut.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
boolean multi_select = mHandler.hasMultiSelectData();
File fd,fn;
    			
    			if(multi_select) {
    				fd=new File(mHandler.mMultiSelectData.get(0));
    				fn=new File(mFileMag.getCurrentDir());
    				//Toast.makeText(getApplicationContext(),fd.getParent(),Toast.LENGTH_SHORT).show();
    				//Toast.makeText(getApplicationContext(),fn.getAbsolutePath(),Toast.LENGTH_SHORT).show();
    				if(!fd.getParent().equalsIgnoreCase(fn.getAbsolutePath()))
    					mHandler.copyFileMultiSelect(mFileMag.getCurrentDir());
    				mDetailLabel.setText("");
    				mHandler.updateDirectory(mFileMag.getNextDir(mFileMag.getCurrentDir(),true));

    				
    			} else if(mHoldingFile && mCopiedTarget.length() > 1) {
    				fd=new File(mCopiedTarget);
    				fn=new File(mFileMag.getCurrentDir());
    				if(!fd.getParent().equalsIgnoreCase(fn.getAbsolutePath()))
    				{
    					mHandler.copyFile(mCopiedTarget, mFileMag.getCurrentDir()+"/");
    					mDetailLabel.setText("");
    				}
    				mDetailLabel.setText("");
    				mHandler.updateDirectory(mFileMag.getNextDir(mFileMag.getCurrentDir(),true));

    				loadadvert();
    			}
    			    			   			
    			mHoldingFile = false;
    			mHandler.updateDirectory(mFileMag.getNextDir(mFileMag.getCurrentDir(),true));

    			pastelayout.setVisibility(LinearLayout.GONE);
    			//mHandler.updateDirectory(mFileMag.getNextDir(mFileMag.getCurrentDir(),true));
			}
		});
        /* setup buttons */
        int[] img_button_id = {
        					   R.id.back_button, 
        					    R.id.multiselect_button};
        
        int[] button_id = {R.id.hidden_copy, R.id.hidden_attach,
        				   R.id.hidden_delete, R.id.hidden_move,R.id.hidden_lock,R.id.hidden_unlock};
        
        ImageView[] bimg = new ImageView[img_button_id.length];
        ImageView[] bt = new ImageView[button_id.length];
        
        for(int i = 0; i < button_id.length; i++) {
        	if(i < 2) {
        	bimg[i] = (ImageView)findViewById(img_button_id[i]);
        	bimg[i].setOnClickListener(mHandler);
        	}
        	
        		bt[i] = (ImageView)findViewById(button_id[i]);
        		bt[i].setOnClickListener(mHandler);
        	
        }
    
      Intent intent = getIntent();
        /*
        if(intent.getAction().equals(Intent.ACTION_GET_CONTENT)) {
        	//bimg[5].setVisibility(View.GONE);
        	mReturnIntent = true;
        	loadadvert();
        	
        
        
     } else 
      
      	if (intent.getAction().equals(ACTION_WIDGET)) {
        	//Log.e("MAIN", "Widget action, string = " + intent.getExtras().getString("folder"));
        	//mHandler.updateDirectory(mFileMag.getNextDir(intent.getExtras().getString("folder"), true));
        	//loadadvert();
        	
        }
        else
        {
        
        
        }*/
      	Intent mon = new Intent(getApplicationContext(),Lock.class);
      	startActivityForResult(mon,9);
      	 mFileMag.setHomeDir(password.getString("location","/sdcard/.simplock"));
      	mHandler.updateDirectory(mFileMag.getNextDir(mFileMag.getCurrentDir(),true));
      //  SharedPreferences prefs = getSharedPreferences("first", MODE_PRIVATE);
        if(password.getBoolean("firstrun",true))
        {
        	Dialog d=new Dialog(this);
        	d.setTitle("SimpLock Help");
        	Toast.makeText(getApplicationContext(),"SHARE FILES TO SIMPLOCK TO HIDE IT SAFE",Toast.LENGTH_LONG).show();
        	d.setContentView(R.layout.activity_share);
        	//d.show();
        	mon = new Intent(getApplicationContext(),ShareActivity.class);
        	
        	startActivity(mon);
        	SharedPreferences.Editor editor = password.edit();
            editor.putBoolean("firstrun",false);
            editor.commit();
            loadadvert();
        }
        loadadvert();
      
    }
	public void loadadvert()
	{
		//begin ad  ca-app-pub-9164569835667595/7969967069
        interstitial = new InterstitialAd(Main.this);
       // Toast.makeText(getApplicationContext(),"ads",Toast.LENGTH_SHORT).show();
    	// Insert the Ad Unit ID
    	interstitial.setAdUnitId("ca-app-pub-9164569835667595/7969967069");

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
	public void displayInterstitial() {
		// If Ads are loaded, show Interstitial else show nothing.
		if (interstitial.isLoaded()) {
			interstitial.show();
		}
	}
	public File createnomedia(File loc)
	{
		 File file= new File(loc.getAbsolutePath()+File.separator+".nomedia");
	     try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     return file;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putString("location", mFileMag.getCurrentDir());
		//loadadvert();
	}
	
	/*(non Java-Doc)
	 * Returns the file that was selected to the intent that
	 * called this activity. usually from the caller is another application.
	 */
	private void returnIntentResults(File data) {
		mReturnIntent = false;
		
		Intent ret = new Intent();
		ret.setData(Uri.fromFile(data));
		setResult(RESULT_OK, ret);
		
		finish();
	}
	
	private void updateStorageLabel() {
		long total, aval;
		int kb = 1024;
		
		StatFs fs = new StatFs(Environment.
								getExternalStorageDirectory().getPath());
		
		total = fs.getBlockCount() * (fs.getBlockSize() / kb);
		aval = fs.getAvailableBlocks() * (fs.getBlockSize() / kb);
		
		mStorageLabel.setText(String.format("sdcard: Total %.2f GB " +
							  "\t\tAvailable %.2f GB", 
							  (double)total / (kb * kb), (double)aval / (kb * kb)));
	}
	
	/**
	 *  To add more functionality and let the user interact with more
	 *  file types, this is the function to add the ability. 
	 *  
	 *  (note): this method can be done more efficiently 
	 */
	
    @Override
    public void onListItemClick(ListView parent, View view, int position, long id) {
    	final String item = mHandler.getData(position);
    	boolean multiSelect = mHandler.isMultiSelected();
    	File file = new File(mFileMag.getCurrentDir() + "/" + item);
    	String item_ext = null;
    	try {
    		item_ext = item.substring(item.lastIndexOf("."), item.length());
    		
    	} catch(IndexOutOfBoundsException e) {	
    		item_ext = ""; 
    	}
    	
    	/*
    	 * If the user has multi-select on, we just need to record the file
    	 * not make an intent for it.
    	 */
    	if(multiSelect) {
    		mTable.addMultiPosition(position, file.getPath());
    		
    	} else {
	    	if (file.isDirectory()) {
				if(file.canRead()) {
					mHandler.stopThumbnailThread();
		    		mHandler.updateDirectory(mFileMag.getNextDir(item, false));
		    		mPathLabel.setText(mFileMag.getCurrentDir());
		    		
		    		/*set back button switch to true 
		    		 * (this will be better implemented later)
		    		 */
		    		if(!mUseBackKey)
		    			mUseBackKey = true;
		    		
	    		} else {
	    			Toast.makeText(this, "Can't read folder due to permissions", 
	    							Toast.LENGTH_SHORT).show();
	    		}
	    	}
	    	
	    	/*music file selected--add more audio formats*/
	    	else if (item_ext.equalsIgnoreCase(".mp3") || 
	    			 item_ext.equalsIgnoreCase(".m4a")||
	    			item_ext.equalsIgnoreCase(".wma")||item_ext.equalsIgnoreCase(".ogg")||item_ext.equalsIgnoreCase(".aac")) 
	    			 {
	    		
	    		if(mReturnIntent) {
	    			returnIntentResults(file);
	    		} else {
	    			Intent i = new Intent();
    				i.setAction(android.content.Intent.ACTION_VIEW);
    				i.setDataAndType(Uri.fromFile(file), "audio/*");
    				startActivity(i);
	    		}
	    	}
	    	
	    	/*photo file selected*/
	    	else if(item_ext.equalsIgnoreCase(".jpeg") || 
	    			item_ext.equalsIgnoreCase(".jpg")  ||
	    			item_ext.equalsIgnoreCase(".png")  ||
	    			item_ext.equalsIgnoreCase(".gif")  || 
	    			item_ext.equalsIgnoreCase(".tiff")) {
	 			    		
	    		if (file.exists()) {
	    			if(mReturnIntent) {
	    				returnIntentResults(file);
	    				
	    			} else {
			    		Intent picIntent = new Intent();
			    		picIntent.setAction(android.content.Intent.ACTION_VIEW);
			    		picIntent.setDataAndType(Uri.fromFile(file), "image/*");
			    		startActivity(picIntent);
	    			}
	    		}
	    	}
	    	
	    	/*video file selected--add more video formats*/
	    	else if(item_ext.equalsIgnoreCase(".m4v") || 
	    			item_ext.equalsIgnoreCase(".3gp") ||
	    			item_ext.equalsIgnoreCase(".wmv") || 
	    			item_ext.equalsIgnoreCase(".mp4") || 
	    			item_ext.equalsIgnoreCase(".ogg") ||
	    			item_ext.equalsIgnoreCase(".wav")) {
	    		
	    		if (file.exists()) {
	    			if(mReturnIntent) {
	    				returnIntentResults(file);
	    				
	    			} else {
	    				Intent movieIntent = new Intent();
			    		movieIntent.setAction(android.content.Intent.ACTION_VIEW);
			    		movieIntent.setDataAndType(Uri.fromFile(file), "video/*");
			    		startActivity(movieIntent);
	    			}
	    		}
	    	}
	    	
	    	/*zip file */
	    	else if(item_ext.equalsIgnoreCase(".zip")) {
	    		
	    		if(mReturnIntent) {
	    			returnIntentResults(file);
	    			
	    		} else {
		    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    		AlertDialog alert;
		    		mZippedTarget = mFileMag.getCurrentDir() + "/" + item;
		    		CharSequence[] option = {"Extract here", "Extract to..."};
		    		
		    		builder.setTitle("Extract");
		    		builder.setItems(option, new DialogInterface.OnClickListener() {
		
						public void onClick(DialogInterface dialog, int which) {
							switch(which) {
								case 0:
									String dir = mFileMag.getCurrentDir();
									mHandler.unZipFile(item, dir + "/");
									break;
									
								case 1:
									mDetailLabel.setText("Holding " + item + 
														 " to extract");
									mHoldingZip = true;
									break;
							}
						}
		    		});
		    		
		    		alert = builder.create();
		    		alert.show();
	    		}
	    	}
	    	
	    	/* gzip files, this will be implemented later */
	    	else if(item_ext.equalsIgnoreCase(".gzip") ||
	    			item_ext.equalsIgnoreCase(".gz")) {
	    		
	    		if(mReturnIntent) {
	    			returnIntentResults(file);
	    			
	    		} else {
	    			//TODO:
	    		}
	    	}
	    	
	    	/*pdf file selected*/
	    	else if(item_ext.equalsIgnoreCase(".pdf")) {
	    		
	    		if(file.exists()) {
	    			if(mReturnIntent) {
	    				returnIntentResults(file);
	    				
	    			} else {
			    		Intent pdfIntent = new Intent();
			    		pdfIntent.setAction(android.content.Intent.ACTION_VIEW);
			    		pdfIntent.setDataAndType(Uri.fromFile(file), 
			    								 "application/pdf");
			    		
			    		try {
			    			startActivity(pdfIntent);
			    		} catch (ActivityNotFoundException e) {
			    			Toast.makeText(this, "Sorry, couldn't find a pdf viewer", 
									Toast.LENGTH_SHORT).show();
			    		}
		    		}
	    		}
	    	}
	    	
	    	/*Android application file*/
	    	else if(item_ext.equalsIgnoreCase(".apk")){
	    		
	    		if(file.exists()) {
	    			if(mReturnIntent) {
	    				returnIntentResults(file);
	    				
	    			} else {
		    			Intent apkIntent = new Intent();
		    			apkIntent.setAction(android.content.Intent.ACTION_VIEW);
		    			apkIntent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		    			startActivity(apkIntent);
	    			}
	    		}
	    	}
	    	
	    	/* HTML file */
	    	else if(item_ext.equalsIgnoreCase(".html")) {
	    		
	    		if(file.exists()) {
	    			if(mReturnIntent) {
	    				returnIntentResults(file);
	    				
	    			} else {
		    			Intent htmlIntent = new Intent();
		    			htmlIntent.setAction(android.content.Intent.ACTION_VIEW);
		    			htmlIntent.setDataAndType(Uri.fromFile(file), "text/html");
		    			
		    			try {
		    				startActivity(htmlIntent);
		    			} catch(ActivityNotFoundException e) {
		    				Toast.makeText(this, "Sorry, couldn't find a HTML viewer", 
		    									Toast.LENGTH_SHORT).show();
		    			}
	    			}
	    		}
	    	}
	    	
	    	/* text file*/
	    	else if(item_ext.equalsIgnoreCase(".txt")) {
	    		
	    		if(file.exists()) {
	    			if(mReturnIntent) {
	    				returnIntentResults(file);
	    				
	    			} else {
		    			Intent txtIntent = new Intent();
		    			txtIntent.setAction(android.content.Intent.ACTION_VIEW);
		    			txtIntent.setDataAndType(Uri.fromFile(file), "text/plain");
		    			
		    			try {
		    				startActivity(txtIntent);
		    			} catch(ActivityNotFoundException e) {
		    				txtIntent.setType("text/*");
		    				startActivity(txtIntent);
		    			}
	    			}
	    		}
	    	}
	    	
	    	/* generic intent */
	    	else {
	    		if(file.exists()) {
	    			if(mReturnIntent) {
	    				returnIntentResults(file);
	    				
	    			} else {
			    		Intent generic = new Intent();
			    		generic.setAction(android.content.Intent.ACTION_VIEW);
			    		generic.setDataAndType(Uri.fromFile(file), "text/plain");
			    		
			    		try {
			    			startActivity(generic);
			    		} catch(ActivityNotFoundException e) {
			    			Toast.makeText(this, "Sorry, couldn't find anything " +
			    						   "to open " + file.getName(), 
			    						   Toast.LENGTH_SHORT).show();
			    		}
	    			}
	    		}
	    	}
    	}
	}
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	SharedPreferences.Editor editor = mSettings.edit();
    	boolean check;
    	boolean thumbnail;
    	int color, sort, space;
    	if(resultCode==9){
            finish();
    	}
    	/* resultCode must equal RESULT_CANCELED because the only way
    	 * out of that activity is pressing the back button on the phone
    	 * this publishes a canceled result code not an ok result code
    	 */
    	if(requestCode == SETTING_REQ && resultCode == RESULT_CANCELED) {
    		//save the information we get from settings activity
    		//check = data.getBooleanExtra("HIDDEN",false);
    		check=true;
    		thumbnail = data.getBooleanExtra("THUMBNAIL", true);
    		color = data.getIntExtra("COLOR",Color.BLACK);
    		sort = data.getIntExtra("SORT", 0);
    		space = data.getIntExtra("SPACE", View.VISIBLE);
    		
    		editor.putBoolean(PREFS_HIDDEN, check);
    		editor.putBoolean(PREFS_THUMBNAIL, thumbnail);
    		editor.putInt(PREFS_COLOR, color);
    		editor.putInt(PREFS_SORT, sort);
    		editor.putInt(PREFS_STORAGE, space);
    		editor.commit();
    		Boolean locch=password.getBoolean("locchanged",false);
    		mFileMag.setShowHiddenFiles(check);
    		mFileMag.setSortType(sort);
    		mHandler.setTextColor(color);
    		mHandler.setShowThumbnails(thumbnail);
    		mStorageLabel.setVisibility(space);
    		if(locch)
    			mHandler.updateDirectory(mFileMag.getNextDir(password.getString("location","/sdcard/.simplock"), true));
    		else
    			mHandler.updateDirectory(mFileMag.getNextDir(mFileMag.getCurrentDir(), true));
    	}
    }
    
    /* ================Menus, options menu and context menu start here=================*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0, MENU_MKDIR, 0, "New Directory").setIcon(R.drawable.newfolder);
    	menu.add(0, MENU_SEARCH, 0, "Search").setIcon(R.drawable.search);
    	
    		/* free space will be implemented at a later time */
//    	menu.add(0, MENU_SPACE, 0, "Free space").setIcon(R.drawable.space);
    	menu.add(0, MENU_SETTING, 0, "Settings").setIcon(R.drawable.setting);
    	menu.add(0, MENU_QUIT, 0, "Quit").setIcon(R.drawable.logout);
    	
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    		case MENU_MKDIR:
    			showDialog(MENU_MKDIR);
    			return true;
    			
    		case MENU_SEARCH:
    			showDialog(MENU_SEARCH);
    			return true;
    			
    		case MENU_SPACE: /* not yet implemented */
    			return true;
    			
    		case MENU_SETTING:
    			Intent settings_int = new Intent(this, Settings.class);
    			settings_int.putExtra("HIDDEN", mSettings.getBoolean(PREFS_HIDDEN, false));
    			settings_int.putExtra("THUMBNAIL", mSettings.getBoolean(PREFS_THUMBNAIL, true));
    			settings_int.putExtra("COLOR", mSettings.getInt(PREFS_COLOR,Color.BLACK));
    			settings_int.putExtra("SORT", mSettings.getInt(PREFS_SORT, 0));
    			settings_int.putExtra("SPACE", mSettings.getInt(PREFS_STORAGE, View.VISIBLE));
    			
    			startActivityForResult(settings_int, SETTING_REQ);
    			return true;
    			
    		case MENU_QUIT:
    			finish();
    			return true;
    	}
    	return false;
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo info) {
    	super.onCreateContextMenu(menu, v, info);
    	
    	boolean multi_data = mHandler.hasMultiSelectData();
    	AdapterContextMenuInfo _info = (AdapterContextMenuInfo)info;
    	mSelectedListItem = mHandler.getData(_info.position);
    	File from = new File(mFileMag.getCurrentDir() +"/"+ mSelectedListItem);
    	// p = getPackageManager();
        //p.setComponentEnabledSetting(getComponentName(),
            //PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
           // PackageManager.DONT_KILL_APP);
    	//PackageManager p = getPackageManager();
    	//ComponentName componentName = new ComponentName("com.nexes.manager","com.nexes.manager.Main");
    	//p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    	/* is it a directory and is multi-select turned off */
    	if(mFileMag.isDirectory(mSelectedListItem) && !mHandler.isMultiSelected()) {
    		menu.setHeaderTitle("Folder operations");
        	menu.add(0, D_MENU_DELETE, 0, "Delete Folder");
        	menu.add(0, D_MENU_RENAME, 0, "Rename Folder");
        	menu.add(0, D_MENU_COPY, 0, "Copy Folder");
        	menu.add(0, D_MENU_MOVE, 0, "Move(Cut) Folder");
        	menu.add(0, D_MENU_ZIP, 0, "Zip Folder");
        	//menu.add(0, D_MENU_UNHIDE, 0, "Unhide Folder");
        	menu.add(0, D_MENU_PASTE, 0, "Paste into folder").setEnabled(mHoldingFile || 
        																 multi_data);
        	menu.add(0, D_MENU_UNZIP, 0, "Extract here").setEnabled(mHoldingZip);
    		
        /* is it a file and is multi-select turned off */
    	} else if(!mFileMag.isDirectory(mSelectedListItem) && !mHandler.isMultiSelected()) {
        	menu.setHeaderTitle("File Operations");
    		menu.add(0, F_MENU_DELETE, 0, "Delete File");
    		menu.add(0, F_MENU_RENAME, 0, "Rename File");
    		menu.add(0, F_MENU_COPY, 0, "Copy File");
    		menu.add(0, F_MENU_UNHIDE, 0, "Unhide File");
    		menu.add(0, F_MENU_LOCK, 0, "Lock File").setEnabled(!(getfiletype(from).toLowerCase().contains("smp"))&&!(getfiletype(from).toLowerCase().contains("nomedia")));
    		menu.add(0, F_MENU_UNLOCK, 0, "Unlock File").setEnabled((getfiletype(from).toLowerCase().contains("smp")));
    		menu.add(0, F_MENU_MOVE, 0, "Move(Cut) File");
    		menu.add(0, F_MENU_ATTACH, 0, "Share File");
    	}	
    }
    public String getfiletype(File f)
    {
    	String type=f.getName();
    	char ch[]=type.toCharArray();
    	int i;
    	for(i=type.length()-1;i>=0;i--)
    	{
    		if(ch[i]=='.')
    			break;
    	}
    	if(i==-1)
    		return "nil";
    	return type.substring(i+1);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {

    	switch(item.getItemId()) {
    		case D_MENU_DELETE:
    		case F_MENU_DELETE:
    			AlertDialog.Builder builder = new AlertDialog.Builder(this);
    			builder.setTitle("Warning ");
    			builder.setIcon(R.drawable.warning);
    			builder.setMessage("Deleting " + mSelectedListItem +
    							" cannot be undone. Are you sure you want to delete?");
    			builder.setCancelable(false);
    			
    			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
    			});
    			builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						mHandler.deleteFile(mFileMag.getCurrentDir() + "/" + mSelectedListItem);
					}
    			});
    			AlertDialog alert_d = builder.create();
    			alert_d.show();
    			return true;
    			
    		case D_MENU_RENAME:
    			showDialog(D_MENU_RENAME);
    			return true;
    			
    		case F_MENU_RENAME:
    			showDialog(F_MENU_RENAME);
    			return true;
    		case F_MENU_LOCK:
    			File from = new File(mFileMag.getCurrentDir() +"/"+ mSelectedListItem);
    			File to= new File(from.getAbsolutePath()+".smp");
    		    from.renameTo(to);
    		    String temp = mFileMag.getCurrentDir();
				mHandler.updateDirectory(mFileMag.getNextDir(temp, true));
    			return true;
    		case F_MENU_UNLOCK:
    			File fro = new File(mFileMag.getCurrentDir() +"/"+ mSelectedListItem);
    			String fromname=fro.getAbsolutePath();
    		     fromname=fromname.substring(0,fromname.length()-4);
    		     File t=new File(fromname);
    		     fro.renameTo(t);
    		     temp = mFileMag.getCurrentDir();
    		     mHandler.updateDirectory(mFileMag.getNextDir(temp, true));
    		     return true;
    		case F_MENU_ATTACH:
    			File file = new File(mFileMag.getCurrentDir() +"/"+ mSelectedListItem);
    			Intent mail_int = new Intent();
    	
    			mail_int.setAction(android.content.Intent.ACTION_SEND);
    			if(getfiletype(file).toLowerCase().contains("jpg")||getfiletype(file).toLowerCase().contains("jpeg")||getfiletype(file).toLowerCase().contains("png")||getfiletype(file).toLowerCase().contains("gif"))
    			{
    				mail_int.setType("image/*");
    			}
    			else if(getfiletype(file).toLowerCase().contains("mp4")||getfiletype(file).toLowerCase().contains("wmv")||getfiletype(file).toLowerCase().contains("3gp")||getfiletype(file).toLowerCase().contains("avi")||getfiletype(file).toLowerCase().contains("mkv"))
    			{
    				mail_int.setType("video/*");
    			}
    			else if(getfiletype(file).toLowerCase().contains("mp3")||getfiletype(file).toLowerCase().contains("ogg")||getfiletype(file).toLowerCase().contains("aac")||getfiletype(file).toLowerCase().contains("wma"))
    			{
    				mail_int.setType("audio/*");
    			}
    			else
    				mail_int.setType("application/mail");
    			
    			//mail_int.setType();
    			//mail_int.putExtra(Intent.EXTRA_BCC, "");
    			mail_int.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
    			startActivity(mail_int);
    			return true;
    		case F_MENU_UNHIDE:
    			file = new File(mFileMag.getCurrentDir() +"/"+ mSelectedListItem);
    			String st=password.getString("unlocation","/sdcard/simplock");
    			st=st.replace(".","");
    			Toast.makeText(getApplicationContext(),"Location : "+st,Toast.LENGTH_LONG).show();
    			File newd=new File(st);
    			st=st+"/"+mSelectedListItem;
    			//Toast.makeText(getApplicationContext(),st,Toast.LENGTH_SHORT).show();
    			File newf=new File(st);
    			newd.mkdirs();
    			mHandler.stopThumbnailThread();
    			int i=1;
    			
    			
    			while(newf.exists())
				{
					newf=new File(newf.getAbsolutePath()+i+"/");
					i++;
				}
    			if(file.renameTo(newf))
    			{
    				Toast.makeText(getApplicationContext(),"Unhide Successful",Toast.LENGTH_SHORT).show();
    				temp = mFileMag.getCurrentDir();
    				mHandler.updateDirectory(mFileMag.getNextDir(temp, true));
    			}
    			else
    			{
    				mHandler.setDeleteAfterCopy(true);
    				mHandler.copyFile(file.getAbsolutePath(),password.getString("unlocation","/sdcard/simplock")+"/");
    				Toast.makeText(getApplicationContext(),"Unhide Successful",Toast.LENGTH_SHORT).show();
    				temp = mFileMag.getCurrentDir();
    				mHandler.updateDirectory(mFileMag.getNextDir(temp, true));
    			}
    			 MediaScannerConnection.scanFile(this,
    			            new String[] { newf.toString() }, null,
    			            new MediaScannerConnection.OnScanCompletedListener() {
    			        public void onScanCompleted(String path, Uri uri) {
    			            Log.i("ExternalStorage", "Scanned " + path + ":");
    			            Log.i("ExternalStorage", "-> uri=" + uri);
    			        }
    			    });
    			return true;
    		case D_MENU_UNHIDE:
    			mCopiedTarget = mFileMag.getCurrentDir();
    			fro=new File(mCopiedTarget+"/"+mSelectedListItem);
    			mCopiedTarget=password.getString("unlocation","/sdcard/simplock");
    			Toast.makeText(getApplicationContext(),"Location : "+mCopiedTarget,Toast.LENGTH_LONG).show();
    			//Toast.makeText(getApplicationContext(),mCopiedTarget,Toast.LENGTH_SHORT).show();
    			from=new File(mCopiedTarget);
    			from.mkdirs();
    			
    			to=new File(mCopiedTarget+"/"+fro.getName().replace(".","")+"/");
				i=1;
				while(to.exists())
				{
					to=new File(to.getAbsolutePath()+i+"/");
					i++;
				}
				if(fro.renameTo(to))
				{
					Toast.makeText(getApplicationContext(),"Unhide Successful",Toast.LENGTH_SHORT).show();
    				temp = mFileMag.getCurrentDir();
    				mHandler.updateDirectory(mFileMag.getNextDir(temp, true));
    			}
    			else
    			{
    				mHandler.setDeleteAfterCopy(true);
    				mHandler.copyFile(fro.getAbsolutePath(),password.getString("unlocation","/sdcard/simplock")+"/");
    				Toast.makeText(getApplicationContext(),"Unhide Successful",Toast.LENGTH_SHORT).show();
    				temp = mFileMag.getCurrentDir();
    				mHandler.updateDirectory(mFileMag.getNextDir(temp, true));
    			}
				MediaScannerConnection.scanFile(this,
			            new String[] { to.toString() }, null,
			            new MediaScannerConnection.OnScanCompletedListener() {
			        public void onScanCompleted(String path, Uri uri) {
			            Log.i("ExternalStorage", "Scanned " + path + ":");
			            Log.i("ExternalStorage", "-> uri=" + uri);
			        }
			    });
    			//mHandler.setDeleteAfterCopy(true);
    			//mHandler.copyFile(mFileMag.getCurrentDir() +"/"+ mSelectedListItem.replace(".",""),mCopiedTarget);
    			temp = mFileMag.getCurrentDir();
   		     	mHandler.updateDirectory(mFileMag.getNextDir(temp, true));
    			return true;
    		case F_MENU_MOVE:
    		case D_MENU_MOVE:
    		case F_MENU_COPY:
    		case D_MENU_COPY:
    			if(item.getItemId() == F_MENU_MOVE || item.getItemId() == D_MENU_MOVE)
    				mHandler.setDeleteAfterCopy(true);
    			
    			mHoldingFile = true;
    			
    			mCopiedTarget = mFileMag.getCurrentDir() +"/"+ mSelectedListItem;
    			mDetailLabel.setText("Holding " + mSelectedListItem);
    			//pastelayout = (LinearLayout)((Activity)mContext).findViewById(R.id.pastehere);
				pastelayout.setVisibility(LinearLayout.VISIBLE);
			
    			return true;
    			
    		
    		case D_MENU_PASTE:
    			boolean multi_select = mHandler.hasMultiSelectData();
    			
    			if(multi_select) {
    				mHandler.copyFileMultiSelect(mFileMag.getCurrentDir() +"/"+ mSelectedListItem);
    				
    			} else if(mHoldingFile && mCopiedTarget.length() > 1) {
    				
    				mHandler.copyFile(mCopiedTarget, mFileMag.getCurrentDir() +"/"+ mSelectedListItem);
    				mDetailLabel.setText("");
    			}
    			    			   			
    			mHoldingFile = false;
    			pastelayout.setVisibility(LinearLayout.GONE);
    			return true;
    			
    		case D_MENU_ZIP:
    			String dir = mFileMag.getCurrentDir();
    			
    			mHandler.zipFile(dir + "/" + mSelectedListItem);
    			return true;
    			
    		case D_MENU_UNZIP:
    			if(mHoldingZip && mZippedTarget.length() > 1) {
    				String current_dir = mFileMag.getCurrentDir() + "/" + mSelectedListItem + "/";
    				String old_dir = mZippedTarget.substring(0, mZippedTarget.lastIndexOf("/"));
    				String name = mZippedTarget.substring(mZippedTarget.lastIndexOf("/") + 1, mZippedTarget.length());
    				
    				if(new File(mZippedTarget).canRead() && new File(current_dir).canWrite()) {
	    				mHandler.unZipFileToDir(name, current_dir, old_dir);				
	    				mPathLabel.setText(current_dir);
	    				
    				} else {
    					Toast.makeText(this, "You do not have permission to unzip " + name, 
    							Toast.LENGTH_SHORT).show();
    				}
    			}
    			
    			mHoldingZip = false;
    			mDetailLabel.setText("");
    			mZippedTarget = "";
    			return true;
    	}
    	return false;
    }
    
    /* ================Menus, options menu and context menu end here=================*/

    @Override
    protected Dialog onCreateDialog(int id) {
    	final Dialog dialog = new Dialog(Main.this);
    	
    	switch(id) {
    		case MENU_MKDIR:
    			dialog.setContentView(R.layout.input_layout);
    			dialog.setTitle("Create New Directory");
    			dialog.setCancelable(false);
    			
    			ImageView icon = (ImageView)dialog.findViewById(R.id.input_icon);
    			icon.setImageResource(R.drawable.newfolder);
    			
    			TextView label = (TextView)dialog.findViewById(R.id.input_label);
    			label.setText(mFileMag.getCurrentDir());
    			final EditText input = (EditText)dialog.findViewById(R.id.input_inputText);
    			
    			Button cancel = (Button)dialog.findViewById(R.id.input_cancel_b);
    			Button create = (Button)dialog.findViewById(R.id.input_create_b);
    			
    			create.setOnClickListener(new OnClickListener() {
    				public void onClick (View v) {
    					if (input.getText().length() > 1) {
    						if (mFileMag.createDir(mFileMag.getCurrentDir() + "/", input.getText().toString()) == 0)
    							Toast.makeText(Main.this, 
    										   "Folder " + input.getText().toString() + " created", 
    										   Toast.LENGTH_LONG).show();
    						else
    							Toast.makeText(Main.this, "New folder was not created", Toast.LENGTH_SHORT).show();
    					}
    					
    					dialog.dismiss();
    					String temp = mFileMag.getCurrentDir();
    					mHandler.updateDirectory(mFileMag.getNextDir(temp, true));
    				}
    			});
    			cancel.setOnClickListener(new OnClickListener() {
    				public void onClick (View v) {	dialog.dismiss(); }
    			});
    		break; 
    		case D_MENU_RENAME:
    		case F_MENU_RENAME:
    			dialog.setContentView(R.layout.input_layout);
    			dialog.setTitle("Rename " + mSelectedListItem);
    			dialog.setCancelable(false);
    			
    			ImageView rename_icon = (ImageView)dialog.findViewById(R.id.input_icon);
    			rename_icon.setImageResource(R.drawable.rename);
    			
    			TextView rename_label = (TextView)dialog.findViewById(R.id.input_label);
    			rename_label.setText(mFileMag.getCurrentDir());
    			final EditText rename_input = (EditText)dialog.findViewById(R.id.input_inputText);
    			
    			Button rename_cancel = (Button)dialog.findViewById(R.id.input_cancel_b);
    			Button rename_create = (Button)dialog.findViewById(R.id.input_create_b);
    			rename_create.setText("Rename");
    			
    			rename_create.setOnClickListener(new OnClickListener() {
    				public void onClick (View v) {
    					if(rename_input.getText().length() < 1)
    						dialog.dismiss();
    					
    					if(mFileMag.renameTarget(mFileMag.getCurrentDir() +"/"+ mSelectedListItem, rename_input.getText().toString()) == 0) {
    						Toast.makeText(Main.this, mSelectedListItem + " was renamed to " +rename_input.getText().toString(),
    								Toast.LENGTH_LONG).show();
    					}else
    						Toast.makeText(Main.this, mSelectedListItem + " was not renamed", Toast.LENGTH_LONG).show();
    						
    					dialog.dismiss();
    					String temp = mFileMag.getCurrentDir();
    					mHandler.updateDirectory(mFileMag.getNextDir(temp, true));
    				}
    			});
    			rename_cancel.setOnClickListener(new OnClickListener() {
    				public void onClick (View v) {	dialog.dismiss(); }
    			});
    		break;
    		
    		case SEARCH_B:
    		case MENU_SEARCH:
    			dialog.setContentView(R.layout.input_layout);
    			dialog.setTitle("Search");
    			dialog.setCancelable(false);
    			
    			ImageView searchIcon = (ImageView)dialog.findViewById(R.id.input_icon);
    			searchIcon.setImageResource(R.drawable.search);
    			
    			TextView search_label = (TextView)dialog.findViewById(R.id.input_label);
    			search_label.setText("Search for a file");
    			final EditText search_input = (EditText)dialog.findViewById(R.id.input_inputText);
    			
    			Button search_button = (Button)dialog.findViewById(R.id.input_create_b);
    			Button cancel_button = (Button)dialog.findViewById(R.id.input_cancel_b);
    			search_button.setText("Search");
    			
    			search_button.setOnClickListener(new OnClickListener() {
    				public void onClick(View v) {
    					String temp = search_input.getText().toString();
    					
    					if (temp.length() > 0)
    						mHandler.searchForFile(temp);
    					dialog.dismiss();
    				}
    			});
    			
    			cancel_button.setOnClickListener(new OnClickListener() {
    				public void onClick(View v) { dialog.dismiss(); }
    			});

    		break;
    	}
    	return dialog;
    }

    
    /*
     * (non-Javadoc)
     * This will check if the user is at root directory. If so, if they press back
     * again, it will close the application. 
     * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
     */
    @Override
   public boolean onKeyDown(int keycode, KeyEvent event) {
    	String current = mFileMag.getCurrentDir();
    	
    	if(keycode == KeyEvent.KEYCODE_SEARCH) {
    		showDialog(SEARCH_B);
    		
    		return true;
    		
    	} else if(keycode == KeyEvent.KEYCODE_BACK && mUseBackKey && !current.equals(password.getString("location","/sdcard/.simplock"))) {
    		if(mHandler.isMultiSelected()) {
    			mTable.killMultiSelect(true);
    			Toast.makeText(Main.this, "Multi-select is now off", Toast.LENGTH_SHORT).show();
    			pastelayout.setVisibility(LinearLayout.GONE);
    		
    		} else {
    			//stop updating thumbnail icons if its running
    			mHandler.stopThumbnailThread();
	    		mHandler.updateDirectory(mFileMag.getPreviousDir());
	    		mPathLabel.setText(mFileMag.getCurrentDir());
    		}
    		return true;
    		
    	} else if(keycode == KeyEvent.KEYCODE_BACK && mUseBackKey && current.equals(password.getString("location","/sdcard/.simplock"))) {
    		Toast.makeText(Main.this, "Press back again to quit.", Toast.LENGTH_SHORT).show();
    		
    		if(mHandler.isMultiSelected()) {
    			mTable.killMultiSelect(true);
    			Toast.makeText(Main.this, "Multi-select is now off", Toast.LENGTH_SHORT).show();
    			pastelayout.setVisibility(LinearLayout.GONE);
    		}
    		
    		mUseBackKey = false;
    		mPathLabel.setText(mFileMag.getCurrentDir());
    		
    		return false;
    		
    	} else if(keycode == KeyEvent.KEYCODE_BACK && !mUseBackKey && current.equals(password.getString("location","/sdcard/.simplock"))) {
    		password=this.getSharedPreferences("simplock",Context.MODE_WORLD_READABLE);
    		if(password.getBoolean("hideapp",false))
    		{
    		PackageManager p = getPackageManager();
           ComponentName componentName = new ComponentName(this, com.nexes.manager.Main.class); 
            p.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    		}
    		finish();
    		
    		return false;
    	}
    	return false;
    }
   /* @Override
    public void onStart(){
        super.onResume();  // Always call the superclass method first

        // Get the Camera instance as the activity achieves full user focus
        Intent mon = new Intent(getApplicationContext(),Lock.class);
        startActivityForResult(mon,2);
    }*/
}

