package net.geekherd.airplanemode;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class settings extends Activity 
{
	public final static String TAG = "AirplaneModeSettings";
	private static final String LEAVE_WIFI_ON = "cell,bluetooth";
	private static final String LEAVE_BT_ON = "cell,wifi";
	private static final String LEAVE_BOTH_ON = "cell";
	private static final String AIRPLANE_REGULAR = "cell,bluetooth,wifi";
	CheckBox checkbox_wifi;
	CheckBox checkbox_bt;
	Button button_save;

    @Override
    public void onCreate(Bundle icicle) 
    {
        super.onCreate(icicle);
        setContentView(R.layout.main);
        
        Log.d(TAG, "Airplane Mode Radios: " + 
        		Settings.System.getString(getContentResolver(), 
        		Settings.System.AIRPLANE_MODE_RADIOS));
        
        checkbox_wifi = (CheckBox) findViewById(R.id.checkbox_wifi);
        checkbox_bt = (CheckBox) findViewById(R.id.checkbox_bt);
        checkbox_wifi.setChecked(checkWifiActiveAirplaneMode());
        checkbox_bt.setChecked(checkBTActiveAirplaneMode());
        
        button_save = (Button) findViewById(R.id.btn_save);
        button_save.setOnClickListener(new OnClickListener() 
        {
        	public void onClick(View v) {
        		
        		if (checkbox_wifi.isChecked() && checkbox_bt.isChecked())
        			Settings.System.putString(getContentResolver(), Settings.System.AIRPLANE_MODE_RADIOS, LEAVE_BOTH_ON);
        		else if (checkbox_wifi.isChecked() && !checkbox_bt.isChecked())
        			Settings.System.putString(getContentResolver(), Settings.System.AIRPLANE_MODE_RADIOS, LEAVE_WIFI_ON);
        		else if (!checkbox_wifi.isChecked() && checkbox_bt.isChecked())
        			Settings.System.putString(getContentResolver(), Settings.System.AIRPLANE_MODE_RADIOS, LEAVE_BT_ON);
        		else
        			Settings.System.putString(getContentResolver(), Settings.System.AIRPLANE_MODE_RADIOS, AIRPLANE_REGULAR);
        		
        		
        		Toast.makeText(getBaseContext(), "Settings saved successfully", Toast.LENGTH_LONG).show();
        		finish();
        	}
        });
    }

	private boolean checkWifiActiveAirplaneMode()
	{
		String[] modifiedRadios = null;
		boolean deactivate = true;
		String airplaneSettings = Settings.System.getString(getContentResolver(), Settings.System.AIRPLANE_MODE_RADIOS);
		
		modifiedRadios = airplaneSettings.split(",");
		
		for(int i = 0; i < modifiedRadios.length; i++){
			if (modifiedRadios[i].equals("wifi"))
				deactivate = false;
		}
		
		return deactivate;
	}
	
	private boolean checkBTActiveAirplaneMode()
	{
		String[] modifiedRadios = null;
		boolean deactivate = true;
		String airplaneSettings = Settings.System.getString(getContentResolver(), Settings.System.AIRPLANE_MODE_RADIOS);
		
		modifiedRadios = airplaneSettings.split(",");
		
		for(int i = 0; i < modifiedRadios.length; i++){
			if (modifiedRadios[i].equals("bluetooth"))
				deactivate = false;
		}
		
		return deactivate;
	}
	
	
	@Override 
    public boolean onCreateOptionsMenu(Menu menu) 
    { 
	    super.onCreateOptionsMenu(menu); 
	    MenuInflater inflater = getMenuInflater(); 
	    inflater.inflate(R.menu.menu, menu); 
	    return true; 
    } 
    
    public void onClick(View v) 
    { 
		switch (v.getId()) 
		{ 
			
			case R.id.menu_about: 
				startActivity(new Intent(this, about.class)); 
				break;

		} 
	}
    
    @Override 
    public boolean onOptionsItemSelected(MenuItem item) 
    { 
    	switch (item.getItemId()) 
    	{ 
    	
    		case R.id.menu_about:
    			startActivity(new Intent(this, about.class)); 
    			return true; 

    	} 
    	return false; 
   	}
	
}

