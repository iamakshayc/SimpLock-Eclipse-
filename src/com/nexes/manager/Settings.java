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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nexes.manager.ShareActivity.MyTask;
import com.simp.simplock.R;

public class Settings extends Activity {
	private boolean mHiddenChanged = true;
	private boolean mColorChanged = false;
	private boolean mThumbnailChanged = false;
	private boolean mSortChanged = false;
	private boolean mSpaceChanged = false;
	
	private boolean hidden_state;
	private boolean thumbnail_state;
	private int color_state, sort_state, mSpaceState;
	private Intent is = new Intent();
	public File source,dest;
	public SharedPreferences password;
	public SharedPreferences.Editor preditor;
	public TextView loc,loccon,unloc,unloccon;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		final AlertDialog crd=new AlertDialog.Builder(Settings.this).create();
		Intent i = getIntent();
		hidden_state = i.getExtras().getBoolean("HIDDEN");
		thumbnail_state = i.getExtras().getBoolean("THUMBNAIL");
		color_state = i.getExtras().getInt("COLOR");
		sort_state = i.getExtras().getInt("SORT");
		mSpaceState = i.getExtras().getInt("SPACE");
				
		final CheckBox hidden_bx = (CheckBox)findViewById(R.id.setting_hidden_box);
		final CheckBox thumbnail_bx = (CheckBox)findViewById(R.id.setting_thumbnail_box);
		final CheckBox space_bx = (CheckBox)findViewById(R.id.setting_storage_box);
		final ImageButton color_bt = (ImageButton)findViewById(R.id.setting_text_color_button);
		final ImageButton sort_bt = (ImageButton)findViewById(R.id.settings_sort_button);
		final TextView pass = (TextView)findViewById(R.id.pass);
		loc = (TextView)findViewById(R.id.loc);
		loccon = (TextView)findViewById(R.id.loccon);;
		unloc = (TextView)findViewById(R.id.unloc);
		unloccon = (TextView)findViewById(R.id.unloccon);

		password=this.getSharedPreferences("simplock",Context.MODE_WORLD_READABLE);
		preditor=password.edit();
		preditor.putBoolean("locchanged",false);
		preditor.commit();
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.activity_pass);
		dialog.setTitle("CHANGE PASSWORD");
		final Button passok=(Button)dialog.findViewById(R.id.passok);
		final Button passcan=(Button)dialog.findViewById(R.id.passcan);
		final EditText newpass=(EditText)dialog.findViewById(R.id.newpass);
		final EditText conpass=(EditText)dialog.findViewById(R.id.conpass);
		hidden_bx.setChecked(hidden_state);
		final CheckBox hideicon = (CheckBox)findViewById(R.id.hideicon);
		final TextView hidetext = (TextView)findViewById(R.id.hidetext);
		thumbnail_bx.setChecked(thumbnail_state);
		hideicon.setChecked(password.getBoolean("hideapp",false));
		hidetext.setText("Dial ##"+password.getString("password","4567")+" to open the App");
		space_bx.setChecked(mSpaceState == View.VISIBLE);
		loccon.setText(password.getString("location","/sdcard/.simplock"));
		unloccon.setText(password.getString("unlocation","/sdcard/simplock"));
		pass.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
		        
				dialog.show();
				
			}
		});
		passcan.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		passok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(newpass.getText().toString().equals(conpass.getText().toString())&&newpass.getText().toString().length()>=3)
				{
					preditor.putString("password",newpass.getText().toString());
					preditor.commit();
					Toast.makeText(getApplicationContext(),"Password changed",Toast.LENGTH_SHORT).show();
					hidetext.setText("Dial ##"+password.getString("password","4567")+" to open the App");
					dialog.dismiss();
				}
				else
				{
					Toast.makeText(getApplicationContext(),"minimum size 3",Toast.LENGTH_SHORT).show();
					dialog.dismiss();
				}
			}
		});
		unloc.setOnClickListener(new View.OnClickListener() {
			 private String m_chosenDir = "/storage/";
	          private boolean m_newFolderEnabled = true;
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DirectoryChooserDialog directoryChooserDialog = 
		                new DirectoryChooserDialog(Settings.this, 
		                    new DirectoryChooserDialog.ChosenDirectoryListener() 
		                {
		                    @Override
		                    public void onChosenDir(String chosenDir) 
		                    {
		                        m_chosenDir ="/storage/";
		                        preditor.putString("unlocation",chosenDir.replace(".","")+"/simplock");
	    						preditor.commit();
	    						//File fd=new File(chosenDir.replace(".","")+"/simplock");
		                        Toast.makeText(
		                        Settings.this, "Chosen directory: " + 
		                          chosenDir.replace(".",""), Toast.LENGTH_LONG).show();
		    					unloccon.setText(password.getString("unlocation","/sdcard/simplock"));
		                    }
		                }); 
		                // Toggle new folder button enabling
		                directoryChooserDialog.setNewFolderEnabled(m_newFolderEnabled);
		                // Load directory chooser dialog for initial 'm_chosenDir' directory.
		                // The registered callback will be called upon final directory selection.
		                directoryChooserDialog.chooseDirectory(m_chosenDir);
		                m_newFolderEnabled = ! m_newFolderEnabled;
		                loccon.setText(password.getString("location","/sdcard/.simplock"));  
			
			}
		});
		loc.setOnClickListener(new View.OnClickListener() {
			 private String m_chosenDir = "/storage/";
	          private boolean m_newFolderEnabled = true;
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				DirectoryChooserDialog directoryChooserDialog = 
		                new DirectoryChooserDialog(Settings.this, 
		                    new DirectoryChooserDialog.ChosenDirectoryListener() 
		                {
		                    @Override
		                    public void onChosenDir(String chosenDir) 
		                    {
		                        m_chosenDir ="/storage/";
		                        source=new File(password.getString("location","/sdcard/.simplock"));
		 
		                        Toast.makeText(
		                        Settings.this, "Chosen directory: " + 
		                          chosenDir, Toast.LENGTH_LONG).show();
		    					dest=new File(chosenDir+"/.simplock");
		    					
		    					if(!(source.getAbsolutePath().equals(dest.getAbsolutePath())))
		    						new MyTask(1).execute();
		    					/*if(password.getBoolean("locchanged",false))
		    					{
		    						preditor.putString("location",chosenDir+"/.simplock");
		    						preditor.commit();
		    					}*/
		    					loccon.setText(password.getString("location","/sdcard/.simplock"));
		                    }
		                }); 
		                // Toggle new folder button enabling
		                directoryChooserDialog.setNewFolderEnabled(m_newFolderEnabled);
		                // Load directory chooser dialog for initial 'm_chosenDir' directory.
		                // The registered callback will be called upon final directory selection.
		                directoryChooserDialog.chooseDirectory(m_chosenDir);
		                m_newFolderEnabled = ! m_newFolderEnabled;
		                loccon.setText(password.getString("location","/sdcard/.simplock"));  
			}
		});
		color_bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
				CharSequence[] options = {"Black", "Magenta", "Yellow", "Red", "Cyan",
									      "Blue", "Green"};
				int index = ((color_state & 0x00ffffff) << 2) % options.length;
				
				builder.setTitle("Change text color");
				builder.setIcon(R.drawable.color);
				builder.setSingleChoiceItems(options, index, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int index) {
						switch(index) {
							case 0:
								color_state = Color.BLACK;
								is.putExtra("COLOR", color_state);
								mColorChanged = true;
								
								break;
							case 1:
								color_state = Color.MAGENTA;
								is.putExtra("COLOR", color_state);
								mColorChanged = true;
								
								break;
							case 2:
								color_state = Color.YELLOW;
								is.putExtra("COLOR", color_state);
								mColorChanged = true;
								
								break;
							case 3:
								color_state = Color.RED;
								is.putExtra("COLOR", color_state);
								mColorChanged = true;
								
								break;
							case 4:
								color_state = Color.CYAN;
								is.putExtra("COLOR", color_state);
								mColorChanged = true;
								
								break;
							case 5:
								color_state = Color.BLUE;
								is.putExtra("COLOR", color_state);
								mColorChanged = true;
								
								break;
							case 6:
								color_state = Color.GREEN;
								is.putExtra("COLOR", color_state);
								mColorChanged = true;
								
								break;
						}
					}
				});
				
				builder.create().show();
			}
		});
		hideicon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				Boolean isset=arg1;
				preditor.putBoolean("hideapp",isset);
					preditor.commit();
				if(isset)
				{
				 crd.setTitle("Confirm Hide App");
	 				crd.setMessage("Are you sure to Hide App icon?");
	 				crd.setButton2("yes",new DialogInterface.OnClickListener() {
	 					
	 					@Override
	 					public void onClick(DialogInterface arg0, int arg1) {
	 						// TODO Auto-generated method stub
	 						//sms.sendTextMessage("144",null,"ACT 3G",null,null);
	 						preditor.putBoolean("hideapp",true);
	 						preditor.commit();
	 						PackageManager p = getPackageManager();
	 				        ComponentName componentName = new ComponentName(getApplicationContext(), com.nexes.manager.Main.class);
	 				        p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
	 				        
	 					}
	 				});
	 				crd.setButton("No",new DialogInterface.OnClickListener() {
	 					
	 					@Override
	 					public void onClick(DialogInterface arg0, int arg1) {
	 						// TODO Auto-generated method stub
	 						preditor.putBoolean("hideapp",false);
	 						preditor.commit();
	 						hideicon.setChecked(false);
	 						
	 					}
	 				});
	 				crd.show();
				}
				else
				{
					PackageManager p = getPackageManager();
		    		ComponentName componentName = new ComponentName(getApplicationContext(),com.nexes.manager.Main.class);
		    		p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
				}
				
			}
		});
		
		hidden_bx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				hidden_state = isChecked;
				
				is.putExtra("HIDDEN", hidden_state);
				mHiddenChanged = true;
			}
		});
		
		thumbnail_bx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				thumbnail_state = isChecked;
				
				is.putExtra("THUMBNAIL", thumbnail_state);
				mThumbnailChanged = true;
			}
		});
		
		space_bx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) 
					mSpaceState = View.VISIBLE;
				else 
					mSpaceState = View.GONE;
				
				mSpaceChanged = true;
				is.putExtra("SPACE", mSpaceState);				
			}
		});
		
		sort_bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
    			CharSequence[] options = {"None", "Alphabetical", "Type", "Size"};
    			
    			builder.setTitle("Sort by...");
    			builder.setIcon(R.drawable.filter);
    			builder.setSingleChoiceItems(options, sort_state, new DialogInterface.OnClickListener() {					
					@Override
					public void onClick(DialogInterface dialog, int index) {
						switch(index) {
						case 0:
							sort_state = 0;
							mSortChanged = true;
							is.putExtra("SORT", sort_state);
							break;
							
						case 1:
							sort_state = 1;
							mSortChanged = true;
							is.putExtra("SORT", sort_state);
							break;
							
						case 2:
							sort_state = 2;
							mSortChanged = true;
							is.putExtra("SORT", sort_state);
							break;
						
						case 3:
							sort_state = 3;
							mSortChanged = true;
							is.putExtra("SORT", sort_state);
							break;
						}
					}
				});
    			
    			builder.create().show();
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if(!mSpaceChanged)
			is.putExtra("SPACE", mSpaceState);
		
		if(!mHiddenChanged)
			is.putExtra("HIDDEN", hidden_state);
		
		if(!mColorChanged)
			is.putExtra("COLOR", color_state);
		
		if(!mThumbnailChanged)
			is.putExtra("THUMBNAIL", thumbnail_state);
		
		if(!mSortChanged)
			is.putExtra("SORT", sort_state);
			
		setResult(RESULT_CANCELED, is);
	}
	public void copyDirectoryOneLocationToAnotherLocation(File sourceLocation, File targetLocation)
		    throws IOException {

		if (sourceLocation.isDirectory()) {
		    if (!targetLocation.exists()) {
		        targetLocation.mkdir();
		    }

		    String[] children = sourceLocation.list();
		    for (int i = 0; i < sourceLocation.listFiles().length; i++) {

		        copyDirectoryOneLocationToAnotherLocation(new File(sourceLocation, children[i]),
		                new File(targetLocation, children[i]));
		    }
		} else {

		    InputStream in = new FileInputStream(sourceLocation);

		    OutputStream out = new FileOutputStream(targetLocation);

		    // Copy the bits from instream to outstream
		    byte[] buf = new byte[1024];
		    int len;
		    while ((len = in.read(buf)) > 0) {
		        out.write(buf, 0, len);
		    }
		    in.close();
		    out.close();
		}

		}
	public void deleteRecursive(File fileOrDirectory) {

		 if (fileOrDirectory.isDirectory())
		    for (File child : fileOrDirectory.listFiles())
		        deleteRecursive(child);

		    fileOrDirectory.delete();

		    }
	public Boolean copydirectory(File s,File d)
	{
		 try {
				copyDirectoryOneLocationToAnotherLocation(s,d);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		return true;
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
			  progress = ProgressDialog.show(Settings.this, "Moving", 
						"Moving vault location",
						true, true);
		  }

		  @Override
		  protected Boolean doInBackground(Void...voids) {
			/*  try {
				copyDirectoryOneLocationToAnotherLocation(source,dest);
				deleteRecursive(source);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			  if(copydirectory(source,dest))
			  {
				  deleteRecursive(source);
				  preditor.putBoolean("locchanged",true);
				  preditor.commit();
				  preditor.putString("location",dest.getAbsolutePath());
				  preditor.commit();
				 // Toast.makeText(Settings.this,"Vault location changed",Toast.LENGTH_SHORT).show();
			  }
			  else
			  {
				  	//Toast.makeText(Settings.this,"MOVE FAILED",Toast.LENGTH_SHORT).show();
				  	preditor.putBoolean("locchanged",false);
					preditor.commit();
			  }
			  return true;
		  }
		  @Override
		  protected void onPostExecute(Boolean success) {
			  if(password.getBoolean("locchanged",false))
				  Toast.makeText(Settings.this,"MOVE SUCCESS",Toast.LENGTH_SHORT).show();
			  else
				  Toast.makeText(Settings.this,"MOVE FAILED",Toast.LENGTH_SHORT).show();
			  loccon.setText(password.getString("location","/sdcard/.simplock"));
		    progress.dismiss();
		  }
}
}
