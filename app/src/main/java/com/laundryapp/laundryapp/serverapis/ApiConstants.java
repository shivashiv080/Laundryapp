package com.laundryapp.laundryapp.serverapis;

/**
 * Created by RuchiTiwari on 1/14/2017.
 */

public class ApiConstants
{
	public static final String appVersion = "1.0.0";
	public static String osVersion = "";

	private static final String BASE_URL = "http://findhostels.in/webservices/";

	public static final String loginUrl = BASE_URL + "hostel_login.php";

	public static final String BedAvailabilityUrl = BASE_URL + "beds_availability.php";

	public static final String ChangePasswordUrl = BASE_URL + "changepassword.php";
	public static final String BedPriceUrl = BASE_URL + "bed_price.php";
	public static final String EditBedPriceUrl = BASE_URL + "edit_price.php";
	public static final String FoodUrl = BASE_URL + "edit_menu.php";
	public static final String FacilitiesUrl = BASE_URL + "edit_facilities.php";
	public static final String DeleteHostelUrl = BASE_URL + "delete_hostel.php";
	public static final String EditHostelUrl = BASE_URL + "edit_hostel.php";
	public static final String EditHostelNameUrl = BASE_URL + "edit_hostel.php";

	public static final String UPDATE_AVALABLE_SHARING_BEDS_URL = BASE_URL + "beds_availability.php";

	public static final String UPDATE_AVALABLE_FOOD_MENU_URL = BASE_URL + "edit_menu.php";
	/*
http://findhostels.in/webservices/beds_availability.php*/

}
