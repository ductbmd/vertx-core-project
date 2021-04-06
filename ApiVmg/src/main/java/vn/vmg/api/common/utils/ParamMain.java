package vn.vmg.api.common.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Load input from java main args[]
 * @author VMG
 *
 */
public class ParamMain {
	private static Map<String, String> mapFlag = new HashMap<>();

	public static synchronized void onLoad(String[] args) throws IOException {
		if (args.length < 1)
			return;
		String[] spl;
		for (String s : args) {
			spl = s.split("=");
			if (spl.length != 2) {
				throw new IOException("Param input invalid. param: " + s);
			}
			mapFlag.put(ApiUtils.toStr(spl[0]), ApiUtils.toStr(spl[1]));
		}
	}

	public static String onCreateStr(String paramName, String defValue, boolean isRequired) {
		if (mapFlag.containsKey(paramName)) {
			return mapFlag.get(paramName);
		} else if (isRequired) {
			System.err.println("Param not exist. param: " + paramName);
			System.exit(0);
		}
		return defValue;
	}

	public static int onCreateInt(String paramName, int defValue, boolean isRequired) {
		return ApiUtils.toInt(onCreateStr(paramName, Integer.toString(defValue), isRequired));
	}

}
