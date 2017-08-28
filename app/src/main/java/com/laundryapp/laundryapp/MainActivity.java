package com.laundryapp.laundryapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener
{
	Context mContext;

	@BindView(R.id.booklaundryBtn)
	Button booklaundryBtn;

	@BindView(R.id.trackOrderBtn)
	Button trackOrderBtn;

	@BindView(R.id.rateCardBtn)
	Button rateCardBtn;

//	Button booklaundryBtn;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		ButterKnife.bind(this);
		mContext = MainActivity.this;

		booklaundryBtn.setOnClickListener(MainActivity.this);
		trackOrderBtn.setOnClickListener(MainActivity.this);
		rateCardBtn.setOnClickListener(MainActivity.this);


		/*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
				        .setAction("Action", null).show();
			}
		});*/

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
	}

	@Override
	public void onBackPressed()
	{
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START))
		{
			drawer.closeDrawer(GravityCompat.START);
		}
		else
		{
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings)
		{
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item)
	{
		// Handle navigation view item clicks here.
		int id = item.getItemId();
		if (id == R.id.nav_profile)
		{
			Intent i = new Intent(this, ProfileActivity.class);
			startActivity(i);
			// Handle the camera action
		}
		if (id == R.id.nav_book_laundry)
		{
			Intent i = new Intent(MainActivity.this, BookLaundryActivity.class);
			startActivity(i);
			// Handle the camera action
		}
		else if (id == R.id.nav_track_order)
		{
			Intent i = new Intent(MainActivity.this, TrackOrderActivity.class);
			startActivity(i);
		}
		else if (id == R.id.nav_rate_card)
		{
			Intent i = new Intent(MainActivity.this, RateCardActivity.class);
			startActivity(i);
		}
		else if (id == R.id.nav_rate_us)
		{

		}
		else if (id == R.id.nav_share)
		{

		}
		else if (id == R.id.nav_send)
		{

		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	@Override
	public void onClick(final View view)
	{

		switch (view.getId())
		{
			case R.id.booklaundryBtn:
				Intent i = new Intent(MainActivity.this, BookLaundryActivity.class);
				startActivity(i);
				break;
			case R.id.trackOrderBtn:
				Intent in = new Intent(MainActivity.this, TrackOrderActivity.class);
				startActivity(in);
				break;
			case R.id.rateCardBtn:
				Intent intent = new Intent(MainActivity.this, RateCardActivity.class);
				startActivity(intent);
				break;
		}

	}
}
