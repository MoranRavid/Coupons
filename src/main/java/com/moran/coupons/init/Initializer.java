package com.moran.coupons.init;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moran.coupons.logic.CouponsController;
import com.moran.coupons.utils.ExpiryCouponsRemoveTask;



@Component
public class Initializer {

	@Autowired
	private CouponsController couponsController;

	@PostConstruct
	public void init() {
		//	clean up task

		TimerTask removeExpiryTask = new ExpiryCouponsRemoveTask(couponsController);
		Timer timer = new Timer();

		Calendar timeToRemoveExpiredCoupons = ExpiryCouponsRemoveTask.getTimeToRemoveExpCoupon();
		long dayInMilliseconds = ExpiryCouponsRemoveTask.getDayInMilSec();

		timer.schedule(removeExpiryTask, timeToRemoveExpiredCoupons.getTime(), dayInMilliseconds);
	}
}
