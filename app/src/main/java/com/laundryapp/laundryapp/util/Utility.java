package com.laundryapp.laundryapp.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;

import butterknife.ButterKnife;


public class Utility
{
	public static int screenHeight;
	public static int screenWidth;
	public static NetworkInfo netInfo;
	public static String CURRENT_SUB_CAT_TITLE = "";
	public static final String FETCH_DIRECTION_UP = "up";

	public static int tabsPost = -1;

	public static String COOKIE = "";

	public static String ROLE = "";

	public static int USER_ID;

	public static String bookingDate = "";
	public static String bookingPickedSlotTime = "";
	public static String bookingCourtID = "";

	public static int VALUE_POSITION = -1;
	public static String VALUE_isAddedToCart = "";
	public static String PickedClubName = "";
	public static String PickedCourtName = "";
	public static String pickedSportName = "";
	public static String PickedDate = "";
	public static String PickedSlotTime = "";
	public static String address = "";
	public static String latLng = "";
	public static int pickedCourtID = -1;
	public static String location = "";
	public static String city = "";
	public static String availability = "";
	public static String number = "";
	public static String slotPrice = "";
	public static String VALUE_convenience = "";
	public static String VALUE_sclabel = "";

	public static String KEY_PHONE_NUM = "PHONE_NUM";
	public static String KEY_NAME = "NAME";
	public static String KEY_EMAIL_ID = "EMAIL_ID";
	public static String KEY_REFERRAL_CODE = "REFERRAL_CODE";
	public static String KEY_NONCE = "NONCE";
	public static String KEY_COMING_FROM = "COMING_FROM";

	public static String KEY_SINGLE_SPORT_ALL_SLOTS_URL = "Sport_All_Slots_URL";
	public static String KEY_SINGLE_SPORT_SINGLE_SLOTS_URL = "Sport_Single_Slots_URL";
	public static String KEY_SPORT_NAME = "Sport_Name";
	public static String KEY_SLUG = "slug";

	public static String KEY_Confirm = "Confirm";
	public static String KEY_pickedPosition = "position";
	public static String KEY_PickedCourtName = "PickedCourtName";
	public static String KEY_pickedClubName = "pickedClubName";
	public static String KEY_pickedClubID = "pickedClubID";
	public static String KEY_address = "address";
	public static String KEY_phone = "phone";
	public static String KEY_latlng = "latlng";
	public static String KEY_location = "location";
	public static String KEY_city = "city";
	public static String KEY_pickedDate = "pickedDate";
	public static String KEY_pickedCourtID = "pickedCourtID";
	public static String KEY_price_interval = "price_interval";
	public static String KEY_PickedSlotTime = "PickedSlotTime";
	public static String KEY_convenience = "convenience";
	public static String KEY_sclabel = "sclabel";

	public static String KEY_CART_LIST_JSON = "CART_JSON";

	public static String ORDER_ID = "";

	public static String KEY_FROM_TO_CART = "KEY_FROM_TO_CART";

	public static String DASHBOARD_ICON_ID = "DASHBOARD_ICON_ID";

	public static String SPINNER_ITEM_SELECTED = "AVAILABLE_SPINNER";


	public static boolean isOnline(Context _Context)
	{
		ConnectivityManager cm = (ConnectivityManager) _Context.getSystemService(Context.CONNECTIVITY_SERVICE);
		netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting())
		{
			return true;
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public static void setDimensions(Context _context)
	{
		try
		{
			Display display = ((Activity) _context).getWindowManager().getDefaultDisplay();
			int screenWidth = display.getWidth();
			Utility.screenWidth = screenWidth;
			int screenHeight = display.getHeight();
			Utility.screenHeight = screenHeight;

			//Utility.font_light = Typeface.createFromAsset(_context.getAssets(), "roboto_light.ttf");
			//Utility.font_reg = Typeface.createFromAsset(_context.getAssets(), "roboto_regular.ttf");
			//Utility.font_bold = Typeface.createFromAsset(_context.getAssets(),"roboto_bold.ttf");

		}
		catch (Exception e)
		{
			if (e != null)
			{
				e.printStackTrace();
				Log.w("HARI-->DEBUG", e);
			}
		}
	}

	public static Bitmap getRoundCornerBitmap(Bitmap bitmap, int radius)
	{
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		final RectF rectF = new RectF(0, 0, w, h);

		canvas.drawRoundRect(rectF, radius, radius, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, null, rectF, paint);

		/**
		 * here to define your corners, this is for left bottom and right bottom corners
		 */
		final Rect clipRect = new Rect(h, w - radius, 0, 0);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
		canvas.drawRect(clipRect, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, null, rectF, paint);

		bitmap.recycle();

		return output;
	}

	public static Intent hariEmailIntent(Context context, String address, String subject, String body, String cc)
	{
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_EMAIL, new String[]{address});
		intent.putExtra(Intent.EXTRA_TEXT, body);
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_CC, cc);
		intent.setType("message/rfc822");
		return intent;
	}

	public static int getScreenOrientation(Context _context)
	{
		int rotation = ((Activity) _context).getWindowManager().getDefaultDisplay().getRotation();
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) _context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		int height = dm.heightPixels;
		int orientation;
		// if the device's natural orientation is portrait:
		if ((rotation == Surface.ROTATION_0
				|| rotation == Surface.ROTATION_180) && height > width ||
				(rotation == Surface.ROTATION_90
						|| rotation == Surface.ROTATION_270) && width > height)
		{
			switch (rotation)
			{
				case Surface.ROTATION_0:
					orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
					break;
				case Surface.ROTATION_90:
					orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
					break;
				case Surface.ROTATION_180:
					orientation =
							ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
					break;
				case Surface.ROTATION_270:
					orientation =
							ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
					break;
				default:
					Log.e("HARI", "Unknown screen orientation. Defaulting to " +
							"portrait.");
					orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
					break;
			}
		}
		// if the device's natural orientation is landscape or if the device
		// is square:
		else
		{
			switch (rotation)
			{
				case Surface.ROTATION_0:
					orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
					break;
				case Surface.ROTATION_90:
					orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
					break;
				case Surface.ROTATION_180:
					orientation =
							ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
					break;
				case Surface.ROTATION_270:
					orientation =
							ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
					break;
				default:
					Log.e("HARI", "Unknown screen orientation. Defaulting to " +
							"landscape.");
					orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
					break;
			}
		}

		return orientation;
	}

	/**
	 * Obtains the LayoutInflater from the given context.
	 */
	public static LayoutInflater from_Context(Context context)
	{
		LayoutInflater layoutInflater = null;
		try
		{
			if (context != null)
			{
				layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			}

			if (layoutInflater == null)
			{
				throw new AssertionError("LayoutInflater not found.");
			}
		}
		catch (Exception e)
		{
			if (e != null)
			{
			}
			layoutInflater = null;
		}
		return layoutInflater;
	}

    /*public static void showCustomToast(String toastmsg, Activity _activity) {
        try {
            LayoutInflater inflater = Utility.from_Context(_activity);
            View layout = null;
            if (inflater != null) {
                layout = inflater.inflate(R.layout.toast_no_netowrk, (ViewGroup) _activity.findViewById(R.id.custom_toast_layout_id));
                TextView tv = (TextView) layout.findViewById(R.id.text);
                // The actual toast generated here.
                Toast toast = new Toast(_activity);
                tv.setText(toastmsg);
                toast.setDuration(Toast.LENGTH_SHORT);
                //tv.setTypeface(Utility.font_reg);
                if (layout != null) {
                    toast.setView(layout);
                    toast.show();
                }
            } else {
                Toast.makeText(_activity, "" + toastmsg, Toast.LENGTH_SHORT).show();
            }
        } catch (AssertionError e) {
            if (e != null) {
                Toast.makeText(_activity, "" + toastmsg, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            if (e != null) {
                Toast.makeText(_activity, "" + toastmsg, Toast.LENGTH_SHORT).show();
            }
        }
    }*/

	public static boolean isEmailValid(CharSequence email)
	{
		return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}


	public static ProgressDialog setProgressDailog(Context mContext)
	{
		ProgressDialog progressdailog = new ProgressDialog(mContext);
		progressdailog.setMessage("Please wait...");
		progressdailog.setIndeterminate(false);
		progressdailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressdailog.setCancelable(false);
		return progressdailog;
	}

	/*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		ButterKnife.bind(this);*/


	public static void headerTextData(Activity mContext)
	{
		ButterKnife.bind(mContext);
	}

}
