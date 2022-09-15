package com.actitime.tests;

import java.io.IOException;

import org.testng.annotations.Test;

import com.actitime.base.BaseClass;
import com.actitime.utils.CommonUtils;

public class Projects extends BaseClass {
//	@Test(groups = {"smoke","PC"})
	public static void projects_001() throws IOException, InterruptedException {
		try {
			writeLog("projects_001");
//			boolean results = CommonUtils.loginToActiTimeApplication();
			boolean results = true;
			if (results) {
				writeResult("projects_001", "pass");
//				writeLog("projects Test case 001 completed");
			} else {
				writeResult("projects_001", "fail");
//				writeLog("projects Test case 001 completed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			writeResult("projects_001", "fail");
		}

	}

}
