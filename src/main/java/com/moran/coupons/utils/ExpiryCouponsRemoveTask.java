package com.moran.coupons.utils;

import java.util.Calendar;
import java.util.TimerTask;
import com.moran.coupons.logic.CouponsController;

public class ExpiryCouponsRemoveTask extends TimerTask {

	private CouponsController couponsController;


	public ExpiryCouponsRemoveTask(CouponsController couponsController) {
		this.couponsController = couponsController;
	}


	@Override
	public void run() {
		try {
			couponsController.removeExpiredCoupons();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



	public static Calendar getTimeToRemoveExpCoupon() {
		Calendar midNight = Calendar.getInstance();

		midNight.set(Calendar.HOUR_OF_DAY, 00);
		midNight.set(Calendar.MINUTE, 00);
		midNight.set(Calendar.SECOND, 00);


		return midNight;
	}



	public static long getDayInMilSec() {
		long dayInMilliseconds = 86400000;
		return dayInMilliseconds;
	}
}
