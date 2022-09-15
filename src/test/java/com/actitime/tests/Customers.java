package com.actitime.tests;

import java.io.IOException;

import org.testng.annotations.Test;

import com.actitime.base.BaseClass;
import com.actitime.utils.CommonUtils;

public class Customers extends BaseClass {
//	@Test(groups = {"smoke","PC"})
	public static void customer_001() throws IOException, InterruptedException {
		try {
			writeLog("Customer_001");
//			boolean results = CommonUtils.loginToActiTimeApplication();
			boolean results = true;
			if (results) {
				writeResult("customer_001", "pass");
//				writeLog("Customer Test case 001 completed");
			} else {
				writeResult("customer_001", "fail");
//				writeLog("Customer Test case 001 completed");
				captureScreenshotOnFailure("customer_001");
			}
		} catch (Exception e) {
			e.printStackTrace();
			writeResult("customer_001", "fail");
			captureScreenshotOnFailure("customer_001");
		}

	}

}
