package com.iexceed.importupdater.shutdown;

public class AppShutDownUtil {

	public static void shutDown(Integer status) {
		System.out.println("----------------------------------");
		System.out.println("Application terminated");
		System.out.println("----------------------------------");
		if (status == null) {
			status = 0;
		}

		System.exit(status);
	}

}
