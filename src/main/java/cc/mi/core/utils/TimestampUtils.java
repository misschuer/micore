package cc.mi.core.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimestampUtils {
	public static final String DATETIME_FORMAT = "%04d-%02d-%02d %02d:%02d:%02d";
	public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String QUARTZ_GAME_FORMAT = "%d %d %d %d %d ? %d";
	public static final String QUARTZ_ARENA_FORMAT = "0 0 %d ? * *";
	public static final String QUARTZ_COMPETITION_FORMAT = "0 0 %d ? * MON";
	public static final int ONE_DAY = 24 * 3600;
	
	public static void main(String[] args) {
		System.out.println(toQuartzTimePattern(TimestampUtils.nowInMillis()));
	}
	
	public static String toQuartzTimePattern(long timestamp) {
		Calendar calendar = toCalendar(timestamp);
        try {
        	return String.format(QUARTZ_GAME_FORMAT,
        						calendar.get(Calendar.SECOND),
        						calendar.get(Calendar.MINUTE),
        						calendar.get(Calendar.HOUR_OF_DAY),
        						calendar.get(Calendar.DATE),
        						calendar.get(Calendar.MONTH)+1,
        						calendar.get(Calendar.YEAR));
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        return null;
	}
	
	public static int tomorrowTimestamp() {
		return nextTimestamp(1);
	}

	public static int nextTimestamp(int days) {
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.DATE, days);

		return (int) (calendar.getTimeInMillis() / 1000);
	}
	
	public static int nextMondayTimestamp() {
		return nextWeekTimestamp(2);
	}
	
	/**
	 * @param week: 1为sunday, 7为saturday
	 * @return 下周几的时间戳
	 **/
	public static int nextWeekTimestamp(int week) {
		Calendar calendar = Calendar.getInstance();
		int date = calendar.get(Calendar.DAY_OF_WEEK);
		int days = (week + 7 - date) % 7;
		if (days == 0) days = 7;
		return nextTimestamp(days);
	}

	public static int now() {
		return (int) (nowInMillis() / 1000);
	}
	
	public static long nowInMillis() {
		return System.currentTimeMillis();
	}

	public static int todayTimestamp() {
		return nextTimestamp(0);
	}
	
	private static String datetimeString(Calendar calendar) {
		String str = String.format(DATETIME_FORMAT,
				calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DATE), 
				calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
		return str;
	}
	
	public static int getTodaySeconds() {
		Calendar calendar = Calendar.getInstance();
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);
		
		return hours * 3600 + minutes * 60 + seconds;
	}
	
	public static int H_M_ToSeconds(String str) {
		String[] args = str.split(":");
		int hours = Integer.parseInt(args[ 0 ]);
		int minutes = Integer.parseInt(args[ 1 ]);
		
		return hours * 3600 + minutes * 60;
	}
	
	public static String standardDatetime(long timestamp) {
		Calendar calendar = toCalendar(timestamp);
		return datetimeString(calendar);
	}
	
	public static String standardDatetime(int seconds) {
		long timestamp = 1000L * seconds;
		return standardDatetime(timestamp);
	}
	
	private static Calendar toCalendar(long timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp);
		
		return calendar;
	}
	
	public static String getYMD() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return year + "" + month + "" + day;
	}
	
	public static long str2Timestamp(String datetime) {
        SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
        try {
        	Date date = sdf.parse(datetime);
        	return date.getTime();
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        return 0;
	}
}
