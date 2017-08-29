package com.laundryapp.laundryapp.serverapis;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RestApiCallUtil
{
	public interface VolleyCallback
	{
		void onSuccessResponse(ApiRequestReferralCode referralCode, String response_info, JSONArray jsonArray, JSONObject jobject);
	}

	public static ProgressDialog mProgressDialog;

	public static void postServerResponse(final Context context,
	                                      final ApiRequestReferralCode api,
	                                      final Map<String, String> passedparams)
	{
		try
		{
			if (isOnline(context))
			{
				RequestQueue queue = Volley.newRequestQueue(context);
				showProgressDialog(context);
				String url = "";
				switch (api)
				{
					case LOGIN:
						url = ApiConstants.loginUrl;
						break;
					case BOOK_LAUNDRY_SUBMIT:
						url = ApiConstants.loginUrl;
						break;
					case RATE_CARD_SUBMIT:
						url = ApiConstants.loginUrl;
						break;
					case TRACK_ORDER_SUBMIT:
						url = ApiConstants.loginUrl;
						break;

				}
				Log.e("url", url + "");
				StringRequest jsonObjRequest = new StringRequest
						(Request.Method.POST, url, new Response.Listener<String>()
						{
							@Override
							public void onResponse(String response)
							{
								try
								{
									hideProgressDialog();

									JSONObject jObj = null;
									JSONArray jsonArraydata = null;
									int response_info = 0;
									Log.e("response", response.toString());

									if (response != null)
									{
										jObj = new JSONObject(response);
										jObj = jObj.getJSONObject("response");
										jsonArraydata = new JSONArray();
										response_info = jObj.getInt("response_info");
										if (response_info == 1)
										{
											if (jObj.getJSONArray("response_data") != null)
											{
												jsonArraydata = jObj.getJSONArray("response_data");
												((VolleyCallback) context).onSuccessResponse(api, String.valueOf(response_info), jsonArraydata, jObj);
											}
										}
										else
										{
											((VolleyCallback) context).onSuccessResponse(api, String.valueOf(response_info), jsonArraydata, jObj);
										}
									}
									else
									{
										NeResponseError(context);
									}


								}
								catch (Exception e)
								{
									hideProgressDialog();
								}

							}
						},
						 new Response.ErrorListener()
						 {
							 @Override
							 public void onErrorResponse(VolleyError error)
							 {
								 hideProgressDialog();
								 Toast.makeText(context, "we had some server issue..", Toast.LENGTH_SHORT).show();
							 }
						 })
				{
					@Override
					protected Map<String, String> getParams()
					{
						Log.e("params", passedparams + "");
						return passedparams;
					}

					@Override
					public Map<String, String> getHeaders() throws AuthFailureError
					{
						Map<String, String> params = new HashMap<String, String>();
						params.put("Content-Type", "application/x-www-form-urlencoded");
						return params;
					}
				};
				// Add the request to the RequestQueue.
				queue.add(jsonObjRequest);
			}
			else
			{
				NetworkError(context);
			}
		}
		catch (Exception e)
		{
			hideProgressDialog();
		}
	}


	public static void showProgressDialog(Context context)
	{

		try
		{
			hideProgressDialog();
			mProgressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
			mProgressDialog.setMessage("Please Wait...");
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.setCancelable(false);
			mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
			                          new DialogInterface.OnClickListener()
			                          {
				                          @Override
				                          public void onClick(DialogInterface dialog, int which)
				                          {
					                          dialog.dismiss();
				                          }
			                          });
			mProgressDialog.show();
		}
		catch (Exception e)
		{

		}
	}

	public static void hideProgressDialog()
	{
		try
		{
			if (mProgressDialog != null)
			{
				mProgressDialog.dismiss();
			}
		}
		catch (Exception e)
		{

		}
	}


	/**
	 * Check Online Connectivity
	 */
	public static boolean isOnline(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnectedOrConnecting();
	}
//

	/**
	 * Display error in case no net connection
	 */

	public static void NetworkError(Context context)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		builder.setTitle("No Network")
		       .setMessage("Please check the internet connection")
		       .setCancelable(false)
		       .setPositiveButton("OK", new DialogInterface.OnClickListener()
		       {
			       @Override
			       public void onClick(DialogInterface dialog, int id)
			       {
				       dialog.dismiss();

			       }
		       });
		alert = builder.create();
		alert.show();

	}

	/**
	 * Display error in case no net connection
	 */
	public static AlertDialog alert;

	public static void NeResponseError(Context context)
	{

		try
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(
					context, AlertDialog.THEME_HOLO_LIGHT);
			builder.setTitle("No Data")

			       .setCancelable(false)
			       .setPositiveButton("OK", new DialogInterface.OnClickListener()
			       {
				       @Override
				       public void onClick(DialogInterface dialog, int id)
				       {
					       dialog.dismiss();

				       }
			       });
			if (builder != null)
			{
				builder.create().show();
			}

		}
		catch (Exception e)
		{

		}
	}
}
