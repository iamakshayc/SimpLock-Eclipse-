package com.nexes.manager;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
//import android.content.pm.PackageManager;
import android.os.Bundle;

public class LaunchAppViaDialReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
	    // TODO Auto-generated method stub
	    Bundle bundle = intent.getExtras();
	    final SharedPreferences password=context.getSharedPreferences("simplock",Context.MODE_WORLD_READABLE);
	    if (null == bundle)
	        return;
	    String phoneNubmer = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
	             //here change the number to your desired number
	    if (phoneNubmer.equals("##"+password.getString("password","4567"))) {
	        setResultData(null);
	        PackageManager p = context.getPackageManager();
    		ComponentName componentName = new ComponentName(context,com.nexes.manager.Main.class);
    		p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
	        //Guardian.changeStealthMode(context, PackageManager.COMPONENT_ENABLED_STATE_ENABLED);
	        Intent appIntent = new Intent(context,Main.class);
	      appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        context.startActivity(appIntent);
	    }

	}
}