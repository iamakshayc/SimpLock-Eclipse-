package com.nexes.manager;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.simp.simplock.R;

public class Pass extends Activity {
	Button passok,passcan;
	EditText newpass,conpass;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pass);
		passok=(Button)findViewById(R.id.passok);
		passcan=(Button)findViewById(R.id.passcan);
		newpass=(EditText)findViewById(R.id.newpass);
		conpass=(EditText)findViewById(R.id.conpass);
		//final SharedPreferences password=this.getPreferences(Context.MODE_PRIVATE);
		//final SharedPreferences.Editor preditor=password.edit();
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pass, menu);
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
}
