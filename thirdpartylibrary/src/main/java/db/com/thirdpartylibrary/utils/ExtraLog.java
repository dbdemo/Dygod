package db.com.thirdpartylibrary.utils;

import android.util.Log;

public class ExtraLog {

	public static final boolean sIsLog = true;

	public static void d(String tag, String msg) {
		if (sIsLog) {
			Log.d(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (sIsLog) {
			Log.e(tag, msg);
		}
	}

	public static void i(String tag, String msg) {
		if (sIsLog) {
			Log.i(tag, msg);
		}
	}

	public static void v(String tag, String msg) {
		if (sIsLog) {
			Log.v(tag, msg);
		}
	}

	public static void w(String tag, String msg) {
		if (sIsLog) {
			Log.w(tag, msg);
		}
	}
}
