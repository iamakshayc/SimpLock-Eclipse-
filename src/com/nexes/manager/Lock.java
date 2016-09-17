package com.nexes.manager;




import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.simp.simplock.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Lock extends Activity implements OnClickListener{
	EditText t,p;
	Button[] bt;
	//Main k;
	InterstitialAd interstitial;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lock);
		t=(EditText)findViewById(R.id.locktext);
		p=(EditText)findViewById(R.id.locktext1);
		final SharedPreferences password=this.getSharedPreferences("simplock",Context.MODE_WORLD_READABLE);
		if(password.getString("password","4567").equals("4567"))
			p.setHint("4567");
		int[] bid={R.id.num0,R.id.num1,R.id.num2,R.id.num3,R.id.num4,R.id.num5,R.id.num6,R.id.num7,R.id.num8,R.id.num9,R.id.numback,R.id.numenter};
		bt = new Button[bid.length];
        
        for(int i = 0; i < bid.length; i++) {
        	
        	bt[i] = (Button)findViewById(bid[i]);
        	bt[i].setOnClickListener(this);
        	}
       //loadadvert();
		
		/*t.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				// TODO Auto-generated method stub
				 if ((arg2.getAction() == KeyEvent.ACTION_DOWN)&&
				            (arg1 == KeyEvent.KEYCODE_ENTER)) {
				          // Perform action on key press
				          Toast.makeText(getApplicationContext(), t.getText(), Toast.LENGTH_SHORT).show();
				          return true;
				 }
				return false;
			}
		}); */
		//t.requestFocus();
		//InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		//imm.showSoftInput(t, InputMethodManager.SHOW_IMPLICIT);
		t.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(),arg0, Toast.LENGTH_SHORT).show();
				if(arg0.toString().equals(password.getString("password","4567")))
					finish();
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});

			
	}
	public void loadadvert()
	{
		//begin ad  ca-app-pub-9164569835667595/7969967069
        interstitial = new InterstitialAd(Lock.this);
        Toast.makeText(getApplicationContext(),"ads",Toast.LENGTH_SHORT).show();
    	// Insert the Ad Unit ID
    	interstitial.setAdUnitId("ca-app-pub-9164569835667595/7969967069");

    	//Locate the Banner Ad in activity_main.xml
    	AdView adView = (AdView)findViewById(R.id.adView);
    	//AdView adView2= (AdView) this.findViewById(R.id.adView2);
    	// Request for Ads
    	AdRequest adRequest = new AdRequest.Builder()

    	// Add a test device to show Test Ads
    	.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
    	.addTestDevice("A86D0DC3D97A32BB11230CFFCAE0CF86")
    			.build();

    	// Load ads into Banner Ads
    	adView.loadAd(adRequest);
    	//adView2.loadAd(adRequest);
    	// Load ads into Interstitial Ads
    	interstitial.loadAd(adRequest);
	}
	public void displayInterstitial() {
		// If Ads are loaded, show Interstitial else show nothing.
		if (interstitial.isLoaded()) {
			interstitial.show();
		}
	}
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.num0:
		case R.id.num1:
		case R.id.num2:
		case R.id.num3:
		case R.id.num4:
		case R.id.num5:
		case R.id.num6:
		case R.id.num7:
		case R.id.num8:
		case R.id.num9:
			Button bms=(Button)findViewById(v.getId());
			t.setText(t.getText().toString()+bms.getText());
			p.setText(p.getText().toString()+"*");
			break;
		case R.id.numback:
			bms=(Button)findViewById(v.getId());
			int len=t.getText().toString().length();
			if(len!=0)
			{
				t.setText(t.getText().toString().substring(0,len-1));
				p.setText(p.getText().toString().substring(0,len-1));
			}
			break;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.lock, menu);
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
	@Override
	public void onBackPressed() {
		//Intent i = new Intent(getApplicationContext(),RechargeAppActivity.class);
		//startActivity(i);
		setResult(9);
		finish();
	}
}
