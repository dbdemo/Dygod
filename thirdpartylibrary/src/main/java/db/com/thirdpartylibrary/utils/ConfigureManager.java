package db.com.thirdpartylibrary.utils;

import android.content.Context;

public class ConfigureManager extends AssetPropertiesHelper {

	public static final String CONFIGURE_FILE_NAME = "configure.properties";

	private static ConfigureManager sManager;

	public static synchronized ConfigureManager getInstance(Context context) {
		if (null == sManager) {
			sManager = new ConfigureManager(context.getApplicationContext());
		}
		return sManager;
	}
	
	/**
	 * must call after actual getInstance(Context context) called
	 * @return
	 */
	public static synchronized ConfigureManager getInstance() {
		return sManager;
	}

	private ConfigureManager(Context context) {
		super(context);
		load(CONFIGURE_FILE_NAME);
	}

	public String getBaseAddress() {
		return getString("BaseAddress");
	}
	public String getFileSavePath(){return getString("fileSavePath");}
}
