package vn.vmg.api.common.utils;

import io.vertx.core.http.HttpServerRequest;
import vn.vmg.api.enumcfg.DateConst;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ApiUtils {
	public static boolean isNullOrEmpty(Object obj) {
		return (obj == null || obj.toString() == null || obj.toString().trim().isEmpty());
	}

	public static String clobToStr(Object obj) throws SQLException {
		Clob objClob = (Clob) obj;
		return obj == null ? "" : objClob.getSubString(1, (int) objClob.length());
	}

	public static String toStr(Object obj) {
		return obj == null || obj.toString() == null ? "" : obj.toString().trim();
	}

	public static int toInt(Object input, int... def) {
		String num = toStr(input);
		if (num.equals("true")) {
			return 1;
		}

		if (num.equals("false")) {
			return 0;
		}

		if (num == null || !num.matches("^[-]?\\d+$")) {
			return def.length > 0 ? def[0] : 0;
		}
		return Integer.parseInt(num);
	}

	public static Long toLongObj(Object input, Long def) {
		if (input == null)
			return def;

		String num = toStr(input);

		if (!num.matches("^([-]?\\d+)|(true)|(false)$"))
			return def;

		return Long.valueOf(toLong(input, 0));
	}

	public static long toLong(Object input, long def) {
		String num = toStr(input);
		if (num.equals("true")) {
			return 1;
		}

		if (num.equals("false")) {
			return 0;
		}

		if (num == null || !num.matches("^[-]?\\d+$")) {
			return def;
		}
		return Long.parseLong(num);
	}

	public static void sleep(int sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static long getTimeHandle(long st) {
		return System.currentTimeMillis() - st;
	}

	public static String getClientIpAddr(HttpServerRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.remoteAddress().host().toString();
		}
		return ip;
	}

	public static Date toDate(String dateFormat, String dateIn) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
			return toDate(formatter, dateIn);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date toDate(SimpleDateFormat formatter, String dateIn) {
		try {
			if (isNullOrEmpty(dateIn))
				return null;
			formatter.setLenient(false);
			return formatter.parse(dateIn);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public static java.sql.Date toDateSql(SimpleDateFormat formatter, String dateIn) {
		Date dateParse = toDate(formatter, dateIn);
		return (dateParse == null ? null : (new java.sql.Date(dateParse.getTime())));
	}

	public static java.sql.Date toDateSql(String formatter, String dateIn) {
		Date dateParse = toDate(formatter, dateIn);
		return (dateParse == null ? null : (new java.sql.Date(dateParse.getTime())));
	}

	public static String writeOutException(Exception e) {
		StringWriter sw = null;
		try {
			sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			return sw.toString().replaceAll("\\t", "");
		} finally {
			try {
				if (sw != null)
					sw.close();
			} catch (Exception e2) {
			}
		}
	}

	public static Date addDateTime(Date dateIn, DateConst calChange, int addVal) {
		Calendar calTime = Calendar.getInstance();
		calTime.setTime(dateIn);
		switch (calChange) {
		case DATE:
			calTime.add(Calendar.DATE, addVal);
			break;
		case MONTH:
			calTime.add(Calendar.MONTH, addVal);
			break;
		case YEAR:
			calTime.add(Calendar.YEAR, addVal);
			break;
		case MILLISECOND:
			calTime.add(Calendar.MILLISECOND, addVal);
			break;
		}
		return calTime.getTime();
	}

	public static String dateToString(String formatter, Date dateIn) {
		if (formatter == null || dateIn == null)
			return null;
		try {
			SimpleDateFormat df = new SimpleDateFormat(formatter);
			return df.format(dateIn);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public static String changeDateTime(String dateValue, String formatIn, String formatOut, DateConst calChange,
			int addVal) {
		try {
			if (isNullOrEmpty(dateValue))
				return null;

			Date newDate = addDateTime(toDate(formatIn, dateValue), calChange, addVal);
			return dateToString(formatOut, newDate);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
}
