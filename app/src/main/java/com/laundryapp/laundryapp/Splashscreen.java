package com.laundryapp.laundryapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;



public class Splashscreen extends AppCompatActivity
{
	// Splash screen timer
	public static int SPLASH_TIME_OUT = 3000;
	public Context mContext;
	TextView apptitle;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		apptitle = (TextView) findViewById(R.id.apptitle);
		mContext = Splashscreen.this;
		try
		{
			/*ActionBar actionBar = getSupportActionBar();
			actionBar.hide();*/
			apptitle.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Semibold.ttf"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}


		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
//				String hostel_id = SharedPrefsUtil.getStringPreference(mContext, "hostel_id");
				/*if (hostel_id != null && !hostel_id.isEmpty())
				{*/
				Intent i = new Intent(Splashscreen.this, MainActivity.class);
				startActivity(i);
//				}
				/*else
				{
					Intent next = new Intent(Splashscreen.this, LoginActivity.class);
					startActivity(next);
				}*/
				finish();


			}
		}, SPLASH_TIME_OUT);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
	}


}


