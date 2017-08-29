package com.laundryapp.laundryapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shiva on 8/28/2017.
 */

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener
{
	Context mContext;

	@BindView(R.id.cancelBtn)
	Button cancelBtn;

	@BindView(R.id.updateBtn)
	Button updateBtn;

	@BindView(R.id.radioSex)
	RadioGroup radioSex;


	private RadioButton radioSexButton;

	@Override
	public void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_profile_actvity);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		ButterKnife.bind(this);
		mContext = ProfileActivity.this;


		cancelBtn.setOnClickListener(this);
		updateBtn.setOnClickListener(this);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				onBackPressed();
				return true;
		}
		return false;
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void onClick(final View view)
	{
		switch (view.getId())
		{
			case R.id.cancelBtn:
				Intent i = new Intent(ProfileActivity.this, MainActivity.class);
				startActivity(i);
				break;
			case R.id.updateBtn:
				int selectedId = radioSex.getCheckedRadioButtonId();
				// find the radiobutton by returned id
				radioSexButton = (RadioButton) findViewById(selectedId);
				Toast.makeText(mContext,radioSexButton.getText(), Toast.LENGTH_SHORT).show();

				Toast.makeText(mContext, "Profile Details Updates Successfully", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
				startActivity(intent);
				break;

		}
	}
}
