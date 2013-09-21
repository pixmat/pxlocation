package com.pixmatstudios.pxlocation;

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;

import org.appcelerator.titanium.TiApplication;
import org.appcelerator.kroll.common.Log;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

@Kroll.module(name="Pxlocation", id="com.pixmatstudios.pxlocation")
public class PxlocationModule extends KrollModule
{
	// Standard Debugging variables
	private static final String TAG = "PxlocationModule";
	
	public PxlocationModule()
	{
		super();
	}

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app)
	{
		Log.d(TAG, "inside onAppCreate");
		// put module init code that needs to run when the application is created
	}
	
	@Kroll.method
	public String getBestLocation() {
		Location gpslocation = getLocationByProvider(LocationManager.GPS_PROVIDER);
		Location networkLocation = getLocationByProvider(LocationManager.NETWORK_PROVIDER);

		if (gpslocation != null) {
			Log.d(TAG, "GPS is available");
			return "GPS";
		} else if (networkLocation != null) {
			Log.d(TAG, "Network is available");
			return "NETWORK";
		}
		
		Log.d(TAG, "Network and GPS are not available. Using cached.");
		return "PASSIVE";
	}

	private Location getLocationByProvider(String provider) {
		Location location = null;
		LocationManager locationManager = (LocationManager) TiApplication
				.getInstance().getApplicationContext()
				.getSystemService(Context.LOCATION_SERVICE);

		try {
			if (locationManager.isProviderEnabled(provider)) {
				location = locationManager.getLastKnownLocation(provider);
			}
		} catch (IllegalArgumentException e) {
			Log.d(TAG, "Cannot acces Provider " + provider);
		}
		return location;
	}

}

