import java.util.Calendar;

public class Clock {

	public static int HHMMSS = 1;
	public static int HHMM = 0;


	/**
	 * 時間の取得関数
	 *
	 * @param mode HHMM:秒表示なし HHMMSS:秒表示有り
	 * @return 時刻表示 hh:mm, hh:mm:ss 形式のString値
	 */
	static String getTime(int mode) {
		Calendar now = Calendar.getInstance();

		String h = String.format("%02d", now.get(Calendar.HOUR_OF_DAY));
		String m = String.format("%02d", now.get(Calendar.MINUTE));
		String s = String.format("%02d", now.get(Calendar.SECOND));

		if (mode == HHMMSS)
			return (h + ":" + m + ":" + s);
		else if (mode == HHMM)
			return (h + ":" + m);
		else
			return "mode error";
	}

	static long getTimeNumLong() {

		return Calendar.getInstance().getTimeInMillis();
	}
}
