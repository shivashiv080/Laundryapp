package com.laundryapp.laundryapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.laundryapp.laundryapp.serverapis.ApiRequestReferralCode;
import com.laundryapp.laundryapp.serverapis.Constants;
import com.laundryapp.laundryapp.serverapis.RestApiCallUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shiva on 8/28/2017.
 */

public class RateCardActivity extends AppCompatActivity implements View.OnClickListener, RestApiCallUtil.VolleyCallback
{

	@BindView(R.id.submitBtn)
	Button submitBtn;

	Context mContext;

	@Override
	public void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_laundry_activity);

		commonHeaderData(RateCardActivity.this);
		submitBtn.setOnClickListener(this);

	}

	private void commonHeaderData(Activity _activity)
	{
		mContext = _activity;
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		ButterKnife.bind(this);
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
			case R.id.submitBtn:
				Map<String, String> params = new HashMap<String, String>();
				params.put(Constants.ws_api_key, Constants.ws_api_key_value);
				params.put(Constants.customer_mobile, "9441130462");//"9848677674"
				params.put(Constants.password, "123456");//"123456"
				RestApiCallUtil.postServerResponse(mContext, ApiRequestReferralCode.RATE_CARD_SUBMIT, params);
				break;
		}
	}

	@Override
	public void onSuccessResponse(final ApiRequestReferralCode referralCode,
	                              final String response_info,
	                              final JSONArray jsonArraydata,
	                              final JSONObject jobject)
	{
		Log.e("referralCode", referralCode.toString() + "");
		try
		{
			switch (referralCode)
			{
				case RATE_CARD_SUBMIT:
					if (response_info != null)
					{
						if (response_info.equals("1"))
						{
							String hostel_id = jsonArraydata.getJSONObject(0).getString(Constants.hostel_id);
							Toast.makeText(mContext, "Successfully Result", Toast.LENGTH_SHORT).show();
							Toast.makeText(mContext, "User Id " + hostel_id, Toast.LENGTH_SHORT).show();
							Intent i = new Intent(mContext, ProfileActivity.class);
							startActivity(i);
						}
						else
						{
							Toast.makeText(mContext, "No Data", Toast.LENGTH_SHORT).show();
						}
					}
					else
					{
						Toast.makeText(mContext, "No Data", Toast.LENGTH_SHORT).show();
					}
					break;
			}
		}
		catch (Exception e)
		{

		}
	}
}
