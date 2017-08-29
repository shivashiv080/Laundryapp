package com.laundryapp.laundryapp.serverapis;

/**
 * Referral int for url call
 * <p>
 * Created by Shiva on 30/08/2017.
 */
public enum ApiRequestReferralCode
{

	LOGIN(1),
	BOOK_LAUNDRY_SUBMIT(2),
	RATE_CARD_SUBMIT(3),
	TRACK_ORDER_SUBMIT(4);

	private int code;

	ApiRequestReferralCode(int code)
	{
		this.code = code;
	}

	public int getCode()
	{
		return code;
	}

	@Override
	public String toString()
	{
		return code + "";
	}
}
